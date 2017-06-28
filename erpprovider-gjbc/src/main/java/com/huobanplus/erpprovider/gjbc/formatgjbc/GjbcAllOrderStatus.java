package com.huobanplus.erpprovider.gjbc.formatgjbc;

import lombok.Data;

/**
 * Created by montage on 2017/6/27.
 */

@Data
public class GjbcAllOrderStatus {
    private boolean payOrderSyncStatus;
    private boolean customOrderSyncStatus;
    public boolean isSyncSuccess(){
        return payOrderSyncStatus&&customOrderSyncStatus;
    }
}
