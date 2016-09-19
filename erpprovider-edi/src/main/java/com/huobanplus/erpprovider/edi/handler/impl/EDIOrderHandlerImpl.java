/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.handler.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edi.common.EDISysData;
import com.huobanplus.erpprovider.edi.formatdata.*;
import com.huobanplus.erpprovider.edi.handler.EDIOrderHandler;
import com.huobanplus.erpprovider.edi.search.EDILogiSearch;
import com.huobanplus.erpprovider.edi.util.EDIUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class EDIOrderHandlerImpl implements EDIOrderHandler {

    private static final Log log = LogFactory.getLog(EDIOrderHandlerImpl.class);

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        log.info("order:" + pushNewOrderEvent.getOrderInfoJson());

        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        EDISysData ediSysData = JSON.parseObject(erpInfo.getSysDataJson(), EDISysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();


        try {
            String head = "<methodType>create</methodType><busCode></busCode><hgCode></hgCode>";
            String body = new XmlMapper().writeValueAsString(convertOrder(orderInfo, ediSysData));
            String requestXml = createRequestXml(head, body);
            String requestTime = StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN);

            Map<String, Object> requestMap = createRequestMap(requestXml, requestTime, ediSysData);

            HttpResult httpResult = HttpClientUtil.getInstance().get(ediSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String xmlResp = httpResult.getHttpContent();
                Document document = DocumentHelper.parseText(xmlResp);
                Element root = document.getRootElement();

                Element resultElem = root.element("result");
                if (resultElem.getText().equals("success")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    Element messageElem = root.element("message");
                    return EventResult.resultWith(EventResultEnum.ERROR, messageElem.getText(), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private Map<String, Object> createRequestMap(String requestXml, String requestTime, EDISysData ediSysData) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("customer", ediSysData.getCustomer());
        requestMap.put("datetime", requestTime);
        requestMap.put("sign", EDIUtil.buildSign(requestXml, ediSysData.getSecretKey(), requestTime));
        requestMap.put("msgtype", "xml");
        requestMap.put("method", "create");
        requestMap.put("version", "0.0.1");
        requestMap.put("sign_method", "md5");
        requestMap.put("data", URLEncoder.encode(requestXml, "utf-8"));
        requestMap.put("appkey", ediSysData.getAppKey());
        requestMap.put("secretKey", ediSysData.getSecretKey());
        return requestMap;
    }

    private String createRequestXml(String head, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("<request>")
                .append("<head>")
                .append(head)
                .append("</head>")
                .append("<body>")
                .append(body)
                .append("</body>")
                .append("</request>");
        return sb.toString();
    }

    private EDIOrder convertOrder(Order order, EDISysData ediSysData) {
        EDIOrder ediOrder = new EDIOrder();

        ediOrder.setOrderShop(ediSysData.getShopCode());
        ediOrder.setOrderFrom("");
        ediOrder.setPackageFlag("");
        ediOrder.setBusOrderNo("");
        ediOrder.setPostFee(0);
        ediOrder.setInsuranceFee(0);
        ediOrder.setAmount(0);
        ediOrder.setBuyerAccount("");
        ediOrder.setPhone("");
        ediOrder.setEmail("");
        ediOrder.setTaxAmount(0);
        ediOrder.setTariffAmount(0);
        ediOrder.setGrossWeight(0);
        ediOrder.setDisAmount(0);
        ediOrder.setDealDate("");
        ediOrder.setAddedValueTaxAmount(0);
        ediOrder.setConsumptionDutyAmount(0);


        EDIPromotion ediPromotion = new EDIPromotion();
        ediPromotion.setProAmount(0);
        ediPromotion.setProRemark("");

        EDIPayOrder ediPayOrder = new EDIPayOrder();

        ediPayOrder.setName("");
        ediPayOrder.setPaytime("");
        ediPayOrder.setPaymentNo("");
        ediPayOrder.setOrderSeqNo("");
        ediPayOrder.setSource("");
        ediPayOrder.setIdnum("");
        ediPayOrder.setMerId("");

        EDILogiOrder ediLogiOrder = new EDILogiOrder();

        ediLogiOrder.setLogisticsNo("");
        ediLogiOrder.setLogisticsName("");
        ediLogiOrder.setLogisticsCode("");
        ediLogiOrder.setConsignee("");
        ediLogiOrder.setProvince("");
        ediLogiOrder.setCity("");
        ediLogiOrder.setDistrict("");
        ediLogiOrder.setConsigneeAddr("");
        ediLogiOrder.setConsigneeTel("");
        ediLogiOrder.setMailNo("");
        ediLogiOrder.setGoodsName("");


        EDIOrderItem ediOrderItem = new EDIOrderItem();
        ediOrderItem.setAmount("");
        ediOrderItem.setPrice(0);
        ediOrderItem.setQty(0);
        ediOrderItem.setProductId("");

        ediOrder.setEdiPromotion(ediPromotion);
        ediOrder.setEdiPayOrder(ediPayOrder);
        ediOrder.setEdiLogiOrder(ediLogiOrder);
        ediOrder.setEdiOrderItem(ediOrderItem);

        return ediOrder;
    }

    @Override
    public EventResult logisticSearch(EDILogiSearch ediLogiSearch, EDISysData ediSysData) {
        try {


            String head = "<method></method><methodType>find</methodType><rule>2</rule><busCode></busCode>";

            String body = new XmlMapper().writeValueAsString(ediLogiSearch);
            String requestXml = createRequestXml(head, body);
            String requestTime = StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN);

            Map<String, Object> requestMap = createRequestMap(requestXml, requestTime, ediSysData);

            HttpResult httpResult = HttpClientUtil.getInstance().get(ediSysData.getRequestUrl(), requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String xmlResp = httpResult.getHttpContent();
                Document document = DocumentHelper.parseText(xmlResp);
                Element root = document.getRootElement();

                Element resultElem = root.element("result");
                if (resultElem.getText().equals("success")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);// TODO: 2016-09-19
                } else {
                    Element messageElem = root.element("message");
                    return EventResult.resultWith(EventResultEnum.ERROR, messageElem.getText(), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}