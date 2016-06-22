package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYSku {

    /**
     *  规格代码 是
     */
    @JSONField(name = "sku_code")
    private String skuCode;

    /**
     *  规格名称 是
     */
    @JSONField(name = "sku_name")
    private String skuName;

    /**
     * 规格重量
     */
    @JSONField(name = "sku_weight")
    private double skuWeight;

    /**
     * 规格标准进价
     */
    @JSONField(name = "sku_purchase_price")
    private double skuPurchasePrice;

    /**
     * 规格标准售价
     */
    @JSONField(name = "sku_sales_price")
    private double skuSalesPrice;

    /**
     * 规格代理售价
     */
    @JSONField(name = "sku_agent_price")
    private double skuAgentPrice;

    /**
     * 规格成本价
     */
    @JSONField(name = "sku_cost_price")
    private double skuCostPrice;

    /**
     * 规格打包积分
     */
    @JSONField(name = "sku_package_point")
    private double skuPackagePoint;

    /**
     * 规格销售积分
     */
    @JSONField(name = "sku_sales_point")
    private double skuSalesPoint;

    /**
     * 规格备注
     */
    @JSONField(name = "sku_note")
    private String skuNote;
}
