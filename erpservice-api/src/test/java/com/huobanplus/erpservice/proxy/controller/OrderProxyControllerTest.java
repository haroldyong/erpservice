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
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.service.ScheduledService;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by liual on 2015-10-26.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderProxyControllerTest extends SpringWebTest {
    private ERPInfo mockERP;

//    private MallOrderBean mockOrder;

    private Order mockOrder;

    private String mockCustomer = "3447";

    private ERPTypeEnum.UserType mockUserType = ERPTypeEnum.UserType.HUOBAN_MALL;
    @Autowired
    private ScheduledService scheduledService;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        EDBSysData sysData = new EDBSysData();

        sysData.setRequestUrl(EDBConstant.REQUEST_URI);
        sysData.setDbHost(EDBConstant.DB_HOST);
        sysData.setAppKey(EDBConstant.APP_KEY);
        sysData.setAppSecret(EDBConstant.APP_SECRET);
        sysData.setToken(EDBConstant.TOKEN);
        sysData.setFormat(EDBConstant.FORMAT);
        sysData.setV(EDBConstant.V);
        sysData.setSlencry(EDBConstant.SLENCRY);
        sysData.setIp(EDBConstant.IP);
        mockERP.setSysDataJson(JSON.toJSONString(sysData));
        mockERP.setSysDataJson(DxDESCipher.encrypt(mockERP.getSysDataJson()));

        mockOrder = new Order();
        mockOrder.setOrderId("12312312321");
        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setLogiCode("1212");
        OrderItem orderItem = new OrderItem();
        orderItem.setProductBn("123213");
        orderItem.setName("方便面");
        orderItem.setStandard("大碗");
        mockOrder.setOrderItems(Arrays.asList(orderItem));
//        orderItem.setOrderId(mockOrder.getOrderId());
//        orderItem.setNum(1);
//        mockOrder.setOrderItems(Arrays.asList(orderItem));

    }

    @Test
    public void testCreateOrder() throws Exception {
        String orderInfoJson = JSON.toJSONString(mockOrder);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("customerId", mockCustomer);

        String sign = buildSign(signMap, null, HBConstant.SECRET_KEY);
        mockMvc.perform(post("/hotProxy/order/pushOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("customerId", mockCustomer)
                .param("sign", sign)
                .param("userType", String.valueOf(mockUserType.getCode())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        mockOrder.setLogiName("申通快递");
        mockOrder.setLogiNo("1231232");
        String orderInfoJson = JSON.toJSONString(mockOrder);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("customerId", mockCustomer);

        String sign = buildSign(signMap, null, "66668888");
        mockMvc.perform(post("/hotProxy/order/updateOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("customerId", mockCustomer)
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderDeliver() throws Exception {
        String deliverTime = StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderId", mockOrder.getOrderId());
        signMap.put("deliverTime", deliverTime);
        signMap.put("logiName", "申通快递");
        signMap.put("logiNo", "1231232");
        signMap.put("customerId", mockCustomer);
        String sign = buildSign(signMap, null, signKey);
        mockMvc.perform(post("/hotProxy/order/orderDeliver")
                .param("orderId", mockOrder.getOrderId())
                .param("deliverTime", deliverTime)
                .param("logiName", "申通快递")
                .param("logiNo", "1231232")
                .param("customerId", mockCustomer)
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testScheduled() throws Exception {
        scheduledService.syncOrderShip();
    }
}