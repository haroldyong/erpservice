package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderItemResult {
    @JacksonXmlProperty(localName = "ProductId")
    private String ProductId;

    @JacksonXmlProperty(localName = "suborderno")
    private String suborderNo;

    @JacksonXmlProperty(localName = "tradegoodsno")
    private String tradeGoodsNo;

    @JacksonXmlProperty(localName = "tradegoodsname")
    private String tradeGoodsName;

    @JacksonXmlProperty(localName = "tradegoodsspec")
    private String tradeGoodsSpec;

    @JacksonXmlProperty(localName = "goodscount")
    private int goodsCount;

    @JacksonXmlProperty(localName = "Remark")
    private String remark;

    @JacksonXmlProperty(localName = "price")
    private BigDecimal price;

    @JacksonXmlProperty(localName = "discountmoney")
    private BigDecimal discountMoney;

    @JacksonXmlProperty(localName = "taxamount")
    private BigDecimal taxAmount;

    @JacksonXmlProperty(localName = "refundStatus")
    private String refundStatus;

    @JacksonXmlProperty(localName = "Status")
    private String Status;

}
