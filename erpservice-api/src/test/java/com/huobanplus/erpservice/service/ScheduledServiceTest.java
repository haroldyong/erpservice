/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpservice.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.service.EDBSyncInventory;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.BatchDeliverResult;
import com.huobanplus.erpservice.datacenter.model.OrderListInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.common.ApiResult;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by allan on 07/03/2017.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduledServiceTest {
    ERPDetailConfigEntity mockEDBConfig = new ERPDetailConfigEntity();
    ERPBaseConfigEntity baseConfig = new ERPBaseConfigEntity();


    @Autowired
    private EDBSyncInventory syncInventory;
    @Autowired
    private HBOrderHandler orderHandler;

    @Before
    public void setUp() throws Exception {
        baseConfig.setIsSyncInventory(1);
        baseConfig.setIsSyncDelivery(1);

        EDBSysData mockEDBSysData = new EDBSysData();
        mockEDBSysData.setRequestUrl("http://qimen.6x86.net:10537/restxin/index.aspx");
        mockEDBSysData.setAppKey("c184567b");
        mockEDBSysData.setAppSecret("90353b57f17a4bf6a11263f0545ddbdc");
        mockEDBSysData.setToken("e6513e432b724720ae6b6ab4155e6ccb");
        mockEDBSysData.setDbHost("edb_a99999");
        mockEDBSysData.setIp("192.168.1.168");
        mockEDBSysData.setStorageIds("5");

        mockEDBConfig.setCustomerId(296);
        mockEDBConfig.setErpType(ERPTypeEnum.ProviderType.EDB);
        mockEDBConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockEDBConfig.setErpSysData(JSON.toJSONString(mockEDBSysData));
        mockEDBConfig.setErpBaseConfig(baseConfig);
    }

    @Test
    public void testInventorySync() throws Exception {
        syncInventory.doSync(mockEDBConfig, new Date());
    }

    @Test
    public void testRePushFailedOrder() throws Exception {

    }

    @Test
    public void testGetOrderListInfo() throws Exception {
//        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
//        orderSearchInfo.setPageIndex(1);
//        orderSearchInfo.setPageSize(10);
//        ERPUserInfo userInfo = new ERPUserInfo(ERPTypeEnum.UserType.HUOBAN_MALL, 296);
//        orderHandler.obtainOrderList(orderSearchInfo, userInfo);


        String testStr = "{\"code\":200,\"msg\":\"批量操作成功\",\"data\":{\"failedOrders\":[{\"OrderNo\":null,\"LogiName\":\"分销申通\",\"LogiNo\":\"3325264139415\",\"LogiMoney\":0.0,\"LogiCode\":\"STO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6924617834930,1\"},{\"OrderNo\":null,\"LogiName\":\"分销申通\",\"LogiNo\":\"3325275476104\",\"LogiMoney\":0.0,\"LogiCode\":\"STO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6924617807095,1\"},{\"OrderNo\":null,\"LogiName\":\"分销圆通\",\"LogiNo\":\"884488042967759869\",\"LogiMoney\":0.0,\"LogiCode\":\"YTO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6924617804339,1\"},{\"OrderNo\":null,\"LogiName\":\"分销圆通\",\"LogiNo\":\"884488083229668950\",\"LogiMoney\":0.0,\"LogiCode\":\"YTO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6926044347069,1\"},{\"OrderNo\":null,\"LogiName\":\"分销申通\",\"LogiNo\":\"3325278023881\",\"LogiMoney\":0.0,\"LogiCode\":\"STO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6924617821770,1\"},{\"OrderNo\":null,\"LogiName\":\"分销圆通\",\"LogiNo\":\"884488053116335627\",\"LogiMoney\":0.0,\"LogiCode\":\"YTO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6926044347274,1\"},{\"OrderNo\":null,\"LogiName\":\"分销申通\",\"LogiNo\":\"3325278293912\",\"LogiMoney\":0.0,\"LogiCode\":\"STO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6924617805398,1\"},{\"OrderNo\":null,\"LogiName\":\"分销圆通\",\"LogiNo\":\"884488050326294213\",\"LogiMoney\":0.0,\"LogiCode\":\"YTO\",\"Remark\":\"订单不存在\",\"SupplierId\":null,\"AgentShopId\":0,\"DeliverItemsStr\":\"6926044323841,1\"}],\"successOrders\":[]}}";

//        ApiResult<OrderListInfo> apiResult = JSON.parseObject(testStr, new TypeReference<ApiResult<OrderListInfo>>() {
//        });

        ApiResult<BatchDeliverResult> apiResult = new Gson().fromJson(testStr, new TypeToken<ApiResult<BatchDeliverResult>>() {
        }.getType());

        String test = "";
    }

    @Test
    public void testObtainOrderList() throws Exception {
        OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
        orderSearchInfo.setBeginTime("2017-03-03 13:06:35");
        orderSearchInfo.setEndTime("2017-03-18 13:06:35");
        orderSearchInfo.setPageIndex(1);
        orderSearchInfo.setPageSize(200);
        EventResult eventResult = orderHandler.obtainOrderList(orderSearchInfo, new ERPUserInfo(ERPTypeEnum.UserType.HUOBAN_SUPPLIER, 7630));

        int test = ((OrderListInfo) eventResult.getData()).getRecordCount();

        System.out.println(((OrderListInfo) eventResult.getData()).getRecordCount());
    }
}