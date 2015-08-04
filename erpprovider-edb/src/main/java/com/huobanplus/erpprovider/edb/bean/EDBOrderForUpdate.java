package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by allan on 2015/8/3.
 */
public class EDBOrderForUpdate {
    private String tid;
    @JacksonXmlProperty(localName = "out_tid")
    private String outTid;
    private String express;
    @JacksonXmlProperty(localName = "express_code")
    private String expressCode;
    @JacksonXmlProperty(localName = "express_no")
    private String expressNo;
    private String printer;
    @JacksonXmlProperty(localName = "Cargo_operator")
    private String cargoOperator;
    @JacksonXmlProperty(localName = "Cargo_time")
    private String cargoTime;
    @JacksonXmlProperty(localName = "print_time")
    private String printTime;
    private String inspecter;
    @JacksonXmlProperty(localName = "inspect_time")
    private String inspectTime;
    @JacksonXmlProperty(localName = "is_inspect_delivery")
    private String isInspectDelivery;
    @JacksonXmlProperty(localName = "delivery_operator")
    private String deliveryOperator;
    @JacksonXmlProperty(localName = "delivery_time")
    private String deliveryTime;
    @JacksonXmlProperty(localName = "GrossWeight")
    private String grossWeight;
    @JacksonXmlProperty(localName = "internal_note")
    private String internalNote;
    @JacksonXmlProperty(localName = "origin_code")
    private String originCode;
    @JacksonXmlProperty(localName = "dest_code")
    private String destCode;
    private String barCode;
    @JacksonXmlProperty(localName = "inspection_num")
    private String inspectionNum;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOutTid() {
        return outTid;
    }

    public void setOutTid(String outTid) {
        this.outTid = outTid;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String getCargoOperator() {
        return cargoOperator;
    }

    public void setCargoOperator(String cargoOperator) {
        this.cargoOperator = cargoOperator;
    }

    public String getCargoTime() {
        return cargoTime;
    }

    public void setCargoTime(String cargoTime) {
        this.cargoTime = cargoTime;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getInspecter() {
        return inspecter;
    }

    public void setInspecter(String inspecter) {
        this.inspecter = inspecter;
    }

    public String getInspectTime() {
        return inspectTime;
    }

    public void setInspectTime(String inspectTime) {
        this.inspectTime = inspectTime;
    }

    public String getIsInspectDelivery() {
        return isInspectDelivery;
    }

    public void setIsInspectDelivery(String isInspectDelivery) {
        this.isInspectDelivery = isInspectDelivery;
    }

    public String getDeliveryOperator() {
        return deliveryOperator;
    }

    public void setDeliveryOperator(String deliveryOperator) {
        this.deliveryOperator = deliveryOperator;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getInternalNote() {
        return internalNote;
    }

    public void setInternalNote(String internalNote) {
        this.internalNote = internalNote;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(String inspectionNum) {
        this.inspectionNum = inspectionNum;
    }
}
