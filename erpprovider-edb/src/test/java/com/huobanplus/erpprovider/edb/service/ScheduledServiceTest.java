/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.service;

import com.huobanplus.erpprovider.edb.EDBTestBase;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by allan on 3/1/16.
 */
public class ScheduledServiceTest extends EDBTestBase {
    @Autowired
    private ScheduledService scheduledService;
    @Autowired
    private ERPDetailConfigService erpDetailConfigService;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    @Test
    public void testScheduledOrder() throws Exception {
//        ERPDetailConfigEntity erpDetailConfigEntity = erpDetailConfigService.findByCustomerIdAndDefault(3677, ERPTypeEnum.UserType.HUOBAN_MALL);
//        ERPInfo erpInfo = new ERPInfo(ERPTypeEnum.ProviderType.EDB, erpDetailConfigEntity.getErpSysData());

        scheduledService.syncOrderShip();
    }
}