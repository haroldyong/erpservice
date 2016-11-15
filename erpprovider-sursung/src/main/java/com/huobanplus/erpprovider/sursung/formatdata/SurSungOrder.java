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

import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
@Data
public class SurSungOrder {

    /**
     * 店铺编号
     */
    @JSONField(name = "shop_id")
    private int shopId;

    /**
     * 店铺名称
     */
    @JSONField(name = "shop_name")
    private String shopName;

    /**
     * 店铺站点
     */
    @JSONField(name = "shop_site")
    private String shopSite;

    /**
     * 订单号
     */
    @JSONField(name = "so_id")
    private String soId;

    /**
     * 下单时间
     */
    @JSONField(name = "order_date")
    private String orderDate;

    /**
     * 订单状态
     */
    @JSONField(name = "shop_status")
    private String shopStatus;

    /**
     * 买家昵称
     */
    @JSONField(name = "shop_buyer_id")
    private String shopBuyerId;

    /**
     * 收货人省份
     */
    @JSONField(name = "receiver_state")
    private String receiverState;

    /**
     * 收货人城市
     */
    @JSONField(name = "receiver_city")
    private String receiverCity;

    /**
     * 收货人地区
     */
    @JSONField(name = "receiver_district")
    private String receiverDistrict;

    /**
     * 收货人详细地址
     */
    @JSONField(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人姓名
     */
    @JSONField(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人固话
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 收货人手机
     */
    @JSONField(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 指定发货日期
     */
    @JSONField(name = "send_date")
    private String sendDate;

    /**
     * 应付金额
     */
    @JSONField(name = "pay_amount")
    private double payAmount;

    /**
     * 抵扣金额
     */
    @JSONField(name = "free_amount")
    private double freeAmount;

    /**
     * 运费
     */
    @JSONField(name = "freight")
    private double freight;

    /**
     * 买家留言
     */
    @JSONField(name = "buyer_message")
    private String buyerMessage;

    /**
     * 备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * 发票抬头
     */
    @JSONField(name = "invoice_title")
    private String invoiceTitle;

    /**
     * 是否货到付款
     */
    @JSONField(name = "is_cod")
    private boolean isCod;

    /**
     * 店铺修改日期
     */
    @JSONField(name = "shop_modified")
    private String shopModified;

    /**
     * 支付信息 推送订单时设置
     */
    @JSONField(name = "pay")
    private SursungPay pay;

    /**
     *  支付信息 查询订单结果返回时设置
     */
    @JSONField(name = "pays")
    private List<SursungPay> pays;

    /**
     * 快递单号
     */
    @JSONField(name = "l_id")
    private String logiNo;

    /**
     * 快递公司
     */
    @JSONField(name = "logistics_company")
    private String logiCompany;

    /**
     * 客户申请退款
     */
    @JSONField(name = "question_desc")
    private String questionDesc;

    /**
     * 买家标记
     */
    @JSONField(name = "tag")
    private int tag;

    /**
     * 卖家标签
     */
    @JSONField(name = "seller_flag")
    private int sellerFlag;

    /**
     * 查询返回结果有值
     */
    @JSONField(name = "outer_pay_id")
    private String outerPayId;

    /**
     * 订单状态 Sent,Merged...
     */
    @JSONField(name = "status")
    private String status;

    @JSONField(name = "paid_amount")
    private double paidAmount;


    /**
     * 订单明细
     */
    @JSONField(name = "items")
    private List<SurSungOrderItem> surSungOrderItems;

}
