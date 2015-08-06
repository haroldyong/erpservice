package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by allan on 2015/8/4.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class HotOrderControllerTest extends SpringWebTest {

    @Test
    public void testCreateOrder() throws Exception {
        mockMvc.perform(post("")
                .param("", ""))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testObtainOrder() throws Exception {

    }

    @Test
    public void testObtainInventory() throws Exception {

    }

    @Test
    public void testOrderDeliver() throws Exception {

    }

    @Test
    public void testOrderUpdate() throws Exception {

    }

    @Test
    public void testOrderStatusUpdate() throws Exception {

    }
}