/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by liual on 2015-08-26.
 */
@Data
public class NSGoodDetailResult {

    @JacksonXmlProperty(localName = "ItemID")
    private String itemID;

    @JacksonXmlProperty(localName = "ItemName")
    private String itemName;

    @JacksonXmlProperty(localName = "OuterID")
    private String outerID;

    @JacksonXmlProperty(localName = "Num")
    private int num;

    @JacksonXmlProperty(localName = "Price")
    private double price;

    @JacksonXmlProperty(localName = "IsSku")
    private int isSku;

    @JacksonXmlProperty(localName = "Remark")
    private String remark;

    @JacksonXmlProperty(localName = "Item")
    private List<NSGoodItemResult> itemResults;

}
