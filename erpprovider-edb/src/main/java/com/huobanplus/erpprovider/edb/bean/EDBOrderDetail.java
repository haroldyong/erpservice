/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by liual on 2015-10-23.
 */
public class EDBOrderDetail {
    private String tid;
    private String storageId;
    /**
     * 交易编号
     */
    private String transactionId;
    /**
     * 客户编号
     */
    private String edbCustomerId;
    /**
     * 分销商编号
     */
    private String edbDistributorId;
    private String shopName;
    /**
     * 外部平台订单编号
     */
    private String outTid;
    private String outPayTid;
    /**
     * 凭证单号
     */
    private String voucherId;
    /**
     * 店铺代码
     */
    private String shopId;
    private String type;
    private String express;
    private String expressNo;
    private String status;
    private String getTime;
    private String reviewOrdersOperator;
    private String reviewOrdersTime;
    private String financeReviewOperator;
    private String financeReviewTime;
    private String printer;
    private String printTime;
    /**
     * 配货员
     */
    private String distributer;
    /**
     * 配货时间
     */
    private String distributTime;
    /**
     * 验货员
     */
    private String inspecter;
    /**
     * 验货时间
     */
    private String inspectTime;
    /**
     * 取消员
     */
    private String cancelOperator;
    /**
     * 取消世间
     */
    private String cancelTime;
    private String deliveryOperator;
    private String deliveryTime;
    private String finishTime;
    private String modifyTime;
    private String deliveryStatus;
    private List<EDBOrderItemDetail> orderItemDetails;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getEdbCustomerId() {
        return edbCustomerId;
    }

    public void setEdbCustomerId(String edbCustomerId) {
        this.edbCustomerId = edbCustomerId;
    }

    public String getEdbDistributorId() {
        return edbDistributorId;
    }

    public void setEdbDistributorId(String edbDistributorId) {
        this.edbDistributorId = edbDistributorId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getOutPayTid() {
        return outPayTid;
    }

    public void setOutPayTid(String outPayTid) {
        this.outPayTid = outPayTid;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getReviewOrdersOperator() {
        return reviewOrdersOperator;
    }

    public void setReviewOrdersOperator(String reviewOrdersOperator) {
        this.reviewOrdersOperator = reviewOrdersOperator;
    }

    public String getReviewOrdersTime() {
        return reviewOrdersTime;
    }

    public void setReviewOrdersTime(String reviewOrdersTime) {
        this.reviewOrdersTime = reviewOrdersTime;
    }

    public String getFinanceReviewOperator() {
        return financeReviewOperator;
    }

    public void setFinanceReviewOperator(String financeReviewOperator) {
        this.financeReviewOperator = financeReviewOperator;
    }

    public String getFinanceReviewTime() {
        return financeReviewTime;
    }

    public void setFinanceReviewTime(String financeReviewTime) {
        this.financeReviewTime = financeReviewTime;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getDistributer() {
        return distributer;
    }

    public void setDistributer(String distributer) {
        this.distributer = distributer;
    }

    public String getDistributTime() {
        return distributTime;
    }

    public void setDistributTime(String distributTime) {
        this.distributTime = distributTime;
    }

    public String getInspecter() {
        return inspecter;
    }

    public void setInspecter(String inspecter) {
        this.inspecter = inspecter;
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }

    public String getCancelOperator() {
        return cancelOperator;
    }

    public void setCancelOperator(String cancelOperator) {
        this.cancelOperator = cancelOperator;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getDeliveryOperator() {
        return deliveryOperator;
    }

    public void setDeliveryOperator(String deliveryOperator) {
        this.deliveryOperator = deliveryOperator;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public List<EDBOrderItemDetail> getOrderItemDetails() {
        return orderItemDetails;
    }

    public void setOrderItemDetails(List<EDBOrderItemDetail> orderItemDetails) {
        this.orderItemDetails = orderItemDetails;
    }
}
