/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by liual on 2015-10-28.
 */
public class StringUtilTest {

    @Test
    public void testCreateRandomStr() throws Exception {
        for (int i = 0; i <10 ; i++) {
            System.out.println(StringUtil.createRandomStr(9));
        }

    }
}