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

import java.util.List;


/**
 * 创建订单主体
 * Created by allan on 4/19/16.
 */
@Data
public class ISCSCreateOrderInfo {
    /**
     * 订单编号
     */
    @JSONField(name = "order_no")
    private String orderNo;
    /**
     * 仓库id
     */
    @JSONField(name = "stock_id")
    private int stockId;
    /**
     * 是否已指定快递
     * 0:否;1:是
     */
    @JSONField(name = "transporter_flag")
    private int transporterFlag;
    /**
     * 运输商
     * 当TRANSPORTER_FLAG为1时必填
     */
    @JSONField(name = "transporter_id")
    private int transporterId;
    /**
     * 店铺id
     */
    @JSONField(name = "shop_id")
    private int shopId;
    /**
     * 下单时间
     */
    @JSONField(name = "order_create_time")
    private String createTime;
    /**
     * 支付时间
     */
    @JSONField(name = "order_pay_time")
    private String payTime;
    /**
     * 买家昵称
     */
    @JSONField(name = "buyer_nick")
    private String buyNick;
    /**
     * 收货人国家
     */
    @JSONField(name = "receiver_country")
    private String country;
    /**
     * 收货人省
     */
    @JSONField(name = "receiver_province")
    private String province;
    /**
     * 收货人市
     */
    @JSONField(name = "receiver_city")
    private String city;
    /**
     * 区
     */
    @JSONField(name = "receiver_county")
    private String county;
    /**
     * 地址
     */
    @JSONField(name = "receiver_address")
    private String address;
    /**
     * 邮编
     */
    @JSONField(name = "receiver_zip")
    private String zip;
    /**
     * 名称
     */
    @JSONField(name = "receiver_name")
    private String name;
    /**
     * 手机
     */
    @JSONField(name = "receiver_mobile")
    private String mobile;
    /**
     * 电话
     */
    @JSONField(name = "receiver_phone")
    private String tel;
    /**
     * 要求发货日期
     */
    @JSONField(name = "request_ship_date")
    private String requestShipDate;
    /**
     * 是否需要发票
     */
    @JSONField(name = "need_invoice")
    private int needInvoice;
    /**
     * 发票抬头
     */
    @JSONField(name = "invoice_name")
    private String invoiceName;
    /**
     * 发票内容
     */
    @JSONField(name = "invoice_content")
    private String invoiceContent;
    /**
     * 付款金额
     */
    @JSONField(name = "payment")
    private double payment;
    /**
     * 总金额
     */
    @JSONField(name = "total_fee")
    private double totalFee;
    /**
     * 优惠金额
     */
    @JSONField(name = "discount_fee")
    private double discountFee;
    /**
     * 邮费
     */
    @JSONField(name = "post_fee")
    private double postFee;
    /**
     * 是否需要贺卡
     */
    @JSONField(name = "need_card")
    private int needCard;
    /**
     * 贺卡内容
     */
    @JSONField(name = "card_content")
    private String cardContent;
    @JSONField(name = "skus")
    private List<ISCSCreateOrderItem> createOrderItems;
}
