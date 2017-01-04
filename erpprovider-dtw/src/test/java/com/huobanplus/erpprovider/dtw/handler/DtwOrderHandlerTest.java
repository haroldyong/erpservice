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
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.common.DtwConstant;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwWayBill;
import com.huobanplus.erpprovider.dtw.util.AESUtil;
import com.huobanplus.erpprovider.dtw.util.Arith;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.dtw.util.RSAUtil;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by wuxiongliu on 2016/6/16.
 */
public class DtwOrderHandlerTest extends DtwTestBase {

    @Autowired
    private DtwOrderHandler dtwOrderHandler;

    private PushNewOrderEvent mockPushNewOrderEvent;

    private String orderInfoJson = "{\"orderId\":\"2016082966169442\",\"memberId\":17423,\"userLoginName\":\"15868807873\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":3500.000,\"orderName\":\"??????2(??,42?)(7)(?7)\",\"itemNum\":7,\"lastUpdateTime\":\"2016-08-29 17:09:49\",\"createTime\":\"2016-08-29 17:09:49\",\"shipName\":\"???\",\"shipArea\":\"???/???/???\",\"province\":\"???\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"????????????????????e?\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15868807873\",\"costItem\":0.700,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.700,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-08-29 17:09:49\",\"unionOrderId\":\"2016082976823811\",\"receiveStatus\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"orderItems\":[{\"itemId\":14701,\"orderId\":\"2016082966169442\",\"unionOrderId\":\"2016082976823811\",\"productBn\":\"CSXJ0005\",\"name\":\"??????2(??,42?)(7)\",\"cost\":5.000,\"price\":0.100,\"amount\":0.700,\"num\":7,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"0402210000\",\"standard\":\"??,42?\",\"brief\":null,\"shipStatus\":0}]}";


//    @Test
//    public void testStockQuery() {
//        DtwStockSearch dtwStockSearch = new DtwStockSearch();
//        dtwStockSearch.setPassKey(mockDtwSysData.getPassKey());
//        dtwStockSearch.setPartNo("test");
//        dtwStockSearch.setECommerceName(mockDtwSysData.getECommerceName());
//        dtwStockSearch.setECommerceCode(mockDtwSysData.getECommerceCode());
//        EventResult result = dtwOrderHandler.stockQuery(dtwStockSearch, mockDtwSysData);
//        System.out.println(result.getData());
//        System.out.println(result.getResultCode());
//        System.out.println(result.getResultMsg());
//    }


    @Test
    public void testWayBill() {
        DtwWayBill dtwWayBill = new DtwWayBill();
        EventResult result = dtwOrderHandler.wayBill(dtwWayBill, mockDtwSysData);
        System.out.println(result.getData());
        System.out.println(result.getResultCode());
        System.out.println(result.getResultMsg());
    }


    @Test
    public void testWeixinSign() throws UnsupportedEncodingException {
        Map<String, Object> map = new TreeMap<>();
        map.put("appid", "wxd930ea5d5a258f4f");
        map.put("mch_id", "10000100");
        map.put("device_info", "1000");
        map.put("body", "test");
        map.put("nonce_str", "ibuaiVcKdpRxkhJA");
        map.put("test", "");
        map.put("test", null);

        //9A0A8659F005D6984697E2CA0A9CF3B7
        //9A0A8659F005D6984697E2CA0A9CF3B7
        System.out.println(DtwUtil.weixinBuildSign(map, "192006250b4c09247ec02edce69f6a2d"));
    }

    @Test
    public void testPushOrder() {

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        for (int i = 0; i < 10; i++) {

            EventResult result = dtwOrderHandler.pushOrder(mockPushNewOrderEvent);
            System.out.println(result.getData());
            System.out.println(result.getResultMsg());
            System.out.println(result.getResultCode());
        }
    }

