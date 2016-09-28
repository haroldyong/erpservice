/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.SAPTestBase;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.SAPOrderItem;
import com.huobanplus.erpprovider.sap.formatsap.SAPSaleOrderInfo;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by elvis on 2016/4/15.
 */

public class SAPOrderHandlerTest extends SAPTestBase {

    @Autowired
    private SAPOrderHandler sapOrderHandler;


    private Order mockOrder;
    private ERPUserInfo mockErpUserInfo;
    private ERPInfo mockERP;
    private SAPSysData mockSysData;
    private PushNewOrderEvent mockPushNewOrderEvent;
    private List<OrderItem> mockOrderItemList;

    private String orderJson = "{\n" +
            "    \"orderId\": \"2016082824876289\",\n" +
            "    \"memberId\": 17423,\n" +
            "    \"userLoginName\": \"15868807873\",\n" +
            "    \"confirm\": 1,\n" +
            "    \"orderStatus\": 0,\n" +
            "    \"payStatus\": 1,\n" +
            "    \"shipStatus\": 0,\n" +
            "    \"weight\": 2500,\n" +
            "    \"orderName\": \"??????2(??,42?)(5)(?5)\",\n" +
            "    \"itemNum\": 10,\n" +
            "    \"lastUpdateTime\": \"2016-08-28 10:07:47\",\n" +
            "    \"createTime\": \"2016-08-28 10:07:47\",\n" +
            "    \"shipName\": \"???\",\n" +
            "    \"shipArea\": \"???/???/???\",\n" +
            "    \"province\": \"???\",\n" +
            "    \"city\": \"???\",\n" +
            "    \"district\": \"???\",\n" +
            "    \"shipAddr\": \"????????????????????e?\",\n" +
            "    \"shipZip\": \"\",\n" +
            "    \"shipTel\": \"\",\n" +
            "    \"shipEmail\": \"\",\n" +
            "    \"shipMobile\": \"15868807873\",\n" +
            "    \"costItem\": 107,\n" +
            "    \"onlinePayAmount\": 0,\n" +
            "    \"costFreight\": 0,\n" +
            "    \"currency\": \"CNY\",\n" +
            "    \"finalAmount\": 100,\n" +
            "    \"pmtAmount\": 7,\n" +
            "    \"memo\": \"\",\n" +
            "    \"remark\": \"\",\n" +
            "    \"printStatus\": 0,\n" +
            "    \"paymentName\": \"???\",\n" +
            "    \"payType\": 700,\n" +
            "    \"customerId\": 296,\n" +
            "    \"supplierId\": 0,\n" +
            "    \"logiName\": null,\n" +
            "    \"logiNo\": null,\n" +
            "    \"logiCode\": null,\n" +
            "    \"payTime\": \"2016-08-28 10:07:48\",\n" +
            "    \"unionOrderId\": \"2016082846587983\",\n" +
            "    \"receiveStatus\": 0,\n" +
            "    \"isTax\": 0,\n" +
            "    \"taxCompany\": \"\",\n" +
            "    \"buyerPid\": \"362322199411050053\",\n" +
            "    \"buyerName\": \"???\",\n" +
            "    \"orderItems\": [\n" +
            "        {\n" +
            "            \"itemId\": 14654,\n" +
            "            \"orderId\": \"2016082824876283\",\n" +
            "            \"unionOrderId\": \"2016082846587983\",\n" +
            "            \"productBn\": \"CXJS0006\",\n" +
            "            \"name\": \"??????2(??,42?)(5)\",\n" +
            "            \"cost\": 5,\n" +
            "            \"price\": 10.7,\n" +
            "            \"amount\": 53.5,\n" +
            "            \"num\": 5,\n" +
            "            \"sendNum\": 0,\n" +
            "            \"refundNum\": 0,\n" +
            "            \"supplierId\": 0,\n" +
            "            \"customerId\": 296,\n" +
            "            \"goodBn\": \"0402210000\",\n" +
            "            \"standard\": \"??,42?\",\n" +
            "            \"brief\": null,\n" +
            "            \"shipStatus\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"itemId\": 14654,\n" +
            "            \"orderId\": \"2016082824876283\",\n" +
            "            \"unionOrderId\": \"2016082846587983\",\n" +
            "            \"productBn\": \"CXJS0006\",\n" +
            "            \"name\": \"??????2(??,42?)(5)\",\n" +
            "            \"cost\": 5,\n" +
            "            \"price\": 10.7,\n" +
            "            \"amount\": 53.5,\n" +
            "            \"num\": 5,\n" +
            "            \"sendNum\": 0,\n" +
            "            \"refundNum\": 0,\n" +
            "            \"supplierId\": 0,\n" +
            "            \"customerId\": 296,\n" +
            "            \"goodBn\": \"0402210000\",\n" +
            "            \"standard\": \"??,42?\",\n" +
            "            \"brief\": null,\n" +
            "            \"shipStatus\": 0\n" +
            "        }\n" +
            "\n" +
            "    ]\n" +
            "}";


