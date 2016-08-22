/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.GYTestBase;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016/6/23.
 */
public class GYStockHandlerTest extends GYTestBase {

    @Autowired
    private GYStockHandler gyStockHandler;

    @Test
    public void testStockQuery(){
        GYStockSearch gyStockSearch = new GYStockSearch();
        EventResult eventResult = gyStockHandler.stockQuery(gyStockSearch,mockGySysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());

    }
}
