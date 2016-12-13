/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.dtw.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPurchaseOrder;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPurchaseOrderItem;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrder;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushPurchaseOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
public class DtwPurchaseHandlerTest extends DtwTestBase {

    @Autowired
    private DtwPuchaseHandler dtwPurchaseHandler;

    @Test
    public void testPurchaseOrderPush() {
        DtwPurchaseOrder dtwPurchaseOrder = new DtwPurchaseOrder();
        dtwPurchaseOrder.setPassKey(mockDtwSysData.getPassKey());
        dtwPurchaseOrder.setMsgId("201611232562");
        dtwPurchaseOrder.setSupplier("0000000000000");
        dtwPurchaseOrder.setECommerceCode("330196T018");
        dtwPurchaseOrder.setECommerceName("杭州美伴网络科技有限公司");
        dtwPurchaseOrder.setHawb("22222222222");
        dtwPurchaseOrder.setMawb("11111111111");

        List<DtwPurchaseOrderItem> dtwPurchaseOrderItems = new ArrayList<>();
        DtwPurchaseOrderItem dtwPurchaseOrderItem = new DtwPurchaseOrderItem();
        dtwPurchaseOrderItem.setMsgItem(1);
        dtwPurchaseOrderItem.setPartNo("1090-0909-09767");
        dtwPurchaseOrderItem.setPartName("测试商品");
        dtwPurchaseOrderItem.setSpec("无规格");
        dtwPurchaseOrderItem.setInvoiceNo("10908080");
        dtwPurchaseOrderItem.setHsCode("HS-101010");
        dtwPurchaseOrderItem.setBatch("1");
        dtwPurchaseOrderItem.setQty(3);
        dtwPurchaseOrderItem.setUnit("111");
        dtwPurchaseOrderItem.setDref1("");
        dtwPurchaseOrderItem.setDref2("");
        dtwPurchaseOrderItem.setDref3("");
        dtwPurchaseOrderItem.setDref4("");
        dtwPurchaseOrderItem.setCurrency("146");
        dtwPurchaseOrderItem.setOriginCountry("304");
        dtwPurchaseOrderItem.setAmount(100);
        dtwPurchaseOrderItems.add(dtwPurchaseOrderItem);

        dtwPurchaseOrder.setDtwPurchaseOrderItems(dtwPurchaseOrderItems);

        EventResult eventResult = dtwPurchaseHandler.purchaseOrderPush(dtwPurchaseOrder, mockDtwSysData);
        System.out.println("\n*********************");
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println("\n*********************");
    }

    @Test
    @Rollback(value = false)
    public void testPushPurchaseOrder() {

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setReceiveNo(SerialNo.create());
        purchaseOrder.setBolNo("111111111111");
        purchaseOrder.setSupplierId("datian");

        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setProductBn("1908-0980-0102");
        purchaseOrderItem.setProductName("测试商品");
        purchaseOrderItem.setStandard("测试规格");
        purchaseOrderItem.setInvoiceNo("190909090");
        purchaseOrderItem.setGoodsBn("HS-101010");
        purchaseOrderItem.setQty(10);
        purchaseOrderItem.setUnit("件");
        purchaseOrderItem.setOriginCountry("304");
        purchaseOrderItem.setAmount(100);
        purchaseOrderItems.add(purchaseOrderItem);

        purchaseOrder.setItems(purchaseOrderItems);

        PushPurchaseOrderEvent pushPurchaseOrderEvent = new PushPurchaseOrderEvent();
        pushPurchaseOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushPurchaseOrderEvent.setErpInfo(mockErpInfo);
        pushPurchaseOrderEvent.setPurchaseOrderJson(JSON.toJSONString(purchaseOrder));

        dtwPurchaseHandler.pushPurchaseOrder(pushPurchaseOrderEvent);
    }
}
