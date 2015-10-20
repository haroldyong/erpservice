/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by allan on 2015/7/28.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {EDBConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EDBProductHandlerTest {
    @Autowired
    private EDBProductHandler EDBProductHandler;

    @Test
    public void testGetProInventoryInfo() throws Exception {
        // EDBProductHandler.getProInventoryInfo();
    }
}