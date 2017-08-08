/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
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
    /**
     * 平台商品id
     */
    private int goodId;
    /**
     * 平台货品id
     */
    private int productId;
    private String productBn;
    private String name;
    private String brand;
    /**
     * 成本价
     */
    private double cost;
    /**
     * 市场价
     */
    private double price;
    /**
     * 商品总价
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
     * 商品货号(原产国代码（3位）+商品HS编码)
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

    /**
     * 商品重量 单位g
     */
    private double weight;

    /**
     * 净重 单位g
     */
    private double suttleWeight;

    /**
     * 退款状态（售后状态）{@link}
     */
    private int refundStatus;
    
    /**
     * 包装信息
     */
    private String packageInfo;
}
