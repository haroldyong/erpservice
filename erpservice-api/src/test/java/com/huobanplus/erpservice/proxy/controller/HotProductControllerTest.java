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
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.proxy.utils.DesUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by allan on 2015/8/6.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class HotProductControllerTest extends SpringWebTest {
    private ERPInfo mockERP;

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
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));
    }

    @Test
    public void testObtainInventory() throws Exception {
        byte[] result = mockMvc.perform(get("/hotClientOrderApi/obtainInventory")
                .param("sysDataJson", DesUtil.encrypt(mockERP.getSysDataJson())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsByteArray();
        String ddd = StreamUtils.copyToString(new ByteArrayInputStream(result), Charset.forName("utf-8"));
        System.out.println(ddd);
    }
}