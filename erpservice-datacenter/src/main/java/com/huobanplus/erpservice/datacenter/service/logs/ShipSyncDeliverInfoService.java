/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by allan on 4/21/16.
 */
public interface ShipSyncDeliverInfoService {
    /**
     * 保存一个发货同步失败的订单
     *
     * @param shipSyncDeliverInfo
     * @return
     */
    ShipSyncDeliverInfo save(ShipSyncDeliverInfo shipSyncDeliverInfo);

    /**
     * 批量保存
     *
     * @param shipSyncDeliverInfoses
     */
    void batchSave(List<ShipSyncDeliverInfo> shipSyncDeliverInfoses);

    /**
     * 按条件搜索
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<ShipSyncDeliverInfo> findAll(int pageIndex, int pageSize, long shipSyncId, String orderId);

    ShipSyncDeliverInfo findById(long id);

    /**
     * 将发货信息组装成同步日志
     *
     * @param shipSyncDeliverInfoList
     * @param orderDeliveryInfoList
     */
    void shipSyncDeliverInfoList(List<ShipSyncDeliverInfo> shipSyncDeliverInfoList, List<OrderDeliveryInfo> orderDeliveryInfoList, OrderShipSyncLog orderShipSyncLog);
}
