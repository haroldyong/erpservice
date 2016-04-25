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
import com.huobanplus.erpservice.datacenter.repository.logs.ShipSyncDetailRepository;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncFailureOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allan on 4/21/16.
 */
@Service
public class ShipSyncFailureOrderServiceImpl implements ShipSyncFailureOrderService {
    @Autowired
    private ShipSyncDetailRepository shipSyncDetailRepository;

    @Override
    public ShipSyncFailureOrder save(ShipSyncFailureOrder shipSyncFailureOrder) {
        return shipSyncDetailRepository.save(shipSyncFailureOrder);
    }

    @Override
    public void batchSave(List<ShipSyncFailureOrder> shipSyncFailureOrders) {
        shipSyncDetailRepository.save(shipSyncFailureOrders);
    }

    @Override
    public Page<ShipSyncFailureOrder> findAll(int pageIndex, int pageSize, long shipSyncId, String orderId) {
        Specification<ShipSyncFailureOrder> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("orderShipSyncLog").get("id").as(Long.class), shipSyncId));
            if (!StringUtils.isEmpty(orderId)) {
                predicates.add(cb.like(root.get("orderId").as(String.class), orderId));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return shipSyncDetailRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }

    @Override
    public ShipSyncFailureOrder findById(long id) {
        return shipSyncDetailRepository.findOne(id);
    }
}
