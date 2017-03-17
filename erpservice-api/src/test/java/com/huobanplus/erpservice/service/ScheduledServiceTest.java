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
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.service.EDBSyncInventory;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
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
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    @Before
    public void setUp() throws Exception {
        baseConfig.setIsSyncInventory(1);
        baseConfig.setIsSyncDelivery(1);

        EDBSysData mockEDBSysData = new EDBSysData();
        mockEDBSysData.setRequestUrl("http://vip207.edb01.com/rest/index.aspx");
        mockEDBSysData.setAppKey("57dcd6e6");
        mockEDBSysData.setAppSecret("c0a96e25382a447382de0f255e0b3677");
        mockEDBSysData.setToken("6ae2a7b0ef8c43449434b561ee1cf1b7");
        mockEDBSysData.setDbHost("edb_a67707");
        mockEDBSysData.setIp("122.224.212.246");
        mockEDBSysData.setStorageIds("2,7");

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

    @Test
    public void testObtainOrderList() throws Exception {
        EDBSysData mockEDBSysData = new EDBSysData();
        mockEDBSysData.setRequestUrl("http://vip3011.edb08.com.cn/rest/index.aspx");
        mockEDBSysData.setAppKey("6b43eafc");
        mockEDBSysData.setAppSecret("41735d6699b24b6a8d10af1157e7c643");
        mockEDBSysData.setToken("5ebce2a0550b4b86b02f9b9f9a259762");
        mockEDBSysData.setDbHost("edb_a76822");
        mockEDBSysData.setIp("122.224.212.246");
        mockEDBSysData.setStorageIds("2,7");
        mockEDBSysData.setStorageId("102");
        mockEDBSysData.setShopId("92");

        EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
        edbOrderSearch.setDateType(EDBEnum.DateType.DELIVER_TIME.getDateType());
        edbOrderSearch.setBeginTime("2015-01-01");
        edbOrderSearch.setEndTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        edbOrderSearch.setPageIndex(1);
        edbOrderSearch.setPageSize(EDBConstant.PAGE_SIZE);
        edbOrderSearch.setStorageId(mockEDBSysData.getStorageId());
        edbOrderSearch.setShopId(mockEDBSysData.getShopId());
        edbOrderSearch.setPayStatus(EDBEnum.PayStatusEnum.ALL_PAYED);
        edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
        edbOrderSearch.setOrderId("20170309704215304763");

        edbOrderHandler.obtainOrderList(mockEDBSysData, edbOrderSearch);
    }
}