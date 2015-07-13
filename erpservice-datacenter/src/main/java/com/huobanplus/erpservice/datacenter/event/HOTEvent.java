package com.huobanplus.erpservice.datacenter.event;

import com.huobanplus.erpservice.datacenter.model.ERPInfo;

/**
 * 所有ERP事件的父类
 * Created by Administrator on 2015/7/10.
 */
public abstract class HOTEvent {

    private ERPInfo erpInfo;

    public ERPInfo getErpInfo() {
        return erpInfo;
    }

    public void setErpInfo(ERPInfo erpInfo) {
        this.erpInfo = erpInfo;
    }
}
