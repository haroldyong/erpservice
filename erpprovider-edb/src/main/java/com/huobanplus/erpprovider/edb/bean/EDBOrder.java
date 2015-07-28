package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by allan on 2015/7/27.
 */
@JacksonXmlRootElement(localName = "order")
public class EDBOrder {
    @JacksonXmlProperty(localName = "orderInfo")
    private EDBOrderInfo edbOrderInfo;

    public EDBOrderInfo getEdbOrderInfo() {
        return edbOrderInfo;
    }

    public void setEdbOrderInfo(EDBOrderInfo edbOrderInfo) {
        this.edbOrderInfo = edbOrderInfo;
    }

    public EDBOrder(EDBOrderInfo edbOrderInfo) {
        this.edbOrderInfo = edbOrderInfo;
    }
}
