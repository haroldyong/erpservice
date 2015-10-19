package com.huobanplus.erpservice.eventhandler.model;

import lombok.Data;

import java.io.Serializable;

/**
 * erp使用者信息
 * Created by liual on 2015-10-15.
 */
@Data
public class ERPUserInfo implements Serializable{
    private static final long serialVersionUID = 52479852258664542L;

    /**
     * erp使用者名称
     * <ul>
     *     <li>huobanmall</li>
     *     <li>supplier</li>
     * </ul>
     */
    private String ERPUserName;

    /**
     * 时间戳
     */
    private long timestamp;
}
