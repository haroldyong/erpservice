package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.event.model.InventoryInfo;

/**
 *  库存信息处理事件
 */
public class InventoryBaseEvent extends ERPBaseEvent {

    /**
     * 确认出库单
     * @return 库存信息
     */
    public InventoryInfo edbOutStoreConfirm(){return new InventoryInfo();}

    /**
     * 确认入库单
     * @return 库存信息
     */
    public InventoryInfo edbInStoreConfirm(){return new InventoryInfo();}

    /**
     * 入库单回写信息
     * @return 库存信息
     */
    public InventoryInfo edbInStoreWriteback(){return  new InventoryInfo();}

    /**
     * 出库单回写
     * @return 库存信息
     */
    public InventoryInfo edbOutStoreWriteback(){return new InventoryInfo();}

    /**
     * 获取入库单信息
     * @return 库存信息
     */
    public InventoryInfo edbInStoreGet(){return  new InventoryInfo();}

    /**
     * 增加入库单信息
     * @return 库存信息
     */
    public InventoryInfo edbInStoreAdd(){return  new InventoryInfo();}

    /**
     *  增加出库单信息
     * @return 库存信息
     */
    public InventoryInfo edbOutStoreAdd(){return  new InventoryInfo();}

    /**
     * 获取出库单信息
     * @return 库存信息
     */
    public InventoryInfo edbOutStoreGet(){return  new InventoryInfo();}
}
