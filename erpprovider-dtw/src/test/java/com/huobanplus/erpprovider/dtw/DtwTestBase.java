/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.config.DtwTestConfig;
import com.huobanplus.erpprovider.dtw.util.Arith;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by wuxiongliu on 2016/6/16.
 */

@ActiveProfiles("test")
@ContextConfiguration(classes = {DtwTestConfig.class})
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class DtwTestBase {

    protected Order mockOrder;

    protected DtwSysData mockDtwSysData;

    protected List<OrderItem> mockOrderItems;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    private String mockOrderNo;

    @Before
    public void setUp() {

        mockOrderNo = "20160825113538530780";

        mockDtwSysData = new DtwSysData();
        mockDtwSysData.setPassKey("1c78cac2-b8b7-4764-9045-4810d3ef20e9");
        mockDtwSysData.setECommerceName("kdian.co.ltd");
        mockDtwSysData.setECommerceCode("9133010832821677XM");

        mockDtwSysData.setCompanyCode("330196T018");
        mockDtwSysData.setCompanyName("杭州美伴网络科技有限公司");
        mockDtwSysData.setRequestUrl("http://logistics.dtw.com.cn:8080/QBT/api");

        mockDtwSysData.setAliPartner("2088211251545121");

        mockDtwSysData.setWeixinKey("hzmeibanwangluokejiyouxiangongsi");
        mockDtwSysData.setWeixinMchId("1342661701");
        mockDtwSysData.setWeiXinAppId("gh_4dbf09a0a18e");

        mockDtwSysData.setSenderName("吴雄琉");
        mockDtwSysData.setSenderAddr("浙江省杭州市滨江区");

        mockDtwSysData.setTaxRate(11);

        int[] number = {1, 2, 3, 4};
        double[] price = {3, 4, 5, 3};
        double costFreight = 5;
        double finalAmount = 0.0;
        for (int i = 0; i < number.length; i++) {
            double goodPrice = Arith.mul(price[i], number[i]);
            double taxPrice = Arith.mul(goodPrice, mockDtwSysData.getTaxRate() / 100);
            double tempPrice = Arith.add(goodPrice, taxPrice);
            finalAmount = Arith.add(finalAmount, tempPrice);
        }
        finalAmount = Arith.add(finalAmount, costFreight);

        mockOrderItems = createOrderItem(price, number);

        mockOrder = new Order();
        mockOrder.setOrderId(mockOrderNo);
        mockOrder.setMemberId(4542);
        mockOrder.setUserLoginName("18705153967");
        mockOrder.setConfirm(1);
        mockOrder.setOrderStatus(0);
        mockOrder.setPayStatus(1);
        mockOrder.setShipStatus(0);
        mockOrder.setWeight(100);
        mockOrder.setOrderName("跨境商品(蓝色,42码)(10)(×10)");
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
        mockOrder.setShipMobile("18705153967");
        mockOrder.setCostItem(10);
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(costFreight);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(finalAmount);// 商品费用+商品费用*税率+运费

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));

        mockOrder.setPayNumber("1000039301521608242043325704");

        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.DTW);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockDtwSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);

    }

    public static String create() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        Random random = new Random();
        String code = "";
        //随机产生6位数字的字符串
        for (int i = 0; i < 6; i++) {
            String rand = String.valueOf(random.nextInt(10));
            code += rand;
        }
        return date + code;
    }

    public List<OrderItem> createOrderItem(double[] prices, int[] number) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(178770);
            orderItem.setOrderId(mockOrderNo);
            orderItem.setUnionOrderId("2016062455965373");
            orderItem.setProductBn("CSXJ0001");
            orderItem.setName("婴儿配方奶粉0001");
            orderItem.setCost(0.5);
            orderItem.setPrice(prices[i]);
            orderItem.setAmount(10);
            orderItem.setNum(number[i]);
            orderItem.setStandard("测试");
            orderItem.setCustomerId(296);
            orderItem.setGoodBn("1901101000");
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
