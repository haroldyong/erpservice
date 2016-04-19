package com.huobanplus.erpprovider.sap.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.sap.SAPTestBase;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvis on 2016/4/15.
 */

public class SAPOrderHandlerTest extends SAPTestBase {

    @Autowired
    private SAPOrderHandler sapOrderHandler;


    private Order mockOrder;
    private ERPUserInfo mockErpUserInfo;
    private SAPSysData mockSysData;

    @Before
    public void setUp() throws Exception {

        mockOrder = new Order();
        mockOrder.setOrderId("100");
        mockOrder.setShipName("小李");
        mockOrder.setShipMobile("15623235656");
        mockOrder.setCity("杭州");
        mockOrder.setShipZip("254565");
        mockOrder.setShipAddr("杭州市滨江区明月江南三栋1号");
        List<OrderItem> items = new ArrayList<OrderItem>();
        OrderItem item1 = new OrderItem();
        item1.setItemId(1);
        OrderItem item2 = new OrderItem();
        item2.setItemId(2);
        mockOrder.setOrderItems(items);
        mockOrder.setItemNum(2);
        mockOrder.setLogiNo("125463");


        mockSysData = new SAPSysData();
        mockSysData.setHost("193.168.9.15");
        mockSysData.setSysNo("00");
        mockSysData.setClient("500");
        mockSysData.setJcoUser("DEV3");
        mockSysData.setJcoPass("800sap");
        mockSysData.setSapRouter("/H/202.107.243.45/H/");


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(12);

    }

    @Test
    public void testPushOrder() throws Exception {

        //EventResult eventResult = sapOrderHandler.pushOrder(mockOrder,mockSysData,mockErpUserInfo);
        //System.out.println("-----------------"+eventResult.getResultCode());
    }



}
