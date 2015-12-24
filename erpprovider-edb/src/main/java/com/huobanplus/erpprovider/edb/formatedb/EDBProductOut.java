/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.formatedb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * edb货品出库
 * Created by allan on 2015/8/7.
 */
public class EDBProductOut {
    @JacksonXmlProperty(localName = "outstorage_no")
    private String outStorageNo;
    @JacksonXmlProperty(localName = "productItem_no")
    private String productItemNo;
    @JacksonXmlProperty(localName = "location_no")
    private String locationNo;
    @JacksonXmlProperty(localName = "storage_no")
    private String storageNo;
    @JacksonXmlProperty(localName = "outstorage_num")
    private String outStorageNum;
    @JacksonXmlProperty(localName = "outstorage_price")
    private double outStoragePrice;
    private String batch;
    @JacksonXmlProperty(localName = "freight_avg")
    private String freightAvg;
    @JacksonXmlProperty(localName = "outstorage_remark")
    private String outStorageRemark;
    @JacksonXmlProperty(localName = "bar_code")
    private String barCode;

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
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

    public String getOutStorageNum() {
        return outStorageNum;
    }

    public void setOutStorageNum(String outStorageNum) {
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
