package com.huobanplus.erpprovider.wangdian.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
@Data
public class WangDianOrderItem {

    /**
     * 货品的唯一编码
     */
    @JSONField(name= "Sku_Code")
    private String skuCode;

    /**
     * 货品名称
     */
    @JSONField(name= "Sku_Name")
    private String skuName;

    /**
     * 货品单价
     */
    @JSONField(name= "Sku_Price")
    private double skuPrice;

    /**
     * 折扣
     */
    @JSONField(name= "Discount")
    private double discount;

    /**
     * 单品金额小计
     */
    @JSONField(name= "Total")
    private double total;

    /**
     * 货品数量
     */
    @JSONField(name= "Qty")
    private int qty;

    /**
     * 行项目备注
     */
    @JSONField(name= "Item_Remark")
    private String itemRemark;
}
