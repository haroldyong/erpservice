/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.entity.MallOrderItemBean;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by liual on 2015-10-26.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderProxyControllerTest extends SpringWebTest {
    private ERPInfo mockERP;

    private MallOrderBean mockOrder;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        mockERP.setErpName("edb");
        EDBSysData sysData = new EDBSysData();

        sysData.setRequestUrl(Constant.REQUEST_URI);
        sysData.setDbHost(Constant.DB_HOST);
        sysData.setAppKey(Constant.APP_KEY);
        sysData.setAppSecret(Constant.APP_SECRET);
        sysData.setToken(Constant.TOKEN);
        sysData.setFormat(Constant.FORMAT);
        sysData.setV(Constant.V);
        sysData.setSlencry(Constant.SLENCRY);
        sysData.setIp(Constant.IP);
        mockERP.setSysDataJson(JSON.toJSONString(sysData));
        mockERP.setSysDataJson(DxDESCipher.encrypt(mockERP.getSysDataJson()));

        mockOrder = new MallOrderBean();
        mockOrder.setOrderId("12312312321");
        mockOrder.setCreateTime(new Date());
        mockOrder.setPayTime(new Date());
        mockOrder.setExpressNo("1212");
        MallOrderItemBean orderItem = new MallOrderItemBean();
        orderItem.setItemId(1);
        orderItem.setBn("123213");
        orderItem.setName("方便面");
        orderItem.setStandard("大碗");
        orderItem.setOrderId(mockOrder.getOrderId());
        orderItem.setNum(1);
        mockOrder.setOrderItems(Arrays.asList(orderItem));

    }

    @Test
    public void testCreateOrder() throws Exception {
        String orderInfoJson = JSON.toJSONString(mockOrder);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("erpName", mockERP.getErpName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, null, "66668888");
        mockMvc.perform(post("/hotProxy/order/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("erpName", mockERP.getErpName())
                .param("sysDataJson", mockERP.getSysDataJson())
                .param("sign", sign))
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
        signMap.put("erpName", mockERP.getErpName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());

        String sign = buildSign(signMap, null, "66668888");
        mockMvc.perform(post("/hotProxy/order/updateOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("erpName", mockERP.getErpName())
                .param("sysDataJson", mockERP.getSysDataJson())
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
        signMap.put("erpName", mockERP.getErpName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        String sign = buildSign(signMap, null, signKey);
        mockMvc.perform(post("/hotProxy/order/orderDeliver")
                .param("orderId", mockOrder.getOrderId())
                .param("deliverTime", deliverTime)
                .param("logiName", "申通快递")
                .param("logiNo", "1231232")
                .param("erpName", mockERP.getErpName())
                .param("sysDataJson", mockERP.getSysDataJson())
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelOrder() throws Exception {

    }
}