/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.eventhandler.model.BaseInfo;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by liual on 2015-10-19.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class OrderApiControllerTest extends SpringWebTest {
    @Autowired
    private ERPBaseConfigService baseConfigService;

    private ERPBaseConfigEntity mockHbBaseConfig;
    private ERPBaseConfigEntity mockSupBaseConfig;

    private String mockAppKey = "fhcpam1w";
    private String mockToken = "975733031175ed399a48702d51012879";

    private int mockCustomerId = 3677;
    private int mockSupplierId = 6340;

    @Before
    public void setUp() throws Exception {
        mockHbBaseConfig = new ERPBaseConfigEntity();
        mockHbBaseConfig.setIsOpen(1);
        mockHbBaseConfig.setAppKey(StringUtil.createRandomStr(8));
        mockHbBaseConfig.setToken(StringUtil.createRandomStr32());
        mockHbBaseConfig.setCustomerId(mockCustomerId);
        mockHbBaseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockHbBaseConfig.setSecretKey("123456");
        mockHbBaseConfig = baseConfigService.save(mockHbBaseConfig);

        mockSupBaseConfig = new ERPBaseConfigEntity();
        mockSupBaseConfig.setIsOpen(1);
        mockSupBaseConfig.setAppKey(StringUtil.createRandomStr(8));
        mockSupBaseConfig.setToken(StringUtil.createRandomStr32());
        mockSupBaseConfig.setCustomerId(mockSupplierId);
        mockSupBaseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_SUPPLIER);
        mockSupBaseConfig.setSecretKey("123456");
        mockSupBaseConfig = baseConfigService.save(mockSupBaseConfig);
    }

    @Test
    public void testDeliverInfo() throws Exception {
        String response = mockMvc.perform(post("/hotapi/deliverInfo")
                .param("appKey", "123123")
                .param("token", "123213")
                .param("sign", "sfsdf123123")
                .param("timestamp", String.valueOf(new Date().getTime()))
                .param("orderId", "1231232222")
                .param("logiName", "中国邮政")
                .param("logiNo", "12312321")
                .param("remark", ""))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println(response);
    }

    @Test
    public void testObtainOrderList() throws Exception {
        Date now = new Date();
        //使用者：伙伴商城
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("appKey", mockHbBaseConfig.getAppKey());
        signMap.put("token", mockHbBaseConfig.getToken());
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("pageIndex", "1");
        signMap.put("pageSize", "2");
        signMap.put("eventType", HotApiConstant.OBTAIN_ORDER_LIST);

        String sign = buildSign(signMap, null, mockHbBaseConfig.getSecretKey());
        ResultActions result = mockMvc.perform(post("/hotApi/rest/order/index")
                .param("pageIndex", "1")
                .param("pageSize", "2")
                .param("eventType", HotApiConstant.OBTAIN_ORDER_LIST)
                .param("sign", sign)
                .param("appKey", mockHbBaseConfig.getAppKey())
                .param("token", mockHbBaseConfig.getToken())
                .param("timestamp", String.valueOf(now.getTime())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()));
        MockHttpServletResponse response = result.andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        System.out.println(response.getContentAsString());
        //使用者：供应商
        Map<String, Object> supSignMap = new TreeMap<>();
        supSignMap.put("appKey", mockSupBaseConfig.getAppKey());
        supSignMap.put("token", mockSupBaseConfig.getToken());
        supSignMap.put("timestamp", String.valueOf(now.getTime()));
        supSignMap.put("pageIndex", 1);
        supSignMap.put("pageSize", 2);
        supSignMap.put("eventType", HotApiConstant.OBTAIN_ORDER_LIST);

        String supSign = SignBuilder.buildSignIgnoreEmpty(supSignMap, null, mockSupBaseConfig.getSecretKey());
        ResultActions supResult = mockMvc.perform(post("/hotApi/rest/order/index")
                .param("pageIndex", "1")
                .param("pageSize", "2")
                .param("eventType", HotApiConstant.OBTAIN_ORDER_LIST)
                .param("sign", supSign)
                .param("appKey", mockSupBaseConfig.getAppKey())
                .param("token", mockSupBaseConfig.getToken())
                .param("timestamp", String.valueOf(now.getTime())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getResultCode()));
        MockHttpServletResponse supResponse = supResult.andReturn().getResponse();
        supResponse.setCharacterEncoding("utf-8");
        System.out.println(supResponse);
    }

    @Test
    public void testObtainOrderDetail() throws Exception {
        Date now = new Date();
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderId", "2015120726126556");
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("appKey", mockAppKey);
        signMap.put("token", mockToken);
        signMap.put("eventType", "hbpOrderDetail");
        String sign = buildSign(signMap, null, mockHbBaseConfig.getSecretKey());
        MockHttpServletResponse response = mockMvc.perform(post("/hotApi/rest/order/index")
                .param("orderId", "2015120726126556")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockAppKey)
                .param("token", mockToken)
                .param("eventType", "hbpOrderDetail")
                .param("sign", sign))
                .andDo(print())
                .andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        System.out.println(response.getContentAsString());
    }

    @Test
    public void testDeliveryInfo() throws Exception {
        Date now = new Date();
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("appKey", mockHbBaseConfig.getAppKey());
        signMap.put("token", mockHbBaseConfig.getToken());
        signMap.put("orderId", "2015011719995195");
        signMap.put("logiName", "申通快递");
        signMap.put("logiNo", "12312331");
        signMap.put("remark", "大家立刻就开始");
        signMap.put("deliverItemsStr", "01,1");
        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, mockHbBaseConfig.getSecretKey());

        mockMvc.perform(post("/hotApi/rest/order/index/hbpDeliveryInfo")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockHbBaseConfig.getAppKey())
                .param("token", mockHbBaseConfig.getToken())
                .param("orderId", "2015011719995195")
                .param("logiName", "申通快递")
                .param("logiNo", "12312331")
                .param("remark", "大家立刻就开始")
                .param("deliverItemsStr", "01,1")
                .param("sign", sign))
                .andDo(print());
    }

    @Test
    public void testReturnInfo() throws Exception {
        BaseInfo deliveryInfo = new DeliveryInfo();

        Class deliverClass = deliveryInfo.getClass();
        Field[] fields = deliverClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            String methodName = "get" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
        }
    }
}