/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.*;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by wuxiongliu on 2016/6/16.
 */
public class DtwOrderHandlerTest extends DtwTestBase {

    private DtwSysData mockDtwSysData;

    @Autowired
    private DtwOrderHandler dtwOrderHandler;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    private PushNewOrderEvent mockPushNewOrderEvent;

    @Before
    public void setUp() {

        mockDtwSysData = new DtwSysData();
        mockDtwSysData.setPassKey("tesaa");
        mockDtwSysData.setECommerceName("kdian.co.ltd");
        mockDtwSysData.setECommerceCode("9133010832821677XM");

        mockDtwSysData.setCompanyCode("330196T018");
        mockDtwSysData.setCompanyName("杭州美伴网络科技有限公司");
        mockDtwSysData.setRequestUrl("http://logistics.dtw.com.cn:8080/QBT/api");


        mockDtwSysData.setAliPartner("2088211251545121");

        mockDtwSysData.setWeixinKey("hzmeibanwangluokejiyouxiangongsi");
        mockDtwSysData.setWeixinMchId("1342661701");
        mockDtwSysData.setWeiXinAppId("gh_4dbf09a0a18e");

        mockOrderItems = new ArrayList<>();

        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setNum(5);
        mockOrderItem.setName("奶粉");
        mockOrderItem.setOrderId("8070");
        mockOrderItem.setProductBn("3872824-ecc4090b639c47f89b453980923afb8e");


        mockOrderItems.add(mockOrderItem);


        mockOrder = new Order();
        mockOrder.setOrderId("000000001222");
        mockOrder.setMemberId(1761390);
        mockOrder.setShipName("吴雄琉");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setShipEmail("");
        mockOrder.setProvince("浙江省");
        mockOrder.setCity("杭州市");
        mockOrder.setDistrict("滨江区");
        mockOrder.setShipAddr("浙江省杭州市滨江区阡陌路智慧E谷B幢4楼火图科技");
        mockOrder.setBuyerPid("362322199411050053");
        mockOrder.setBuyerName("吴雄琉");
        mockOrder.setLogiCode("shunfeng");
        mockOrder.setLogiName("顺丰快递");
        mockOrder.setCurrency("RMB");
        mockOrder.setPaymentName("支付宝");


        mockOrder.setPayTime(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockDtwSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);

    }


    @Test
    public void testStockQuery() {
        DtwStockSearch dtwStockSearch = new DtwStockSearch();
        dtwStockSearch.setPassKey(mockDtwSysData.getPassKey());
        dtwStockSearch.setPartNo("test");
        dtwStockSearch.setECommerceName(mockDtwSysData.getECommerceName());
        dtwStockSearch.setECommerceCode(mockDtwSysData.getECommerceCode());
        EventResult result = dtwOrderHandler.stockQuery(dtwStockSearch, mockDtwSysData);
        System.out.println(result.getData());
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }

    @Test
    public void testWayBill() {
        DtwWayBill dtwWayBill = new DtwWayBill();
        EventResult result = dtwOrderHandler.wayBill(dtwWayBill, mockDtwSysData);
        System.out.println(result.getData());
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }





    @Test
    public void testSign() throws UnsupportedEncodingException {
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "wxd930ea5d5a258f4f");
        map.put("mch_id", "10000100");
        map.put("device_info", "1000");
        map.put("body", "test");
        map.put("nonce_str", "ibuaiVcKdpRxkhJA");
        map.put("test", "");
        map.put("test", null);

