/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by liual on 2015-10-29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = WebConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class ERPConfigControllerTest extends SpringWebTest {
    private ERPBaseConfigEntity mockERPConfig;
    @Autowired
    private ERPBaseConfigService baseConfigService;

    @Before
    public void setUp() throws Exception {
        mockERPConfig = new ERPBaseConfigEntity();
        mockERPConfig.setAppKey(StringUtil.createRandomStr(8));
        mockERPConfig.setToken(StringUtil.createRandomStr32());
        mockERPConfig.setIsOpen(1);
        mockERPConfig.setCustomerId(0);
        mockERPConfig.setSecretKey("123123");
        mockERPConfig = baseConfigService.save(mockERPConfig);
    }

    @Test
    public void testSetOpenStatus() throws Exception {
        ERPBaseConfigEntity mockNull;
        mockMvc.perform(post("/erpService/setOpenStatus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000));
        mockNull = baseConfigService.findByCustomerId(5, ERPTypeEnum.UserType.HUOBAN_MALL);
        assertNotNull(mockNull);

        mockMvc.perform(post("/erpService/setOpenStatus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000));
        entityManager.clear();
        managerFactory.getCache().evictAll();
        mockNull = baseConfigService.findByCustomerId(5, ERPTypeEnum.UserType.HUOBAN_MALL);
        assertEquals(0, mockNull.getIsOpen());
    }

    @Test
    public void testGetToken() throws Exception {
        mockMvc.perform(post("/erpService/getToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void testSaveConfig() throws Exception {

    }
}