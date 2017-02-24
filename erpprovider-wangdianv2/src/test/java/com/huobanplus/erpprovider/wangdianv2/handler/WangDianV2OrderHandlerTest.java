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

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.wangdianv2.WangDianV2TestBase;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2OrderSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
public class WangDianV2OrderHandlerTest extends WangDianV2TestBase {

    @Autowired
    private WangDianV2OrderHandler wangDianV2OrderHandler;

    @Test
    public void test() {
        Assert.assertNotNull(wangDianV2OrderHandler);
    }

    @Test
    public void testPushOrder() {
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);

        EventResult eventResult = wangDianV2OrderHandler.pushOrder(pushNewOrderEvent);

        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }

    @Test
    public void testQueryOrder() {
        WangDianV2OrderSearch wangDianV2OrderSearch = new WangDianV2OrderSearch();
        String startTime = "2017-02-24 07:10:10";
        String endTime = "2017-02-24 10:10:10";
        wangDianV2OrderSearch.setStartTime(startTime);
        wangDianV2OrderSearch.setEndTime(endTime);
        wangDianV2OrderSearch.setPageNo(0);
        wangDianV2OrderSearch.setWarehouseNo("api_test");
        EventResult eventResult = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, mockWangDianV2SysData);
        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }


}
