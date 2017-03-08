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

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncInfo;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.AuditedOrder;
import com.huobanplus.erpservice.datacenter.repository.logs.AuditedOrderSyncInfoRepository;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncInfoService;
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
 * Created by wuxiongliu on 2017-03-08.
 */
@Service
public class AuditedOrderSyncInfoServiceImpl implements AuditedOrderSyncInfoService {

    @Autowired
    private AuditedOrderSyncInfoRepository auditedOrderSyncInfoRepository;

    @Override
    public AuditedOrderSyncInfo save(AuditedOrderSyncInfo auditedOrderSyncInfo) {
        return auditedOrderSyncInfoRepository.save(auditedOrderSyncInfo);
    }

    @Override
    public void batchSave(List<AuditedOrderSyncInfo> auditedOrderSyncInfos) {
        auditedOrderSyncInfoRepository.save(auditedOrderSyncInfos);
    }

    @Override
    public Page<AuditedOrderSyncInfo> findAll(int pageIndex, int pageSize, long syncId, String orderId) {
        Specification<AuditedOrderSyncInfo> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("auditedOrderSyncLog").get("id").as(Long.class), syncId));
            if (!StringUtils.isEmpty(orderId)) {
                predicates.add(cb.like(root.get("orderId").as(String.class), orderId));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return auditedOrderSyncInfoRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }

    @Override
    public AuditedOrderSyncInfo findById(long id) {
        return auditedOrderSyncInfoRepository.findOne(id);
    }

    @Override
    public void auditedOrderInfoList(List<AuditedOrderSyncInfo> auditedOrderSyncInfoList, List<AuditedOrder> auditedOrderList, AuditedOrderSyncLog auditedOrderSyncLog, OrderSyncStatus.AuditedSyncStatus auditedSyncStatus) {
        auditedOrderList.forEach(auditedOrder -> {
            AuditedOrderSyncInfo auditedOrderSyncInfo = new AuditedOrderSyncInfo();
            auditedOrderSyncInfo.setOrderId(auditedOrder.getOrderId());
            auditedOrderSyncInfo.setAuditedOrderSyncLog(auditedOrderSyncLog);
            auditedOrderSyncInfo.setRemark(auditedOrder.getRemark());
            auditedOrderSyncInfo.setAuditedSyncStatus(auditedSyncStatus);
            auditedOrderSyncInfo.setAuditedOrderJson(JSON.toJSONString(auditedOrder));
            auditedOrderSyncInfoList.add(auditedOrderSyncInfo);
        });
    }
}
