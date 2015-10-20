/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Created by liual on 2015-08-26.
 */
public class NSGoodDetailResult {
    private String ItemID;
    private String ItemName;
    private String OuterID;
    private int Num;
    private double Price;
    private int IsSku;
    @JacksonXmlProperty(localName = "Item")
    private List<NSGoodItemResult> itemResults;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getOuterID() {
        return OuterID;
    }

    public void setOuterID(String outerID) {
        OuterID = outerID;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getIsSku() {
        return IsSku;
    }

    public void setIsSku(int isSku) {
        IsSku = isSku;
    }

    public List<NSGoodItemResult> getItemResults() {
        return itemResults;
    }

    public void setItemResults(List<NSGoodItemResult> itemResults) {
        this.itemResults = itemResults;
    }
}
