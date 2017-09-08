package com.huobanplus.test.gjbbc.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.test.gjbbc.config.TestGjbbcConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hxh on 2017-08-15.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestGjbbcConfig.class})
@Transactional
public class TestGjbbcBase {
    protected Order mockOrder;

    protected GjbbcSysData mockGjbbcSysData;

    protected List<OrderItem> mockOrderItems;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    private String mockOrderNo;

    private int itemNumber = 5;

    @Before
    public void setUp() {
        mockOrderNo = SerialNo.create();

        mockGjbbcSysData = new GjbbcSysData();
        mockGjbbcSysData.setName("huoju");
        mockGjbbcSysData.setKey("a6f0d5db57cc55a5c22c175844665e60");
        mockGjbbcSysData.setECommerceName("杭州伙聚网络技术有限公司");
        mockGjbbcSysData.setECommerceCode("3301964K02");
        mockGjbbcSysData.setAliPartner("2088421965473023");
        mockGjbbcSysData.setAliKey("k48u3xqezrpwhpuv8al265p515uhclr5");
        mockGjbbcSysData.setRequestUrl("http://test.goldjet.com.cn/api/index.php?act=bbc&op=bbc_order");


        mockGjbbcSysData.setWeixinKey("192006250b4c09247ec02edce69f6a2d");
        mockGjbbcSysData.setWeixinMchId("1291517501");
        mockGjbbcSysData.setWeiXinAppId("wx5c8085c6edf32b7d");
        mockGjbbcSysData.setWarehouseCode("HT_HKGJ");
        mockGjbbcSysData.setPWeb("http://cosytest.51flashmall.com");
        mockGjbbcSysData.setSenderInfo("王剑南,15958039934,142,330108,杭州市滨江区阡陌路482号");


        double itemPirce = 32.25;
        int itemNum = 3;
        double itemAmount = itemPirce * itemNum;
        double orderCostFreight = 10;
        double taxAmount = 5;
        double finalAmount = itemAmount;


        mockOrderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setGoodId(1);
        orderItem.setGoodBn("0002104200000");
        orderItem.setItemId(178770);
        orderItem.setOrderId(mockOrderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("1658_6931987042704");
        orderItem.setName("AUSSIE袋鼠 3分钟奇迹发膜 236ml");
        orderItem.setCost(0.5);
        orderItem.setPrice(itemPirce);
        orderItem.setAmount(itemAmount);
        orderItem.setNum(itemNum);
        orderItem.setStandard("测试");
        orderItem.setCustomerId(296);
        orderItem.setBrief("AUSSIE袋鼠 3分钟奇迹发膜 236ml");
        orderItem.setWeight(250.23);
        orderItem.setSuttleWeight(240.23);
        orderItem.setBrand("AUSSIE袋鼠");
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
        mockOrder.setCreateTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setShipName("Riven");
        mockOrder.setShipArea("浙江省/杭州市/滨江区");
        mockOrder.setProvince("浙江省");
        mockOrder.setCity("杭州市");
        mockOrder.setDistrict("滨江区");
        mockOrder.setShipAddr("滨江区阡陌路^^^智慧E谷B幢4楼火图科技");
        mockOrder.setShipZip("310000");
        mockOrder.setShipTel("");
        mockOrder.setShipEmail("");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setCostItem(itemAmount);
        mockOrder.setCurrency("142");
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(orderCostFreight);
        mockOrder.setFinalAmount(finalAmount);// 商品费用+税费+运费
        mockOrder.setPayType(1);
        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("王建南");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setPayNumber(SerialNo.create());
        mockOrder.setTaxAmount(taxAmount);

        mockOrder.setOrderItems(mockOrderItems);
        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.GJBBC);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockGjbbcSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);
    }
}
