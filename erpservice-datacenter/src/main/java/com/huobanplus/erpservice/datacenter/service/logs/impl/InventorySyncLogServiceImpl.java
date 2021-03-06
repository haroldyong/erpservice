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
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.repository.logs.InventorySyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncDetailService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 8/3/16.
 */
@Service
public class InventorySyncLogServiceImpl implements InventorySyncLogService {
    private static final Log log = LogFactory.getLog(InventorySyncLogServiceImpl.class);

    @Autowired
    private InventorySyncLogRepository inventorySyncLogRepository;
    @Autowired
    private InventorySyncDetailService inventorySyncDetailService;

    @Override
    public InventorySyncLog save(InventorySyncLog inventorySyncLog) {
        return inventorySyncLogRepository.save(inventorySyncLog);
    }

    @Override
    public InventorySyncLog findLast(int customerId, ERPTypeEnum.ProviderType providerType) {
        return inventorySyncLogRepository.findTopByCustomerIdAndProviderTypeOrderByIdDesc(customerId, providerType);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Page<InventorySyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId) {
        Specification<InventorySyncLog> specification = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));

            if (!StringUtils.isEmpty(beginTime)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(beginTime, StringUtil.DATE_PATTERN)));
            }
            if (!StringUtils.isEmpty(endTime)) {
                predicates.add(cb.lessThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(endTime, StringUtil.DATE_PATTERN)));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });

        return inventorySyncLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }

    @Override
    @Transactional
    public void saveLogAndDetail(
            ERPTypeEnum.UserType userType,
            ERPTypeEnum.ProviderType providerType,
            int customerId,
            int totalCount,
            List<ProInventoryInfo> proInventoryInfoList,
            Date syncTime
    ) {
        InventorySyncLog inventorySyncLog = new InventorySyncLog();
        inventorySyncLog.setProviderType(providerType);
        inventorySyncLog.setUserType(userType);
        inventorySyncLog.setCustomerId(customerId);
        inventorySyncLog.setTotalCount(totalCount);
        inventorySyncLog.setFailedCount(proInventoryInfoList.size());
        inventorySyncLog.setSyncTime(syncTime);
        if (totalCount == proInventoryInfoList.size()) {
            inventorySyncLog.setInventorySyncStatus(OrderSyncStatus.InventorySyncStatus.SYNC_FAILURE);
        }
        if (proInventoryInfoList.size() > 0 && proInventoryInfoList.size() < totalCount) {
            inventorySyncLog.setInventorySyncStatus(OrderSyncStatus.InventorySyncStatus.SYNC_PARTY_SUCCESS);
        }
        if (proInventoryInfoList.size() == 0) {
            inventorySyncLog.setInventorySyncStatus(OrderSyncStatus.InventorySyncStatus.SYNC_SUCCESS);
        }
        inventorySyncLog = this.save(inventorySyncLog);
        inventorySyncDetailService.batchSaveInventoryInfo(proInventoryInfoList, inventorySyncLog);
    }
}
