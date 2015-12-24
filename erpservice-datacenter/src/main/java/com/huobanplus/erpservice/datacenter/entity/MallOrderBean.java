/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;


import com.huobanplus.erpservice.common.ienum.OrderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 订单实体
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "ERP_Order")
@Setter
@Getter
public class MallOrderBean {
    @Id
    @GeneratedValue
    @Column(length = 20)
    private String orderId;
    private int memberId;
    private int confirm;
    private OrderEnum.OrderStatus orderStatus;
    private OrderEnum.PayStatus payStatus;
    private OrderEnum.ShipStatus shipStatus;
    private float weight;
    private String orderName;
    private int itemNum;
    private Date lastUpdateTime;
    private Date createTime;

    private String shipName;
    private String shipArea;
    private String shipAddr;
    private String shipZip;
    private String shipTel;
    private String shipEmail;
    private String shipMobile;
    @Column(precision = 2)
    private double costItem;
    /**
     * 运费
     */
    @Column(precision = 2)
    private double costFreight;
    /**
     * 货币
     */
    @Column(precision = 2)
    private String currency;
    /**
     * 订单金额
     */
    @Column(precision = 2)
    private double finalAmount;

    /**
     * 订单优惠金额
     */
    @Column(precision = 2)
    private double pmtAmount;
    /**
     * 订单附言（用户留言）
     */
    @Lob
    private String memo;
    /**
     * 订单备注（商家留言）
     */
    @Lob
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
    private Date payTime;
    /**
     * 主订单号
     */
    private String unionOrderId;
    /**
     * 签收状态
     */
    private int receiveStatus;

    private String erpInfo;
    @OneToMany(mappedBy = "orderBean")
    private List<MallOrderItemBean> orderItemBeans;
}
