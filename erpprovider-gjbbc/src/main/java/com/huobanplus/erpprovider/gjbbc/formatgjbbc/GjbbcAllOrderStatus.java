package com.huobanplus.erpprovider.gjbbc.formatgjbbc;

import lombok.Data;

/**
 * Created by hxh on 2017-08-15.
 */
@Data
public class GjbbcAllOrderStatus {
    /**
     * 订单支付单状态
     */
    private boolean payOrderSyncStatus;
    /**
     * 高捷平台订单状态
     */
    private boolean orderSyncStatus;

    public boolean isSyncSuccess() {
        return payOrderSyncStatus && orderSyncStatus;
    }
}
