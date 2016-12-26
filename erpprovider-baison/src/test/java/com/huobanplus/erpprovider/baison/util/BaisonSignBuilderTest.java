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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-11-10.
 */
public class BaisonSignBuilderTest {

    @Test
    public void testBuildSign() throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<>();
        params.put("key", " test");
        params.put("requestTime", "20110714101701");
        params.put("secret", "1a2b3c4d5e6f7g8h9i10j11k12l");
        params.put("version", "2.0");
        params.put("serviceType", "goods.list.get");
        params.put("data", "{json}");

        String secret = "test";

        String sign = BaisonSignBuilder.buildSign(params);
        System.out.println("\nsign:" + sign);
    }
}
