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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * 订单列表返回数据
 * Created by liual on 2015-08-25.
 */
@Data
@JacksonXmlRootElement(localName = "Order")
public class NSOrderListResult {
    @JacksonXmlProperty(localName = "OrderNO")
    private List<String> orderNo;

    @JacksonXmlProperty(localName = "OrderCount")
    private String orderCount;

    @JacksonXmlProperty(localName = "Page")
    private String page;

    @JacksonXmlProperty(localName = "Result")
    private String result;

    @JacksonXmlProperty(localName = "Cause")
    private String cause;

}
