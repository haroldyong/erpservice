/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpservice.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.service.EDBSyncInventory;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by allan on 07/03/2017.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledServiceTest {
    ERPDetailConfigEntity mockEDBConfig = new ERPDetailConfigEntity();
    ERPBaseConfigEntity baseConfig = new ERPBaseConfigEntity();


    @Autowired
    private EDBSyncInventory syncInventory;

    @Before
    public void setUp() throws Exception {
        baseConfig.setIsSyncInventory(1);
        baseConfig.setIsSyncDelivery(1);

        EDBSysData mockEDBSysData = new EDBSysData();
        mockEDBSysData.setRequestUrl("http://qimen.6x86.net:10537/restxin/index.aspx");
        mockEDBSysData.setAppKey("c184567b");
        mockEDBSysData.setAppSecret("90353b57f17a4bf6a11263f0545ddbdc");
        mockEDBSysData.setToken("e6513e432b724720ae6b6ab4155e6ccb");
        mockEDBSysData.setDbHost("edb_a99999");
        mockEDBSysData.setIp("192.168.1.168");
        mockEDBSysData.setStorageIds("5");

        mockEDBConfig.setCustomerId(296);
        mockEDBConfig.setErpType(ERPTypeEnum.ProviderType.EDB);
        mockEDBConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockEDBConfig.setErpSysData(JSON.toJSONString(mockEDBSysData));
        mockEDBConfig.setErpBaseConfig(baseConfig);
    }

    @Test
    public void testInventorySync() throws Exception {
        syncInventory.doSync(mockEDBConfig, new Date());
    }

    @Test
    public void testRePushFailedOrder() throws Exception {

    }

    @Autowired
    private HBOrderHandler orderHandler;

    @Test
    public void testGetOrderListInfo() throws Exception {
        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
        orderSearchInfo.setPageIndex(1);
        orderSearchInfo.setPageSize(10);
        ERPUserInfo userInfo = new ERPUserInfo(ERPTypeEnum.UserType.HUOBAN_MALL, 296);
        orderHandler.obtainOrderList(orderSearchInfo, userInfo);
    }
}