package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.event.erpevent.InventoryEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.Assert.*;

/**
 * Created by allan on 2015/7/31.
 */
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ObtainDataControllerTest {
    @Autowired
    private ERPRegister erpRegister;

    @Test
    @Ignore
    public void testObtainOrderInfo() throws Exception {

    }

    @Test
    @Ignore
    public void testObtainPaymentInfo() throws Exception {

    }

    @Test
    @Ignore
    public void testObtainProductInfo() throws Exception {

    }

    @Test
    public void testTest() throws Exception {
//        ERPInfo erpInfo = new ERPInfo();
//        erpInfo.setName("edb");
//        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
//        erpHandler.handleEvent(InventoryEvent.class, null);
    }
}