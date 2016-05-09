package com.huobanplus.erpprovider.gy.formatgy;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2016/5/9.
 */
@Setter
@Getter
public class OrderItem {

    /**
     * "qty":
     */
    private String qty;

    /**
     * "price"
     */
    private String price;

    /**
     * "note":
     */
    private String note;

    /**
     * "refund":
     */
    private String refund;

    /**
     * "oid":
     */
    private String oid;

    /**
     * "item_code"
     */
    @JSONField(name = "item_code")
    private String itemCode;

    /**
     * "sku_code":
     */
    @JSONField(name="sku_code")
    private String skuCode;

}
