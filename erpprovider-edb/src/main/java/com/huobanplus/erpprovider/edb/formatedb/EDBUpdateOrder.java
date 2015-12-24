/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.formatedb;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderForUpdate;

/**
 * Created by allan on 2015/8/3.
 */
@JacksonXmlRootElement(localName = "order")
public class EDBUpdateOrder {
    @JacksonXmlProperty(localName = "orderInfo")
    private EDBOrderForUpdate orderForUpdate;

    public EDBOrderForUpdate getOrderForUpdate() {
        return orderForUpdate;
    }

    public void setOrderForUpdate(EDBOrderForUpdate orderForUpdate) {
        this.orderForUpdate = orderForUpdate;
    }

    public EDBUpdateOrder(EDBOrderForUpdate orderForUpdate) {
        this.orderForUpdate = orderForUpdate;
    }
}
