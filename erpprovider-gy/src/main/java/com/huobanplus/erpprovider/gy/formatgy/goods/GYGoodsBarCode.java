package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 * 管易商品条码实体
 */
@Data
public class GYGoodsBarCode {

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
     * 商品条码
     */
    @JSONField(name = "barcode")
    private String barCode;
}
