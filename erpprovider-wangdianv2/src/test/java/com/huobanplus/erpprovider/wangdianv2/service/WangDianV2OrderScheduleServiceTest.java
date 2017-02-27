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

package com.huobanplus.erpprovider.wangdianv2.service;

import com.huobanplus.erpprovider.wangdianv2.WangDianV2TestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2017-02-27.
 */
public class WangDianV2OrderScheduleServiceTest extends WangDianV2TestBase {

    @Autowired
    private WangDianV2OrderScheduleService wangDianV2OrderScheduleService;

    @Test
    public void test() {


        wangDianV2OrderScheduleService.syncShip();
    }
}
