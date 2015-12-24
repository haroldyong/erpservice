/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.EDBConfig;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by allan on 2015/7/29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EDBOrderHandlerTest {
    //    @Autowired
    private EDBOrderHandler edbOrderHandler;

    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl(EDBConstant.REQUEST_URI);
        sysData.setDbHost(EDBConstant.DB_HOST);
        sysData.setAppKey(EDBConstant.APP_KEY);
        sysData.setAppSecret(EDBConstant.APP_SECRET);
        sysData.setToken(EDBConstant.TOKEN);
        sysData.setFormat(EDBConstant.FORMAT);
        sysData.setV(EDBConstant.V);
        sysData.setSlencry(EDBConstant.SLENCRY);
        sysData.setIp(EDBConstant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));

    }

    @Test
    public void testCreateOrder() throws Exception {
//        MallOrderBean orderInfo = new MallOrderBean();
//        orderInfo.setOutTid("1232222132");
//        orderInfo.setShopId("12");
//        orderInfo.setStorageId("1");
//        orderInfo.setExpress("dddd");
//        orderInfo.setTidTime(new Date());
//        orderInfo.setOrderId("1232222132");
//
//        MallOrderItemBean productBean = new MallOrderItemBean();
//        productBean.setBarcode("22222");
//        productBean.setProName("方便面");
//        productBean.setSpecification("大碗");
//        productBean.setOutTid("1232222132");
//        productBean.setProNum(1);
//        productBean.setOrderBean(orderInfo);
//        orderInfo.setOrderItems(Arrays.asList(productBean));
//
//        EventResult eventResult = edbOrderHandler.createOrder(orderInfo, mockERP);
    }

    @Test
    public void testObtainOrderList() throws Exception {
//       edbOrderHandler.obtainOrderList(null, mockERP);
//        System.out.println(monitor.get().getSystemResult());
    }

    @Test
    public void testOrderStatusUpdate() throws Exception {
//        MallOrderBean orderBean = new MallOrderBean();
//        orderBean.setOrderId("1231232");
//        orderBean.setOrderType("Order");
//        EventResult eventResult = edbOrderHandler.orderStatusUpdate(orderBean, mockERP);
    }

    @Test
    public void testOrderUpdate() throws Exception {
//        MallOrderBean orderBean = new MallOrderBean();
//        orderBean.setTid("S1412110000004");
//        orderBean.setDeliveryTime(new Date());
//        orderBean.setDistributTime(new Date());
//        orderBean.setPrintTime(new Date());
//        orderBean.setInspectTime(new Date());
//        MallOrderItemBean orderItem = new MallOrderItemBean();
//        orderItem.setTid(orderBean.getTid());
//        orderItem.setBarcode("1123123213");
//        orderItem.setInspectionNum(1);
//        orderItem.setOrderBean(orderBean);
//        orderBean.setOrderItems(Arrays.asList(orderItem));
//        EventResult eventResult = edbOrderHandler.orderUpdate(orderBean, mockERP);
    }

    @Test
    public void testOrderDeliver() throws Exception {
//        MallOrderBean orderBean = new MallOrderBean();
//        orderBean.setOrderId("1232123123");
//        orderBean.setExpressNo("123213");
//        orderBean.setExpress("2312");
//        EventResult eventResult = edbOrderHandler.orderDeliver(orderBean, mockERP);
    }
}