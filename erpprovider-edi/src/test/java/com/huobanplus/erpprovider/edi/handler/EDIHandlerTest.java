/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edi.formatdata.*;
import org.junit.Test;

/**
 * Created by wuxiongliu on 2016-09-19.
 */
public class EDIHandlerTest /*extends EDITestBase*/ {

    @Test
    public void testXml() throws JsonProcessingException {
        EDINingBoOrder ediOrder = new EDINingBoOrder();
        EDIPromotion ediPromotion = new EDIPromotion();
        EDIPayOrder ediPayOrder = new EDIPayOrder();
        EDILogiOrder ediLogiOrder = new EDILogiOrder();
        EDIOrderItem ediOrderItem = new EDIOrderItem();
        ediOrder.setEdiPromotion(ediPromotion);
        ediOrder.setEdiPayOrder(ediPayOrder);
        ediOrder.setEdiLogiOrder(ediLogiOrder);
        ediOrder.setEdiOrderItem(ediOrderItem);

        String orderResultXml = new XmlMapper().writeValueAsString(ediOrder);
        orderResultXml = orderResultXml.replaceFirst("<promotion>", "<promotions><promotion>");
        orderResultXml = orderResultXml.replaceFirst("</promotion>", "</promotion></promotions>");
        orderResultXml = orderResultXml.replaceFirst("<detail>", "<goods><detail>");
        orderResultXml = orderResultXml.replaceFirst("</detail>", "</detail></goods>");
        System.out.println();
        System.out.println(orderResultXml);
    }
}
