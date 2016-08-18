/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-18.
 */
@Data
@JacksonXmlRootElement(localName = "orderInfo")
public class CustomOrderInfo {

    @JacksonXmlProperty(localName = "jkfSign")
    private CustomSign customSign;

    @JacksonXmlProperty(localName = "jkfOrderImportHead")
    private CustomOrderHead customOrderHead;

    @JacksonXmlProperty(localName = "jkfOrderDetailList")
    private List<CustomOrderDetail> customOrderDetails;

    @JacksonXmlProperty(localName = "jkfGoodsPurchaser")
    private CustomGoodsPurchaser customGoodsPurchaser;
}
