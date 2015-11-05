/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * Created by liual on 2015-11-03.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ProviderApiControllerTest extends SpringWebTest {
    @Autowired
    private ERPBaseConfigService baseConfigService;
    private String mockSecret = "123456";

    @Test
    public void testIndex() throws Exception {
        long currentTime = new Date().getTime();
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("mType", "testMethod");
        signMap.put("timestamp", String.valueOf(currentTime));
        signMap.put("uCode", "123456");
        String sign = this.buildSign(signMap, mockSecret, mockSecret);
        mockMvc.perform(post("/providerApi/rest/1/0")
                .param("mType", "testMethod")
                .param("timestamp", String.valueOf(currentTime))
                .param("uCode", "123456")
                .param("sign", sign))
                .andDo(print());
    }
}