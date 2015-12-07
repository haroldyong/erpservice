/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.jsonmodel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by liual on 2015-12-07.
 */
@Setter
@Getter
public class Order {
    private String orderId;
    private int memberId;
    private int confirm;
    private int orderStatus;
    private int payStatus;
    private int shipStatus;
    private float weight;
    private String orderName;
    private int itemNum;
    private String lastUpdateTime;
    private String createTime;

    private String shipName;
    private String shipArea;
    private String shipAddr;
    private String shipZip;
    private String shipTel;
    private String shipEmail;
    private String shipMobile;
    private double costItem;
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
    private List<OrderItem> orderItems;
}
