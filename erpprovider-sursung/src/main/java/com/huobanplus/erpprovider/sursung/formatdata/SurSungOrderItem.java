/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
@Data
public class SurSungOrderItem {

    /**
     * 商家SKU，对应库存管理的 SKU，最大 40 Y
     */
    @JSONField(name = "sku_id")
    private String skuId;

    /**
     * 网站对应的自定义SKU编号，最大30 N
     */
    @JSONField(name = "shop_sku_id")
    private String shopSkuId;

    /**
     * 属性；最长不超过100 长度 N
     */
    @JSONField(name = "properties_value")
    private String propertiesValue;

    /**
     * 应付金额，保留两位小数，单位（元）；备注：可能存在人工改价 Y
     */
    @JSONField(name = "amount")
    private double amount;

    /**
     * 基本价（拍下价格），保留两位小数，单位（元） Y
     */
    @JSONField(name = "base_price")
    private double basePrice;

    /**
     * 购买数量 Y
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * 商品名称;最长不超过  100 长度 Y
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 子订单号，最长不超过 50 Y
     */
    @JSONField(name = "outer_oi_id")
    private String outerOiId;
}
