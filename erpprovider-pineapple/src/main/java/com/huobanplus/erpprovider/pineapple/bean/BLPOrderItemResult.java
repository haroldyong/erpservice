package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderItemResult {
    @JsonProperty("ProductId")
    private String ProductId;

    @JsonProperty("suborderno")
    private String suborderNo;

    @JsonProperty("tradegoodsno")
    private String tradeGoodsNo;

    @JsonProperty("tradegoodsname")
    private String tradeGoodsName;

    @JsonProperty("tradegoodsspec")
    private String tradeGoodsSpec;

    @JsonProperty("goodscount")
    private int goodsCount;

    @JsonProperty("Remark")
    private String remark;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("discountmoney")
    private BigDecimal discountMoney;

    @JsonProperty("taxamount")
    private BigDecimal taxAmount;

    @JsonProperty("refundStatus")
    private String refundStatus;

    @JsonProperty("Status")
    private String Status;

}
