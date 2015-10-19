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
import com.huobanplus.erpservice.commons.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}