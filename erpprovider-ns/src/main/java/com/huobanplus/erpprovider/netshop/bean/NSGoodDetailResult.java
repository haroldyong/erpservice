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
