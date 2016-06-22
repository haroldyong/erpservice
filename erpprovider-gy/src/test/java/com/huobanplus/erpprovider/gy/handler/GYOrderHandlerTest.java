package com.huobanplus.erpprovider.gy.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.order.*;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYRefundOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYReturnOrderSearch;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
public class GYOrderHandlerTest extends GYTestBase {

    private GYSysData mockGySysData;

    private PushNewOrderEvent mockPushNewOrderEvent;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    @Autowired
    private GYOrderHandler gyOrderHandler;

    @Before
    public void setUp(){

        mockGySysData = new GYSysData();

        mockGySysData.setURL("https://demo.guanyierp.com/erpapi/rest/erp_open");
        mockGySysData.setAppKey("143158");
        mockGySysData.setSessionkey("58b9c91e195e4a28be107e1485264890");
        mockGySysData.setSecret("a4384907606e435bbf594c420760d29a");



        mockOrderItems = new ArrayList<>();
        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setNum(5);
        mockOrderItem.setOrderId("2016wxl1");
        mockOrderItem.setProductBn("3872824-ecc4090b639c47f89b453980923afb8e");
        mockOrderItem.setNum(10);
        mockOrderItem.setPrice(20.0);
        mockOrderItem.setStandard("test1");
        mockOrderItem.setGoodBn("1020021");

        OrderItem mockOrderItem2 = new OrderItem();
        mockOrderItem2.setNum(5);
        mockOrderItem2.setOrderId("2016wxl1");
        mockOrderItem2.setProductBn("3872824-ecc4090b639c47f89b453980923afb8e");
        mockOrderItem2.setNum(10);
        mockOrderItem2.setPrice(20.0);
        mockOrderItem2.setStandard("test2");
        mockOrderItem2.setGoodBn("1020021");
        mockOrderItems.add(mockOrderItem);
        mockOrderItems.add(mockOrderItem2);

        mockOrder = new Order();
        mockOrder.setOrderId("2016wxl1");
        mockOrder.setUserLoginName("wuliuxiong22");
        mockOrder.setMemberId(1761390);
        mockOrder.setShipName("wuxiongliu");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setShipEmail("");
        mockOrder.setProvince("浙江省");
        mockOrder.setCity("杭州市");
        mockOrder.setDistrict("滨江区");
        mockOrder.setShipAddr("浙江省杭州市滨江区阡陌路智慧E谷B幢4楼火图科技");
        mockOrder.setBuyerPid("330682199006015217");
        mockOrder.setBuyerName("testName");

        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);


        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockGySysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);
    }

    @Test
    public void testPushOrder(){

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);

        gyOrderHandler.pushOrder(mockPushNewOrderEvent);
    }

    @Test
    public void testOrderQuery(){
        GYOrderSearch gyOrderSearch = new GYOrderSearch();
        gyOrderSearch.setPageNo(1);
        gyOrderSearch.setPageSize(GYConstant.PAGE_SIZE);
        gyOrderSearch.setBeginTime(new Date());
        gyOrderSearch.setOrderState(1);
        gyOrderSearch.setReceiverMobile("18705153967");
        gyOrderSearch.setPlatformCode("2016wxl");
        gyOrderHandler.orderQuery(gyOrderSearch,mockGySysData);
    }

    @Test
    public void testOrderMemoUpdate(){
        GYOrderMemo gyOrderMemo = new GYOrderMemo();
        gyOrderMemo.setMemo("add memo");
        gyOrderMemo.setTid("1234566584511222111");
        gyOrderHandler.orderMemoUpdate(gyOrderMemo,mockGySysData);
    }

    @Test
    public void testOrderRefundStateUpdate(){
        GYOrderRefundUpdate gyOrderRefundUpdate = new GYOrderRefundUpdate();
        gyOrderRefundUpdate.setOid("test1");
        gyOrderRefundUpdate.setTid("2016wxl1");
        gyOrderRefundUpdate.setRefundState(1);
        gyOrderHandler.orderRefundStateUpdate(gyOrderRefundUpdate,mockGySysData);
    }

    @Test
    public void testDeliverOrderQuery(){
        GYDeliveryOrderSearch gyDeliveryOrderSearch = new GYDeliveryOrderSearch();
        gyDeliveryOrderSearch.setPageNo(1);
        gyDeliveryOrderSearch.setPageSize(20);
        gyDeliveryOrderSearch.setShopCode("9999");
        gyDeliveryOrderSearch.setOuterCode("2016wxl1");
        gyOrderHandler.deliveryOrderQuery(gyDeliveryOrderSearch,mockGySysData);
    }

    @Test
    public void testDeliveryOrderUpdate(){
        GYDeliveryOrderUpdate gyDeliveryOrderUpdate = new GYDeliveryOrderUpdate();
        gyDeliveryOrderUpdate.setCode("test");
        gyDeliveryOrderUpdate.setExpressCode("test");

        List<GYDeliveryState> gyDeliveryStates = new ArrayList<>();
        GYDeliveryState gyDeliveryState = new GYDeliveryState();
        gyDeliveryState.setAreaId("0");
        gyDeliveryState.setOperator("wuxiongliu");
        gyDeliveryState.setOperatorDate(new Date());
        gyDeliveryState.setNote("update");
        gyDeliveryStates.add(gyDeliveryState);

        gyDeliveryOrderUpdate.setDeliveryStates(gyDeliveryStates);
        gyOrderHandler.deliveryOrderUpdate(gyDeliveryOrderUpdate,mockGySysData);

    }

    @Test
    public void testPushReturnOrder(){
        GYReturnOrder gyReturnOrder = new GYReturnOrder();
        gyReturnOrder.setTypeCode("001");
        gyReturnOrder.setShopCode("9999");
        gyReturnOrder.setVipCode("wuliuxiong22");
        gyReturnOrder.setNote("wxo");

        List<GYReturnOrderItem> gyReturnOrderItems = new ArrayList<>();
        GYReturnOrderItem gyReturnOrderItem = new GYReturnOrderItem();
        gyReturnOrderItem.setItemCode("1020021");
        gyReturnOrderItem.setSkuCode("3872824-ecc4090b639c47f89b453980923afb8e");
        gyReturnOrderItem.setQty(10);
        gyReturnOrderItem.setOriginPrice(10.0);
        gyReturnOrderItem.setPrice(11.0);
        gyReturnOrderItems.add(gyReturnOrderItem);

        gyOrderHandler.pushReturnOrder(gyReturnOrder,mockGySysData);
    }

    @Test
    public void testReturnOrderQuery(){
        GYReturnOrderSearch gyReturnOrderSearch = new GYReturnOrderSearch();
        gyReturnOrderSearch.setPageNo(1);
        gyReturnOrderSearch.setSkuCode("3872824-ecc4090b639c47f89b453980923afb8e");
        gyOrderHandler.returnOrderQuery(gyReturnOrderSearch,mockGySysData);
    }

    @Test
    public void testPushRefundOrder(){
        GYRefundOrder gyRefundOrder = new GYRefundOrder();
        gyRefundOrder.setRefundType(1);
        gyRefundOrder.setShopCode("9999");
        gyRefundOrder.setVipCode("wuliuxiong22");
        gyRefundOrder.setAmount(10.2);
        List<GYRefundOrderItem> gyRefundOrderItems = new ArrayList<>();
        GYRefundOrderItem gyRefundOrderItem = new GYRefundOrderItem();
        gyRefundOrderItem.setBarCode("afasdfsadfasd");
        gyRefundOrderItem.setQty(10);
        gyRefundOrderItem.setPrice(20.0);
        gyRefundOrderItem.setNote("wuxiongliutest");
        gyRefundOrderItems.add(gyRefundOrderItem);
        gyRefundOrder.setItemDetails(gyRefundOrderItems);

        gyOrderHandler.pushRefundOrder(gyRefundOrder,mockGySysData);

    }


    @Test
    public void testRefundOrderQuery(){
        GYRefundOrderSearch gyRefundOrderSearch = new GYRefundOrderSearch();
        gyRefundOrderSearch.setCode("RMO1020536851");
        gyOrderHandler.refundOrderQuery(gyRefundOrderSearch,mockGySysData);
    }

}
