package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 事件中携带的erp相关信息，用于erphandler选择合适的erp-provider处理
 * Created by allan on 2015/7/13.
 */
public class ERPInfo implements Serializable {


    private static final long serialVersionUID = 52479859658664542L;

    /**
     * erp类型
     */
    private String type;
    /**
     * erp名称
     */
    private String name;
    /**
     * erp验证信息，erp
     */
    private String validation;

    /**
     * 时间戳（当前时间的毫秒数，long）
     */
    private String timestamp;
    /**
     * erp系统级参数
     * <p>json格式</p>
     * <p><p/>
     */
    private String sysDataJson;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ERPInfo(String type, String name, String validation) {
        this.type = type;
        this.name = name;
        this.validation = validation;
    }

    public ERPInfo() {
    }
}
