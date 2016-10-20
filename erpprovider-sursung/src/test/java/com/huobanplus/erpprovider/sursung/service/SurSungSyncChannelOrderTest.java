/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.service;

import com.huobanplus.erpprovider.sursung.SurSungTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * Created by wuxiongliu on 2016-09-16.
 */
public class SurSungSyncChannelOrderTest extends SurSungTestBase {

    @Autowired
    private SurSungSyncChannelOrder surSungSyncChannelOrder;

    @Test
    @Rollback(value = false)
    public void testSyncChannelOrder() {
        surSungSyncChannelOrder.syncChannelOrder();
    }

    @Test
    @Rollback(value = false)
    public void testSaveLog() {
//        surSungSyncChannelOrder.syncLog(1,9,10,mockErpUserInfo,mockErpInfo);
    }
}
