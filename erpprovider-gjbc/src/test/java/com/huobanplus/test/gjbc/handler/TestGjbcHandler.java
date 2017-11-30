/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.test.gjbc.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.gjbc.handler.GJBCProductHandler;
import com.huobanplus.erpprovider.gjbc.handler.GjbcOrderHandler;
import com.huobanplus.erpprovider.gjbc.response.GjbcInventorySearchListResponse;
import com.huobanplus.erpprovider.gjbc.search.GjbcInventorySearch;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import com.huobanplus.test.gjbc.TestGjbcBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by montage on 2017/6/29.
 */

public class TestGjbcHandler extends TestGjbcBase {


    @Autowired
    private GjbcOrderHandler gjbcOrderHandler;

    private PushNewOrderEvent mockPushNewOrderEvent;
    @Autowired
    private HBOrderHandler hbOrderHandler;

    private String orderInfoJson = "{\"orderId\":\"20170721878358972678\",\"memberId\":22713,\"userLoginName\":\"18958045485\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":39.000,\"suttleWeight\":0.00,\"orderName\":\"娇兰小黑裙我的香氛唇膏 2.8g #010(#010)(1)(×1)\",\"itemNum\":1,\"lastUpdateTime\":\"2017-07-21 17:01:56\",\"createTime\":\"2017-07-21 17:01:56\",\"shipName\":\"王剑南\",\"shipArea\":\"浙江省/杭州市/滨江区\",\"province\":\"浙江省\",\"city\":\"杭州市\",\"district\":\"滨江区\",\"shipAddr\":\"浙江省杭州市滨江区阡陌路智慧e谷\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"18958045485\",\"costItem\":189.000,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":238.800,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"支付宝跨境支付\",\"payType\":12,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2017-07-21 17:01:56\",\"unionOrderId\":\"20170721192128393740\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"321324198901095211\",\"buyerName\":\"王剑南\",\"payNumber\":\"20170721652737240644\",\"taxAmount\":49.800,\"orderItems\":[{\"itemId\":178277,\"orderId\":\"20170721878358972677\",\"unionOrderId\":\"20170721192128393740\",\"goodId\":21195,\"productId\":22865,\"productBn\":\"3346470421578\",\"name\":\"娇兰小黑裙我的香氛唇膏 2.8g #010(#010)(1)\",\"cost\":0.000,\"price\":189.000,\"amount\":189.000,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"3053304100091\",\"standard\":\"#010\",\"brief\":null,\"shipStatus\":0,\"weight\":39.000,\"suttleWeight\":0.00,\"unit\":null,\"refundStatus\":-1,\"brand\":\"无品牌\"}],\"errorMessage\":null,\"payedAmount\":238.800}";


    @Autowired
    private GJBCProductHandler productHandler;

