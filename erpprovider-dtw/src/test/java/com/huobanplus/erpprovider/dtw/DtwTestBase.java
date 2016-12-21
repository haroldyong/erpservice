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

    private int itemNumber = 5;

    @Before
    public void setUp() {


        mockOrderNo = SerialNo.create();//"20160825113538530780";

        mockDtwSysData = new DtwSysData();
        mockDtwSysData.setPassKey("1c78cac2-b8b7-4764-9045-4810d3ef20e9");
        mockDtwSysData.setECommerceName("扬州市新扬达进出口有限公司");
        mockDtwSysData.setECommerceCode("3210932722");

//        mockDtwSysData.setCompanyCode("3210932722");
//        mockDtwSysData.setCompanyName("扬州市新扬达进出口有限公司");
        mockDtwSysData.setRequestUrl("http://logistics.dtw.com.cn:8080/QBT/api");

        mockDtwSysData.setAliPartner("2088421965473023");
        mockDtwSysData.setAliKey("k48u3xqezrpwhpuv8al265p515uhclr5");

        mockDtwSysData.setWeixinKey("hzmeibanwangluokejiyouxiangongsi");
        mockDtwSysData.setWeixinMchId("1342661701");
        mockDtwSysData.setWeiXinAppId("gh_4dbf09a0a18e");

        mockDtwSysData.setRsaPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJFlixAXFom5VY6TZEveQ8KmiCbfBdj8zxg52WUEzX5VNaxqce2XU7N4rTZm4WFJjLgwJmMQK5VazIo46mr5bo8CAwEAAQ==");
        mockDtwSysData.setRsaPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkWWLEBcWiblVjpNkS95DwqaIJt8F2PzPGDnZZQTNflU1rGpx7ZdTs3itNmbhYUmMuDAmYxArlVrMijjqavlujwIDAQABAkBkq7tCs8m+KF4N7w3V3hpqryW8TFVfLYQy0PMuF8o0urUZ07S0AoOTvlmKTKRThn4rE4/oh7m3p3SJ5jyJpTAxAiEA+QUyLngSN5M95r+Mj/2DyXMb2LnblhkiDOJlWdzPWUkCIQCVeNAX89Q92xBNsixn2o7U/hv0GBC9xRrXYGkN0SYhFwIhAOFUSFIwKBvNyoeP8HsipSuWUy5LD13EpEEQYzFrUtyxAiBSOFyvcE6ln+T9+C55Cj5bZ1RVFw/Oc6fqJXxkP1IsDQIgIZBDSR1DOMweY3XPD4+8+o1koFNlgNjq2uCvB7t42Ms=");
        mockDtwSysData.setAesKey("02oTDtoGk+2XpY0WOglyog==");

        double itemPirce = 10;
        int itemNum = 3;
        double itemAmount = itemPirce * itemNum;

        double orderCostFreight = 10;
        double taxAmount = 5;
        double finalAmount = taxAmount + itemAmount + orderCostFreight;

        mockOrderItems = new ArrayList<>();

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(178770);
        orderItem.setOrderId(mockOrderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("CSXJ0001");
        orderItem.setName("婴儿配方奶粉0001");
        orderItem.setCost(0.5);
        orderItem.setPrice(itemPirce);
        orderItem.setAmount(itemAmount);
        orderItem.setNum(itemNum);
        orderItem.setStandard("测试");
        orderItem.setCustomerId(296);
        orderItem.setGoodBn("1901101000");
        mockOrderItems.add(orderItem);


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
        mockOrder.setCostItem(itemAmount);
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(orderCostFreight);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(finalAmount);// 商品费用+税费+运费

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));

        mockOrder.setPayNumber(SerialNo.create());
        mockOrder.setTaxAmount(taxAmount);

        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.DTW);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockDtwSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);

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
            orderItem.setAmount(Arith.mul(prices[i], number[i]));
            orderItem.setNum(number[i]);
            orderItem.setStandard("测试");
            orderItem.setCustomerId(296);
            orderItem.setGoodBn("1901101000");
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public int[] createRandomNum() {
        Random random = new Random();
        int[] num = new int[itemNumber];
        for (int i = 0; i < itemNumber; i++) {
            num[i] = random.nextInt(5) + 1;
        }
        return num;
    }
}
