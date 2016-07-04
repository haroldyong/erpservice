/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

/**
 * Created by liual on 2015-08-26.
 */
public class NSOrderItemResult {
    private String GoodsID;
    private String GoodsName;
    private double Price;
    private String GoodsSpec;
    private int Count;
    private String GoodsStatus;
    private double Tax;

    public String getGoodsID() {
        return GoodsID;
    }

    public void setGoodsID(String goodsID) {
        GoodsID = goodsID;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getGoodsSpec() {
        return GoodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        GoodsSpec = goodsSpec;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getGoodsStatus() {
        return GoodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        GoodsStatus = goodsStatus;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }
}
