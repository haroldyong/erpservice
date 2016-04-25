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
        sysData.setRequestUrl("");
        sysData.setRequestUrl("http://qimen.6x86.net:10537/restxin/index.aspx");
        sysData.setDbHost("edb_a99999");
        sysData.setAppKey("c184567b");
        sysData.setAppSecret("90353b57f17a4bf6a11263f0545ddbdc");
        sysData.setToken("e6513e432b724720ae6b6ab4155e6ccb");
        sysData.setIp("117.79.148.228");
        sysData.setShopId("1");
        sysData.setStorageId("1");
        sysData.setExpress("申通");
        sysData.setBeginTime("2015-12-12");

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
        edbOrderSearch.setOrderId("2016042576478449");
        edbOrderHandler.obtainOrderList(sysData, edbOrderSearch);

//        edbScheduledService.syncOrderShip();
    }
}
