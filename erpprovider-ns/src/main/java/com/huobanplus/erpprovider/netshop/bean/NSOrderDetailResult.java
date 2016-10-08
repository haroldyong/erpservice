/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * 订单详细返回结果（用来转换为xml返回）
 * Created by liual on 2015-08-26.
 */
@Data
@JacksonXmlRootElement(localName = "Order")
public class NSOrderDetailResult {

    @JacksonXmlProperty(localName = "Result")
    private int result;

    @JacksonXmlProperty(localName = "Cause")
    private String cause;

    @JacksonXmlProperty(localName = "OrderNO")
    private String orderNo;

    @JacksonXmlProperty(localName = "DateTime")
    private String dateTime;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "BuyerID")
    private String buyerId;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "BuyerName")
    private String buyerName;

    @JacksonXmlProperty(localName = "CardType")
    private int cardType;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "IDCard")
    private String idCard;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Country")
    private String country;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Province")
    private String province;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "City")
    private String city;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Town")
    private String town;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Adr")
    private String adr;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Zip")
    private String zip;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Email")
    private String email;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Phone")
    private String phone;

    @JacksonXmlProperty(localName = "Total")
    private double total;

    @JacksonXmlProperty(localName = "Currency")
    private String currency;

    @JacksonXmlProperty(localName = "Postage")
    private double postage;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "PayAccount")
    private String payAccount;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "PayID")
    private String payID;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "logisticsName")
    private String logisticsName;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "chargetype")
    private String chargetype;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "CustomerRemark")
    private String customerRemark;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "Remark")
    private String remark;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "InvoiceTitle")
    private String invoiceTitle;

    @JacksonXmlProperty(localName = "Item")
    private List<NSOrderItemResult> orderItemResults;
}
