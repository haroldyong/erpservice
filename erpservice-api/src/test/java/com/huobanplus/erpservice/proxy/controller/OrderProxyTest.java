/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.iscs.common.ISCSSysData;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.repository.ERPBaseConfigRepository;
import com.huobanplus.erpservice.datacenter.repository.ERPDetailConfigRepository;
import com.huobanplus.erpservice.datacenter.repository.ERPSysDataInfoRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by allan on 4/20/16.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {WebConfig.class})
@WebAppConfiguration
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderProxyTest extends SpringWebTest {
    protected final Random random = new Random();
    protected ERPDetailConfigEntity edbConfig;
    protected ERPDetailConfigEntity iscsConfig;
    protected EDBSysData mockSysDataEdb;
    protected ISCSSysData mockIscsSysData;
    protected int mockEdbCustomerId = 666666; //模拟的使用E店宝的用户
    protected int mockIscsCustomerId = 777777; //模拟的使用网仓的用户


    @Autowired
    private ERPBaseConfigRepository baseConfigRepository;
    @Autowired
    private ERPSysDataInfoRepository sysDataInfoRepository;
    @Autowired
    private ERPDetailConfigRepository detailConfigRepository;

    @Before
    public void initConfig() throws Exception {
        //E店宝配置信息
        ERPBaseConfigEntity baseConfig = new ERPBaseConfigEntity();
        baseConfig.setCustomerId(mockEdbCustomerId);
        baseConfig.setIsOpen(1);
        baseConfig.setAppKey(StringUtil.createRandomStr(8));
        baseConfig.setToken(StringUtil.createRandomStr32());
        baseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        baseConfig = baseConfigRepository.saveAndFlush(baseConfig);

        edbConfig = new ERPDetailConfigEntity();
        edbConfig.setErpType(ERPTypeEnum.ProviderType.EDB);
        edbConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        edbConfig.setIsDefault(1);
        edbConfig.setCustomerId(baseConfig.getCustomerId());
        mockSysDataEdb = new EDBSysData();
        mockSysDataEdb.setRequestUrl("http://vip545.edb05.com/rest/index.aspx");
        mockSysDataEdb.setDbHost("edb_a84130");
        mockSysDataEdb.setAppKey("43f7db02");
        mockSysDataEdb.setAppSecret("1bf2a0bc51dc4eef8c188cc7e09572a7");
        mockSysDataEdb.setToken("4d81a78f97d04b39b72407b8ada9b9e3");
        mockSysDataEdb.setIp("183.131.19.145");
        mockSysDataEdb.setShopId("10");
        mockSysDataEdb.setStorageId("1");
        mockSysDataEdb.setExpress("申通快递");
        mockSysDataEdb.setBeginTime(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN));
        String sysDataJson = JSON.toJSONString(mockSysDataEdb);

        edbConfig.setErpBaseConfig(baseConfig);
        edbConfig.setErpSysData(sysDataJson);
        edbConfig = detailConfigRepository.saveAndFlush(edbConfig);

        //网仓配置信息
        ERPBaseConfigEntity baseConfigForIscs = new ERPBaseConfigEntity();
        baseConfigForIscs.setCustomerId(mockIscsCustomerId);
        baseConfigForIscs.setIsOpen(1);
        baseConfigForIscs.setAppKey(StringUtil.createRandomStr(8));
        baseConfigForIscs.setToken(StringUtil.createRandomStr32());
        baseConfigForIscs.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        baseConfigForIscs = baseConfigRepository.saveAndFlush(baseConfigForIscs);

        iscsConfig = new ERPDetailConfigEntity();
        iscsConfig.setErpType(ERPTypeEnum.ProviderType.ISCS);
        iscsConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        iscsConfig.setIsDefault(1);
        iscsConfig.setCustomerId(baseConfigForIscs.getCustomerId());
        mockIscsSysData = new ISCSSysData();
        mockIscsSysData.setHost("");
        mockIscsSysData.setAppKey("");
        mockIscsSysData.setAppSecret("");
        mockIscsSysData.setOwnerId(1);
        mockIscsSysData.setShopId(1);
        mockIscsSysData.setStockId(1);
        String iscsSysDataJson = JSON.toJSONString(mockIscsSysData);
        iscsConfig.setErpBaseConfig(baseConfigForIscs);
        iscsConfig.setErpSysData(iscsSysDataJson);
        iscsConfig = detailConfigRepository.saveAndFlush(iscsConfig);
    }

    /**
     * 创建一个模拟的订单
     *
     * @param orderId
     * @param productBn
     * @return
     */
    protected Order randomOrder(String orderId, String productBn, int customerId) {
        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setMemberId(random.nextInt(100) + 1);
        order.setConfirm(1);
        order.setOrderStatus(0);
        order.setPayStatus(1);
        order.setShipStatus(0);
        order.setOrderName(UUID.randomUUID().toString());
        order.setItemNum(1);
        order.setLastUpdateTime(nowStr);
        order.setCreateTime(nowStr);
        order.setShipName(UUID.randomUUID().toString());
        order.setShipArea("浙江省/杭州市/滨江区");
        order.setProvince("浙江省");
        order.setCity("杭州市");
        order.setDistrict("滨江区");
        order.setShipAddr(UUID.randomUUID().toString());
        order.setShipMobile("18657889876");
        order.setCostItem(100);
        order.setFinalAmount(100);
        order.setPaymentName("微信支付");
        order.setCustomerId(customerId);
        order.setPayTime(nowStr);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setProductBn(productBn);
        orderItem.setName(UUID.randomUUID().toString());
        orderItem.setCost(50);
        orderItem.setPrice(100);
        orderItem.setAmount(100);
        orderItem.setNum(1);
        orderItem.setCustomerId(customerId);
        orderItem.setGoodBn(productBn);
        orderItem.setStandard(UUID.randomUUID().toString());
        orderItem.setBrief(UUID.randomUUID().toString());
        orderItem.setShipStatus(0);
        order.setOrderItems(Arrays.asList(orderItem));
        return order;
    }

    protected String randomOrderId() {
        String prefix = StringUtil.DateFormat(new Date(), "yyyyMMddHHmmss");
        return prefix + random.nextInt(89) + 10;
    }
}
