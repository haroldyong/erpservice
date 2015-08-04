package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

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
