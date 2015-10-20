/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.bean;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * edb订单中的货品更新实体
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
