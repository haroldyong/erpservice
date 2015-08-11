package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Created by allan on 2015/8/10.
 */
public class EDBProductForUpdate {
    /**
     * 订单编号（edb中的订单编号）
     */
    private String tid;
    /**
     * 产品条形码
     */
    private String barCode;
    /**
     * 验货数量
     */
    @JacksonXmlProperty(localName = "inspection_num")
    private int inspectionNum;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getInspectionNum() {
        return inspectionNum;
    }

    public void setInspectionNum(int inspectionNum) {
        this.inspectionNum = inspectionNum;
    }
}
