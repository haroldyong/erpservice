package com.huobanplus.test.gjbc;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.util.GjbcConstant;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.test.gjbc.config.TestGjbcConfig;
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
 * 高捷 测试类
 *
 * Created by montage on 2017/6/29.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestGjbcConfig.class})
@Transactional
public class TestGjbcBase {


    protected Order mockOrder;

    protected GjbcSysData mockGjbcSysData;

    protected List<OrderItem> mockOrderItems;

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    private String mockOrderNo;

    private int itemNumber = 5;

    @Before
    public void setUp(){
        mockOrderNo = SerialNo.create();

        mockGjbcSysData = new GjbcSysData();
        mockGjbcSysData.setName("huoju");
        mockGjbcSysData.setKey("a6f0d5db57cc55a5c22c175844665e60");
        mockGjbcSysData.setECommerceName("扬州市新扬达进出口有限公司");
        mockGjbcSysData.setECommerceCode("3210932722");
        mockGjbcSysData.setAliPartner("2088421965473023");
        mockGjbcSysData.setAliKey("k48u3xqezrpwhpuv8al265p515uhclr5");
        mockGjbcSysData.setRequestUrl(GjbcConstant.REQUEST_URL);


        mockGjbcSysData.setWeixinKey("192006250b4c09247ec02edce69f6a2d");
        mockGjbcSysData.setWeixinMchId("1291517501");
        mockGjbcSysData.setWeiXinAppId("wx5c8085c6edf32b7d");

        mockGjbcSysData.setRsaPublicKey("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJFlixAXFom5VY6TZEveQ8KmiCbfBdj8zxg52WUEzX5VNaxqce2XU7N4rTZm4WFJjLgwJmMQK5VazIo46mr5bo8CAwEAAQ==");
        mockGjbcSysData.setRsaPrivateKey("MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAkWWLEBcWiblVjpNkS95DwqaIJt8F2PzPGDnZZQTNflU1rGpx7ZdTs3itNmbhYUmMuDAmYxArlVrMijjqavlujwIDAQABAkBkq7tCs8m+KF4N7w3V3hpqryW8TFVfLYQy0PMuF8o0urUZ07S0AoOTvlmKTKRThn4rE4/oh7m3p3SJ5jyJpTAxAiEA+QUyLngSN5M95r+Mj/2DyXMb2LnblhkiDOJlWdzPWUkCIQCVeNAX89Q92xBNsixn2o7U/hv0GBC9xRrXYGkN0SYhFwIhAOFUSFIwKBvNyoeP8HsipSuWUy5LD13EpEEQYzFrUtyxAiBSOFyvcE6ln+T9+C55Cj5bZ1RVFw/Oc6fqJXxkP1IsDQIgIZBDSR1DOMweY3XPD4+8+o1koFNlgNjq2uCvB7t42Ms=");
        mockGjbcSysData.setAesKey("02oTDtoGk+2XpY0WOglyog==");
        mockGjbcSysData.setSenderInfo("吴雄琉,浙江省,杭州市,滨江区,浙江省杭州市滨江区智慧e谷,15067134475");
        mockGjbcSysData.setCustomUrl("");

        mockGjbcSysData.setPName("京东");
        mockGjbcSysData.setPWeb("www.jd.com");

        double itemPirce = 32;
        int itemNum = 3;
        double itemAmount = itemPirce * itemNum;
        double orderCostFreight = 10;
        double taxAmount = 5;
        double finalAmount = taxAmount + itemAmount + orderCostFreight;


        mockOrderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setGoodId(1);
        orderItem.setGoodBn("1102104200000");
        orderItem.setItemId(178770);
        orderItem.setOrderId(mockOrderNo);
        orderItem.setUnionOrderId("2016062455965373");
        orderItem.setProductBn("381519004711");
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
        orderItem.setBrand("指甲刀");
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
        mockOrder.setShipAddr("浙江省^^^杭州市^^^滨江区阡陌路^^^智慧E谷B幢4楼火图科技");
        mockOrder.setShipZip("310000");
        mockOrder.setShipTel("");
        mockOrder.setShipEmail("");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setCostItem(itemAmount);
        mockOrder.setCurrency("142");
//        mockOrder.setOnlinePayAmount();
        mockOrder.setCostFreight(orderCostFreight);
        mockOrder.setFinalAmount(finalAmount);// 商品费用+税费+运费

        mockOrder.setPaymentName("微信");
        mockOrder.setPayType(OrderEnum.PaymentOptions.WEIXINPAY_V3.getCode());//微信支付V3

        mockOrder.setBuyerName("Riven");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));

        mockOrder.setPayNumber(SerialNo.create());
        mockOrder.setTaxAmount(taxAmount);

        mockOrder.setOrderItems(mockOrderItems);
        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.GJBC);
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockGjbcSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);
    }
}
