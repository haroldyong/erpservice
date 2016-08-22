/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-08-18.
 */
@Data
public class CustomOrderHead {

    @JacksonXmlProperty(localName = "eCommerceCode")
    private String commerceCode;

    @JacksonXmlProperty(localName = "eCommerceName")
    private String commerceName;

    @JacksonXmlProperty(localName = "ieFlag")
    private String ieFlag;

    @JacksonXmlProperty(localName = "payType")
    private String payType;

    @JacksonXmlProperty(localName = "payCompanyCode")
    private String payCompanyCode;

    @JacksonXmlProperty(localName = "payNumber")
    private String payNumber;

    @JacksonXmlProperty(localName = "orderTotalAmount")
    private double orderTotalAmount;

    @JacksonXmlProperty(localName = "orderNo")
    private String orderNo;

    @JacksonXmlProperty(localName = "orderTaxAmount")
    private double orderTaxAmount;

    @JacksonXmlProperty(localName = "orderGoodsAmount")
    private double orderGoodsAmount;

    @JacksonXmlProperty(localName = "feeAmount")
    private double feeAmount;

    @JacksonXmlProperty(localName = "insureAmount")
    private double insureAmount;

    @JacksonXmlProperty(localName = "companyName")
    private String companyName;

    @JacksonXmlProperty(localName = "companyCode")
    private String companyCode;

    @JacksonXmlProperty(localName = "tradeTime")
    private String tradeTime;

    @JacksonXmlProperty(localName = "currCode")
    private String currCode;

    @JacksonXmlProperty(localName = "totalAmount")
    private double totalAmount;

    @JacksonXmlProperty(localName = "consigneeEmail")
    private String consigneeEmail;

    @JacksonXmlProperty(localName = "consigneeTel")
    private String consigneeTel;

    @JacksonXmlProperty(localName = "consignee")
    private String consignee;

    @JacksonXmlProperty(localName = "consigneeAddress")
    private String consigneeAddress;

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;

    @JacksonXmlProperty(localName = "postMode")
    private String postMode;

    @JacksonXmlProperty(localName = "senderCountry")
    private String senderCountry;

    @JacksonXmlProperty(localName = "senderName")
    private String senderName;

    @JacksonXmlProperty(localName = "purchaserId")
    private String purchaserId;

    @JacksonXmlProperty(localName = "logisCompanyName")
    private String logisCompanyName;

    @JacksonXmlProperty(localName = "logisCompanyCode")
    private String logisCompanyCode;

    @JacksonXmlProperty(localName = "zipCode")
    private String zipCode;

    @JacksonXmlProperty(localName = "note")
    private String note;

    @JacksonXmlProperty(localName = "wayBills")
    private String wayBills;

    @JacksonXmlProperty(localName = "rate")
    private String rate;

    @JacksonXmlProperty(localName = "userProcotol")
    private String userProcotol;
}
