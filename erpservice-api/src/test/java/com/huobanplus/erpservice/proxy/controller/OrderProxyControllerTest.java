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
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
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
public class OrderProxyControllerTest extends OrderProxyTest {
    @Autowired
    private EDBScheduledService edbScheduledService;

    @Test
    public void testCreateOrder() throws Exception {
        //创建EDB订单
        String mockEdbProductBn = "CP12011GG1";
        Order mockEdbOrder = randomOrder(randomOrderId(), mockEdbProductBn, mockEdbCustomerId);
        String orderInfoJson = JSON.toJSONString(mockEdbOrder);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("customerId", String.valueOf(mockEdbCustomerId));
        signMap.put("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode()));

        String sign = buildSign(signMap, null, HBConstant.SECRET_KEY);
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("customerId", String.valueOf(mockEdbCustomerId))
                .param("sign", sign)
                .param("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode())))
                .andDo(print())
                .andExpect(status().isOk());

        //创建网仓订单
        String mockIscsProductBn = "";
        Order mockIscsOrder = randomOrder(randomOrderId(), mockIscsProductBn, mockIscsCustomerId);
        String iscsOrderInfoJson = JSON.toJSONString(mockIscsOrder);
        Map<String, String> iscsSignMap = new TreeMap<>();
        iscsSignMap.put("orderInfoJson", iscsOrderInfoJson);
        iscsSignMap.put("customerId", String.valueOf(mockIscsCustomerId));
        iscsSignMap.put("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode()));
        sign = buildSign(iscsSignMap, null, HBConstant.SECRET_KEY);
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", iscsOrderInfoJson)
                .param("customerId", String.valueOf(mockIscsCustomerId))
                .param("sign", sign)
                .param("userType", String.valueOf(ERPTypeEnum.UserType.HUOBAN_MALL.getCode())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEdbSyncShip() throws Exception {

    }

    @Test
    public void testObtainOrder() throws Exception {

    }
}