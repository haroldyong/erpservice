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
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.eventhandler.model.BaseInfo;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by liual on 2015-10-19.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class OrderApiControllerTest extends SpringWebTest {
    private ERPBaseConfigEntity mockBaseConfigEntity;
    @Autowired
    private ERPBaseConfigService baseConfigService;

    @Before
    public void setUp() throws Exception {
        mockBaseConfigEntity = new ERPBaseConfigEntity();
        mockBaseConfigEntity.setIsOpen(1);
        mockBaseConfigEntity.setAppKey("123456");
        mockBaseConfigEntity.setToken(DigestUtils.md5Hex(String.valueOf(new Date().getTime()).getBytes("utf-8")));
        mockBaseConfigEntity.setCustomerId(3447);
        mockBaseConfigEntity.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockBaseConfigEntity.setSecretKey("123456");
        mockBaseConfigEntity = baseConfigService.save(mockBaseConfigEntity);
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
        String date = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("appKey", mockBaseConfigEntity.getAppKey());
        signMap.put("token", mockBaseConfigEntity.getToken());
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("pageIndex", "1");
        signMap.put("pageSize", "20");
        signMap.put("beginTime", "2015-10-01 00:00:00");
        signMap.put("endTime", date);

        String sign = buildSign(signMap, null, mockBaseConfigEntity.getSecretKey());
        mockMvc.perform(post("/hotApi/rest/order/index/hbpOrderList")
                .param("pageIndex", "1")
                .param("pageSize", "20")
                .param("beginTime", "2015-10-01 00:00:00")
                .param("endTime", date)
                .param("sign", sign)
                .param("appKey", mockBaseConfigEntity.getAppKey())
                .param("token", mockBaseConfigEntity.getToken())
                .param("timestamp", String.valueOf(now.getTime())))
                .andDo(print());
    }

    @Test
    public void testObtainOrderDetail() throws Exception {
        Date now = new Date();
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderId", "2015110263231953");
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("appKey", mockBaseConfigEntity.getAppKey());
        signMap.put("token", mockBaseConfigEntity.getToken());
        String sign = buildSign(signMap, null, mockBaseConfigEntity.getSecretKey());
        String response = mockMvc.perform(post("/hotApi/rest/order/index/hbpOrderDetail")
                .param("orderId", "2015110263231953")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockBaseConfigEntity.getAppKey())
                .param("token", mockBaseConfigEntity.getToken())
                .param("sign", sign))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testDeliveryInfo() throws Exception {
        Date now = new Date();
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("timestamp", String.valueOf(now.getTime()));
        signMap.put("appKey", mockBaseConfigEntity.getAppKey());
        signMap.put("token", mockBaseConfigEntity.getToken());
        signMap.put("orderId", "2015011719995195");
        signMap.put("logiName", "申通快递");
        signMap.put("logiNo", "12312331");
        signMap.put("remark", "大家立刻就开始");
        signMap.put("deliverItemsStr", "01,1");
        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, mockBaseConfigEntity.getSecretKey());

        mockMvc.perform(post("/hotApi/rest/order/index/hbpDeliveryInfo")
                .param("timestamp", String.valueOf(now.getTime()))
                .param("appKey", mockBaseConfigEntity.getAppKey())
                .param("token", mockBaseConfigEntity.getToken())
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