package com.huobanplus.erpprovider.pineapple.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderDetailResult;
import com.huobanplus.erpprovider.pineapple.bean.BLPSysData;
import com.huobanplus.erpprovider.pineapple.config.BLPTestConfig;
import com.huobanplus.erpprovider.pineapple.util.BLPConstant;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
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
 * BLP的单元测试
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
    private ERPSysDataInfoService sysDataInfoService;

    @Autowired
    private ERPDetailConfigService detailConfigService;


    /**
     * 订单下载测试
     */
    @Test
    public void test1(){
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpOrderHandler.obtainOrderInfoList("20170620701474696543", 1, 10, 1, "2016-07-26 10:59:10", BLPConstant.OBTAIN_ORDER_LIST, erpUserInfo, "2016-08-02 10:59:10");
        System.out.println(eventResult.getData());
        EventResult eventResult1 = blpOrderHandler.obtainOrderInfoList("", 1, 10, 1, "", BLPConstant.OBTAIN_ORDER_LIST, erpUserInfo, "");
        System.out.println(eventResult1.getData());
    }

    /**
     * 转换json数据测试
     */
    @Test
    public void test2() throws JsonProcessingException {
        BLPOrderDetailResult blpOrderDetailResult = new BLPOrderDetailResult();
        blpOrderDetailResult.setNick("11");
        blpOrderDetailResult.setTradeStatus("2");
        blpOrderDetailResult.setCardType("3");
        String s = new ObjectMapper().writeValueAsString(blpOrderDetailResult);
        System.out.println(s);
    }

    /**
     * 订单发货测试
     */
    @Test
    public void test3(){
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpOrderHandler.deliverOrder("20170620701474696543", "BestRiven", "315235532", erpUserInfo, BLPConstant.DELIVER_INFO);
        System.out.println(eventResult.getData());
    }

    /**
     * 商品库存同步测试
     */
    @Test
    public void test4(){
        ERPUserInfo erpUserInfo = new ERPUserInfo();
        erpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        erpUserInfo.setCustomerId(3447);
        EventResult eventResult = blpProductHandler.syncStock("20170620701474696543", 3, erpUserInfo, BLPConstant.SYNC_STOCK);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());

    }
    @Test
    public void testHandlerBuilder(){
        List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserTypeAndParamNameAndParamVal(ERPTypeEnum.ProviderType.BLP, ERPTypeEnum.UserType.HUOBAN_SUPPLIER, "appKey", "1");
        ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, ERPTypeEnum.ProviderType.BLP, ERPTypeEnum.UserType.HUOBAN_SUPPLIER);
        System.out.println(sysDataInfos.toString());
        System.out.println(erpDetailConfig.toString());
        BLPSysData blpSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), BLPSysData.class);
        System.out.println(blpSysData.toString());
    }

}

