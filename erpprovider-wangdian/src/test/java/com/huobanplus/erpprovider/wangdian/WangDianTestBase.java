/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.wangdian;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.wangdian.common.WangDianSysData;
import com.huobanplus.erpprovider.wangdian.config.WangDianTestConfig;
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
@ContextConfiguration(classes = {WangDianTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class WangDianTestBase {

    protected Order mockOrder;

    protected List<OrderItem> mockOrderItems;

    protected WangDianSysData mockWangDianSysData;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    @Before
    public void setUp() {

        mockWangDianSysData = new WangDianSysData();
        mockWangDianSysData.setRequestUrl("http://121.199.38.85/openapi/interface.php");
        mockWangDianSysData.setAppKey("12345");
        mockWangDianSysData.setInterfaceId("youzuntest");
        mockWangDianSysData.setSellerId("dev5");
        mockWangDianSysData.setWarehouseNo("keketest");
        mockWangDianSysData.setShopName("测试店铺");

        // TODO: 2016-11-07

        String orderNo = SerialNo.create();

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(178770);
        orderItem.setOrderId(orderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("interface-test-001");
        orderItem.setName("婴儿配方奶粉0001");
        orderItem.setCost(0.5);
        orderItem.setPrice(1);
        orderItem.setAmount(2);
        orderItem.setNum(2);
        orderItem.setStandard("测试");
        orderItem.setCustomerId(296);
        orderItem.setGoodBn("1901101000");

        mockOrderItems = new ArrayList<>();
        mockOrderItems.add(orderItem);

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
        mockOrder.setItemNum(12);
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
        mockOrder.setCostFreight(0);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(10);// 商品费用+商品费用*税率+运费

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayNumber(SerialNo.create());

        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.SURSUNG);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockWangDianSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);

    }

}
