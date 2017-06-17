package com.huobanplus.erpprovider.pineapple.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * Created by hxh on 2017-06-16.
 */
@Data
@JacksonXmlRootElement(localName = "Orders")
public class BLPOrderListResult {

    @JacksonXmlProperty(localName = "OrderNos")
    private List<String> orderNos;

    @JacksonXmlProperty(localName = "Page")
    private int page;

    @JacksonXmlProperty(localName = "Size")
    private int size;

    @JacksonXmlProperty(localName = "numtotalorder")
    private int numTotalOrder;
}
