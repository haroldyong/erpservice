package com.huobanplus.erpprovider.iscs.handler.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.iscs.ISCSTestBase;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpprovider.iscs.formatiscs.ISCSCancelOrder;
import com.huobanplus.erpprovider.iscs.handler.ISCSOrderHandler;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/4/25.
 */
public class ISCSOrderHandlerImplTest extends ISCSTestBase {

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    private String mockOrderNo = "123";

    private int mockCustomer = 3677;

    private CancelOrderEvent mockCancelOrderEvent;

    private ISCSCancelOrder mockIscsCancelOrder;

    private ISCSSysData mockIscsSysData;

    @Autowired
    private ISCSOrderHandler iscsOrderHandler;

    @Test
    public void notNull(){
        Assert.assertNotNull(iscsOrderHandler);
    }

    @Before
    public void setUp(){
        mockIscsSysData = new ISCSSysData();
        mockIscsSysData.setStockId(220038);
        mockIscsSysData.setOwnerId(260136);
        mockIscsSysData.setShopId(250091);
        mockIscsSysData.setAppSecret("wangyingapp130705");
        mockIscsSysData.setAppKey("1900001");
        mockIscsSysData.setHost("http://testapi.iscs.com.cn/openapi/do");
        Date now = new Date();
        mockIscsSysData.setBeginTime(StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.ISCS);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockIscsSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(mockCustomer);
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);

        mockCancelOrderEvent = new CancelOrderEvent();
        mockCancelOrderEvent.setErpUserInfo(mockErpUserInfo);
        mockCancelOrderEvent.setErpInfo(mockErpInfo);
        mockCancelOrderEvent.setOrderId(mockOrderNo);
    }

    @Test
    public void testCancelOrder(){
        iscsOrderHandler.cancelOrder(mockCancelOrderEvent);
    }

}
