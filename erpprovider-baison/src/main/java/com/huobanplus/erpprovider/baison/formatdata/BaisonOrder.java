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
     * 添加时间
     */
    @JSONField(name = "add_time")
    private String addTime;

    /**
     *订单编号
     */
    @JSONField(name = "order_sn")
    private String orderSn;

    /**
     *店铺代码
     */
    @JSONField(name = "sd_code")
    private String sdCode;

    /**
     *订单状态（0，未确认；1，已确认；2，已取消；3，无效；4，退货；5，锁定；6，解锁；7，完成；8，拒收；9，已合并；10，已拆分；）
     */
    @JSONField(name = "order_status")
    private int orderStatus;

    /**
     *付款状态（0，未付款；1，付款中；2，已付款；3，已结算）
     */
    @JSONField(name = "pay_status")
    private int payStatus;

    /**
     *收货人
     */
    @JSONField(name = "consignee")
    private String consignee;

    /**
     *省
     */
    @JSONField(name = "province_name")
    private String provinceName;

    /**
     *市
     */
    @JSONField(name = "city_name")
    private String cityName;

    /**
     *区
     */
    @JSONField(name = "district_name")
    private String districtName;

    /**
     *详细地址
     */
    @JSONField(name = "address")
    private String address;

    /**
     *会员名
     */
    @JSONField(name = "user_name")
    private String userName;

    /**
     *电子邮件
     */
    @JSONField(name = "email")
    private String email;

    /**
     *手机号码
     */
    @JSONField(name = "mobile")
    private String mobile;

    /**
     *电话号码
     */
    @JSONField(name = "tel")
    private String tel;

    /**
     *邮政编码
     */
    @JSONField(name = "zipcode")
    private String zipcode;

    /**
     *O2O门店代码
     */
    @JSONField(name = "pos_code")
    private String posCode;

    /**
     *下单门店代码
     */
    @JSONField(name = "saleShopCode")
    private String saleShopCode;

    /**
     *下单店员编号
     */
    @JSONField(name = "salerEmployeeNo")
    private String salerEmployeeNo;

    /**
     *支付方式代码
     */
    @JSONField(name = "pay_code")
    private String payCode;

    /**
     *会员编号
     */
    @JSONField(name = "vip_no")
    private String vipNo;

    /**
     *快递公司代码
     */
    @JSONField(name = "shipping_code")
    private String shippingCode;

    /**
     *运费
     */
    @JSONField(name = "shipping_fee")
    private double shippingFee;

    /**
     *买家应付金额
     */
    @JSONField(name = "order_amount")
    private double orderAmount;

    /**
     *买家已付金额
     */
    @JSONField(name = "payment")
    private double payment;

    /**
     * 订单明细
     */
    @JSONField(name = "items")
    private List<BaisonOrderItem> orderItems;
}