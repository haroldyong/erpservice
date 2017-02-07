/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.service;

import com.huobanplus.erpprovider.baison.BaisonTestBase;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2017-01-16.
 */
public class BaisonInventoryScheduleServiceTest extends BaisonTestBase {

    @Autowired
    private BaisonInventoryScheduleService baisonInventoryScheduleService;

    @Test
    public void testCaptureMallProduts() {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setCustomerId(296);
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);

        EventResult eventResult = baisonInventoryScheduleService.captureMallProducts(erpUserInfo);

        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testSyncInventory() {
        baisonInventoryScheduleService.syncInventory();

    }
}
