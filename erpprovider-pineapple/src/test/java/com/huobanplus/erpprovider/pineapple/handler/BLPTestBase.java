package com.huobanplus.erpprovider.pineapple.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderDetailResult;
import com.huobanplus.erpprovider.pineapple.config.BLPTestConfig;
import com.huobanplus.erpprovider.pineapple.util.BLPConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hxh on 2017-06-19.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {BLPTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BLPTestBase {

    @Autowired
    private BLPOrderHandler blpOrderHandler;
    @Test
    public void test1(){
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        erpUserInfo.setCustomerId(7297);
        EventResult eventResult = blpOrderHandler.obtainOrderInfoList("20182270207", 1, 10, 1, "2016-07-26 10:59:10", BLPConstant.OBTAIN_ORDER_LIST, erpUserInfo, "2016-08-02 10:59:10");
        System.out.println(eventResult.getData());
    }
    @Test
    public void test2() throws JsonProcessingException {
        BLPOrderDetailResult blpOrderDetailResult = new BLPOrderDetailResult();
        blpOrderDetailResult.setNick("11");
        blpOrderDetailResult.setTradeStatus("2");
        blpOrderDetailResult.setCardType("3");
        String s = new ObjectMapper().writeValueAsString(blpOrderDetailResult);
        System.out.println(s);
    }

}

