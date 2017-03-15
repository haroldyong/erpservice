/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.SurSungTestBase;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungLogistic;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * Created by wuxiongliu on 2016-09-16.
 */
public class SurSungSyncChannelOrderTest extends SurSungTestBase {

    @Autowired
    private SurSungSyncChannelOrder surSungSyncChannelOrder;
    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    @Test
    @Rollback(value = false)
    public void testSyncChannelOrder() {
        surSungSyncChannelOrder.syncChannelOrder();
    }

    @Test
    @Rollback(value = false)
    public void testSaveLog() {
//        surSungSyncChannlOrder.syncLog(1,9,10,mockErpUserInfo,mockErpInfo);
        String testStr = "{\"o_id\":\"21840678\",\"l_id\":\"3324607805091\",\"so_id\":\"20170307916874872763\",\"logistics_company\":\"申通快递\",\"send_date\":\"2017-03-07 18:24:56\",\"items\":[{\"sku_id\":\"Y93033\",\"qty\":\"2\",\"name\":\"【始于美白】艺福堂蜂蜜冻干柠檬片80g 做冻龄女神(2)\",\"so_id\":\"20170307916874872763\"},{\"sku_id\":\"Y96096\",\"qty\":\"2\",\"name\":\"【惬意茉莉】艺福堂茉莉花茶45g  广西横县原产(2)\",\"so_id\":\"20170307916874872763\"},{\"sku_id\":\"Y93015\",\"qty\":\"1\",\"name\":\"【忙碌中的呵护】艺福堂熟决明子茶450g 解放你的大脑(1)\",\"so_id\":\"20170307916874872763\"},{\"sku_id\":\"YZ01028\",\"qty\":\"1\",\"name\":\"赠品--艺福堂体验袋（福袋A袋）\",\"so_id\":\"20170307916874872763\"},{\"sku_id\":\"Y93033\",\"qty\":\"2\",\"name\":\"艺福堂花草茶冻干柠檬片80g/罐\",\"so_id\":\"20170307916874872763\"},{\"sku_id\":\"GA103\",\"qty\":\"1\",\"name\":\"雅集玻璃杯芳茗杯250ml（单杯贴花款)\",\"so_id\":\"20170307916874872763\"}]}";

        SurSungLogistic surSungLogistic = JSON.parseObject(testStr, SurSungLogistic.class);

        ERPUserInfo userInfo = new ERPUserInfo(ERPTypeEnum.UserType.HUOBAN_MALL, 296);

        ERPInfo erpInfo = new ERPInfo(ERPTypeEnum.ProviderType.SURSUNG, null);

        surSungOrderHandler.logisticUpload(surSungLogistic, userInfo, erpInfo);
    }
}
