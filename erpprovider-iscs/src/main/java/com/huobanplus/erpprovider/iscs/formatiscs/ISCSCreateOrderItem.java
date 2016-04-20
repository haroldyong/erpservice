/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.iscs.formatiscs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 创建订单货品
 * Created by allan on 4/19/16.
 */
@Data
public class ISCSCreateOrderItem {
    /**
     * 货号
     */
    @JSONField(name = "product_code")
    private String productCode;
    /**
     * 数量
     */
    @JSONField(name = "qty")
    private int num;
    /**
     * 实付金额
     */
    @JSONField(name = "payment")
    private double price;
    /**
     * 优惠金额
     */
    @JSONField(name = "discount_fee")
    private double discountFee;
    /**
     * 店铺id
     */
    @JSONField(name = "shop_id")
    private int shopId;
    /**
     * 货主id
     */
    @JSONField(name = "owner_id")
    private int ownerId;
    /**
     * 销售价
     */
    @JSONField(name = "sell_price")
    private double sellPrice;
}
