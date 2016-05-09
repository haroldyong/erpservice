/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.service.EDBScheduledService;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by liual on 2015-10-26.
 */
public class OrderProxyControllerForEDBTest extends OrderProxyTest {
    private final String mockProductBn = "CP12011GG1";
    @Autowired
    private EDBScheduledService edbScheduledService;
    private Order mockOrder;

    @Before
    public void setUp() throws Exception {
        mockOrder = randomOrder(randomOrderId(), mockProductBn);
    }

    @Test
    public void testCreateOrder() throws Exception {
        //创建订单
        String orderInfoJson = JSON.toJSONString(mockOrder);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("customerId", String.valueOf(mockCustomerId));
        signMap.put("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode()));

        String sign = buildSign(signMap, null, HBConstant.SECRET_KEY);
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("customerId", String.valueOf(mockCustomerId))
                .param("sign", sign)
                .param("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEdbSyncShip() throws Exception {
        edbScheduledService.syncOrderShip();
    }

    @Test
    public void testObtainOrder() throws Exception {

    }

    @Test
    public void testOrderDeliver() throws Exception {
        String deliveryNo = randomOrderId();
        Map<String, Object> requestMap = new TreeMap<>();
        requestMap.put("customerId", String.valueOf(mockCustomerId));
        requestMap.put("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode()));
        requestMap.put("orderId", mockOrderId);
        requestMap.put("logiName", mockSysDataEdb.getExpress());
        requestMap.put("logiNo", deliveryNo);

        String sign = SignBuilder.buildSignIgnoreEmpty(requestMap, null, HBConstant.SECRET_KEY);
        mockMvc.perform(
                post("/hotProxy/order/orderDeliver")
                        .param("customerId", String.valueOf(mockCustomerId))
                        .param("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode()))
                        .param("orderId", mockOrderId)
                        .param("logiName", mockSysDataEdb.getExpress())
                        .param("logiNo", deliveryNo)
                        .param("sign", sign)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
}