package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/28.
 */
@Data
public class ISCSPurchaseOrderItem {

    /**
     * 商品条码
     */
    @JSONField(name = "product_code")
    private String productCode;

    /**
     * 数量
     */
    @JSONField(name = "qty")
    private String num;

    /**
     * 单位价格
     */
    @JSONField(name = "unit_purchase_cost")
    private String unitPurchaseCost;
}
