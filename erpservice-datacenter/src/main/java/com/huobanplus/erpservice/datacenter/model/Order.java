/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by liual on 2015-12-07.
 */
@Setter
@Getter
public class Order extends BaseInfo {
    private String orderId;
    private int memberId;
    /**
     * 下单用户账户名（手机号）
     */
    private String userLoginName;
    /**
     * 下单用户用户昵称
     */
    private String userNickname;
    private int confirm;
    private int orderStatus;
    private int payStatus;
    private int shipStatus;
    private float weight;
    private String orderName;
    private int itemNum;
    private String lastUpdateTime;
    private String createTime;
    /**
     * 收货人
     */
    private String shipName;
    /**
     * 收货区域
     */
    private String shipArea;
    private String province;
    private String city;
    private String district;
    /**
     * 收货地址
     */
    private String shipAddr;
    /**
     * 邮编
     */
    private String shipZip;
    /**
     * 电话
     */
    private String shipTel;
    private String shipEmail;
    /**
     * 手机
     */
    private String shipMobile;
    /**
     * 产品总金额
     */
    private double costItem;
    /**
     * 线上支付金额
     */
    private double onlinePayAmount;
    /**
     * 运费
     */
    private double costFreight;
    /**
     * 货币
     */
    private String currency;
    /**
     * 订单金额
     */
    private double finalAmount;

    /**
     * 订单税款
     */
    private double taxAmount;

    /**
     * 订单优惠金额
     */
    private double pmtAmount;
    /**
     * 订单附言（用户留言）
     */
    private String memo;
    /**
     * 订单备注（商家留言）
     */
    private String remark;
    private int printStatus;
    /**
     * 支付方式名称
     */
    private String paymentName;
    /**
     * 支付方式 {@link com.huobanplus.erpservice.common.ienum.OrderEnum.PaymentOptions}
     */
    private int payType;
    /**
     * 分销商id
     */
    private int customerId;
    /**
     * 供应商id
     */
    private int supplierId;
    /**
     * 物流公司
     */
    private String logiName;
    /**
     * 物流单号
     */
    private String logiNo;
    /**
     * 快递公司编号
     */
    private String logiCode;
    /**
     * 支付时间
     */
    private String payTime;
    /**
     * 主订单号
     */
    private String unionOrderId;
    /**
     * 签收状态
     */
    private int receiveStatus;
    /**
     * 是否需要开票
     */
    private int isTax;
    /**
     * 开票公司抬头
     */
    private String taxCompany;


    /**
     * 考拉&跨境易购：买家身份证
     */
    private String buyerPid;

    /**
     * 跨境易购： 买家真实姓名
     */
    private String buyerName;

    /**
     * 支付成功后支付平台返回的支付单号
     */
    private String payNumber;

    /**
     *  订单来源店铺code
     */
    private int sourceShop;

    /**
     * 订单推送失败信息
     */
    private String errorMessage;

    /**
     * 订单明细
     */
    private List<OrderItem> orderItems;


}
