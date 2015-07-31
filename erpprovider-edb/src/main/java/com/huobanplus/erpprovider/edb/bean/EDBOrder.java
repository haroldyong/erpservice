package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by allan on 2015/7/27.
 */
@JacksonXmlRootElement(localName = "order")
public class EDBOrder {
    @JacksonXmlProperty(localName = "orderInfo")
    private EDBCreateOrderInfo edbCreateOrderInfo;

    public EDBCreateOrderInfo getEdbCreateOrderInfo() {
        return edbCreateOrderInfo;
    }

    public void setEdbCreateOrderInfo(EDBCreateOrderInfo edbCreateOrderInfo) {
        this.edbCreateOrderInfo = edbCreateOrderInfo;
    }

    public EDBOrder(EDBCreateOrderInfo edbCreateOrderInfo) {
        this.edbCreateOrderInfo = edbCreateOrderInfo;
    }
}