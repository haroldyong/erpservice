/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.util;

import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungUtilTest {

    private SurSungSysData surSungSysData;

    @Before
    public void mockData() {
        surSungSysData = new SurSungSysData();

        surSungSysData.setPartnerId("ywv5jGT8ge6Pvlq3FZSPol345asd");
        surSungSysData.setPartnerKey("ywv5jGT8ge6Pvlq3FZSPol2323");
        surSungSysData.setToken("37bef1003c31bd5e");
        surSungSysData.setRequestUrl("http://b.sursung.com/api/open/query.aspx");
    }


    @Test
    public void testCreateRequestUrl() throws UnsupportedEncodingException {
        System.out.println(SurSungUtil.createRequestUrl("logistic.query", (int) (new Date().getTime() / 1000), surSungSysData));
    }
}
