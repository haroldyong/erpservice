/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * edb出库单实体
 * Created by allan on 2015/8/7.
 */
@JacksonXmlRootElement(localName = "orderInfo")
public class EDBOutStoreInfo {
    @JacksonXmlProperty(localName = "outstorage_no")
    private String outStorageNo;
    @JacksonXmlProperty(localName = "outstorage_type")
    private int outStorageType;
    @JacksonXmlProperty(localName = "outstorage_time")
    private String outStorageTime;
    @JacksonXmlProperty(localName = "storage_no")
    private String storageNo;
    @JacksonXmlProperty(localName = "upplier_no")
    private String supplierNo;
    @JacksonXmlProperty(localName = "freight_avgway")
    private String freightAvgWay;
    private String freight;
    @JacksonXmlProperty(localName = "import_sign")
    private String importSign;
    @JacksonXmlProperty(localName = "relate_orderNo")
    private String relateOrderNo;
    @JacksonXmlProperty(localName = "YS_instorage_no")
    private String YSInStorageNo;
    @JacksonXmlProperty(localName = "outStorage_remark")
    private String outStorageRemark;
    @JacksonXmlProperty(localName = "product_item")
    private List<EDBProductOut> productOuts;

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
    }

    public int getOutStorageType() {
        return outStorageType;
    }

    public void setOutStorageType(int outStorageType) {
        this.outStorageType = outStorageType;
    }

    public String getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(String outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public String getStorageNo() {
        return storageNo;
    }

    public void setStorageNo(String storageNo) {
        this.storageNo = storageNo;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getFreightAvgWay() {
        return freightAvgWay;
    }

    public void setFreightAvgWay(String freightAvgWay) {
        this.freightAvgWay = freightAvgWay;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getImportSign() {
        return importSign;
    }

    public void setImportSign(String importSign) {
        this.importSign = importSign;
    }

    public String getRelateOrderNo() {
        return relateOrderNo;
    }

    public void setRelateOrderNo(String relateOrderNo) {
        this.relateOrderNo = relateOrderNo;
    }

    public String getYSInStorageNo() {
        return YSInStorageNo;
    }

    public void setYSInStorageNo(String YSInStorageNo) {
        this.YSInStorageNo = YSInStorageNo;
    }

    public String getOutStorageRemark() {
        return outStorageRemark;
    }

    public void setOutStorageRemark(String outStorageRemark) {
        this.outStorageRemark = outStorageRemark;
    }

    public List<EDBProductOut> getProductOuts() {
        return productOuts;
    }

    public void setProductOuts(List<EDBProductOut> productOuts) {
        this.productOuts = productOuts;
    }
}
