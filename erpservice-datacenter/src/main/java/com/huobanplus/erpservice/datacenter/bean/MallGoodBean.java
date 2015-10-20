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
import java.util.List;

/**
 * 商品实体
 * Created by liual on 2015-08-26.
 */
@Entity
@Table(name = "Mall_Good")
public class MallGoodBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int goodId;
    private String bn;
    private String goodName;
    private int num;
    private double price;
    private int isSku;
    private String remark;
    private String erpSysData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodBean", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MallProductBean> productBeans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getBn() {
        return bn;
    }

    public void setBn(String bn) {
        this.bn = bn;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsSku() {
        return isSku;
    }

    public void setIsSku(int isSku) {
        this.isSku = isSku;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<MallProductBean> getProductBeans() {
        return productBeans;
    }

    public void setProductBeans(List<MallProductBean> productBeans) {
        this.productBeans = productBeans;
    }

    public String getErpSysData() {
        return erpSysData;
    }

    public void setErpSysData(String erpSysData) {
        this.erpSysData = erpSysData;
    }
}
