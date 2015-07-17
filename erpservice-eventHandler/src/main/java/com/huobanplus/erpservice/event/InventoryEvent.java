package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.model.InventoryInfo;

/**
 *  库存信息处理事件
 */
public class InventoryEvent extends ERPEvent {

    /**
     * 确认出库单
     * @param inventoryInfo
     * @return
     */
    public InventoryInfo edbOutStoreConfirm(InventoryInfo inventoryInfo){return null;}

    /**
     * 确认入库单
     * @return
     */
    public InventoryInfo edbInStoreConfirm(){return null;}

    /**
     * 入库单回写信息
     * @return
     */
    public InventoryInfo edbInStoreWriteback(){return  null;}

    /**
     * 出库单回写
     * @return
     */
    public InventoryInfo edbOutStoreWriteback(){return null;}

    /**
     * 获取入库单信息
     * @return
     */
    public InventoryInfo edbInStoreGet(){return  null;}

    /**
     * 增加入库单信息
     * @return
     */
    public InventoryInfo edbInStoreAdd(){return  null;}

    /**
     *  增加出库单信息
     * @return
     */
    public InventoryInfo edbOutStoreAdd(){return  null;}

    /**
     * 获取出库单信息
     * @return
     */
    public InventoryInfo edbOutStoreGet(){return  null;}
}
