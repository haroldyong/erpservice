package com.huobanplus.erpprovider.edb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by allan on 2015/7/29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EDBOrderHandlerTest {
    @Autowired
    private EDBOrderHandler EDBOrderHandler;

    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        mockERP.setName("edb");
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
        orderInfo.setOrderDate(new Date());
        orderInfo.setOrderCode("1232222132");

        MallOrderItem productBean = new MallOrderItem();
        productBean.setBarcode("22222");
        productBean.setProName("方便面");
        productBean.setSpecification("大碗");
        productBean.setOutTid("1232222132");
        productBean.setOrderBean(orderInfo);
        orderInfo.setProductBeans(Arrays.asList(productBean));

        Monitor<EventResult> monitor = EDBOrderHandler.createOrder(orderInfo, mockERP);
        System.out.println(monitor.get().getSystemResult());
    }

    @Test
    public void testGetOrderInfo() throws Exception {

    }

    @Test
    @Ignore
    public void testOrderStatusUpdate() throws Exception {

    }

    @Test
    @Ignore
    public void testOrderUpdate() throws Exception {

    }

    @Test
    public void testOrderDeliver() throws Exception {

    }
}