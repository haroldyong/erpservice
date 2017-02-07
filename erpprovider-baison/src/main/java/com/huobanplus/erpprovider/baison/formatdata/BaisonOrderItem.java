/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class BaisonOrderItem {

    /**
     *Sku
     */
    @JSONField(name = "sku_sn")
    private String skuSn;

    /**
     *商品单价(折前)
     */
    @JSONField(name = "goods_price")
    private double goodsPrice;

    /**
     *商品单价(折后)
     */
    @JSONField(name = "transaction_price")
    private double transactionPrice;

    /**
     *折扣
     */
    @JSONField(name = "discount")
    private double discount;

    /**
     *数量
     */
    @JSONField(name = "goods_number")
    private int goodsNumber;

    /**
     * 是否礼品
     */
    @JSONField(name = "is_gift")
    private int isGift;
}