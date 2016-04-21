/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncFailureOrder;
import com.huobanplus.erpservice.datacenter.repository.logs.ShipSyncFailureOrderRepository;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncFailureOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 4/21/16.
 */
@Service
public class ShipSyncFailureOrderServiceImpl implements ShipSyncFailureOrderService {
    @Autowired
    private ShipSyncFailureOrderRepository shipSyncFailureOrderRepository;

    @Override
    public ShipSyncFailureOrder save(ShipSyncFailureOrder shipSyncFailureOrder) {
        return shipSyncFailureOrderRepository.save(shipSyncFailureOrder);
    }

    @Override
    public Page<ShipSyncFailureOrder> findAll(int pageIndex, int pageSize) {
        // TODO: 4/21/16
        return null;
    }
}
