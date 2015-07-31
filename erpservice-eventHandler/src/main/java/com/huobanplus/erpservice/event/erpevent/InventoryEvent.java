package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.InventoryInfo;

/**
 * <b>类描述：<b/>库存信息处理事件
 */
public class InventoryEvent extends ERPBaseEvent {

    /**
     * 库存信息实体
     */
    private InventoryInfo inventoryInfo;

    public InventoryInfo getInventoryInfo() {
        return inventoryInfo;
    }

    public void setInventoryInfo(InventoryInfo inventoryInfo) {
        this.inventoryInfo = inventoryInfo;
    }
}
