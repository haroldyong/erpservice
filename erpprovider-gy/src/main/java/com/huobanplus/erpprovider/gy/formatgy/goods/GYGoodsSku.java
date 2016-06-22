package com.huobanplus.erpprovider.gy.formatgy.goods;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Data
public class GYGoodsSku {

    @JSONField(name = "item_id")
    private String itemId;

    @JSONField(name = "code")
    private String code;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "stock_status_code")
    private String stockStatusCode;

    @JSONField(name = "weight")
    private double weight;

    @JSONField(name = "sales_point")
    private double salesPoint;

    @JSONField(name = "package_point")
    private double packagePoint;

    @JSONField(name = "purchase_price")
    private double purchasePrice;

    @JSONField(name = "sales_price")
    private double salesPrice;

    @JSONField(name = "agent_price")
    private double agentPrice;

    @JSONField(name = "cost_price")
    private double costPrice;

    @JSONField(name = "note")
    private String note;
}
