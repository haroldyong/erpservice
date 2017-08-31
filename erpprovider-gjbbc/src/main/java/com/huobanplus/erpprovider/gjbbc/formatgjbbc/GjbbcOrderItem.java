package com.huobanplus.erpprovider.gjbbc.formatgjbbc;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 商品明细
 * Created by hxh on 2017-08-15.
 */
@Data
public class GjbbcOrderItem {
    /**
     * 备案商品ID 不为空
     */
    @JSONField(name = "customs_goods_id")
    private int customsGoodsId;
    /**
     * 订单商品数量 不为空
     */
    @JSONField(name = "goods_num")
    private String goodsNum;
    /**
     * 订单商品单价 不为空
     */
    @JSONField(name = "goods_price")
    private double goodsPrice;
    /**
     * 订单币制代码 不为空
     */
    @JSONField(name = "trade_curr")
    private String tradeCurr;
}
