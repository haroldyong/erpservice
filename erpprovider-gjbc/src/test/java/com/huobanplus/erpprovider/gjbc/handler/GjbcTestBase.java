package com.huobanplus.erpprovider.gjbc.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.config.GjbcTestConfig;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hxh on 2017-06-29.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {GjbcTestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class GjbcTestBase {

    private Order mockOrder;

    private GjbcSysData gjbcSysData;

    private List<OrderItem> mockOrderItems;
    private String mockOrderNo;
    private PushNewOrderEvent mockPushNewOrderEvent;
    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    @Autowired
    private GjbcOrderHandler gjbcOrderHandler;

    @Before
    public void setInfo() {
        gjbcSysData = new GjbcSysData();
        gjbcSysData.setKey("a6f0d5db57cc55a5c22c175844665e60");
        gjbcSysData.setName("huoju");
        gjbcSysData.setRequestUrl("http://test.goldjet.com.cn/api/index.php?act=order_bc&op=order");
        gjbcSysData.setECommerceName("扬州市新扬达进出口有限公司");
        gjbcSysData.setECommerceCode("3210932722");
        gjbcSysData.setPWeb("www.jd.com");
        gjbcSysData.setAliPartner("2088421965473023");
        gjbcSysData.setAliKey("k48u3xqezrpwhpuv8al265p515uhclr5");

        gjbcSysData.setWeixinKey("a7b7e7e043d28c35ef98b15e7db503d5");
        gjbcSysData.setWeixinMchId("1335070101");
        gjbcSysData.setWeiXinAppId("wxfc149b79a2b3dcd7");

        gjbcSysData.setWeixinKey("abcdefg123456789cosyljcosyljcosy");
        gjbcSysData.setWeixinMchId("1291517501");
        gjbcSysData.setWeiXinAppId("wx5c8085c6edf32b7d");
        gjbcSysData.setPName("杭州火图科技");
        gjbcSysData.setRsaPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJFlixAXFom5VY6TZEveQ8KmiCbfBdj8zxg52WUEzX5VNaxqce2XU7N4rTZm4WFJjLgwJmMQK5VazIo46mr5bo8CAwEAAQ==");
        gjbcSysData.setRsaPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkWWLEBcWiblVjpNkS95DwqaIJt8F2PzPGDnZZQTNflU1rGpx7ZdTs3itNmbhYUmMuDAmYxArlVrMijjqavlujwIDAQABAkBkq7tCs8m+KF4N7w3V3hpqryW8TFVfLYQy0PMuF8o0urUZ07S0AoOTvlmKTKRThn4rE4/oh7m3p3SJ5jyJpTAxAiEA+QUyLngSN5M95r+Mj/2DyXMb2LnblhkiDOJlWdzPWUkCIQCVeNAX89Q92xBNsixn2o7U/hv0GBC9xRrXYGkN0SYhFwIhAOFUSFIwKBvNyoeP8HsipSuWUy5LD13EpEEQYzFrUtyxAiBSOFyvcE6ln+T9+C55Cj5bZ1RVFw/Oc6fqJXxkP1IsDQIgIZBDSR1DOMweY3XPD4+8+o1koFNlgNjq2uCvB7t42Ms=");
        gjbcSysData.setAesKey("02oTDtoGk+2XpY0WOglyog==");
        gjbcSysData.setSenderInfo("吴雄琉,杭州市,浙江省杭州市滨江区智慧e谷,15067134475,142");
        double itemPirce = 10;
        int itemNum = 3;
        double itemAmount = itemPirce * itemNum;
        mockOrderNo = SerialNo.create();
        double orderCostFreight = 10;
        double taxAmount = 5;
        Order order = new Order();
        double finalAmount = taxAmount + itemAmount + orderCostFreight;
        mockOrderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(178770);
        orderItem.setOrderId(mockOrderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("XYDCSXJ0013");
        orderItem.setBrief("1kg");
        orderItem.setName("综合维生素片");
        orderItem.setCost(0.5);

        orderItem.setPrice(itemPirce);
        orderItem.setAmount(itemAmount);
        orderItem.setNum(itemNum);
        orderItem.setStandard("测试");
        orderItem.setCustomerId(296);
        orderItem.setGoodBn("1901109000");
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
        mockOrder.setShipAddr("浙江省^^^杭州市^^^滨江区^^^阡陌路482号智慧E谷B幢4楼火图科技");
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
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.GJBC);
        mockErpInfo.setSysDataJson(JSON.toJSONString(gjbcSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(7297);


    }

    @Test
    public void testPushPlatOrder() {
        EventResult eventResult = gjbcOrderHandler.pushPlatformOrder(mockOrder, gjbcSysData);
        System.out.println("code:" + eventResult.getResultCode());
        System.out.println("data:" + eventResult.getData());
        System.out.println("msg:" + eventResult.getResultMsg());
    }

    @Test
    public void testPushOrderCustom() {
        EventResult eventResult = gjbcOrderHandler.PushOrderCustom(mockOrder, gjbcSysData);
        System.out.println("code:" + eventResult.getResultCode());
        System.out.println("msg:" + eventResult.getResultMsg());

    }

    @Test
    public void testPushOrderAliPay() {
        EventResult eventResult = gjbcOrderHandler.PushOrderAliPay(mockOrder, gjbcSysData);
        System.out.println("code:" + eventResult.getResultCode());
        System.out.println("msg:" + eventResult.getResultMsg());
        System.out.println("data:" + eventResult.getData());
    }

    @Test
    public void testPushOrder() {
        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        EventResult result = gjbcOrderHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(result.getData());
        System.out.println(result.getResultMsg());
        System.out.println(result.getResultCode());

    }
}
