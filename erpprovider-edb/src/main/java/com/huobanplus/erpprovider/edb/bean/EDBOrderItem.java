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

/**
 * edb订单货品
 * Created by allan on 2015/8/7.
 */
public class EDBOrderItem {
    /**
     * 条形码，必填
     */
    private String barCode;
    /**
     * 网店品名，必填
     */
    @JacksonXmlProperty(localName = "product_title")
    private String productTitle;
    /**
     * 网店规格，必填
     */
    private String standard;
    @JacksonXmlProperty(localName = "out_price")
    /**
     * 销售价/外部单价
     */
    private double outPrice;
    @JacksonXmlProperty(localName = "favorite_money")
    /**
     * 优惠价
     */
    private double favoriteMoney;
    /**
     * 订货数量
     */
    @JacksonXmlProperty(localName = "orderGoods_Num")
    private int orderGoodsNum;
    /**
     * 赠品数量
     */
    @JacksonXmlProperty(localName = "gift_Num")
    private int giftNum;
    /**
     * 成交单价
     */
    @JacksonXmlProperty(localName = "cost_Price")
    private double costPrice;
    /**
     * 订单编号
     */
    private String tid;
    /**
     * 缺货状态（0：否 1：是）默认：0
     */
    @JacksonXmlProperty(localName = "product_stockout")
    private String productStockout;
    /**
     * 是否预订（0：否 1：是）默认：0
     */
    @JacksonXmlProperty(localName = "is_Book")
    private String isBook;
    /**
     * 是否预售
     */
    @JacksonXmlProperty(localName = "is_presell")
    private String isPreSell;
    /**
     * 是否赠品（0：否 1：是）默认：0
     */
    @JacksonXmlProperty(localName = "is_Gift")
    private String isGift;
    /**
     * 加权平均单价
     */
    @JacksonXmlProperty(localName = "avg_price")
    private double avgPrice;
    /**
     * 运费
     */
    @JacksonXmlProperty(localName = "product_freight")
    private String productFreight;
    /**
     * 店铺编号
     */
    @JacksonXmlProperty(localName = "shop_id")
    private String shopId;
    /**
     * 外部平台单号，必填
     */
    @JacksonXmlProperty(localName = "out_tid")
    private String outTid;
    /**
     * 外部平台产品Id
     */
    @JacksonXmlProperty(localName = "out_productId")
    private String outProductId;
    /**
     * 外部平台条形码
     */
    @JacksonXmlProperty(localName = "out_barCode")
    private String outBarCode;
    /**
     * 产品简介
     */
    @JacksonXmlProperty(localName = "product_intro")
    private String productIntro;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public double getFavoriteMoney() {
        return favoriteMoney;
    }

    public void setFavoriteMoney(double favoriteMoney) {
        this.favoriteMoney = favoriteMoney;
    }

    public int getOrderGoodsNum() {
        return orderGoodsNum;
    }

    public void setOrderGoodsNum(int orderGoodsNum) {
        this.orderGoodsNum = orderGoodsNum;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getProductStockout() {
        return productStockout;
    }

    public void setProductStockout(String productStockout) {
        this.productStockout = productStockout;
    }

    public String getIsBook() {
        return isBook;
    }

    public void setIsBook(String isBook) {
        this.isBook = isBook;
    }

    public String getIsPreSell() {
        return isPreSell;
    }

    public void setIsPreSell(String isPreSell) {
        this.isPreSell = isPreSell;
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getProductFreight() {
        return productFreight;
    }

    public void setProductFreight(String productFreight) {
        this.productFreight = productFreight;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getOutProductId() {
        return outProductId;
    }

    public void setOutProductId(String outProductId) {
        this.outProductId = outProductId;
    }

    public String getOutBarCode() {
        return outBarCode;
    }

    public void setOutBarCode(String outBarCode) {
        this.outBarCode = outBarCode;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }
}
