/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderShipSyncLog;
import com.huobanplus.erpservice.datacenter.entity.logs.ShipSyncDeliverInfo;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.repository.logs.ShipSyncDeliverInfoRepository;
import com.huobanplus.erpservice.datacenter.service.logs.ShipSyncDeliverInfoService;
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
public class ShipSyncDeliverInfoServiceImpl implements ShipSyncDeliverInfoService {
    @Autowired
    private ShipSyncDeliverInfoRepository shipSyncDeliverInfoRepository;

    @Override
    public ShipSyncDeliverInfo save(ShipSyncDeliverInfo shipSyncDeliverInfo) {
        return shipSyncDeliverInfoRepository.save(shipSyncDeliverInfo);
    }

    @Override
    public void batchSave(List<ShipSyncDeliverInfo> shipSyncDeliverInfoses) {
        shipSyncDeliverInfoRepository.save(shipSyncDeliverInfoses);
    }

    @Override
    public Page<ShipSyncDeliverInfo> findAll(int pageIndex, int pageSize, long shipSyncId, String orderId) {
        Specification<ShipSyncDeliverInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("orderShipSyncLog").get("id").as(Long.class), shipSyncId));
            if (!StringUtils.isEmpty(orderId)) {
                predicates.add(cb.like(root.get("orderDeliveryInfo").get("orderId").as(String.class), orderId));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return shipSyncDeliverInfoRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }

    @Override
    public ShipSyncDeliverInfo findById(long id) {
        return shipSyncDeliverInfoRepository.findOne(id);
    }

    @Override
    public void shipSyncDeliverInfoList(List<ShipSyncDeliverInfo> shipSyncDeliverInfoList, List<OrderDeliveryInfo> orderDeliveryInfoList, OrderShipSyncLog orderShipSyncLog, OrderSyncStatus.ShipSyncStatus shipSyncStatus) {
        orderDeliveryInfoList.forEach(deliveryInfo -> {
            ShipSyncDeliverInfo shipSyncDeliverInfo = new ShipSyncDeliverInfo();
            shipSyncDeliverInfo.setOrderDeliveryInfo(deliveryInfo);
            shipSyncDeliverInfo.setOrderShipSyncLog(orderShipSyncLog);
            shipSyncDeliverInfo.setShipSyncStatus(shipSyncStatus);
            shipSyncDeliverInfoList.add(shipSyncDeliverInfo);
        });
    }
}
