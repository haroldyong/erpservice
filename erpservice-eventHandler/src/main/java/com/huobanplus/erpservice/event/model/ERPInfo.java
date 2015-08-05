package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 事件中携带的erp相关信息，用于erphandler选择合适的erp-provider处理
 * Created by allan on 2015/7/13.
 */
public class ERPInfo implements Serializable {
    private static final long serialVersionUID = 52479859658664542L;

    //todo erp相关信息，如类型，名称，验证信息等
    /**
     * erp类型
     */
    private String type;//erp类型
    /**
     * erp名称
     */
    private String name;//ERP名称
    /**
     * erp验证信息
     */
    private String validation;//验证信息
    /**
     * erp系统级参数
     * <p>json格式</p>
     */
    private String sysDataJson;
    /**
     * 商户id
     */
    private int customerId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getSysDataJson() {
        return sysDataJson;
    }

    public void setSysDataJson(String sysDataJson) {
        this.sysDataJson = sysDataJson;
    }

    public ERPInfo(String type, String name, String validation) {
        this.type = type;
        this.name = name;
        this.validation = validation;
    }

    public ERPInfo() {
    }
}
