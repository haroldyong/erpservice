/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 商品实体
 */
@Entity
@Table(name = "ERP_OrderItem")
@Setter
@Getter
public class MallOrderItemBean {
    @Id
    @GeneratedValue
    private Integer itemId;
    @ManyToOne
    @JoinColumn(name = "ORDERID")
    private MallOrderBean orderBean;
    private String unionOrderId;
    private int productId;
    private String bn;
    private String name;
    /**
     * 成本价
     */
    @Column(precision = 2)
    private double cost;
    /**
     * 市场价
     */
    @Column(precision = 2)
    private double price;
    /**
     * 销售价
     */
    @Column(precision = 2)
    private double amount;
    /**
     * 购买数量
     */
    private int num;
    /**
     * 已发数量
     */
    private int sendNum;
    /**
     * 供应商id
     */
    private int supplierId;
    /**
     * 分销商id
     */
    private int customerId;
    private int goodId;
    private String goodBn;
    /**
     * 网店规格
     */
    private String standard;

    /**
     * 产品简介
     */
    private String brief;
}
