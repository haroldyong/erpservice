package com.huobanplus.erpprovider.pineapple.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderDetailResult;
import com.huobanplus.erpprovider.pineapple.config.BLPHandlerBuilder;
import com.huobanplus.erpprovider.pineapple.config.BLPTestConfig;
import com.huobanplus.erpprovider.pineapple.util.BLPConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private BLPProductHandler blpProductHandler;

    @Autowired
    private BLPHandlerBuilder blpHandlerBuilder;

    @Autowired
    private ERPSysDataInfoService sysDataInfoService;

    @Test
    public void testObtainOrderInfo() {
//        BLPConfig blpConfig = new BLPConfig();
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpOrderHandler.obtainOrderInfoList("20170620701474696543", 1, 10, 1, "2016-07-26 10:59:10", BLPConstant.OBTAIN_ORDER_LIST, erpUserInfo, "2016-08-02 10:59:10");
        System.out.println(eventResult.getData());
        EventResult eventResult1 = blpOrderHandler.obtainOrderInfoList("", 1, 20, 1, "", BLPConstant.OBTAIN_ORDER_LIST, erpUserInfo, "");
        System.out.println(eventResult1.getData());
    }

    @Test
    public void testJson() throws JsonProcessingException {
        BLPOrderDetailResult blpOrderDetailResult = new BLPOrderDetailResult();
        blpOrderDetailResult.setNick("11");
        blpOrderDetailResult.setTradeStatus("2");
        blpOrderDetailResult.setCardType("3");
        blpOrderDetailResult.setTradeStatusDescription("djsa");
        String s = JSON.toJSONString(blpOrderDetailResult);
        System.out.println(s);
    }

    @Test
    public void testDeliverInfo() {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_SUPPLIER);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpOrderHandler.deliverOrder("20170613125629896564 ", "顺风", "WR6685851555", erpUserInfo, BLPConstant.DELIVER_INFO);
        System.out.println(eventResult.getData());
    }

    @Test
    public void testSynStock() {
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_SUPPLIER);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpProductHandler.syncStock("20170620701474696543", 10, erpUserInfo, BLPConstant.SYNC_STOCK);
        System.out.println(eventResult.getData());
    }
    @Test
    public void testHandlerBuilder(){
        List<ERPSysDataInfo> appkey = sysDataInfoService.findByErpTypeAndErpUserTypeAndParamNameAndParamVal(ERPTypeEnum.ProviderType.BLP, ERPTypeEnum.UserType.HUOBAN_SUPPLIER, "appKey", "1");
        System.out.println(appkey.toString());
    }

}