    @Test
    public void testPushPersonalDeclareOrder() {

        mockOrder.setOrderId("20161229140157038627");
        EventResult eventResult = dtwOrderHandler.pushPersonalDeclareOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushPlatformOrder() {
        for (int i = 0; i < 1; i++) {
            EventResult eventResult = dtwOrderHandler.pushPlatformOrder(mockOrder, mockDtwSysData);
            System.out.println(eventResult.getResultCode());
            System.out.println(eventResult.getResultMsg());
        }
    }

    @Test
    public void testPushAliPayOrder() {
        mockOrder.setOrderId("20161215608041164709");
        mockOrder.setPayNumber("2016121521001004570220367991");
        String orderJson = "{\"orderId\":\"20161215608041164709\",\"memberId\":256362,\"userLoginName\":\"S8AZBYFMDGZ\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"??app????app??(???,XXS)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-12-15 10:24:46\",\"createTime\":\"2016-12-15 10:22:20\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.100,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.100,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":1,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-12-15 10:24:47\",\"unionOrderId\":\"20161215733517365701\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"20161215394589117200\",\"orderItems\":[{\"itemId\":176873,\"orderId\":\"20161215608041164709\",\"unionOrderId\":\"20161215733517365701\",\"productBn\":\"CSXJ0001\",\"name\":\"??app????app??(???,XXS)(1)\",\"cost\":0.100,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901101000\",\"standard\":\"???,XXS\",\"brief\":null,\"shipStatus\":0}],\"errorMessage\":null}";
        Order order = JSON.parseObject(orderJson, Order.class);
        order.setPayNumber("2016121521001004570220367991");
        EventResult eventResult = dtwOrderHandler.pushAliPayOrder(order, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushWeixinPayOrder() {
        mockOrder.setPayNumber(SerialNo.create());
        mockOrder.setOrderId(SerialNo.create());

        String orderJson = "{\"orderId\":\"20170104791652071258\",\"memberId\":256362,\"userLoginName\":\"15067134475\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":500.000,\"orderName\":\"??????(?,M)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2017-01-04 15:09:33\",\"createTime\":\"2017-01-04 15:09:14\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.110,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.110,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"????V3\",\"payType\":9,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2017-01-04 15:09:33\",\"unionOrderId\":\"20170104786682536304\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"4006202001201701045159213044\",\"taxAmount\":0.010,\"orderItems\":[{\"itemId\":177065,\"orderId\":\"20170104791652071258\",\"unionOrderId\":\"20170104786682536304\",\"productBn\":\"XYDCSXJ0013\",\"name\":\"??????(?,M)(1)\",\"cost\":0.500,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901109000\",\"standard\":\"?,M\",\"brief\":null,\"shipStatus\":0,\"weight\":0.0,\"unit\":null}],\"errorMessage\":null}";
        String orderJson2 = "{\"orderId\":\"20161207974540631835\",\"memberId\":256362,\"userLoginName\":\"S8AZBYFMDGZ\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"??app????app????app????app????app????app????app????app????app??(???,XXS)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-12-07 17:42:01\",\"createTime\":\"2016-12-07 17:41:30\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.100,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.100,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"????V3\",\"payType\":9,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-12-07 17:42:01\",\"unionOrderId\":\"20161207268060346380\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"20161207919917848518\",\"orderItems\":[{\"itemId\":176853,\"orderId\":\"20161207974540631835\",\"unionOrderId\":\"20161207268060346380\",\"productBn\":\"CSXJ0001\",\"name\":\"??app????app????app????app????app????app????app????app????app??(???,XXS)(1)\",\"cost\":0.100,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901101000\",\"standard\":\"???,XXS\",\"brief\":null,\"shipStatus\":0}],\"errorMessage\":null}";
        Order order = JSON.parseObject(orderJson, Order.class);
        Order order2 = JSON.parseObject(orderJson2, Order.class);
        order2.setPayNumber("4006202001201612072076863542");
        order2.setPayType(9);
        EventResult eventResult = dtwOrderHandler.pushWeixinPayOrder(order, mockDtwSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushCustomOrder() {

        Order order = JSON.parseObject(orderInfoJson, Order.class);
        for (int i = 0; i < 1; i++) {

            EventResult eventResult = dtwOrderHandler.pushCustomOrder(mockOrder, mockDtwSysData);
            System.out.println(eventResult.getResultCode());
            System.out.println(eventResult.getResultMsg());
        }

    }


    //根据商品算出税费：约定商品单价*税率=税费
    private double calculateTaxPrice(List<OrderItem> orderItems, double taxRate) {
        double taxPrice = 0.0;
        for (OrderItem orderItem : orderItems) {
            taxPrice = Arith.add(taxPrice, Arith.mul(Arith.mul(orderItem.getPrice(), orderItem.getNum()), taxRate));
            System.out.println(taxPrice);
        }
        return taxPrice;
    }

    // 根据商品反推出商品除税后的价格
    private double caculateGoodsPrice(List<OrderItem> orderItems, double taxRate) {
        double goodPrice = 0.0;
        for (OrderItem orderItem : orderItems) {
            double itemPrice = Arith.sub(orderItem.getPrice(), Arith.mul(orderItem.getPrice(), taxRate));
            goodPrice = Arith.add(goodPrice, Arith.mul(itemPrice, orderItem.getNum()));
        }
        return goodPrice;
    }

//    @Test
//    public void testCaculateTaxPrice() {
//        double taxRate = Arith.div(mockDtwSysData.getTaxRate(), 100);
//        Order order = JSON.parseObject(orderInfoJson, Order.class);
//        List<OrderItem> orderItems = order.getOrderItems();
//        System.out.println("\n" + calculateTaxPrice(orderItems, taxRate));
//    }

//    @Test
//    public void testCaculateGoodsPrice() {
//        double r = Arith.div(mockDtwSysData.getTaxRate(), 100);
//
//        System.out.println("\n" + caculateGoodsPrice(mockOrderItems, r));
//    }

//    @Test
//    public void testPrice() {
//        double r = Arith.div(mockDtwSysData.getTaxRate(), 100);
//
//        System.out.println();
//        System.out.println("总金额：" + mockOrder.getFinalAmount());
//        System.out.println("运费：" + mockOrder.getCostFreight());
//        System.out.println("贷款:" + (mockOrder.getFinalAmount() - mockOrder.getCostFreight()));
//        System.out.println("税费：" + calculateTaxPrice(mockOrderItems, r));
//        System.out.println("商品费用：" + caculateGoodsPrice(mockOrderItems, r));
//    }

    @Test
    public void testCustomBack() throws Exception {
        String encData = "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";
        String sign = "bhbuK5k6QXDTHGDvzqZ0KeXOrb9ljsUhDvk14PRktJk/esVJOFBdOtH0xftROyfRLrLkv5GiLo1pmdokiTQx3g==";
        String aesKey = DtwConstant.AES_KEY;
        String rsaPubKey = DtwConstant.RSA_PUBLIC_KEY;
        String rsaPriKey = DtwConstant.RSA_PRIVATE_KEY;


        // 解密
        byte[] inputContent = Base64.getDecoder().decode(encData.getBytes("utf-8"));
        byte[] aesKeyB = Base64.getDecoder().decode(aesKey.getBytes("utf-8"));
        String originalContent = new String(AESUtil.decrypt(inputContent, aesKeyB), "utf-8");
        System.out.println(originalContent);

        // 验签
        byte[] inpuData = originalContent.getBytes("utf-8");
        byte[] publicKey = Base64.getDecoder().decode(rsaPubKey);
        byte[] sign2 = Base64.getDecoder().decode(sign);
        Boolean isOk = RSAUtil.verify(inpuData, publicKey, sign2);

        Assert.assertTrue(isOk);
    }

    @Test
    public void test() {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }

}
