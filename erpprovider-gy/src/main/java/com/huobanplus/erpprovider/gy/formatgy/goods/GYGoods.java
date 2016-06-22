package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYGoods {

    /**
     * 商品代码
     */
    @JSONField(name = "code")
    private String code;

    /**
     * 商品名称
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 商品简称
     */
    @JSONField(name = "simple_name")
    private String simpleName;

    /**
     * 商品类别code
     */
    @JSONField(name = "category_code")
    private String categoryCode;

    /**
     * 供应商code
     */
    @JSONField(name = "supplier_code")
    private String supplierCode;

    /**
     * 库存状态code
     */
    @JSONField(name = "stock_status_code")
    private String stockStatusCode;

    /**
     * 重量
     */
    @JSONField(name = "weight")
    private double weight;

    /**
     * 单位code
     */
    @JSONField(name = "unit_code")
    private String unitCode;

    /**
     * 销售积分
     */
    @JSONField(name = "sales_point")
    private double salesPoint;

    /**
     * 打包积分
     */
    @JSONField(name = "package_point")
    private double packagePoint;

    /**
     * 标准进价
     */
    @JSONField(name = "purchase_price")
    private double purchasePrice;

    /**
     * 标准售价
     */
    @JSONField(name = "sales_price")
    private double salesPrice;

    /**
     * 代理售价
     */
    @JSONField(name = "agent_price")
    private double agentPrice;

    /**
     * 成本价
     */
    @JSONField(name = "cost_price")
    private double costPrice;

    /**
     * 供应商货号
     */
    @JSONField(name = "supplier_outer_id")
    private String supplierOuterId;

    /**
     * 备注
     */
    @JSONField(name = "note")
    private String note;

    /**
     * 规格数组
     */
    @JSONField(name = "skus")
    private List<GYSku> skus;
}
