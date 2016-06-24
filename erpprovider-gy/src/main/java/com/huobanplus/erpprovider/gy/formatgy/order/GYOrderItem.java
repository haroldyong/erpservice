package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2016/5/9.
 */
@Setter
@Getter
public class GYOrderItem {

    /**
     * "qty": 商品数量
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * "price" 实际单价
     */
    @JSONField(name = "price")
    private double price;

    /**
     * "note": 备注
     */
    @JSONField(name = "note")
    private String note;

    /**
     * "refund": 是否退款 0非退款 ,1退款(退款中);
     */
    @JSONField(name = "refund")
    private int refund;

    /**
     * "oid": 子订单ID
     */
    @JSONField(name = "oid")
    private String oid;

    /**
     * "item_code" 商品代码  必填
     */
    @JSONField(name = "item_code")
    private String itemCode;

    /**
     * "sku_code": 规格代码
     */
    @JSONField(name="sku_code")
    private String skuCode;

}
