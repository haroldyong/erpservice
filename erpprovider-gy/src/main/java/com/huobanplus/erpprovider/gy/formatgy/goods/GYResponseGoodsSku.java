package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/22.
 */
@Data
public class GYResponseGoodsSku {

    /**
     * 规格ID
     */
    @JSONField(name= "id")
    private String id;

    /**
     * 规格代码
     */
    @JSONField(name= "code")
    private String code;

    /**
     * 规格名称
     */
    @JSONField(name= "name")
    private String name;

    /**
     * 重量
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
     * 商品条码
     */
    @JSONField(name= "bar_code")
    private String barCode;

    /**
     * 库存状态代码
     */
    @JSONField(name= "stock_status_code")
    private String stockStatusCode;

    /**
     * 备注
     */
    @JSONField(name= "note")
    private String note;
}
