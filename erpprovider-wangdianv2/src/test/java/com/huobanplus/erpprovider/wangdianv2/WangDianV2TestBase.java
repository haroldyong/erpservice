/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;
import com.huobanplus.erpprovider.wangdianv2.config.WangDianV2TestConfig;
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
 * Created by wuxiongliu on 2016-11-02.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {WangDianV2TestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class WangDianV2TestBase {

    protected Order mockOrder;

    protected List<OrderItem> mockOrderItems;

    protected WangDianV2SysData mockWangDianV2SysData;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    @Before
    public void setUp() {

        mockWangDianV2SysData = new WangDianV2SysData();
//        mockWangDianV2SysData.setRequestUrl("http://121.41.177.115");
//        mockWangDianV2SysData.setWangdianv2Sid("apidev2");
//        mockWangDianV2SysData.setAppKey("beiying2test");
//        mockWangDianV2SysData.setAppSecret("12345");
//        mockWangDianV2SysData.setShopNo("api_test");
//        mockWangDianV2SysData.setShopName("API公共测试");
//        mockWangDianV2SysData.setWarehouseNo("api_test");
//        mockWangDianV2SysData.setWarehouseName("API公共测试");
        mockWangDianV2SysData.setRequestUrl("http://api.wangdian.cn");
        mockWangDianV2SysData.setWangdianv2Sid("nuoke2");
        mockWangDianV2SysData.setAppKey("nuoke2-gw");
        mockWangDianV2SysData.setAppSecret("6bdf29d3afba83bcd111e2ef0d35fce5");
        mockWangDianV2SysData.setShopNo("029");
//        mockWangDianV2SysData.setShopName("API公共测试");
        mockWangDianV2SysData.setWarehouseNo("CK001");
//        mockWangDianV2SysData.setWarehouseName("API公共测试");

        // TODO: 2016-11-07

        String orderNo = SerialNo.create();

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(178770);
        orderItem.setOrderId(orderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("hot123");
        orderItem.setName("婴儿配方奶粉0001");
        orderItem.setCost(0.5);
        orderItem.setPrice(1);
        orderItem.setAmount(2);
        orderItem.setNum(2);
        orderItem.setStandard("测试");
        orderItem.setCustomerId(296);
        orderItem.setGoodBn("hot123");
        orderItem.setGoodId(999999);
        orderItem.setProductId(999999);
        orderItem.setRefundStatus(-1);

        mockOrderItems = new ArrayList<>();
        mockOrderItems.add(orderItem);

        mockOrder = new Order();
        mockOrder.setOrderId(orderNo);
        mockOrder.setMemberId(4542);
        mockOrder.setUserLoginName(UUID.randomUUID().toString());
        mockOrder.setConfirm(1);
        mockOrder.setOrderStatus(0);
        mockOrder.setPayStatus(OrderEnum.PayStatus.PAYED.getCode());
        mockOrder.setShipStatus(0);
        mockOrder.setWeight(100);
        mockOrder.setOrderName("测试商品(蓝色,42码)(10)(×10)");
        mockOrder.setItemNum(1);
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
        mockOrder.setCostItem(10);
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(8);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(10);// 商品费用+商品费用*税率+运费
        mockOrder.setOnlinePayAmount(0);// 商品费用+商品费用*税率+运费

        mockOrder.setPaymentName(OrderEnum.PaymentOptions.WEIXINPAY_V3.getName());
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//货到付款

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayNumber(SerialNo.create());

        mockOrder.setMemo("用户备注");
        mockOrder.setRemark("商家备注");

        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.WANGDIANV2);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockWangDianV2SysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);

    }

}
