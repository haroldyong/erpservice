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
        mockEDBSysData.setRequestUrl("http://vip502.edb05.com/rest/index.aspx");
        mockEDBSysData.setAppKey("fd0297ac");
        mockEDBSysData.setAppSecret("313a62f368104928b44208ae3bc18cc4");
        mockEDBSysData.setToken("ffb839ad76184e69a3c51eb4ba072b80");
        mockEDBSysData.setDbHost("edb_a87711");
        mockEDBSysData.setIp("119.23.60.222");
//        mockEDBSysData.setStorageIds("5");
        mockEDBSysData.setShopId("1");
        mockEDBSysData.setStorageId("2");
        mockEDBSysData.setExpress("圆通");

        EDBOrderSearch edbOrderSearch = new EDBOrderSearch();
        edbOrderSearch.setBeginTime("2017-07-15 19:40:00");
        edbOrderSearch.setEndTime("2017-07-15 20:20:00");
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