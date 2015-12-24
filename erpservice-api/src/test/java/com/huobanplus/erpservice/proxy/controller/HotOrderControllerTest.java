/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.entity.MallOrderItemBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URLEncoder;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by allan on 2015/8/4.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class HotOrderControllerTest extends SpringWebTest {

    private ERPInfo mockERP;

    private MallOrderBean mockOrder;
    @Autowired
    private MallOrderService orderService;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        EDBSysData sysData = new EDBSysData();
//        sysData.setRequestUrl(EDBConstant.REQUEST_URI_TEST);
//        sysData.setDbHost(EDBConstant.DB_HOST_TEST);
//        sysData.setAppKey(EDBConstant.APP_KEY_TEST);
//        sysData.setAppSecret(EDBConstant.APP_SECRET_TEST);
//        sysData.setToken(EDBConstant.TOKEN_TEST);
//        sysData.setFormat(EDBConstant.FORMAT);
//        sysData.setV(EDBConstant.V);
//        sysData.setSlencry(EDBConstant.SLENCRY);
//        sysData.setIp(EDBConstant.IP_TEST);

        sysData.setRequestUrl(EDBConstant.REQUEST_URI);
        sysData.setDbHost(EDBConstant.DB_HOST);
        sysData.setAppKey(EDBConstant.APP_KEY);
        sysData.setAppSecret(EDBConstant.APP_SECRET);
        sysData.setToken(EDBConstant.TOKEN);
        sysData.setFormat(EDBConstant.FORMAT);
        sysData.setV(EDBConstant.V);
        sysData.setSlencry(EDBConstant.SLENCRY);
        sysData.setIp(EDBConstant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));


//        mockOrder = orderService.save(mockOrder);

//        mockOrder = orderService.save(mockOrder);
    }

    @Test
    public void testCreateOrder() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId("1232222132");

        MallOrderItemBean orderItem = new MallOrderItemBean();
//        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, signKey, null);
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"));
    }

    @Test
    public void testObtainOrder() throws Exception {
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/obtainOrder")
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderDeliver() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
//        orderInfo.setDeliveryTime(new Date());
        orderInfo.setLogiCode("12323");
//        orderInfo.setExpress("mockExpress");
//        orderInfo.setGrossWeight("dd");
        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderDeliver")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderUpdate() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
//        orderInfo.setTid(mockOrder.getOrderId());
//        orderInfo.setDeliveryTime(new Date());
//        orderInfo.setDistributTime(new Date());
//        orderInfo.setPrintTime(new Date());
//        orderInfo.setInspectTime(new Date());
        MallOrderItemBean orderItem = new MallOrderItemBean();
//        orderItem.setId(mockOrder.getOrderItems().get(0).getId());
//        orderItem.setTid(orderInfo.getOrderId());
//        orderItem.setBarcode("1123123213");
//        orderItem.setInspectionNum(1);
//        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderUpdate")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testOrderStatusUpdate() throws Exception {

    }
}