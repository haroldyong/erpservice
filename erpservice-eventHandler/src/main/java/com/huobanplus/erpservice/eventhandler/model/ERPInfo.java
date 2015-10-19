package com.huobanplus.erpservice.eventhandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 事件中携带的erp相关信息，用于erphandler选择合适的erp-provider处理
 * Created by allan on 2015/7/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
