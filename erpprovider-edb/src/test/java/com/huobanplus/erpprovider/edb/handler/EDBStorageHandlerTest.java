/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.config.EDBConfig;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by allan on 2015/8/7.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EDBStorageHandlerTest {
    @Autowired
    private EDBStorageHandler storageHandler;

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
    public void testOutStorageAdd() throws Exception {
//        MallOutStoreBean outStoreBean = new MallOutStoreBean();
//        outStoreBean.setOutStorageNo("1231232");
//        outStoreBean.setOutStorageType(120);
//        outStoreBean.setStorageNo("1");
//        outStoreBean.setSupplierNo("10");
//        outStoreBean.setFreight("10");
//
//        MallProductOutBean productOutBean = new MallProductOutBean();
//        productOutBean.setOutStoreBean(outStoreBean);
//        productOutBean.setProductItemNo("5540");
//        productOutBean.setLocationNo("1");
//        productOutBean.setStorageNo("1");
//        productOutBean.setOutStorageNum(1);
//        productOutBean.setOutStoragePrice(1);
//        productOutBean.setBatch("1");
//        productOutBean.setBarCode("123123222");
//
//        MallProductOutBean productOutBean1 = new MallProductOutBean();
//        productOutBean1.setOutStoreBean(outStoreBean);
//        productOutBean1.setProductItemNo("5558");
//        productOutBean1.setLocationNo("1");
//        productOutBean1.setStorageNo("1");
//        productOutBean1.setOutStorageNum(1);
//        productOutBean1.setOutStoragePrice(1);
//        productOutBean1.setBatch("1");
//        productOutBean1.setBarCode("123123223");
//        outStoreBean.setMallProductOutBeans(Arrays.asList(productOutBean, productOutBean1));
//        storageHandler.outStorageAdd(outStoreBean, mockERP);
    }
}