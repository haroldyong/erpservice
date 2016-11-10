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

import java.util.List;

@Data
public class BaisonOrder {

    /**
     *
     */
    @JSONField(name = "add_time")
    private String addTime;

    /**
     *
     */
    @JSONField(name = "address")
    private String address;

    /**
     *
     */
    @JSONField(name = "city_name")
    private String cityName;

    /**
     *
     */
    @JSONField(name = "consignee")
    private String consignee;

    /**
     *
     */
    @JSONField(name = "district_name")
    private String districtName;

    /**
     *
     */
    @JSONField(name = "email")
    private String email;

    /**
     *
     */
    @JSONField(name = "goods_amount")
    private String goodsAmount;

    /**
     *
     */
    @JSONField(name = "goods_count")
    private String goodsCount;

    /**
     *
     */
    @JSONField(name = "mobile")
    private String mobile;

    /**
     *
     */
    @JSONField(name = "money_paid")
    private String moneyPaid;

    /**
     *
     */
    @JSONField(name = "oid")
    private String oid;

    /**
     *
     */
    @JSONField(name = "order_amount")
    private String orderAmount;

    /**
     *
     */
    @JSONField(name = "pay_name")
    private String payName;

    /**
     *
     */
    @JSONField(name = "pay_time")
    private String payTime;

    /**
     *
     */
    @JSONField(name = "postscript")
    private String postscript;

    /**
     *
     */
    @JSONField(name = "province_name")
    private String provinceName;

    /**
     *
     */
    @JSONField(name = "sd_id")
    private String sdId;

    /**
     *
     */
    @JSONField(name = "shipping_fee")
    private String shippingFee;

    /**
     *
     */
    @JSONField(name = "shipping_name")
    private String shippingName;

    /**
     *
     */
    @JSONField(name = "tel")
    private String tel;

    /**
     *
     */
    @JSONField(name = "to_buyer")
    private String toBuyer;

    /**
     *
     */
    @JSONField(name = "total_amount")
    private String totalAmount;

    /**
     *
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     *
     */
    @JSONField(name = "zipcode")
    private String zipcode;

    /**
     * 订单明细
     */
    @JSONField(name = "orders")
    private List<BaisonOrderItem> orderItems;
}