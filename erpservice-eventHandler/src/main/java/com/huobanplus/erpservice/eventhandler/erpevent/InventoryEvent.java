package com.huobanplus.erpservice.eventhandler.erpevent;

import lombok.Data;

/**
 * <b>类描述：<b/>库存信息处理事件
 */
@Data
public class InventoryEvent extends ERPBaseEvent {
    private int goodId;
    private int productId;
    private String bn;
    private int stock;
}
