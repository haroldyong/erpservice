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

/**
 * edb出库单会写实体
 * Created by allan on 2015/8/7.
 */
@JacksonXmlRootElement(localName = "orderInfo")
public class EDBOutStoreWriteBack {
    @JacksonXmlProperty(localName = "bar_code")
    private String barCode;
    @JacksonXmlProperty(localName = "outstorage_no")
    private String outStorageNo;
    @JacksonXmlProperty(localName = "outstorage_num")
    private int outStorageNum;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getOutStorageNo() {
        return outStorageNo;
    }

    public void setOutStorageNo(String outStorageNo) {
        this.outStorageNo = outStorageNo;
    }

    public int getOutStorageNum() {
        return outStorageNum;
    }

    public void setOutStorageNum(int outStorageNum) {
        this.outStorageNum = outStorageNum;
    }
}
