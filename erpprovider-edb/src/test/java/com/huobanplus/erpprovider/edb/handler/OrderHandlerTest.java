package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by allan on 2015/7/29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderHandlerTest {
    @Autowired
    private OrderHandler orderHandler;

    @Test
    @Ignore
    public void testCreateOrder() throws Exception {

    }

    @Test
    public void testGetOrderInfo() throws Exception {
        Monitor<EventResult> monitor = orderHandler.getOrderInfo();
    }
}