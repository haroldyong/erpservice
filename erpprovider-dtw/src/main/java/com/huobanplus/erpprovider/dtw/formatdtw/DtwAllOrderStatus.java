/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016-07-27.
 */
@Data
public class DtwAllOrderStatus {

    private boolean orderSyncStatus;

    private boolean personalSyncStatus;

    private boolean payOrderSyncStatus;

    private boolean customOrderSyncStatus;

    private boolean customBackStatus;

    public boolean isSyncSuccess() {
        return orderSyncStatus && personalSyncStatus
                && payOrderSyncStatus && customOrderSyncStatus;
    }

    public boolean isBackSuccess() {
        return customBackStatus;
    }

}