    @Before
    public void setUp() throws Exception {

        mockOrderItemList = new ArrayList<OrderItem>();

        for (int i = 0; i < 5; i++) {
            OrderItem mockOrderItem = new OrderItem();
            mockOrderItem.setName(UUID.randomUUID().toString());
            mockOrderItem.setNum(i + 5);
            mockOrderItemList.add(mockOrderItem);
        }


        mockOrder = new Order();
        mockOrder.setOrderId("100");
        mockOrder.setShipName("小李");
        mockOrder.setShipMobile("15623235656");
        mockOrder.setCity("杭州");
        mockOrder.setShipZip("254565");
        mockOrder.setShipAddr("杭州市滨江区明月江南三栋1号");
        List<OrderItem> items = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setItemId(1);
        OrderItem item2 = new OrderItem();
        item2.setItemId(2);
        mockOrder.setOrderItems(items);
        mockOrder.setItemNum(2);
        mockOrder.setLogiNo("125463");
        mockOrder.setOrderItems(mockOrderItemList);


        mockSysData = new SAPSysData();
        mockSysData.setHost("193.168.9.15");
        mockSysData.setSysNo("00");
        mockSysData.setClient("500");
        mockSysData.setJcoUser("dev3");
        mockSysData.setJcoPass("800sap");
        mockSysData.setSapRouter("/H/202.107.243.45/H/");


        mockERP = new ERPInfo();
        mockERP.setErpType(ERPTypeEnum.ProviderType.SAP);
        mockERP.setSysDataJson(JSON.toJSONString(mockSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(12);

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setErpInfo(mockERP);
        mockPushNewOrderEvent.setOrderInfoJson(orderJson);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
    }

    @Test
    public void testPushOrder() throws Exception {

        EventResult eventResult = sapOrderHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());


//        Assert.assertTrue(eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode());
    }

    @Test
    public void testPrice() {
        List<SAPOrderItem> sapOrderItemList = new ArrayList<>();
        SAPOrderItem sapOrderItem1 = new SAPOrderItem();
        sapOrderItem1.setNum(3);
        sapOrderItem1.setAmount(17);
        sapOrderItem1.setPrice(17);


        SAPOrderItem sapOrderItem2 = new SAPOrderItem();
        sapOrderItem2.setNum(3);
        sapOrderItem2.setAmount(13);
        sapOrderItem2.setPrice(13);

        sapOrderItemList.add(sapOrderItem1);
        sapOrderItemList.add(sapOrderItem2);


        SAPSaleOrderInfo sapSaleOrderInfo = new SAPSaleOrderInfo();
        sapSaleOrderInfo.setCostItem(90);
        int index = 0;
        int totalPmtAmount = 0;

        for (SAPOrderItem sapOrderItem : sapOrderItemList) {
            double percent = sapOrderItem.getAmount() * sapOrderItem.getNum() / sapSaleOrderInfo.getCostItem();
            double subPmtAmount = index == sapOrderItemList.size() - 1 ?
                    sapSaleOrderInfo.getPmtAmount() - totalPmtAmount :
                    sapSaleOrderInfo.getPmtAmount() * percent;
            double netPrice = sapOrderItem.getPrice() - sapOrderItem.getAmount() - subPmtAmount; //净价 市场价-销售价-优惠金额
            System.out.println(netPrice);
        }
    }
}
