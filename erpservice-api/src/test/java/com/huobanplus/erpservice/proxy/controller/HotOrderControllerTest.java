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
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
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
        mockERP.setName("edb");
        EDBSysData sysData = new EDBSysData();
//        sysData.setRequestUrl(Constant.REQUEST_URI_TEST);
//        sysData.setDbHost(Constant.DB_HOST_TEST);
//        sysData.setAppKey(Constant.APP_KEY_TEST);
//        sysData.setAppSecret(Constant.APP_SECRET_TEST);
//        sysData.setToken(Constant.TOKEN_TEST);
//        sysData.setFormat(Constant.FORMAT);
//        sysData.setV(Constant.V);
//        sysData.setSlencry(Constant.SLENCRY);
//        sysData.setIp(Constant.IP_TEST);

        sysData.setRequestUrl(Constant.REQUEST_URI);
        sysData.setDbHost(Constant.DB_HOST);
        sysData.setAppKey(Constant.APP_KEY);
        sysData.setAppSecret(Constant.APP_SECRET);
        sysData.setToken(Constant.TOKEN);
        sysData.setFormat(Constant.FORMAT);
        sysData.setV(Constant.V);
        sysData.setSlencry(Constant.SLENCRY);
        sysData.setIp(Constant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));

        mockOrder = new MallOrderBean();
        mockOrder.setOutTid("123212322");
        mockOrder.setShopId("12");
        mockOrder.setStorageId("1");
        mockOrder.setExpress("dddd");
        mockOrder.setTidTime(new Date());
        mockOrder.setOrderId("123212322");
        mockOrder.setTid("123212322");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("123123");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("123212322");
        orderItem.setProNum(1);
        orderItem.setFreight("10");
        MallOrderItem orderItem1 = new MallOrderItem();
        orderItem1.setBarcode("1231234");
        orderItem1.setProName("方便面");
        orderItem1.setSpecification("大碗");
        orderItem1.setOutTid("123212322");
        orderItem1.setProNum(1);
        orderItem1.setFreight("10");

        mockOrder.setOrderItems(Arrays.asList(orderItem, orderItem1));
//        mockOrder = orderService.save(mockOrder);

//        mockOrder = orderService.save(mockOrder);
    }

    @Test
    public void testCreateOrder() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOutTid("1232222132");
        orderInfo.setTid("1232222132");

        orderInfo.setShopId("12");
        orderInfo.setStorageId("1");
        orderInfo.setExpress("dddd");
        orderInfo.setTidTime(new Date());
        orderInfo.setOrderId("1232222132");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("22222");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("1232222132");
        orderItem.setProNum(1);
        orderItem.setFreight("10");
        orderItem.setCostPrice(1153);
        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, signKey, null);
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"));
    }

    @Test
    public void testObtainOrder() throws Exception {
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/obtainOrder")
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderDeliver() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
        orderInfo.setDeliveryTime(new Date());
        orderInfo.setExpressNo("12323");
        orderInfo.setExpress("mockExpress");
        orderInfo.setGrossWeight("dd");
        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderDeliver")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderUpdate() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
        orderInfo.setTid(mockOrder.getOrderId());
        orderInfo.setDeliveryTime(new Date());
        orderInfo.setDistributTime(new Date());
        orderInfo.setPrintTime(new Date());
        orderInfo.setInspectTime(new Date());
        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setId(mockOrder.getOrderItems().get(0).getId());
        orderItem.setTid(orderInfo.getOrderId());
        orderItem.setBarcode("1123123213");
        orderItem.setInspectionNum(1);
        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderUpdate")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
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