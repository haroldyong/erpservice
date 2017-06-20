package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderDetailResult {
    @JsonProperty("PlatOrderNo")
    private String PlatOrderNo;

    @JsonProperty("tradeStatus")
    private String tradeStatus;

    @JsonProperty("tradeStatusdescription")
    private String tradeStatusDescription;

    @JsonProperty("tradetime")
    private String tradeTime;

    @JsonProperty("payorderno")
    private String payOrderNo;

    @JsonProperty("address")
    private String address;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("country")
    private String country;

    @JsonProperty("province")
    private String province;

    @JsonProperty("city")
    private String city;

    @JsonProperty("town")
    private String town;

    @JsonProperty("area")
    private String area;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("customerremark")
    private String customerRemark;

    @JsonProperty("sellerremark")
    private double sellerRemark;

    @JsonProperty("postfee")
    private BigDecimal postFee;

    @JsonProperty("goodsfee")
    private BigDecimal goodsFee;

    @JsonProperty("totalmoney")
    private BigDecimal totalMoney;

    @JsonProperty("favourablemoney")
    private BigDecimal favourableMoney;

    @JsonProperty("commissionvalue")
    private BigDecimal commissionValue;

    @JsonProperty("taxamount")
    private BigDecimal taxAmount;

    @JsonProperty("tariffamount")
    private BigDecimal tariffAmount;

    @JsonProperty("addedvalueamount")
    private BigDecimal addedValueAmount;

    @JsonProperty("consumptiondutyamount")
    private BigDecimal consumptionDutyAmount;

    @JsonProperty("sendstyle")
    private String sendStyle;

    @JsonProperty("qq")
    private String qq;

    @JsonProperty("paytime")
    private String payTime;

    @JsonProperty("invoicetitle")
    private String invoiceTitle;

    @JsonProperty("codservicefee")
    private BigDecimal codServiceFee;

    @JsonProperty("cardtype")
    private String cardType;

    @JsonProperty("idcard")
    private String idCard;

    @JsonProperty("idcardtruename")
    private String idCardTrueName;

    @JsonProperty("receivername")
    private String receiverName;

    @JsonProperty("nick")
    private String nick;

    @JsonProperty("ShouldPayType")
    private String ShouldPayType;

    @JsonProperty("goodinfos")
    private List<BLPOrderItemResult> goodInfoList;

}
