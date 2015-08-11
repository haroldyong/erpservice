package com.huobanplus.erpservice.transit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.utils.DesUtil;
import com.huobanplus.erpservice.transit.utils.DxDESCipher;
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
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

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

    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        mockERP.setName("edb");
        mockERP.setType("mockType");
        mockERP.setValidation("mockValidation");
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl(Constant.REQUEST_URI);
        sysData.setDbHost(Constant.DB_HOST);
        sysData.setAppKey(Constant.APP_KEY);
        sysData.setAppSecret(Constant.APP_SECRET);
        sysData.setToken(Constant.TOKEN);
        sysData.setFormat(Constant.FORMAT);
        sysData.setV(Constant.V);
        sysData.setSlencry(Constant.SLENCRY);
        sysData.setIp(Constant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));
    }

    @Test
    public void testCreateOrder() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOutTid("1232222132");
        orderInfo.setShopId("12");
        orderInfo.setStorageId("1");
        orderInfo.setExpress("dddd");
        orderInfo.setTidTime(new Date());
        orderInfo.setOrderId("1232222132");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("22222");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("1232222132");
        orderItem.setProNum(1);
        orderInfo.setOrderItems(Arrays.asList(orderItem));

        Map<String, String> signMap = new TreeMap<>();
        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());

        String sign = buildSign(signMap, null, null);
        mockMvc.perform(post("/hotClientOrderApi/createOrder")
                .param("orderInfoJson", orderInfoJson)
                .param("name", DesUtil.encrypt(mockERP.getName()))
                .param("type", DesUtil.encrypt(mockERP.getType()))
                .param("validation", DesUtil.encrypt(mockERP.getValidation()))
                .param("sysDataJson", DesUtil.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("1"));
    }

    @Test
    public void testObtainOrder() throws Exception {

        mockMvc.perform(get("/hotClientOrderApi/obtainOrder")
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson())))
                .andDo(print())
                .andExpect(status().isOk());
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