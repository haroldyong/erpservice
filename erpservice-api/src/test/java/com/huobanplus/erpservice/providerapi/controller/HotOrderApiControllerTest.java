/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.bean.NSSysData;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.entity.MallOrderItemBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by liual on 2015-08-26.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class, ApplicationConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class HotOrderApiControllerTest extends SpringWebTest {
    @Autowired
    private MallOrderService orderService;

    private MallOrderBean mockOrder;
    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {

    }

    /**
     * 测试获取订单列表
     * @throws Exception
     */
    @Test
    public void testObtainOrderList() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", "123456");
        signMap.put("mType", "mOrderSearch");
        signMap.put("TimeStamp", timestamp);
        signMap.put("OrderStatus", "0");
        signMap.put("PageSize", "10");
        signMap.put("Page", "1");

        String sign = buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        mockMvc.perform(post("/providerApi/rest/1/0/obtainOrders")
                .param("uCode", "123456")
                .param("mType", "mOrderSearch")
                .param("TimeStamp", timestamp)
                .param("OrderStatus", "0")
                .param("PageSize", "10")
                .param("Page", "1")
                .param("sign", sign))
                .andDo(print());

    }

    /**
     * 测试获取订单详情
     * @throws Exception
     */
    @Test
    public void testObtainOrderDetails() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", "123456");
        signMap.put("mType", "mGetOrder");
        signMap.put("TimeStamp", timestamp);
        signMap.put("OrderNO", "123456");

        String sign = buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        mockMvc.perform(post("/providerApi/rest/1/0/obtainOrder")
                .param("uCode", "123456")
                .param("mType", "mGetOrder")
                .param("TimeStamp", timestamp)
                .param("OrderNO", "123456")
                .param("sign", sign))
                .andDo(print());

    }



}