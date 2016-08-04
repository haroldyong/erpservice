/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.formatgy.stock;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


/**
 * Created by wuxiongliu on 2016/6/20.
 * 库存查询返回bean
 */
@Data
public class GYStockInfo {

    /**
     * 仓库id
     */
    @JSONField(name = "warehouse_id")
    private String warehouseId;

    /**
     * 规格代码
     */
    @JSONField(name = "sku_code")
    private String skuCode;

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
