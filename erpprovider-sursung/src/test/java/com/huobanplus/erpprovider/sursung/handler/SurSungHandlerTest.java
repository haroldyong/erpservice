/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.SurSungTestBase;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungReturnRefund;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungReturnRefundItem;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungHandlerTest extends SurSungTestBase {

    private String orderInfoJson = "{\"orderId\":\"2016082966169445\",\"memberId\":17423,\"userLoginName\":\"15868807873\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":3500.000,\"orderName\":\"??????2(??,42?)(7)(?7)\",\"itemNum\":7,\"lastUpdateTime\":\"2016-08-29 17:09:49\",\"createTime\":\"2016-08-29 17:09:49\",\"shipName\":\"???\",\"shipArea\":\"???/???/???\",\"province\":\"???\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"zhe\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15868807873\",\"costItem\":0.700,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.700,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-08-29 17:09:49\",\"unionOrderId\":\"2016082976823811\",\"receiveStatus\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"orderItems\":[{\"itemId\":14701,\"orderId\":\"2016082966169442\",\"unionOrderId\":\"2016082976823811\",\"productBn\":\"CSXJ0005\",\"name\":\"??????2(??,42?)(7)\",\"cost\":5.000,\"price\":0.100,\"amount\":0.700,\"num\":7,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"0402210000\",\"standard\":\"??,42?\",\"brief\":null,\"shipStatus\":0}]}";

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    @Test
    public void testPushOrder() {
        Order order = JSON.parseObject(orderInfoJson, Order.class);

        String orderInfoJson = JSON.toJSONString(mockOrder);
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(orderInfoJson);
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);
        EventResult eventResult = surSungOrderHandler.pushOrder(pushNewOrderEvent);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testLogisticSearch() {
        SurSungLogisticSearch surSungLogisticSearch = new SurSungLogisticSearch();
        EventResult eventResult = surSungOrderHandler.logisticSearch(surSungLogisticSearch, mockSurSungSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testReturnRefund() {

        List<SurSungReturnRefundItem> surSungReturnRefundItems = new ArrayList<>();
        SurSungReturnRefundItem surSungReturnRefundItem = new SurSungReturnRefundItem();
        surSungReturnRefundItem.setOuterOiId("0987654321");
        surSungReturnRefundItem.setSkuId("123");
        surSungReturnRefundItem.setQty(2);
        surSungReturnRefundItem.setAmount(50);
        surSungReturnRefundItem.setType("退货");
        surSungReturnRefundItem.setName("奶粉");
        surSungReturnRefundItem.setPropertiesValue("红色");
        surSungReturnRefundItems.add(surSungReturnRefundItem);

        SurSungReturnRefund surSungReturnRefund = new SurSungReturnRefund();
        surSungReturnRefund.setShopId(14670);
        surSungReturnRefund.setOuterAsId("1234567890");
        surSungReturnRefund.setSoId("20160908145050387283");
        surSungReturnRefund.setType("普通退货");
        surSungReturnRefund.setLogiCompany("顺丰快递");
        surSungReturnRefund.setLogiNo("12345logino");
        surSungReturnRefund.setShopStatus("WAIT_SELLER_AGREE");
        surSungReturnRefund.setRemark("不喜欢");
        surSungReturnRefund.setGoodStatus("BUYER_RETURNED_GOODS");
        surSungReturnRefund.setQuestionType("测试");
        surSungReturnRefund.setTotalAmount(100);
        surSungReturnRefund.setRefund(100);
        surSungReturnRefund.setPayment(0);
        surSungReturnRefund.setItems(surSungReturnRefundItems);
        EventResult eventResult = surSungOrderHandler.returnRefundUpload(surSungReturnRefund, mockSurSungSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

}