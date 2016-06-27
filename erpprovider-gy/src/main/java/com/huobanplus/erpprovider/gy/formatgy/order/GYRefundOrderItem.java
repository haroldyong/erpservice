package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易退款单明细实体
 */
@Data
public class GYRefundOrderItem {

    /**
     * 商品条码
     */
    @JSONField(name = "barcode")
    private String barCode;

    /**
     * 数量
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * 单价
     */
    @JSONField(name = "price")
    private double price;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;
}
