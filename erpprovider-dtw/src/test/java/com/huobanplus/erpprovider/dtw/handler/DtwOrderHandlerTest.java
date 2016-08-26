/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.formatdtw.*;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpprovider.dtw.util.Arith;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/6/16.
 */
public class DtwOrderHandlerTest extends DtwTestBase {

    @Autowired
    private DtwOrderHandler dtwOrderHandler;


    private PushNewOrderEvent mockPushNewOrderEvent;

    private String orderInfoJson = "{\"orderId\":\"2016082682778787\",\"memberId\":17423,\"userLoginName\":\"15868807873\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":200.000,\"orderName\":\"????????(??,42?)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-08-26 14:40:08\",\"createTime\":\"2016-08-26 14:40:08\",\"shipName\":\"???\",\"shipArea\":\"???/???/???\",\"province\":\"???\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"????????????????????e?\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15868807873\",\"costItem\":5.000,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":5.000,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-08-26 14:40:08\",\"unionOrderId\":\"2016082692988419\",\"receiveStatus\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"orderItems\":[{\"itemId\":14631,\"orderId\":\"2016082682778787\",\"unionOrderId\":\"2016082692988419\",\"productBn\":\"CSXJ0001\",\"name\":\"????????(??,42?)(1)\",\"cost\":3.000,\"price\":5.000,\"amount\":5.000,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"1901101000\",\"standard\":\"??,42?\",\"brief\":null,\"shipStatus\":0}]}";


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
        mockPushNewOrderEvent.setOrderInfoJson(orderInfoJson);
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        for (int i = 0; i < 1; i++) {

            EventResult result = dtwOrderHandler.pushOrder(mockPushNewOrderEvent);
            System.out.println(result.getData());
            System.out.println(result.getResultMsg());
            System.out.println(result.getResultCode());
        }
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
        EventResult eventResult = dtwOrderHandler.pushAliPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushWeixinPayOrder() {
        EventResult eventResult = dtwOrderHandler.pushWeixinPayOrder(mockOrder, mockDtwSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushCustomOrder() {
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
            taxPrice = Arith.add(taxPrice, Arith.mul(orderItem.getPrice() * orderItem.getNum(), taxRate));
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

    @Test
    public void testCaculateTaxPrice() {
        double taxRate = Arith.div(mockDtwSysData.getTaxRate(), 100);
        System.out.println("\n" + calculateTaxPrice(mockOrderItems, taxRate));
    }

    @Test
    public void testCaculateGoodsPrice() {
        double r = Arith.div(mockDtwSysData.getTaxRate(), 100);
        System.out.println("\n" + caculateGoodsPrice(mockOrderItems, r));
    }

    @Test
    public void testPrice() {
        double r = Arith.div(mockDtwSysData.getTaxRate(), 100);

        System.out.println();
        System.out.println("总金额：" + mockOrder.getFinalAmount());
        System.out.println("运费：" + mockOrder.getCostFreight());
        System.out.println("贷款:" + (mockOrder.getFinalAmount() - mockOrder.getCostFreight()));
        System.out.println("税费：" + calculateTaxPrice(mockOrderItems, r));
        System.out.println("商品费用：" + caculateGoodsPrice(mockOrderItems, r));
        System.out.println("\n--------------------------------\n");
        System.out.println(Arith.div(3.0, 1.11));
    }

}
