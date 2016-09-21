/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.formatdata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "order")
public class EDINingBoOrder {

    /**
     *
     */
    @JacksonXmlProperty(localName = "orderShop")
    private String orderShop;

    /**
     * OTO店铺代码
     */
    @JacksonXmlProperty(localName = "otoCode")
    private String otoCode;


    /**
     *
     */
    @JacksonXmlProperty(localName = "orderFrom")
    private String orderFrom;

    /**
     *
     */
    @JacksonXmlProperty(localName = "packageFlag")
    private String packageFlag;

    /**
     *
     */
    @JacksonXmlProperty(localName = "busOrderNo")
    private String busOrderNo;

    /**
     *
     */
    @JacksonXmlProperty(localName = "postFee")
    private double postFee;

    /**
     *
     */
    @JacksonXmlProperty(localName = "insuranceFee")
    private double insuranceFee;

    /**
     *
     */
    @JacksonXmlProperty(localName = "amount")
    private double amount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "buyerAccount")
    private String buyerAccount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "phone")
    private String phone;

    /**
     *
     */
    @JacksonXmlProperty(localName = "email")
    private String email;

    /**
     *
     */
    @JacksonXmlProperty(localName = "taxAmount")
    private double taxAmount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "tariffAmount")
    private double tariffAmount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "addedValueTaxAmount")
    private double addedValueTaxAmount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "consumptionDutyAmount")
    private double consumptionDutyAmount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "grossWeight")
    private double grossWeight;

    /**
     *
     */
    @JacksonXmlProperty(localName = "disAmount")
    private double disAmount;

    /**
     *
     */
    @JacksonXmlProperty(localName = "dealDate")
    private String dealDate;

    /**
     *
     */
    @JacksonXmlProperty(localName = "promotion")
    private EDIPromotion ediPromotion;

    /**
     *
     */
    @JacksonXmlProperty(localName = "detail")
    private EDIOrderItem ediOrderItem;

    /**
     *
     */
    @JacksonXmlProperty(localName = "pay")
    private EDIPayOrder ediPayOrder;

    /**
     *
     */
    @JacksonXmlProperty(localName = "logistics")
    private EDILogiOrder ediLogiOrder;



}