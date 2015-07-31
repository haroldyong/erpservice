package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
    public void testCreateOrder() throws Exception {
        OrderInfo orderInfo = new OrderInfo();
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
        orderHandler.createOrder(orderInfo);
    }

    @Test
    public void testGetOrderInfo() throws Exception {
        Monitor<EventResult> monitor = orderHandler.getOrderInfo();
    }
}