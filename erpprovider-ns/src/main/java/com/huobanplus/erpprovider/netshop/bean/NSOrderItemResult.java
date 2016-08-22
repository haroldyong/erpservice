/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * Created by liual on 2015-08-26.
 */
@Data
public class NSOrderItemResult {

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "GoodsID")
    private String goodsID;

    @JacksonXmlCData(value = true)
    @JacksonXmlProperty(localName = "GoodsName")
    private String goodsName;

    @JacksonXmlProperty(localName = "Price")
    private double price;

    @JacksonXmlProperty(localName = "GoodsSpec")
    private String goodsSpec;

    @JacksonXmlProperty(localName = "Count")
    private int count;

    @JacksonXmlProperty(localName = "GoodsStatus")
    private String goodsStatus;

    @JacksonXmlProperty(localName = "Tax")
    private double tax;

}
