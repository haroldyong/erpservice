package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/22.
 * 管易商品明细实体  响应封装实体
 */
@Data
public class GYResponseGoodsItem {

    /**
     * 商品名称
     */
    @JSONField(name= "name")
    private String name;

    /**
     * 商品简称
     */
    @JSONField(name= "simple_name")
    private String simpleName;

    /**
     * 商品分类代码
     */
    @JSONField(name= "category_code")
    private String categoryCode;

    /**
     * 是否为组合商品
     */
    @JSONField(name= "combine")
    private boolean combine;

    /**
     * 商品分类名称
     */
    @JSONField(name= "category_name")
    private String categoryName;

    /**
     * 供应商代码
     */
    @JSONField(name= "supplier_code")
    private String supplierCode;

    /**
     * 库存状态代码
     */
    @JSONField(name= "stock_status_code")
    private String stockStatusCode;

    /**
     * 商品单位代码
     */
    @JSONField(name= "item_unit_code")
    private String itemUnitCode;

    /**
     * 商品重量
     */
    @JSONField(name= "weight")
    private double weight;

    /**
     * 标准进价
     */
    @JSONField(name= "purchase_price")
    private double purchasePrice;

    /**
     * 成本价
     */
    @JSONField(name= "cost_price")
    private double costPrice;

    /**
     * 标准售价
     */
    @JSONField(name= "sales_price")
    private double salesPrice;

    /**
     * 代理售价
     */
    @JSONField(name= "agent_price")
    private double agentPrice;

    /**
     * 销售积分
     */
    @JSONField(name= "sales_point")
    private double salesPoint;

    /**
     * 打包积分
     */
    @JSONField(name= "package_point")
    private double packagePoint;

    /**
     * 商品图片路径
     */
    @JSONField(name= "pic_url")
    private String picUrl;

    /**
     * 商品规格列表
     */
    @JSONField(name= "skus")
    private List<GYResponseGoodsSku> skus;

    /**
     * 组合商品列表
     */
    @JSONField(name= "combine_items")
    private List<GYResponseCombineItem> combineItems;

    /**
     * 商品自定义属性
     */
    @JSONField(name= "custom_attr")
    private String customAttr;

    /**
     * 备注
     */
    @JSONField(name= "note")
    private String note;
}
