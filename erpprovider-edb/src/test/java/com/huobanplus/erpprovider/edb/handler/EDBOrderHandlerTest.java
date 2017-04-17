/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBTestBase;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by allan on 2015/7/29.
 */
public class EDBOrderHandlerTest extends EDBTestBase {
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void obtainOrderListTest() throws Exception {
        EDBSysData mockEDBSysData = new EDBSysData();
        mockEDBSysData.setRequestUrl("http://vip3192.edb08.com.cn/rest/index.aspx");
        mockEDBSysData.setAppKey("57dcd6e6");
        mockEDBSysData.setAppSecret("c0a96e25382a447382de0f255e0b3677");
        mockEDBSysData.setToken("6ae2a7b0ef8c43449434b561ee1cf1b7");
        mockEDBSysData.setDbHost("edb_a67707");
        mockEDBSysData.setIp("122.224.212.246");
//        mockEDBSysData.setStorageIds("5");
        mockEDBSysData.setShopId("66");
        mockEDBSysData.setStorageId("7");
        mockEDBSysData.setExpress("韵达");

        EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
        edbOrderSearch.setBeginTime("2017-04-13 09:00:00");
        edbOrderSearch.setEndTime("2017-04-13 09:20:00");
        edbOrderSearch.setDateType(EDBEnum.DateType.DELIVER_TIME.getDateType());
        edbOrderSearch.setPageIndex(1);
        edbOrderSearch.setPageSize(EDBConstant.PAGE_SIZE);
        edbOrderSearch.setStorageId(mockEDBSysData.getStorageId());
        edbOrderSearch.setShopId(mockEDBSysData.getShopId());
        edbOrderSearch.setPayStatus(EDBEnum.PayStatusEnum.ALL_PAYED);
        edbOrderSearch.setShipStatus(EDBEnum.ShipStatusEnum.ALL_DELIVER);

        edbOrderHandler.obtainOrderList(mockEDBSysData, edbOrderSearch);

//        edbOrderHandler.obtainOrderList(mockEDBSysData,)
    }
}