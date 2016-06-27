package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/22.
 */
@Data
public class GYResponseCombineItem {

    /**
     * 商品id
     */
    @JSONField(name= "id")
    private String id;

    /**
     * 起始时间
     */
    @JSONField(name= "create_date")
    private Date createDate;

    /**
     * 结束时间
     */
    @JSONField(name= "modify_date")
    private Date modifyDate;

    /**
     * 商品代码
     */
    @JSONField(name= "item_code")
    private String itemCode;

    /**
     * 商品名称
     */
    @JSONField(name= "item_name")
    private String itemName;

    /**
     * 商品简称
     */
    @JSONField(name= "simple_name")
    private String simpleName;

    /**
     * 商品规格代码
     */
    @JSONField(name= "item_sku_code")
    private String itemSkuCode;

    /**
     * 商品规格名称
     */
    @JSONField(name= "item_sku_name")
    private String itemSkuName;

    /**
     * 售价
     */
    @JSONField(name= "sales_price")
    private double salesPrice;

    /**
     * 数量
     */
    @JSONField(name= "qty")
    private double qty;

    /**
     * 权重比例
     */
    @JSONField(name= "percent")
    private double percent;
}
