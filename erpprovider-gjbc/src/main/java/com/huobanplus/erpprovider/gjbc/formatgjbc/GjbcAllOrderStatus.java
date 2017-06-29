package com.huobanplus.erpprovider.gjbc.formatgjbc;

import lombok.Data;

/**
 * Created by montage on 2017/6/27.
 */

@Data
public class GjbcAllOrderStatus {
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
        return payOrderSyncStatus&&customOrderSyncStatus&&orderSyncStatus;
    }
}
