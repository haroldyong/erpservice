package com.huobanplus.erpprovider.gy.search;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYStockSearch {

    /**
     * 页码
     */
    @JSONField(name = "")
    private int pageNo;

    /**
     * 	每页大小
     */
    @JSONField(name = "")
    private int pageSize;

    /**
     * 商品代码
     */
    @JSONField(name = "")
    private String itemCode;

    /**
     * 规格代码
     */
    @JSONField(name = "")
    private String skuCode;

    /**
     * 仓库code
     */
    @JSONField(name = "")
    private String earehouseCode;

}
