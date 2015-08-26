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
}
