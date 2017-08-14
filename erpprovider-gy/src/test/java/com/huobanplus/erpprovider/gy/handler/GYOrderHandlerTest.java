/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.formatgy.order.*;
import com.huobanplus.erpprovider.gy.handler.impl.GYOrderHandlerImpl;
import com.huobanplus.erpprovider.gy.search.GYDeliveryOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYRefundOrderSearch;
import com.huobanplus.erpprovider.gy.search.GYReturnOrderSearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
public class GYOrderHandlerTest extends GYTestBase {

    private PushNewOrderEvent mockPushNewOrderEvent;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    @Autowired
    private GYOrderHandler gyOrderHandler;

    @Test
    public void testPushOrder() {

        mockOrderItems = new ArrayList<>();
        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setNum(5);
        mockOrderItem.setOrderId("2016wxl1");
        mockOrderItem.setProductBn("testskucode");
        mockOrderItem.setNum(10);
        mockOrderItem.setPrice(20.0);
        mockOrderItem.setStandard("test1");
        mockOrderItem.setGoodBn("1014hot");
        mockOrderItem.setItemId(123456);


//        OrderItem mockOrderItem2 = new OrderItem();
//        mockOrderItem2.setNum(5);
//        mockOrderItem2.setOrderId("2016wxl1");
//        mockOrderItem2.setProductBn("3872824-ecc4090b639c47f89b453980923afb8e");
//        mockOrderItem2.setNum(10);
//        mockOrderItem2.setPrice(20.0);
//        mockOrderItem2.setStandard("test2");
//        mockOrderItem2.setGoodBn("1020021");
        mockOrderItems.add(mockOrderItem);
//        mockOrderItems.add(mockOrderItem2);

        mockOrder = new Order();
        mockOrder.setOrderId("1111111");
        mockOrder.setUserLoginName("18705153967");
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
        mockOrder.setLogiCode("QFKD");
        mockOrder.setPaymentName("支付宝");
        mockOrder.setCostFreight(5);
        mockOrder.setFinalAmount(20.0 * 10);

        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);


        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);

        EventResult result = gyOrderHandler.pushOrder(mockPushNewOrderEvent, mockGySysData);
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }

    @Test
    public void testOrderQuery() throws Exception {
        GYDeliveryOrderSearch orderSearch = new GYDeliveryOrderSearch();
        orderSearch.setPageNo(1);
        orderSearch.setPageSize(GYConstant.PAGE_SIZE);
        orderSearch.setStartDeliveryDate("2017-08-11 14:00:00");
        orderSearch.setEndDeliveryDate("2017-08-11 17:00:20");
        orderSearch.setShopCode(mockGySysData.getShopCode());
        orderSearch.setDelivery(1);// 已发货
        String requestData = GYOrderHandlerImpl.getRequestData(mockGySysData, orderSearch, GYConstant.DELIVERY_QUERY);

        HttpClientUtil.getInstance().postAsync(mockGySysData.getRequestUrl(), requestData, httpResult -> {
            System.out.println(httpResult.toString());
        });

        Thread.sleep(100000);
    }

    @Test
    public void testOrderMemoUpdate() {
//        GYOrderMemo gyOrderMemo = new GYOrderMemo();
//        gyOrderMemo.setMemo("add memo");
//        gyOrderMemo.setTid("1234566584511222111");
//        EventResult eventResult = gyOrderHandler.orderMemoUpdate(gyOrderMemo,mockGySysData);
//        System.out.println(eventResult.getResultCode());
//        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testOrderRefundStateUpdate() {
//        GYOrderRefundUpdate gyOrderRefundUpdate = new GYOrderRefundUpdate();
//        gyOrderRefundUpdate.setOid("test1");
//        gyOrderRefundUpdate.setTid("2016wxl1");
//        gyOrderRefundUpdate.setRefundState(1);
//        EventResult eventResult = gyOrderHandler.orderRefundStateUpdate(gyOrderRefundUpdate,mockGySysData);
//        System.out.println(eventResult.getResultCode());
//        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testDeliverOrderQuery() {
        GYDeliveryOrderSearch gyDeliveryOrderSearch = new GYDeliveryOrderSearch();
        gyDeliveryOrderSearch.setPageNo(1);
        gyDeliveryOrderSearch.setPageSize(20);
        gyDeliveryOrderSearch.setShopCode("9999");
        gyDeliveryOrderSearch.setOuterCode("2016wxl1");
        EventResult eventResult = gyOrderHandler.deliveryOrderQuery(gyDeliveryOrderSearch, mockGySysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testDeliveryOrderUpdate() {
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
        EventResult eventResult = gyOrderHandler.deliveryOrderUpdate(gyDeliveryOrderUpdate, mockGySysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());

    }

    @Test
    public void testPushReturnOrder() {
//        GYReturnOrder gyReturnOrder = new GYReturnOrder();
//        gyReturnOrder.setTypeCode("001");
//        gyReturnOrder.setShopCode("9999");
//        gyReturnOrder.setVipCode("wuliuxiong22");
//        gyReturnOrder.setNote("wxo");
//
//        List<GYReturnOrderItem> gyReturnOrderItems = new ArrayList<>();
//        GYReturnOrderItem gyReturnOrderItem = new GYReturnOrderItem();
//        gyReturnOrderItem.setItemCode("1020021");
//        gyReturnOrderItem.setSkuCode("3872824-ecc4090b639c47f89b453980923afb8e");
//        gyReturnOrderItem.setQty(10);
//        gyReturnOrderItem.setOriginPrice(10.0);
//        gyReturnOrderItem.setPrice(11.0);
//        gyReturnOrderItems.add(gyReturnOrderItem);
//
//        EventResult eventResult = gyOrderHandler.pushReturnOrder(gyReturnOrder,mockGySysData);
//        System.out.println(eventResult.getResultCode());
//        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testReturnOrderInStock() {
        GYReturnOrderInStock gyReturnOrderInStock = new GYReturnOrderInStock();
        gyReturnOrderInStock.setCode("RGO1020525785");
        gyReturnOrderInStock.setWareHouseCode("tk01");

        EventResult eventResult = gyOrderHandler.returnOrderInStock(gyReturnOrderInStock, mockGySysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());

    }

    @Test
    public void testReturnOrderQuery() {
        GYReturnOrderSearch gyReturnOrderSearch = new GYReturnOrderSearch();
        gyReturnOrderSearch.setPageNo(1);
        gyReturnOrderSearch.setSkuCode("3872824-ecc4090b639c47f89b453980923afb8e");
        EventResult eventResult = gyOrderHandler.returnOrderQuery(gyReturnOrderSearch, mockGySysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushRefundOrder() {
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

        EventResult eventResult = gyOrderHandler.pushRefundOrder(gyRefundOrder, mockGySysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());

    }


    @Test
    public void testRefundOrderQuery() {
        GYRefundOrderSearch gyRefundOrderSearch = new GYRefundOrderSearch();
        gyRefundOrderSearch.setCode("RMO1020536851");
        EventResult eventResult = gyOrderHandler.refundOrderQuery(gyRefundOrderSearch, mockGySysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

}
