package com.huobanplus.erpprovider.iscs.handler.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.iscs.ISCSTestBase;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.formatiscs.ISCSCancelOrder;
import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelReturnOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushReturnInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.OrderInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wuxiongliu on 2016/4/25.
 */
public class ISCSOrderHandlerImplTest extends ISCSTestBase {

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    private String mockOrderNo = "2016042247125842";

    private int mockCustomer = 3677;

    private CancelOrderEvent mockCancelOrderEvent;

    private OrderStatusInfoEvent mockOrderStatusInfoEvent;

    private PushNewOrderEvent pushNewOrderEvent;

    private ISCSCancelOrder mockIscsCancelOrder;

    private ISCSSysData mockIscsSysData;

    private OrderInfo mockOrderInfo;

    private Order mockOrder;

    private List<OrderItem> mockOrderItemList;

    @Autowired
    private ISCSOrderHandler iscsOrderHandler;

    @Test
    public void notNull(){
        Assert.assertNotNull(iscsOrderHandler);
    }

    @Before
    public void setUp(){

        mockOrderItemList = new ArrayList<OrderItem>();

        for (int i = 0; i < 5; i++) {
            OrderItem mockOrderItem = new OrderItem();
            mockOrderItem.setName(UUID.randomUUID().toString());
            mockOrderItem.setProductBn("CP12011GG1");
            mockOrderItem.setNum(i + 5);
            mockOrderItemList.add(mockOrderItem);
        }

        mockIscsSysData = new ISCSSysData();
        mockIscsSysData.setStockId(220038);
        mockIscsSysData.setOwnerId(260136);
        mockIscsSysData.setShopId(250091);
        mockIscsSysData.setAppSecret("wangyingapp130705");
        mockIscsSysData.setAppKey("1900001");
        mockIscsSysData.setHost("http://testapi.iscs.com.cn/openapi/do");// http://ecmapi.iscs.com.cn/openapi/do
        Date now = new Date();
        mockIscsSysData.setBeginTime(StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.ISCS);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockIscsSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(mockCustomer);
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);

        mockCancelOrderEvent = new CancelOrderEvent();
        mockCancelOrderEvent.setErpUserInfo(mockErpUserInfo);
        mockCancelOrderEvent.setErpInfo(mockErpInfo);
        mockCancelOrderEvent.setOrderId(mockOrderNo);

        mockOrderStatusInfoEvent = new OrderStatusInfoEvent();
        mockOrderInfo = new OrderInfo();
        mockOrderInfo.setOrderCode(mockOrderNo);

        mockOrder = new Order();
        mockOrder.setOrderId("100");
        mockOrder.setShipName("小李");
        mockOrder.setShipMobile("15623235656");
        mockOrder.setCity("杭州");
        mockOrder.setShipZip("254565");
        mockOrder.setShipArea("杭州/浙江/中");

        mockOrder.setShipAddr("杭州市滨江区明月江南三栋1号");
        mockOrder.setItemNum(5);
        mockOrder.setLogiNo("125463");
        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));

        mockOrder.setOrderItems(mockOrderItemList);

        mockOrderStatusInfoEvent.setOrderInfo(mockOrderInfo);
        mockOrderStatusInfoEvent.setErpInfo(mockErpInfo);
        mockOrderStatusInfoEvent.setErpUserInfo(mockErpUserInfo);

    }


//    @Test
//    public void testCancelOrder(){
//        iscsOrderHandler.cancelOrder(mockCancelOrderEvent);
//    }
//
//    @Test
//    public void testOrdetStatusQuery(){
//        iscsOrderHandler.orderStatusQuery(mockOrderStatusInfoEvent);
//    }

    @Test
    public void testPushOrder(){

        pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);
        pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        iscsOrderHandler.pushOrder(pushNewOrderEvent);
    }

    @Test
    public void testReturnOrder(){
        PushReturnInfoEvent pushReturnInfoEvent = new PushReturnInfoEvent();
        pushReturnInfoEvent.setErpInfo(mockErpInfo);
        pushReturnInfoEvent.setErpUserInfo(mockErpUserInfo);
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setReturnItemStr("123123,1|12312312,2");
        returnInfo.setOrderId(mockOrderNo);

        pushReturnInfoEvent.setReturnInfo(returnInfo);

        iscsOrderHandler.pushReturnOrder(pushReturnInfoEvent);
    }

//    @Test
//    public void testOrderQuery(){
//        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
//
//        iscsOrderHandler.orderQuery(orderSearchInfo,mockErpUserInfo,mockErpInfo);
//    }

    @Test
    public void testCancelReturnOrder(){
        CancelReturnOrderEvent cancelReturnOrderEvent = new CancelReturnOrderEvent();
        cancelReturnOrderEvent.setErpUserInfo(mockErpUserInfo);
        cancelReturnOrderEvent.setErpInfo(mockErpInfo);
        cancelReturnOrderEvent.setOrderReturnNo("123456werwqre");
        iscsOrderHandler.cancelReturnOrder(cancelReturnOrderEvent);
    }

}
