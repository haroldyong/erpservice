/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.formatdata;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class WangDianV2Order1 {

    /**
     * 平台订单编号
     */
    @JSONField(name = "tid")
    private String tid;

    /**
     * 订单推送状态: 10未确认(等待付款的订单,cod订单不需要等付款，直接进行待发货)
     * 20待尾款(部分付款的订单,要等尾款付完再发货)
     * 30已付款待发货(包含货到付款)
     * 40部分发货(拆分发货才会出现)
     * 50已发货(平台上订单状态已经发货)
     * 60已签收
     * 70已完成
     * 80已退款(付过款后来全部退款了)
     * 90已关闭(未付款直接取消的订单)
     * 订单可修改状态：30 =》60 /70/ 80，只可以从30状态改变成这3种状态。
     */
    @JSONField(name = "trade_status")
    private Integer tradeStatus;

    /**
     * 付款状态:0未付款1部分付款2已付款
     */
    @JSONField(name = "pay_status")
    private Integer payStatus;

    /**
     * 发货条件:1款到发货 2货到付款(包含部分货到付款)
     */
    @JSONField(name = "delivery_term")
    private Integer deliveryTerm;

    /**
     * 下单时间,格式: YYYY-MM-DD HH:MM:SS
     */
    @JSONField(name = "trade_time")
    private String tradeTime;

    /**
     * 付款时间,格式如上. 未付款订单为: 0000-00-00 00:00:00
     */
    @JSONField(name = "pay_time")
    private String payTime;

    /**
     * 买家昵称
     */
    @JSONField(name = "buyer_nick")
    private String buyerNick;

    /**
     * 买家email
     */
    @JSONField(name = "buyer_email")
    private String buyerEmail;

    /**
     * 支付单号
     */
    @JSONField(name = "pay_id")
    private String payId;

    /**
     * 支付帐号
     */
    @JSONField(name = "pay_account")
    private String payAccount;

    /**
     * 收件人姓名
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * 收件人省份
     */
    @JSONField(name = "receiver_province")
    private String receiverProvince;

    /**
     * 收件城市
     */
    @JSONField(name = "receiver_city")
    private String receiverCity;

    /**
     * 收件人区县
     */
    @JSONField(name = "receiver_district")
    private String receiverDistrict;

    /**
     * 收件人地址
     */
    @JSONField(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收件人手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 收件人电话
     */
    @JSONField(name = "receiver_telno")
    private String receiverTelno;

    /**
     * 收件人邮编
     */
    @JSONField(name = "receiver_zip")
    private String receiverZip;

    /**
     * 物流方式，-1表示自由选择，2平邮 3EMS
     */
    @JSONField(name = "logistics_type")
    private Integer logisticsType;

    /**
     * 是否需要发票,0 不需要，1需要  2增值税发票
     */
    @JSONField(name = "invoice_type")
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    @JSONField(name = "invoice_title")
    private String invoiceTitle;

    /**
     * 买家留言
     */
    @JSONField(name = "buyer_message")
    private String buyerMessage;

    /**
     * 客服备注
     */
    @JSONField(name = "seller_memo")
    private String sellerMemo;

    /**
     * 客服标旗，取值0-5
     */
    @JSONField(name = "seller_flag")
    private int sellerFlag;

    /**
     * 邮费
     */
    @JSONField(name = "post_amount")
    private double postAmount;

    /**
     * 货到付款金额
     */
    @JSONField(name = "code_amount")
    private double codeAmount;

    /**
     * 货到付款买家费用，这个钱卖家收不回来，是收快递公司直接收走，但在快递单是要打印出来，否则快递收款就错了
     */
    @JSONField(name = "ext_cod_fee")
    private String extCodFee;

    /**
     * 其他金额
     */
    @JSONField(name = "other_amount")
    private String otherAmount;

    /**
     * 已支付金额，未付款情况下paid应为0，(货到付款时也可也不为0，表示只有一部分是货到付款)
     */
    @JSONField(name = "paid")
    private double paid;

    /**
     * 证件类型 1 身份证  否
     */
    @JSONField(name = "id_card_type")
    private String idCardType;

    /**
     * 证件号 否
     */
    @JSONField(name = "id_card")
    private String idCard;

    /**
     * 自定义字段
     */
    @JSONField(name = "cust_data")
    private String custData;

    /**
     * 子订单信息(货品信息)
     */
    @JSONField(name = "order_list")
    private List<WangDianV2OrderItem1> orderList;

}