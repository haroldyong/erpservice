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
     * 商品条码
     */
    @JSONField(name= "barcode")
    private String barcode;

    /**
     * 仓库代码
     */
    @JSONField(name= "warehouse_code")
    private String warehouseCode;

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
     * 是否停用
     */
    @JSONField(name= "del")
    private boolean del;

    /**
     * 库存数
     */
    @JSONField(name= "qty")
    private int qty;

    /**
     * 可配数
     */
    @JSONField(name= "pick_qty")
    private int pickQty;

    /**
     * 可销售数
     */
    @JSONField(name= "salable_qty")
    private int salableQty;

    /**
     * 在途数
     */
    @JSONField(name= "road_qty")
    private int roadQty;
}
