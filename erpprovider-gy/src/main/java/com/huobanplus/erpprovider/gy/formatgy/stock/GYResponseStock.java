package com.huobanplus.erpprovider.gy.formatgy.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


/**
 * Created by wuxiongliu on 2016/6/20.
 * 库存查询返回bean
 */
@Data
public class GYResponseStock {

    /**
     * 仓库id
     */
    @JSONField(name = "warehouse_id")
    private String warehouseId;

    /**
     * 规格id
     */
    @JSONField(name = "sku_id")
    private String skuId;

    /**
     * 规格代码
     */
    @JSONField(name = "sku_code")
    private String skuCode;

    /**
     * 规格名称
     */
    @JSONField(name = "sku_name")
    private String skuName;

    /**
     * 商品代码
     */
    @JSONField(name = "item_code")
    private String itemCode;

    /**
     * 是否停用
     */
    @JSONField(name = "del")
    private boolean del;

    /**
     * 库存数
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * 安全库存
     */
    @JSONField(name = "safe_qty")
    private int safe_qty;

    /**
     * 可配数
     */
    @JSONField(name = "pick_qty")
    private int pickQty;

    /**
     * 可销售数
     */
    @JSONField(name = "salable_qty")
    private int salableQty;

    /**
     * 在途数
     */
    @JSONField(name = "road_qty")
    private int roadQty;
}
