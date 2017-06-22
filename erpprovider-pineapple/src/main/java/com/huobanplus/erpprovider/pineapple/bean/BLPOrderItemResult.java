package com.huobanplus.erpprovider.pineapple.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
public class BLPOrderItemResult {
    @JSONField(name="ProductId")
    private String ProductId;

    @JSONField(name="suborderno")
    private String suborderNo;

    @JSONField(name="tradegoodsno")
    private String tradeGoodsNo;

    @JSONField(name="tradegoodsname")
    private String tradeGoodsName;

    @JSONField(name="tradegoodsspec")
    private String tradeGoodsSpec;

    @JSONField(name="goodscount")
    private int goodsCount;

    @JSONField(name="Remark")
    private String remark;

    @JSONField(name="price")
    private BigDecimal price;

    @JSONField(name="discountmoney")
    private BigDecimal discountMoney;

    @JSONField(name="taxamount")
    private BigDecimal taxAmount;

    @JSONField(name="refundStatus")
    private String refundStatus;

    @JSONField(name="Status")
    private String Status;

}
