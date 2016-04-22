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

/**
 * Created by liual on 2015-12-07.
 */
@Setter
@Getter
public class OrderItem extends BaseInfo {
    private Integer itemId;
    private String orderId;
    private String unionOrderId;
    private String productBn;
    private String name;
    /**
     * 成本价
     */
    private double cost;
    /**
     * 市场价
     */
    private double price;
    /**
     * 销售价
     */
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
     * 退货数量
     */
    private int refundNum;
    /**
     * 供应商id
     */
    private int supplierId;
    /**
     * 分销商id
     */
    private int customerId;
    /**
     * 商品货号
     */
    private String goodBn;

    /**
     * 网店规格
     */
    private String standard;

    /**
     * 产品简介
     */
    private String brief;

    /**
     * 发货状态
     */
    private int shipStatus;
}
