/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncFailureOrder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by allan on 4/21/16.
 */
public interface ShipSyncFailureOrderService {
    /**
     * 保存一个发货同步失败的订单
     *
     * @param shipSyncFailureOrder
     * @return
     */
    ShipSyncFailureOrder save(ShipSyncFailureOrder shipSyncFailureOrder);

    /**
     * 批量保存
     *
     * @param shipSyncFailureOrders
     */
    void batchSave(List<ShipSyncFailureOrder> shipSyncFailureOrders);

    /**
     * 按条件搜索
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<ShipSyncFailureOrder> findAll(int pageIndex, int pageSize);
}