        System.out.println(DtwUtil.weixinBuildSign(map, "192006250b4c09247ec02edce69f6a2d"));
    }

    @Test
    public void testWebservice() {
        String param = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.newyork.zjport.gov.cn/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <ws:receive>\n" +
                "         <!--Optional:-->\n" +
                "         <content>?</content>\n" +
                "         <!--Optional:-->\n" +
                "         <msgType>PERSONAL_GOODS_DECLAR</msgType>\n" +
                "         <!--Optional:-->\n" +
                "         <dataDigest>?</dataDigest>\n" +
                "         <!--Optional:-->\n" +
                "         <sendCode>9133010832821677XM</sendCode>\n" +
                "      </ws:receive>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        HttpResult result = HttpClientUtil.getInstance().webServicePost("http://122.224.230.4:18003/newyorkWS/ws/ReceiveEncryptDeclare?wsdl", param);
        System.out.println("\n*********************");
        System.out.println(result.getHttpContent());
        System.out.println(result.getHttpStatus());
        System.out.println("\n*********************");
    }

    @Test
    public void testXml() throws JsonProcessingException {
        CustomOrder customOrder = new CustomOrder();
        CustomHead customHead = new CustomHead();
        CustomBody customBody = new CustomBody();
        customOrder.setHead(customHead);
        customOrder.setBody(customBody);

        CustomOrderInfo customOrderInfo = new CustomOrderInfo();
        CustomSign customSign = new CustomSign();
        CustomOrderHead customOrderHead = new CustomOrderHead();
        CustomOrderDetail customOrderDetail = new CustomOrderDetail();
        List<CustomOrderDetail> customOrderDetails = new ArrayList<>();
        customOrderDetails.add(customOrderDetail);
        customOrderInfo.setCustomSign(customSign);
        customOrderInfo.setCustomOrderHead(customOrderHead);


        customOrderInfo.setCustomOrderDetailList(customOrderDetails);
        CustomOrderInfoList customOrderInfoList = new CustomOrderInfoList();
        customOrderInfoList.setCustomOrderInfo(customOrderInfo);


        customBody.setOrerInfoList(customOrderInfoList);


        String xmlResult = new XmlMapper().writeValueAsString(customOrder);
        int start = xmlResult.indexOf("<jkfOrderDetail>");

        int end = xmlResult.lastIndexOf("</jkfOrderDetail>");
        String firstPane = xmlResult.substring(0, start);
        String middlePane = xmlResult.substring(start + 16, end);
        String lastPane = xmlResult.substring(end + 17);

        System.out.println("\n*********************");
        System.out.println(firstPane);
        System.out.println(middlePane);
        System.out.println(lastPane);
        System.out.println("\n*********************");
    }


    @Rollback(value = false)
    @Test
    public void testPushOrder() {

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        EventResult result = dtwOrderHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(result.getData());
        System.out.println(result.getResultMsg());
        System.out.println(result.getResultCode());
    }

    @Test
    public void testPushPersonalDeclareOrder() {

        EventResult eventResult = dtwOrderHandler.pushPersonalDeclareOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushPlatformOrder() {
        EventResult eventResult = dtwOrderHandler.pushPlatformOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushAliPayOrder() {
        AliCustomer aliCustomer = new AliCustomer();
        aliCustomer.setOutRequestNo("2016102510252");
        aliCustomer.setAmount("100");
        aliCustomer.setCustomsPlace("hangzhou");
        aliCustomer.setIsSplit("no");
        aliCustomer.setMerchantCustomsCode("hangzhou");
        aliCustomer.setMerchantCustomsName("hangzhou");
        aliCustomer.setTradeNo("tradNo");
        EventResult eventResult = dtwOrderHandler.pushAliPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushWeixinPayOrder() {
        WeixinCustomer weixinCustomer = new WeixinCustomer();
        weixinCustomer.setMchId(mockDtwSysData.getWeixinMchId());
        weixinCustomer.setOrderFee(1000);
        weixinCustomer.setMchCustomsNo("huoban");
        weixinCustomer.setFeeType("CNY");
        weixinCustomer.setAppid(mockDtwSysData.getWeiXinAppId());
        weixinCustomer.setCustoms("HANGZHOU");
        weixinCustomer.setOutTradeNo("201623021562");
        weixinCustomer.setTransactionId("1111111111111111111111111111");
        weixinCustomer.setTransactionId("uuid");
        EventResult eventResult = dtwOrderHandler.pushWeixinPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushCustomOrder() {
        EventResult eventResult = dtwOrderHandler.pushCustomOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());

    }

}
