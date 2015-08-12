package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 订单出货
 * Created by allan on 2015/8/11.
 */
@JacksonXmlRootElement(localName = "orderInfo")
public class EDBOrderDeliver {
    /**
     * 订单编号
     */
    @JacksonXmlProperty(localName = "OrderCode")
    private String orderId;
    /**
     * 验货时间
     */
    @JacksonXmlProperty(localName = "delivery_time")
    private String deliveryTime;
    /**
     * 快递单号
     */
    @JacksonXmlProperty(localName = "express_no")
    private String expressNo;
    /**
     * 快递公司名：需要在EDB中存在
     */
    @JacksonXmlProperty(localName = "express")
    private String express;
    /**
     * 订单净重
     */
    private String weight;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