    /**
     * 高捷订单推送测试
     */
    @Test
    public void testPushPlatformOrder() {
        EventResult eventResult = gjbcOrderHandler.pushPlatformOrder(mockOrder, mockGjbcSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushAliPay() {

    }

    @Test
    public void testWeiXinSign() throws UnsupportedEncodingException {
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "wxd930ea5d5a258f4f");
        map.put("mch_id", "10000100");
        map.put("device_info", "1000");
        map.put("body", "test");
        map.put("nonce_str", "ibuaiVcKdpRxkhJA");


/*        map.put("ProductFee","-2996");
        map.put("appId","wx5c8085c6edf32b7d");
        map.put("certId","362322199411050053");
        map.put("certType","IDCARD");
        map.put("customs","HANGZHOU");
        map.put("duty","0");
        map.put("freeType","CNY");
        map.put("mchCustomsNo","3210932722");
        map.put("mchId","1291517501");
        map.put("name","吴雄牛");
        map.put("orderFee","4");
        map.put("outTradeNo","20170110856625924301");
        map.put("subOrderNo","20170110856625924301");
        map.put("transactionId","4006202001201701105815031325");
        map.put("transportFee","3000");*/

        System.out.println("hello word");
        //9A0A8659F005D6984697E2CA0A9CF3B7
        //9A0A8659F005D6984697E2CA0A9CF3B7

        //E150A451892AEC6EC6E4F5D95FAF5D8A
        System.out.println(DtwUtil.weixinBuildSign(map, "192006250b4c09247ec02edce69f6a2d"));

    }


    @Test
    public void testPushOrderAliPay() {
        mockOrder.setOrderId("20161215608041164709");
        mockOrder.setPayNumber("2016121521001004570220367991");
        String orderJson = "{\"orderId\":\"20161215608041164709\",\"memberId\":256362,\"userLoginName\":\"S8AZBYFMDGZ\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"??app????app??(???,XXS)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-12-15 10:24:46\",\"createTime\":\"2016-12-15 10:22:20\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.100,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.100,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":1,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-12-15 10:24:47\",\"unionOrderId\":\"20161215733517365701\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"20161215394589117200\",\"orderItems\":[{\"itemId\":176873,\"orderId\":\"20161215608041164709\",\"unionOrderId\":\"20161215733517365701\",\"productBn\":\"CSXJ0001\",\"name\":\"??app????app??(???,XXS)(1)\",\"cost\":0.100,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901101000\",\"standard\":\"???,XXS\",\"brief\":null,\"shipStatus\":0}],\"errorMessage\":null}";
        Order order = JSON.parseObject(orderJson, Order.class);
        order.setPayNumber("2016121521001004570220367991");
        EventResult eventResult = gjbcOrderHandler.PushOrderAliPay(order, mockGjbcSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushOrderWeiXin() {
        mockOrder.setPayNumber(SerialNo.create());
        mockOrder.setOrderId(SerialNo.create());

        String orderJson = "{\"orderId\":\"20170110856625924301\",\"memberId\":256362,\"userLoginName\":\"15067134475\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":500.000,\"orderName\":\"??????(?,M)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2017-01-10 10:25:03\",\"createTime\":\"2017-01-10 10:24:50\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.150,\"onlinePayAmount\":0.040,\"costFreight\":30.000,\"currency\":\"CNY\",\"finalAmount\":33.740,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"????V3\",\"payType\":9,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2017-01-10 10:25:03\",\"unionOrderId\":\"20170110265180668484\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"吴雄牛\",\"payNumber\":\"4006202001201701105815031325\",\"taxAmount\":3.590,\"orderItems\":[{\"itemId\":177159,\"orderId\":\"20170110856625924301\",\"unionOrderId\":\"20170110265180668484\",\"productBn\":\"XYDCSXJ0013\",\"name\":\"吴雄牛\",\"cost\":0.500,\"price\":0.150,\"amount\":0.150,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901109000\",\"standard\":\"?,M\",\"brief\":null,\"shipStatus\":0,\"weight\":0.0,\"unit\":null}],\"errorMessage\":null}";
        String orderJson2 = "{\"orderId\":\"20161207974540631835\",\"memberId\":256362,\"userLoginName\":\"S8AZBYFMDGZ\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"??app????app????app????app????app????app????app????app????app??(???,XXS)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-12-07 17:42:01\",\"createTime\":\"2016-12-07 17:41:30\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.100,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.100,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"????V3\",\"payType\":9,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-12-07 17:42:01\",\"unionOrderId\":\"20161207268060346380\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"20161207919917848518\",\"orderItems\":[{\"itemId\":176853,\"orderId\":\"20161207974540631835\",\"unionOrderId\":\"20161207268060346380\",\"productBn\":\"CSXJ0001\",\"name\":\"??app????app????app????app????app????app????app????app????app??(???,XXS)(1)\",\"cost\":0.100,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901101000\",\"standard\":\"???,XXS\",\"brief\":null,\"shipStatus\":0}],\"errorMessage\":null}";
        Order order = JSON.parseObject(orderJson, Order.class);
        System.out.println(order.toString());
        Order order2 = JSON.parseObject(orderJson2, Order.class);
        order2.setPayNumber("4006202001201612072076863542");
        order2.setPayType(9);
        EventResult eventResult = gjbcOrderHandler.PushOrderWeiXin(order, mockGjbcSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }


    @Test
    public void testPushOrderCustom() {
//        Order order = JSON.parseObject(orderInfoJson, Order.class);
//        for (int i = 0; i < 1; i++) {
////            EventResult eventResult = gjbcOrderHandler.PushOrderCustom(mockOrder, mockGjbcSysData);
//            System.out.println(eventResult.getResultCode());
//            System.out.println(eventResult.getResultMsg());
//        }
    }


    @Test
    public void testPushOrder() {

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        for (int i = 0; i < 10; i++) {

            EventResult result = gjbcOrderHandler.pushOrder(mockPushNewOrderEvent);
            System.out.println(result.getData());
            System.out.println(result.getResultMsg());
            System.out.println(result.getResultCode());
        }
    }

    @Test
    public void testGetProductStock() throws UnsupportedEncodingException {
        List<String> skus = new ArrayList<>();
        skus.add("888066010658");
        skus.add("717334151000");
        GjbcInventorySearch gjbcInventorySearch = new GjbcInventorySearch();
        gjbcInventorySearch.setGood_barcode(skus.toArray(new String[]{}));
        EventResult nextEventResult = productHandler.getProductInventoryInfo(mockGjbcSysData, gjbcInventorySearch);
        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            List<GjbcInventorySearchListResponse> gjbcInventorySearchListResponses = JSON.parseArray(nextEventResult.getData().toString(), GjbcInventorySearchListResponse.class);
            System.out.println(gjbcInventorySearchListResponses.size());
        }
    }
}
