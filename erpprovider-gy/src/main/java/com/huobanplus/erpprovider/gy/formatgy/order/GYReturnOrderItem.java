package com.huobanplus.erpprovider.gy.formatgy.order;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by admin on 2016/6/8.
 */
@Data
public class GYReturnOrderItem {

    /**
     * 商品条码
     */
    @JSONField(name= "barcode")
    private String barcode;

    /**
     * 商品代码
     */
    @JSONField(name= "item_code")
    private String itemCode;

    /**
     * 规格代码
     */
    @JSONField(name= "sku_code")
    private String skuCode;

    /**
     * 数量
     */
    @JSONField(name= "qty")
    private double qty;

    /**
     * 标准单价
     */
    @JSONField(name= "originPrice")
    private double originPrice;

    /**
     * 实际单价
     */
    @JSONField(name= "price")
    private double price;

}
