package com.huobanplus.erpprovider.edb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        orderInfo.setTid("s123123213");
        orderInfo.setOutTid("sdfsdfsdf");
        orderInfo.setShopId("mockShipId");
        orderInfo.setStorageId("0");
        orderInfo.setBuyerId("1");
        orderInfo.setBuyerMessage("sdfdf");
        orderInfo.setBuyerEmail("sdfd@133.com");
        orderInfo.setBuyerAlipay("sdfsdf");
        orderInfo.setServiceRemarks("sdfsdf");
        orderInfo.setConsignee("ddd");
        orderInfo.setAddress("sdfsdf");
        orderInfo.setPost("12321");
        orderInfo.setPhone("82125585");
        orderInfo.setReceiverMobile("15067144855");
        orderInfo.setProvince("bj");
        orderInfo.setCity("bj");
        orderInfo.setArea("bj");
        orderInfo.setSendingType("sdf");
        orderInfo.setExpress("jdddd");
        orderInfo.setInvoiceType("1232");
        orderInfo.setInvoiceTitle("sdfd");
        orderInfo.setOrderDate(new Date());
        orderInfo.setBarCode("123213");
        orderInfo.setProductTitle("1231232132");
        orderInfo.setStandard("sjlkdjf");
        orderInfo.setAdvDistributTime(new Date());
        orderInfo.setBookDeliveryTime(new Date());
        orderInfo.setPayDate(new Date());
        orderInfo.setFinishDate(new Date());
        orderInfo.setOrderDate(new Date());
        Monitor<EventResult> monitor = EDBOrderHandler.createOrder(orderInfo, mockERP);
        System.out.println(monitor.get().getSystemResult());
    }

    @Test
    public void testGetOrderInfo() throws Exception {
        Monitor<EventResult> monitor = EDBOrderHandler.getOrderInfo(mockERP);
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