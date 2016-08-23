/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.formatdtw.*;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/6/16.
 */
public class DtwOrderHandlerTest extends DtwTestBase {

    @Autowired
    private DtwOrderHandler dtwOrderHandler;


    private PushNewOrderEvent mockPushNewOrderEvent;


    @Test
    public void testStockQuery() {
        DtwStockSearch dtwStockSearch = new DtwStockSearch();
        dtwStockSearch.setPassKey(mockDtwSysData.getPassKey());
        dtwStockSearch.setPartNo("test");
        dtwStockSearch.setECommerceName(mockDtwSysData.getECommerceName());
        dtwStockSearch.setECommerceCode(mockDtwSysData.getECommerceCode());
        EventResult result = dtwOrderHandler.stockQuery(dtwStockSearch, mockDtwSysData);
        System.out.println(result.getData());
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }

    @Test
    public void testWayBill() {
        DtwWayBill dtwWayBill = new DtwWayBill();
        EventResult result = dtwOrderHandler.wayBill(dtwWayBill, mockDtwSysData);
        System.out.println(result.getData());
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }


    @Test
    public void testSign() throws UnsupportedEncodingException {
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "wxd930ea5d5a258f4f");
        map.put("mch_id", "10000100");
        map.put("device_info", "1000");
        map.put("body", "test");
        map.put("nonce_str", "ibuaiVcKdpRxkhJA");
        map.put("test", "");
        map.put("test", null);

        System.out.println(DtwUtil.weixinBuildSign(map, "192006250b4c09247ec02edce69f6a2d"));
    }

    @Test
    public void testXml() throws JsonProcessingException {
        CustomOrder customOrder = new CustomOrder();
        CustomHead customHead = new CustomHead();
        CustomBody customBody = new CustomBody();
        customOrder.setHead(customHead);
        customOrder.setBody(customBody);

        CustomOrderInfo customOrderInfo = new CustomOrderInfo();
        CustomSign customSign = new CustomSign();
        CustomOrderHead customOrderHead = new CustomOrderHead();
        CustomOrderDetail customOrderDetail = new CustomOrderDetail();
        List<CustomOrderDetail> customOrderDetails = new ArrayList<>();
        customOrderDetails.add(customOrderDetail);
        customOrderInfo.setCustomSign(customSign);
        customOrderInfo.setCustomOrderHead(customOrderHead);


        customOrderInfo.setCustomOrderDetailList(customOrderDetails);
        CustomOrderInfoList customOrderInfoList = new CustomOrderInfoList();
        customOrderInfoList.setCustomOrderInfo(customOrderInfo);


        customBody.setOrerInfoList(customOrderInfoList);


        String xmlResult = new XmlMapper().writeValueAsString(customOrder);
        int start = xmlResult.indexOf("<jkfOrderDetail>");

        int end = xmlResult.lastIndexOf("</jkfOrderDetail>");
        String firstPane = xmlResult.substring(0, start);
        String middlePane = xmlResult.substring(start + 16, end);
        String lastPane = xmlResult.substring(end + 17);

        System.out.println("\n*********************");
        System.out.println(firstPane);
        System.out.println(middlePane);
        System.out.println(lastPane);
        System.out.println("\n*********************");
    }


    //    @Rollback(value = false)
    @Test
    public void testPushOrder() {

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        for (int i = 0; i < 10; i++) {

            EventResult result = dtwOrderHandler.pushOrder(mockPushNewOrderEvent);
            System.out.println(result.getData());
            System.out.println(result.getResultMsg());
            System.out.println(result.getResultCode());
        }
    }

    @Test
    public void testPushPersonalDeclareOrder() {

        EventResult eventResult = dtwOrderHandler.pushPersonalDeclareOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushPlatformOrder() {
        EventResult eventResult = dtwOrderHandler.pushPlatformOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushAliPayOrder() {
        EventResult eventResult = dtwOrderHandler.pushAliPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushWeixinPayOrder() {
        EventResult eventResult = dtwOrderHandler.pushWeixinPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushCustomOrder() {
        for (int i = 0; i < 10; i++) {

            EventResult eventResult = dtwOrderHandler.pushCustomOrder(mockOrder, mockDtwSysData);
            System.out.println(eventResult.getResultCode());
            System.out.println(eventResult.getResultMsg());
        }

    }

}
