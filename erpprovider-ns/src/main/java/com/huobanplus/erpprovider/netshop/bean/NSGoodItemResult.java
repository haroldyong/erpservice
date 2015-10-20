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
public class NSGoodItemResult {
    private String Unit;
    private String SkuOuterID;
    private String SkuID;
    private int Num;

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getSkuOuterID() {
        return SkuOuterID;
    }

    public void setSkuOuterID(String skuOuterID) {
        SkuOuterID = skuOuterID;
    }

    public String getSkuID() {
        return SkuID;
    }

    public void setSkuID(String skuID) {
        SkuID = skuID;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }
}
