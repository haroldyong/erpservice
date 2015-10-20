/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 出库单信息
 * Created by allan on 2015/8/7.
 */
@Entity
@Table(name = "Mall_OutStore")
public class MallOutStoreBean {
    /**
     * 出库单号
     */
    @Id
    @Column(name = "OutStorage_No")
    private String outStorageNo;
    /**
     * 出库类型编号（可在档案管理-仓库档案-出库类型设置中查看）
     */
    @Column(name = "OutStorage_Type")
    private int outStorageType;
    /**
     * 出库时间：是仓库中真实库存减少的时间，此时间下软件库存没有减少
     */
    @Column(name = "OutStorage_Time")
    private Date outStorageTime;
    /**
     * 仓库编码（E店宝中档案管理—仓库档案—仓库设置 查看 ）
     */
    @Column(name = "Storage_No")
    private String storageNo;
    /**
     * 返厂供应商编号：在采购管理—供应商档案 里查看
     */
    @Column(name = "Supplier_No")
    private String supplierNo;
    /**
     * 运费分摊方式（按产品数量/按产品重量）
     */
    @Column(name = "Freight_Avg_Way")
    private String freightAvgWay;
    /**
     * 运费
     */
    @Column(name = "Freight")
    private String freight;
    /**
     * 导入标记（不导入、未导入）
     */
    @Column(name = "Imprt_Sign")
    private String importSign;
    /**
     * 相关单号
     */
    @Column(name = "Relate_OrderNo")
    private String relateOrderNo;
    /**
     * 原始入库单号
     */
    @Column(name = "YS_InStorage_No")
    private String YSInStorageNo;
    /**
     * 出库备注
     */
    @Column(name = "OutStorage_Remark")
    private String outStorageRemark;
    /**
     * 产品明细编码
     */
    @Column(name = "Product_ItemNo")
    private String productItemNo;
    /**
     * 库位编号
     */
    @Column(name = "Location_No")
    private String locationNo;
    /**
     * 出库数量
     */
    @Column(name = "OutStorage_Num")
    private String outStorageNum;
    /**
     * 出库价
     */
    @Column(name = "OutStorage_Price")
    private double outStoragePrice;
    /**
     * 批次
     */
    @Column(name = "Batch")
    private String batch;
    /**
     * 运费均摊
     */
    @Column(name = "Freight_Avg")
    private double freightAvg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "outStoreBean", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MallProductOutBean> mallProductOutBeans;

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

    public Date getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(Date outStorageTime) {
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

    public double getFreightAvg() {
        return freightAvg;
    }

    public void setFreightAvg(double freightAvg) {
        this.freightAvg = freightAvg;
    }

    public List<MallProductOutBean> getMallProductOutBeans() {
        return mallProductOutBeans;
    }

    public void setMallProductOutBeans(List<MallProductOutBean> mallProductOutBeans) {
        this.mallProductOutBeans = mallProductOutBeans;
    }
}
