package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;

/**
 * Created by allan on 2015/8/7.
 */
@Entity
@Table(name = "Mall_ProductOut")
public class MallProductOutBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productOutId;
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

    public long getProductOutId() {
        return productOutId;
    }

    public void setProductOutId(long productOutId) {
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
