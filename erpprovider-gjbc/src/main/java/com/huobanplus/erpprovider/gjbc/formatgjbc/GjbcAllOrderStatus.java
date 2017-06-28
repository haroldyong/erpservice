package com.huobanplus.erpprovider.gjbc.formatgjbc;

import lombok.Data;

/**
 * Created by hxh on 2017-06-27.
 */
@Data
public class GjbcAllOrderStatus {
    private boolean orderSyncStatus;

    private boolean personalSyncStatus;

    private boolean payOrderSyncStatus;

    private boolean customOrderSyncStatus;

    private boolean customBackStatus;

    public boolean isSyncSuccess() {
        return orderSyncStatus && personalSyncStatus
                && payOrderSyncStatus && customOrderSyncStatus;
    }
}
