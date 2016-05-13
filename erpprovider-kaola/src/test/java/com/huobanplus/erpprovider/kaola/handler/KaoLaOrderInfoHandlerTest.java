package com.huobanplus.erpprovider.kaola.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.kaola.KaoLaTestBase;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/10.
 */
public class KaoLaOrderInfoHandlerTest extends KaoLaTestBase{

    private KaoLaSysData mockKaoLaSysData;

    private OrderStatusInfoEvent mockOrderStatusInfoEvent;

    private PushNewOrderEvent mockPushNewOrderEvent;

    private OrderInfo mockOrderInfo;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    @Autowired
    private KaoLaOrderInfoHandler kaoLaOrderInfoHandler;

    /**
     *  appkey: bb0b3ad64c9e5eb06c2fb6f163bf179e79051bd5c9b652fc45dc68a2b5dd23c6
     *  appkey: 0dd1a2b29d6e4bfebce479450889b4b2
     *  secretKey: 4ed8b056c32939b9fd66987470b3e9fb720bdded02197e678e516bdcdf810833
     *  secretKey: 3cf1a3ed8556444bbd1fbd8b9381c8bb
     */

    @Before
    public void setUp(){
        mockKaoLaSysData = new KaoLaSysData();
        mockKaoLaSysData.setAppKey("0dd1a2b29d6e4bfebce479450889b4b2");
        mockKaoLaSysData.setAppSecret("3cf1a3ed8556444bbd1fbd8b9381c8bb");
        mockKaoLaSysData.setHost("http://223.252.220.85/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api
        mockKaoLaSysData.setV("1.0");


        mockOrderInfo = new OrderInfo();
        mockOrderInfo.setOrderCode("123");
        mockOrderInfo.setOrderChannel("1200");
        mockOrderInfo.setPayTime(new Date());


        mockOrderItems = new ArrayList<>();
        for(int i=0;i<5;i++){
            OrderItem mockOrderItem = new OrderItem();
            mockOrderItem.setNum(i+5);
            mockOrderItems.add(mockOrderItem);
        }

        mockOrder = new Order();
        mockOrder.setOrderId("25874125852656s565d");
        mockOrder.setMemberId(1);
        mockOrder.setShipName("wuxiongliu");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setShipEmail("xiong328160186@qq.com");
        mockOrder.setProvince("zhejiang");
        mockOrder.setCity("hangzhou");
        mockOrder.setDistrict("binjiang");
        mockOrder.setShipAddr("zhihuiegu");

        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);


        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockKaoLaSysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);


    }

    @Test
    public void testQueryOrderInfo(){

        mockOrderStatusInfoEvent = new OrderStatusInfoEvent();
        mockOrderStatusInfoEvent.setOrderInfo(mockOrderInfo);
        mockOrderStatusInfoEvent.setErpInfo(mockErpInfo);
        EventResult eventResult = kaoLaOrderInfoHandler.queryOrderStatusInfo(mockOrderStatusInfoEvent);
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushOrder(){

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = kaoLaOrderInfoHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(eventResult.getResultMsg());
    }

}
