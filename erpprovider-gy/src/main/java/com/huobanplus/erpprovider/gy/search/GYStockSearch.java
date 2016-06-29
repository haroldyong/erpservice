package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易查询实体
 */
@Data
public class GYStockSearch {

    /**
     * 页码
     */
    @JSONField(name = "page_no")
    private Integer pageNo;

    /**
     * 	每页大小
     */
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 商品代码
     */
    @JSONField(name = "item_code")
    private String itemCode;

    /**
     * 规格代码
     */
    @JSONField(name = "sku_code")
    private String skuCode;

    /**
     * 仓库code
     */
    @JSONField(name = "warehouse_code")
    private String earehouseCode;

}
