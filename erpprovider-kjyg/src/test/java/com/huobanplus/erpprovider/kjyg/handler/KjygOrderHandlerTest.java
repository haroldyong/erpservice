package com.huobanplus.erpprovider.kjyg.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.kjyg.KjygTestBase;
import com.huobanplus.erpprovider.kjyg.common.KjygSysData;
import com.huobanplus.erpprovider.kjyg.service.KjygScheduledService;
import com.huobanplus.erpservice.common.util.StringUtil;
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
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public class KjygOrderHandlerTest extends KjygTestBase {

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    private KjygSysData kjygSysData;

    @Autowired
    private KjygOrderHandler kjygOrderHandler;

    private List<OrderItem> mockOrderItems;
    private Order mockOrder;

    @Autowired
    private KjygScheduledService kjygScheduledService;

    @Before
    public void setUp(){
        kjygSysData = new KjygSysData();
        kjygSysData.setRequestUrl("http://www.kjygb2c.com/api/service.aspx");
        kjygSysData.setClientKey("daa48d8a2a77c13ad11a6a4cf7e25531");
        kjygSysData.setClientCode("K-0008");
        kjygSysData.setWebsite("0001");

        mockOrderItems = new ArrayList<>();
        for(int i=0;i<5;i++){
            OrderItem mockOrderItem = new OrderItem();
            mockOrderItem.setNum(i+5);
            mockOrderItem.setItemId(980001234);
            mockOrderItems.add(mockOrderItem);
            mockOrderItem.setPrice(120.00);
        }

        mockOrder = new Order();
        mockOrder.setOrderId("123456789XXX");
        mockOrder.setMemberId(1);
        mockOrder.setShipName("wuxiongliu");
        mockOrder.setShipMobile("13211112222");
        mockOrder.setShipEmail("xiong328160186@qq.com");
        mockOrder.setProvince("zhejiang");
        mockOrder.setCity("0101");
        mockOrder.setDistrict("binjiang");
        mockOrder.setShipAddr("zhihuiegu");
        mockOrder.setUserLoginName("18705153967");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setBuyerName("wuxiongliu");
        mockOrder.setPayWay(1);
        mockOrder.setFhArea("日本");
        mockOrder.setShipZip("310000");
        mockOrder.setShipArea("滨江区");
        mockOrder.setRemark("test");

        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);


        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(kjygSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(1);
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
    }

    @Test
    public void testPushOrder(){
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        pushNewOrderEvent.setErpInfo(mockErpInfo);
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = kjygOrderHandler.pushOrder(pushNewOrderEvent);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryOrder(){

        String rOrderId = "123456789XXX";

        EventResult eventResult = kjygOrderHandler.queryOrderTradNo(rOrderId,kjygSysData);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryOrderStat(){
        String rOrderId = "123456789XXX";

        EventResult eventResult = kjygOrderHandler.queryOrderStat(rOrderId,kjygSysData);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testService(){
        kjygScheduledService.syncOrderShip();
    }


}
