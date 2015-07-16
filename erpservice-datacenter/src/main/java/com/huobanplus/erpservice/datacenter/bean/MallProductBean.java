package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品实体
 */
@Entity
@Table(name = "Mall_Order_Items")
public class MallProductBean {
    /**
     * 主键id
     */
    @Id
    @Column(name = "Item_Id")
    private long id;
    /**
     * 订单号
     */
    @Column(name = "Order_Id")
    private String orderId;
    /**
     * 货品ID
     */
    @Column(name = "Product_Id")
    private int productId;
    /**
     * 配送状态
     */
    @Column(name = "Dly_Status")
    private String deliverStatus;
    /**
     * 商品类型
     */
    @Column(name = "Type_Id")
    private int typeId;
    /**
     * 货号
     */
    @Column(name = "Bn")
    private String bn;
    /**
     * 货品名称
     */
    @Column(name = "Name")
    private String name;
    /**
     * 成本
     */
    @Column(name = "Cost")
    private float cost;
    /**
     * 单价
     */
    @Column(name = "Price")
    private float price;
    /**
     * 总金额
     */
    @Column(name = "Amount")
    private float amount;
    /**
     * 数量
     */
    @Column(name = "Nums")
    private int nums;
    /**
     * 已发数量
     */
    @Column(name = "Sendnum")
    private int sendNum;
    /**
     * 类型
     */
    @Column(name = "Is_Type")
    private String isType;
    /**
     * 供货商Id
     */
    @Column(name = "Supplier_Id")
    private int supplierId;
    /**
     * 商户Id
     */
    @Column(name = "Customer_Id")
    private int customerId;
    /**
     * 商品Id
     */
    @Column(name = "Goods_Id")
    private int goodId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrder() {
        return orderId;
    }

    public void setOrder(String orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public int getSendNum() {
        return sendNum;
    }

    public void setSendNum(int sendNum) {
        this.sendNum = sendNum;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
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
