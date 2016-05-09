/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.service.EDBScheduledService;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 4/25/16.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderSyncTest extends SpringWebTest {
    @Autowired
    private EDBScheduledService edbScheduledService;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    @Test
    public void orderShipSyncForEdb() throws Exception {
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl("http://vip207.edb01.com/rest/index.aspx");
        sysData.setDbHost("edb_a67707");
        sysData.setAppKey("57dcd6e6");
        sysData.setAppSecret("c0a96e25382a447382de0f255e0b3677");
        sysData.setToken("6ae2a7b0ef8c43449434b561ee1cf1b7");
        sysData.setIp("122.224.212.246");
        sysData.setShopId("66");
        sysData.setStorageId("2");
        sysData.setExpress("韵达");
        sysData.setBeginTime("2016-04-26");

        EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
//        edbOrderSearch.setDateType(EDBEnum.DateType.getDateType());
        edbOrderSearch.setBeginTime(sysData.getBeginTime());
        edbOrderSearch.setEndTime("2016-12-12");
        edbOrderSearch.setPageIndex(1);
        edbOrderSearch.setPageSize(EDBConstant.PAGE_SIZE);
        edbOrderSearch.setStorageId(sysData.getStorageId());
        edbOrderSearch.setShopId(sysData.getShopId());
//        edbOrderSearch.setPayStatus(EDBEnum.PayStatusEnum.ALL_PAYED);
//        edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
        edbOrderSearch.setOrderId("2016050742913374");
        EventResult eventResult = edbOrderHandler.obtainOrderList(sysData, edbOrderSearch);
        JSONObject result = (JSONObject) eventResult.getData();
        JSONArray resultArray = result.getJSONObject("items").getJSONArray("item");
        JSONObject orderInfoJson = resultArray.getJSONObject(0);
//        JSONArray orderItemJsonArray = orderInfoJson.getJSONArray("tid_item");

        //E店宝会将两笔相同信息的订单合并成一笔订单,所以需要进行一次拆分
        List<String> splitOrderId = new ArrayList<>();
        List<OrderDeliveryInfo> splitOrderDelivers = new ArrayList<>(); //已拆分的订单物流信息
//            String deliverItemsStr = "";

        //模拟拆分物流信息
        JSONObject orderItem1 = new JSONObject();
        orderItem1.put("out_tid", "123");
        orderItem1.put("barcode", "89403");
        orderItem1.put("pro_num", 1);
        JSONObject orderItem2 = new JSONObject();
        orderItem2.put("out_tid", "123");
        orderItem2.put("barcode", "123123");
        orderItem2.put("pro_num", 2);
        JSONObject orderItem3 = new JSONObject();
        orderItem3.put("out_tid", "1234");
        orderItem3.put("barcode", "12993");
        orderItem3.put("pro_num", 1);
        JSONArray orderItemJsonArray = new JSONArray();
        orderItemJsonArray.add(orderItem1);
        orderItemJsonArray.add(orderItem2);
        orderItemJsonArray.add(orderItem3);

        System.out.println(111);
//        edbScheduledService.syncOrderShip();
    }
}
