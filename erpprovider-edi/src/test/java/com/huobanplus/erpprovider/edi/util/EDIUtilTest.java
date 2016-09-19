/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.util;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wuxiongliu on 2016-09-19.
 */
public class EDIUtilTest {

    @Test
    public void testBuildSign() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String data = "test";
        String secretKey = "12345678";
        String time = "2016-01-01 11:11:11";
        String sign = EDIUtil.buildSign(data, secretKey, time);
        System.out.println(sign);

    }
}
