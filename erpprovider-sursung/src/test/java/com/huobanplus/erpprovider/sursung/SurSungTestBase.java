/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.config.SurSungTestConfig;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {SurSungTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class SurSungTestBase {

    protected Order mockOrder;

    protected List<OrderItem> mockOrderItems;

    protected SurSungSysData mockSurSungSysData;
    protected SurSungSysData mockNormalSysData;



    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    @Before
    public void setUp() {

        mockSurSungSysData = new SurSungSysData();
        mockSurSungSysData.setShopId(14670);
        mockSurSungSysData.setToken("181ee8952a88f5a57db52587472c3798");
        mockSurSungSysData.setPartnerId("ywv5jGT8ge6Pvlq3FZSPol345asd");
        mockSurSungSysData.setPartnerKey("ywv5jGT8ge6Pvlq3FZSPol2323");
        mockSurSungSysData.setVersion("1.0");
        mockSurSungSysData.setRequestUrl("http://b.sursung.com/api/open/query.aspx");

        mockNormalSysData = new SurSungSysData();
        mockNormalSysData.setShopId(10016667);
        mockNormalSysData.setToken("b08e15f90e13b90d217789c528976743");
        mockNormalSysData.setPartnerId("0ca483b4e595c596ca5a8e57f2ab3483");
        mockNormalSysData.setPartnerKey("cc8d64515028c6321b9971738a3185c5");
        mockNormalSysData.setVersion("1.0");
        mockNormalSysData.setRequestUrl("http://www.erp321.com/api/open/query.aspx");

        String orderNo = SerialNo.create();

        OrderItem item1 = new OrderItem();
        item1.setItemId(1);
        item1.setOrderId(orderNo);
        item1.setProductBn("BN-001");
        item1.setName("婴儿配方奶粉0001");
        item1.setCost(1);
        item1.setPrice(2);
        item1.setAmount(2);
        item1.setNum(1);
        item1.setStandard("测试");
        item1.setCustomerId(296);
        item1.setGoodBn("GN-001");

        OrderItem item2 = new OrderItem();
        item2.setItemId(2);
        item2.setOrderId(orderNo);
        item2.setProductBn("BN-002");
        item2.setName("婴儿配方奶粉0002");
        item2.setCost(1);
        item2.setPrice(2);
        item2.setAmount(2);
        item2.setNum(1);
        item2.setStandard("测试");
        item2.setCustomerId(296);
        item2.setGoodBn("GN-002");

        mockOrderItems = new ArrayList<>();
        mockOrderItems.add(item1);
        mockOrderItems.add(item2);

        mockOrder = new Order();
        mockOrder.setOrderId(orderNo);
        mockOrder.setMemberId(4542);
        mockOrder.setUserLoginName(UUID.randomUUID().toString());
        mockOrder.setConfirm(1);
        mockOrder.setOrderStatus(0);
        mockOrder.setPayStatus(1);
        mockOrder.setShipStatus(0);
        mockOrder.setWeight(100);
        mockOrder.setOrderName("测试商品(蓝色,42码)(10)(×10)");
        mockOrder.setItemNum(2);
        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN));
        mockOrder.setShipName("吴雄琉");
        mockOrder.setShipArea("浙江省/杭州市/滨江区");
        mockOrder.setProvince("浙江省");
        mockOrder.setCity("杭州市");
        mockOrder.setDistrict("滨江区");
        mockOrder.setShipAddr("浙江省杭州市滨江区阡陌路智慧E谷B幢4楼火图科技");
        mockOrder.setShipZip("310000");
        mockOrder.setShipTel("");
        mockOrder.setShipEmail("");
        mockOrder.setShipMobile("18705153968");
        mockOrder.setCostItem(4);
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(0);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(4);// 商品费用+商品费用*税率+运费

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayNumber(SerialNo.create());

        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.SURSUNG);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockSurSungSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);

    }

}
