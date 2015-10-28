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
 * Created by allan on 2015/8/7.
 */
@Entity
@Table(name = "ERP_ProductOut")
public class MallProductOutBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOutId;
    @ManyToOne
    @JoinColumn(name = "OutStorage_No")
    private MallOutStoreBean outStoreBean;
    @Column(name = "Product_ItemNo")
    private String productItemNo;
    @Column(name = "Location_No")
    private String locationNo;
    @Column(name = "Storage_No")
    private String storageNo;
    @Column(name = "OutStorage_Num")
    private int outStorageNum;
    @Column(name = "OutStorage_Price")
    private double outStoragePrice;
    @Column(name = "Bacth")
    private String batch;
    @Column(name = "Freight_Avg")
    private String freightAvg;
    @Column(name = "OutStorage_Remark")
    private String outStorageRemark;
    @Column(name = "BarCode")
    private String barCode;

    public Long getProductOutId() {
        return productOutId;
    }

    public void setProductOutId(Long productOutId) {
        this.productOutId = productOutId;
    }

    public MallOutStoreBean getOutStoreBean() {
        return outStoreBean;
    }

    public void setOutStoreBean(MallOutStoreBean outStoreBean) {
        this.outStoreBean = outStoreBean;
    }

    public String getProductItemNo() {
        return productItemNo;
    }

    public void setProductItemNo(String productItemNo) {
        this.productItemNo = productItemNo;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public int getOutStorageNum() {
        return outStorageNum;
    }

    public void setOutStorageNum(int outStorageNum) {
        this.outStorageNum = outStorageNum;
    }

    public double getOutStoragePrice() {
        return outStoragePrice;
    }

    public void setOutStoragePrice(double outStoragePrice) {
        this.outStoragePrice = outStoragePrice;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getFreightAvg() {
        return freightAvg;
    }

    public void setFreightAvg(String freightAvg) {
        this.freightAvg = freightAvg;
    }

    public String getOutStorageRemark() {
        return outStorageRemark;
    }

    public void setOutStorageRemark(String outStorageRemark) {
        this.outStorageRemark = outStorageRemark;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
