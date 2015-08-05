package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.controller.impl.HotOrderControllerImpl;
import com.huobanplus.erpservice.transit.utils.DesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

/**
 * Created by allan on 2015/8/4.
 */
public class HotOrderControllerTest {

    @Test
    public void testCreateOrder() throws Exception {
        String preStr = "15067144911";

        String encryptBytes = DesUtil.encrypt(preStr, "11112222");

        new HotOrderControllerImpl().createOrder(null, encryptBytes);
    }
}