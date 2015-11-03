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
import java.util.Date;
import java.util.List;

/**
 * 订单实体
 * Created by allan on 2015/7/10.
 */
@Entity
@Table(name = "ERP_Orders")
public class MallOrderBean {
    @Id
    @GeneratedValue
    private String orderId;
    private int memberId;
    private int confirm;
    private int status;
    private int payStatus;
    private int shipStatus;
    private int userStatus;
    private int isDelivery;
    /**
     * 快递公司名称
     */
    private String shipping;
    private String shippingArea;
    private float weight;
    private String orderName;
    private int itemNum;
    private Date actTime;
    private Date createTime;
    /**
     * 发货时间
     */
    private Date deliverTime;

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

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

    public double getPmtAmount() {
        return pmtAmount;
    }

    public void setPmtAmount(double pmtAmount) {
        this.pmtAmount = pmtAmount;
    }

    /**
     * 订单附言（用户留言）
     */
    private String memo;
    /**
     * 订单备注（商家留言）
     */
    private String remark;
    private Date lastChangeTime;
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
    private Date payTime;
    /**
     * 是否货到付款
     */
    private int cashOnDly;
    /**
     * 主订单号
     */
    private String unionOrderId;
    /**
     * 签收状态
     */
    private int receiveStatus;
    /**
     * 快递公司编号（需要伙伴商城或者供应商添加相关字段）
     * 应与edb中的快递公司编号对应
     */
    private String expressNo;

    private String erpInfo;

    public String getErpInfo() {
        return erpInfo;
    }

    public void setErpInfo(String erpInfo) {
        this.erpInfo = erpInfo;
    }

    @Transient
    private List<MallOrderItemBean> orderItems;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(int shipStatus) {
        this.shipStatus = shipStatus;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(int isDelivery) {
        this.isDelivery = isDelivery;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getShippingArea() {
        return shippingArea;
    }

    public void setShippingArea(String shippingArea) {
        this.shippingArea = shippingArea;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipArea() {
        return shipArea;
    }

    public void setShipArea(String shipArea) {
        this.shipArea = shipArea;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipTel() {
        return shipTel;
    }

    public void setShipTel(String shipTel) {
        this.shipTel = shipTel;
    }

    public String getShipEmail() {
        return shipEmail;
    }

    public void setShipEmail(String shipEmail) {
        this.shipEmail = shipEmail;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public double getCostItem() {
        return costItem;
    }

    public void setCostItem(double costItem) {
        this.costItem = costItem;
    }

    public double getCostFreight() {
        return costFreight;
    }

    public void setCostFreight(double costFreight) {
        this.costFreight = costFreight;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastChangeTime() {
        return lastChangeTime;
    }

    public void setLastChangeTime(Date lastChangeTime) {
        this.lastChangeTime = lastChangeTime;
    }

    public int getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(int printStatus) {
        this.printStatus = printStatus;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getLogiName() {
        return logiName;
    }

    public void setLogiName(String logiName) {
        this.logiName = logiName;
    }

    public String getLogiNo() {
        return logiNo;
    }

    public void setLogiNo(String logiNo) {
        this.logiNo = logiNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getUnionOrderId() {
        return unionOrderId;
    }

    public void setUnionOrderId(String unionOrderId) {
        this.unionOrderId = unionOrderId;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public List<MallOrderItemBean> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<MallOrderItemBean> orderItems) {
        this.orderItems = orderItems;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public int getCashOnDly() {
        return cashOnDly;
    }

    public void setCashOnDly(int cashOnDly) {
        this.cashOnDly = cashOnDly;
    }
}
