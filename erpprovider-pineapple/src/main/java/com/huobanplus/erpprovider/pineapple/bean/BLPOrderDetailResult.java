package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
@JacksonXmlRootElement(localName = "Order")
public class BLPOrderDetailResult {
    @JacksonXmlProperty(localName = "PlatOrderNo")
    private String PlatOrderNo;

    @JacksonXmlProperty(localName = "tradeStatus")
    private String tradeStatus;

    @JacksonXmlProperty(localName = "tradeStatusdescription")
    private String tradeStatusDescription;

    @JacksonXmlProperty(localName = "tradetime")
    private String tradeTime;

    @JacksonXmlProperty(localName = "payorderno")
    private String payOrderNo;

    @JacksonXmlProperty(localName = "address")
    private String address;

    @JacksonXmlProperty(localName = "zip")
    private String zip;

    @JacksonXmlProperty(localName = "phone")
    private String phone;

    @JacksonXmlProperty(localName = "country")
    private String country;

    @JacksonXmlProperty(localName = "province")
    private String province;

    @JacksonXmlProperty(localName = "city")
    private String city;

    @JacksonXmlProperty(localName = "town")
    private String town;

    @JacksonXmlProperty(localName = "area")
    private String area;

    @JacksonXmlProperty(localName = "mobile")
    private String mobile;

    @JacksonXmlProperty(localName = "email")
    private String email;

    @JacksonXmlProperty(localName = "customerremark")
    private String customerRemark;

    @JacksonXmlProperty(localName = "sellerremark")
    private double sellerRemark;

    @JacksonXmlProperty(localName = "postfee")
    private BigDecimal postFee;

    @JacksonXmlProperty(localName = "goodsfee")
    private BigDecimal goodsFee;

    @JacksonXmlProperty(localName = "totalmoney")
    private BigDecimal totalMoney;

    @JacksonXmlProperty(localName = "favourablemoney")
    private BigDecimal favourableMoney;

    @JacksonXmlProperty(localName = "commissionvalue")
    private BigDecimal commissionValue;

    @JacksonXmlProperty(localName = "taxamount")
    private BigDecimal taxAmount;

    @JacksonXmlProperty(localName = "tariffamount")
    private BigDecimal tariffAmount;

    @JacksonXmlProperty(localName = "addedvalueamount")
    private BigDecimal addedValueAmount;

    @JacksonXmlProperty(localName = "consumptiondutyamount")
    private BigDecimal consumptionDutyAmount;

    @JacksonXmlProperty(localName = "sendstyle")
    private String sendStyle;

    @JacksonXmlProperty(localName = "qq")
    private String qq;

    @JacksonXmlProperty(localName = "paytime")
    private String payTime;

    @JacksonXmlProperty(localName = "invoicetitle")
    private String invoiceTitle;

    @JacksonXmlProperty(localName = "codservicefee")
    private BigDecimal codServiceFee;

    @JacksonXmlProperty(localName = "cardtype")
    private String cardType;

    @JacksonXmlProperty(localName = "idcard")
    private String idCard;

    @JacksonXmlProperty(localName = "idcardtruename")
    private String idCardTrueName;

    @JacksonXmlProperty(localName = "receivername")
    private String receiverName;

    @JacksonXmlProperty(localName = "nick")
    private String nick;

    @JacksonXmlProperty(localName = "ShouldPayType")
    private String ShouldPayType;

    @JacksonXmlProperty(localName = "goodinfos")
    private List<BLPOrderItemResult> goodInfoList;

}
