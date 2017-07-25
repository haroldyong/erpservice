/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbc.formatgjbc;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
/**
 * Created by montage on 2017/6/27.
 */
@Data
@JacksonXmlRootElement(localName = "mo")
public class CustomOrder {

    @JacksonXmlProperty(localName = "head")
    private CustomHead head;

    @JacksonXmlProperty(localName = "body")
    private CustomBody body;


}
