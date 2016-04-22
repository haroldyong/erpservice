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
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Assert;
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
        mockSysData.setJcoUser("DEV3");
        mockSysData.setJcoPass("800sap");
        mockSysData.setSapRouter("/H/202.107.243.45/H/");


        mockERP = new ERPInfo();
        mockERP.setErpType(ERPTypeEnum.ProviderType.SAP);
        mockERP.setSysDataJson(JSON.toJSONString(mockSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(12);

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setErpInfo(mockERP);
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
    }

    @Test
    public void testPushOrder() throws Exception {

        EventResult eventResult = sapOrderHandler.pushOrder(mockPushNewOrderEvent);
        Assert.assertTrue(eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode());
    }
}
