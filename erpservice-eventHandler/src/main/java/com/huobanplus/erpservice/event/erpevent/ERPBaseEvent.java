package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.ERPInfo;

/**
 * ERP事件父类，携带epr相关信息
 * Created by allan on 2015/7/13.
 */
public class ERPBaseEvent {
    private ERPInfo erpInfo;

    public ERPInfo getErpInfo() {
        return erpInfo;
    }

    public void setErpInfo(ERPInfo erpInfo) {
        this.erpInfo = erpInfo;
    }
}
