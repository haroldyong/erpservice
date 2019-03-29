package com.huobanplus.erpprovider.lz.formatlz;

import lombok.Data;

@Data
public class LzAllOrderStatus {
    /**
     * 订单支付单状态
     */
    private boolean payOrderSyncStatus;
    /**
     * 推送订单到海关状态
     */
    private boolean customOrderSyncStatus;
    /**
     * 高捷平台订单状态
     */
    private boolean orderSyncStatus;
    /**
     * 海关回执状态
     */
    private boolean customBackStatus;
    public boolean isSyncSuccess(){
        return payOrderSyncStatus && orderSyncStatus;
    }
}
