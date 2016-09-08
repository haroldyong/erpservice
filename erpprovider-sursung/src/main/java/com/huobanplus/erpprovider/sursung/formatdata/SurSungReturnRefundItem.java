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
 * Created by wuxiongliu on 2016-09-08.
 */
@Data
public class SurSungReturnRefundItem {

    /**
     * 平台订单明细编号
     */
    @JSONField(name = "outer_oi_id")
    private String outerOiId;

    /**
     * 商家商品编码
     */
    @JSONField(name = "sku_id")
    private String skuId;

    /**
     * 退货数量
     */
    @JSONField(name = "qty")
    private int qty;

    /**
     * SKU退款金额
     */
    @JSONField(name = "amount")
    private double amount;

    /**
     * 可选: 退货，换货，补发，或者其他
     */
    @JSONField(name = "type")
    private String type;

    /**
     * 商品名称，outer_oi_id 存在，此处可以不填
     */
    @JSONField(name = "name")
    private String name;

    /**
     * 图片地址outer_oi_id 存在，此处可以不填
     */
    @JSONField(name = "pic")
    private String pic;

    /**
     * 属性规格outer_oi_id 存在，此处可以不填
     */
    @JSONField(name = "properties_value")
    private String propertiesValue;
}
