/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

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
    @JSONField(name = "Sku_Code")
    private String skuCode;

    /**
     * 货品名称
     */
    @JSONField(name = "Sku_Name")
    private String skuName;

    /**
     * 货品单价
     */
    @JSONField(name = "Sku_Price")
    private double skuPrice;

    /**
     * 折扣
     */
    @JSONField(name = "Discount")
    private double discount;

    /**
     * 单品金额小计
     */
    @JSONField(name = "Total")
    private double total;

    /**
     * 货品数量
     */
    @JSONField(name = "Qty")
    private int qty;

    /**
     * 行项目备注
     */
    @JSONField(name = "Item_Remark")
    private String itemRemark;
}
