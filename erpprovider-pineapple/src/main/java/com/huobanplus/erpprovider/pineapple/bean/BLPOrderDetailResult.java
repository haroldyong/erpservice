package com.huobanplus.erpprovider.pineapple.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderDetailResult {
    @JSONField(name = "PlatOrderNo")
    private String PlatOrderNo;

    @JSONField(name = "tradeStatus")
    private String tradeStatus;

    @JSONField(name = "tradeStatusdescription")
    private String tradeStatusDescription;

    @JSONField(name = "tradetime")
    private String tradeTime;

    @JSONField(name = "payorderno")
    private String payOrderNo;

    @JSONField(name = "address")
    private String address;

    @JSONField(name = "zip")
    private String zip;

    @JSONField(name = "phone")
    private String phone;

    @JSONField(name = "country")
    private String country;

    @JSONField(name = "province")
    private String province;

    @JSONField(name = "city")
    private String city;

    @JSONField(name = "town")
    private String town;

    @JSONField(name = "area")
    private String area;

    @JSONField(name = "mobile")
    private String mobile;

    @JSONField(name = "email")
    private String email;

    @JSONField(name = "customerremark")
    private String customerRemark;

    @JSONField(name = "sellerremark")
    private double sellerRemark;

    @JSONField(name = "postfee")
    private BigDecimal postFee;

    @JSONField(name = "goodsfee")
    private BigDecimal goodsFee;

    @JSONField(name = "totalmoney")
    private BigDecimal totalMoney;

    @JSONField(name = "favourablemoney")
    private BigDecimal favourableMoney;

    @JSONField(name = "commissionvalue")
    private BigDecimal commissionValue;

    @JSONField(name = "taxamount")
    private BigDecimal taxAmount;

    @JSONField(name = "tariffamount")
    private BigDecimal tariffAmount;

    @JSONField(name = "addedvalueamount")
    private BigDecimal addedValueAmount;

    @JSONField(name = "consumptiondutyamount")
    private BigDecimal consumptionDutyAmount;

    @JSONField(name = "sendstyle")
    private String sendStyle;

    @JSONField(name = "qq")
    private String qq;

    @JSONField(name = "paytime")
    private String payTime;

    @JSONField(name = "invoicetitle")
    private String invoiceTitle;

    @JSONField(name = "codservicefee")
    private BigDecimal codServiceFee;

    @JSONField(name = "cardtype")
    private String cardType;

    @JSONField(name = "idcard")
    private String idCard;

    @JSONField(name = "idcardtruename")
    private String idCardTrueName;

    @JSONField(name = "receivername")
    private String receiverName;

    @JSONField(name = "nick")
    private String nick;

    @JSONField(name = "ShouldPayType")
    private String ShouldPayType;

    @JSONField(name = "goodinfos")
    private List<BLPOrderItemResult> goodInfoList;

}
