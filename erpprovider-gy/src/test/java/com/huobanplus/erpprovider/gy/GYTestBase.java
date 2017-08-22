/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gy;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.config.GYTestConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wuxiongliu on 2016/6/14.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {GYTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class GYTestBase {

    protected GYSysData mockGySysData;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    @Before
    public void setUp() {
        String requestUrl = "http://v2.api.guanyierp.com/rest/erp_open";
        String appKey = "174076";
        String secret = "f3c982a0d4894323a5c9d9b84ddb5b78";
        String sessionKey = "4e648c06377345569438eac5f77e7012";
        String warehouseCode = "CK01";
        String shopCode = "001";
        String logiCode = "YTO";

        mockGySysData = new GYSysData();

        mockGySysData.setRequestUrl(requestUrl);
        mockGySysData.setAppKey(appKey);
        mockGySysData.setSessionkey(sessionKey);
        mockGySysData.setSecret(secret);
        mockGySysData.setShopCode(shopCode);
        mockGySysData.setWarehouseCode(warehouseCode);
        mockGySysData.setDefaultLogiCode(logiCode);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockGySysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);
    }

}
