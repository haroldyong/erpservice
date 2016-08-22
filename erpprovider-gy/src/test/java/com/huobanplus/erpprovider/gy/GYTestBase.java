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
    public void setUp(){
        String requestUrl  = "http://v2.api.guanyierp.com/rest/erp_open";
        String appKey = "112194";
        String secret = "7d3330562019420da7f3098aa630556b";
        String sessionKey = "50b24306aacf479a9a5651f0fc7cabc3";
        String warehouseCode = "hotstock";
        String shopCode = "huotu";

        mockGySysData = new GYSysData();

        mockGySysData.setRequestUrl(requestUrl);
        mockGySysData.setAppKey(appKey);
        mockGySysData.setSessionkey(sessionKey);
        mockGySysData.setSecret(secret);
        mockGySysData.setShopCode(shopCode);
        mockGySysData.setWarehouseCode(warehouseCode);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockGySysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);
    }

}
