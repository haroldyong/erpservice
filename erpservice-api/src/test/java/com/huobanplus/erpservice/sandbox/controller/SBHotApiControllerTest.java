/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.sandbox.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.sandbox.common.SBConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by allan on 12/23/15.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class SBHotApiControllerTest extends SpringWebTest {

    @Test
    public void testOrderIndex() throws Exception {
        mockMvc.perform(post("/erpService/sandbox/rest/order/index")
                .param("appKey", SBConstant.APP_KEY)
                .param("token", SBConstant.TOKEN)
                .param("eventType", HotApiConstant.OBTAIN_ORDER_LIST))
                .andDo(print())
                .andExpect(status().isOk());
    }
}