/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.EDBTestBase;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by allan on 2015/7/29.
 */
public class EDBOrderHandlerTest extends EDBTestBase {
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    private ERPInfo mockERP;

    @Before
    public void setUp() throws Exception {

    }


}