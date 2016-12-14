/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.datacenter.entity.logs.PurchaseOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrder;
import com.huobanplus.erpservice.datacenter.repository.logs.PurchaseOrderSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.logs.PurchaseOrderSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
@Service
public class PurchaseOrderSyncLogServiceImpl implements PurchaseOrderSyncLogService {

    @Autowired
    private PurchaseOrderSyncLogRepository purchaseOrderSyncLogRepository;

    @Override
    public void batchSave(List<PurchaseOrder> purchaseOrderList) {

        List<PurchaseOrderSyncLog> purchaseOrderSyncLogs = new ArrayList<>();

        for (PurchaseOrder purchaseOrder : purchaseOrderList) {
            if (purchaseOrderSyncLogRepository.findByReceiveNo(purchaseOrder.getReceiveNo()) == null) {
                PurchaseOrderSyncLog purchaseOrderSyncLog = new PurchaseOrderSyncLog();
                purchaseOrderSyncLog.setCustomerId(11);// TODO: 2016-12-12

                purchaseOrderSyncLog.setReceiveNo(purchaseOrder.getReceiveNo());
            }
        }
    }

    @Override
    public Page<PurchaseOrderSyncLog> findAll(int pageIndex, int pageSize, int customerId, String receiveNo) {
        Specification<PurchaseOrderSyncLog> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            if (!StringUtils.isEmpty(receiveNo)) {
                predicates.add(cb.like(root.get("receiveNo").as(String.class), "%" + receiveNo + '%'));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return purchaseOrderSyncLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }
}
