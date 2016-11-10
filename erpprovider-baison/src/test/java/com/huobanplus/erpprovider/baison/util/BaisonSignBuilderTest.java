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

package com.huobanplus.erpprovider.baison.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016-11-10.
 */
public class BaisonSignBuilderTest {

    @Test
    public void testBuildSign() throws UnsupportedEncodingException {
        Map<String, Object> params = new TreeMap<>();
        params.put("app_act", " [接口名称]");
        params.put("app_key", "testerp_appkey");
        params.put("format", "json");
        params.put("timestamp", "2015-04-26 00:00:07");
        params.put("v", "2.0");

        String secret = "test";

        String sign = BaisonSignBuilder.buildSign(params, secret, secret);
        System.out.println("\nsign:" + sign);
    }
}
