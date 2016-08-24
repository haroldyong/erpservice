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

        mockOrderNo = create();

        mockDtwSysData = new DtwSysData();
        mockDtwSysData.setPassKey("e9af47d2-962b-470b-8306-08a8471d0ce0");
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

        mockOrderItems = new ArrayList<>();

        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setItemId(178770);
        mockOrderItem.setOrderId(mockOrderNo);
        mockOrderItem.setUnionOrderId("2016062455965373");
        mockOrderItem.setProductBn("2969cC2hC4");
        mockOrderItem.setName("跨境商品(蓝色,42码)(10)");
        mockOrderItem.setCost(0.5);
        mockOrderItem.setPrice(1);
        mockOrderItem.setAmount(10);
        mockOrderItem.setNum(5);
        mockOrderItem.setStandard("蓝色,42码");
        mockOrderItem.setCustomerId(296);

        mockOrderItems.add(mockOrderItem);


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
        mockOrder.setItemNum(5);
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
        mockOrder.setCostFreight(0.0);
        mockOrder.setCurrency("CYN");
        mockOrder.setFinalAmount(5);

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));

        mockOrder.setPayNumber("fdsafasfsfasfs");

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
}
