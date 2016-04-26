/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.service.EDBScheduledService;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by allan on 4/25/16.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderSyncTest extends SpringWebTest {
    @Autowired
    private EDBScheduledService edbScheduledService;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    @Test
    public void orderShipSyncForEdb() throws Exception {
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl("http://vip207.edb01.com/rest/index.aspx");
        sysData.setDbHost("edb_a67707");
        sysData.setAppKey("57dcd6e6");
        sysData.setAppSecret("c0a96e25382a447382de0f255e0b3677");
        sysData.setToken("6ae2a7b0ef8c43449434b561ee1cf1b7");
        sysData.setIp("122.224.212.246");
        sysData.setShopId("66");
        sysData.setStorageId("2");
        sysData.setExpress("韵达");
        sysData.setBeginTime("2016-04-26");

        EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
        edbOrderSearch.setDateType(EDBEnum.DateType.DELIVER_TIME.getDateType());
        edbOrderSearch.setBeginTime(sysData.getBeginTime());
        edbOrderSearch.setEndTime("2016-12-12");
        edbOrderSearch.setPageIndex(1);
        edbOrderSearch.setPageSize(EDBConstant.PAGE_SIZE);
        edbOrderSearch.setStorageId(sysData.getStorageId());
        edbOrderSearch.setShopId(sysData.getShopId());
        edbOrderSearch.setPayStatus(EDBEnum.PayStatusEnum.ALL_PAYED);
        edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);
//        edbOrderSearch.setOrderId("2016042626588157");
        EventResult eventResult = edbOrderHandler.obtainOrderList(sysData, edbOrderSearch);
        System.out.println(111);
        edbScheduledService.syncOrderShip();
    }
}
