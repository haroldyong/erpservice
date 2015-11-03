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
 * 商品sku货品列表
 * Created by liual on 2015-08-26.
 */
@Entity
@Table(name = "ERP_Product")
public class MallProductBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 商品规格名称
     */
    private String skuName;
    /**
     * sku编号
     */
    private String skuId;
    private int num;

    @ManyToOne
    @JoinColumn(name = "goodId")
    private MallGoodEntity goodBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public MallGoodEntity getGoodBean() {
        return goodBean;
    }

    public void setGoodBean(MallGoodEntity goodBean) {
        this.goodBean = goodBean;
    }
}
