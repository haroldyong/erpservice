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

package com.huobanplus.erpprovider.wangdianv2.handler;

import com.huobanplus.erpprovider.wangdianv2.WangDianV2TestBase;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2StockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
public class WangDianV2ProductHandlerTest extends WangDianV2TestBase {

    @Autowired
    private WangDianV2ProductHandler wangDianV2ProductHandler;

    @Test
    public void testQueryStock() {
        WangDianV2StockSearch wangDianV2StockSearch = new WangDianV2StockSearch();
        String startTime = "2017-02-24 07:10:10";
        String endTime = "2017-02-24 10:10:10";
        wangDianV2StockSearch.setStartTime(startTime);
        wangDianV2StockSearch.setEndTime(endTime);
        wangDianV2StockSearch.setWarehouseNo(mockWangDianV2SysData.getWarehouseNo());
        EventResult eventResult = wangDianV2ProductHandler.queryStock(wangDianV2StockSearch, mockWangDianV2SysData);
        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }
}
