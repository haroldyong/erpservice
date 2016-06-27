package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/20.
 * 管易订单明细实体 响应实体（针对订单查询返回结果）
 */
@Data
public class GYResponseOrderItem {

    /**
     * 子订单号
     */
    @JSONField(name= "oid")
    private String oid;

    /**
     * 退款状态
     */
    @JSONField(name= "refund")
    private int refund;

    /**
     * 商品代码
     */
    @JSONField(name= "item_code")
    private String itemCode;

    /**
     * 商品名称
     */
    @JSONField(name= "item_name")
    private String itemName;

    /**
     * 商品简称
     */
    @JSONField(name= "item_simple_name")
    private String itemSimpleName;

    /**
     * 规格代码
     */
    @JSONField(name= "sku_code")
    private String skuCode;

    /**
     * 规格名称
     */
    @JSONField(name= "sku_name")
    private String skuName;

    /**
     * 数量
     */
    @JSONField(name= "qty")
    private int qty;

    /**
     * 实际单价
     */
    @JSONField(name= "price")
    private double price;

    /**
     * 实际金额
     */
    @JSONField(name= "amount")
    private double amount;

    /**
     * 让利金额
     */
    @JSONField(name= "discount_fee")
    private double discountFee;

    /**
     * 让利后金额
     */
    @JSONField(name= "amount_after")
    private double amountAfter;

    /**
     * 物流费用
     */
    @JSONField(name= "post_fee")
    private double postFee;

    /**
     * 平台商品名称
     */
    @JSONField(name= "platform_item_name")
    private String platformItemName;

    /**
     * 平台规格名称
     */
    @JSONField(name= "platform_sku_name")
    private String platformSkuName;

    /**
     * 商品备注
     */
    @JSONField(name= "note")
    private String note;
}
