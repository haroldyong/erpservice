/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import javax.persistence.*;

/**
 * 商品实体
 */
@Entity
@Table(name = "ERP_OrderItem")
public class MallOrderItemBean {
    @Id
    @GeneratedValue
    private Integer itemId;
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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

    /**
     * 网店规格
     */
    private String standard;

    /**
     * 产品简介
     */
    private String brief;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getUnionOrderId() {
        return unionOrderId;
    }

    public void setUnionOrderId(String unionOrderId) {
        this.unionOrderId = unionOrderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }
}
