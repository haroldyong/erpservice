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

import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.repository.logs.AuditedOrderSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2017-03-07.
 */

@Service
public class AuditedOrderSyncLogServiceImpl implements AuditedOrderSyncLogService {

    @Autowired
    private AuditedOrderSyncLogRepository auditedOrderSyncLogREpository;

    @Override
    public AuditedOrderSyncLog save(AuditedOrderSyncLog auditedOrderSyncLog) {
        return auditedOrderSyncLogREpository.save(auditedOrderSyncLog);
    }

    @Override
    public AuditedOrderSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType) {
        return auditedOrderSyncLogREpository.findTopByCustomerIdAndProviderTypeOrderByIdDesc(customerId, providerType);
    }

    @Override
    public Page<AuditedOrderSyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId) {
        Specification<AuditedOrderSyncLog> specification = (root, query, cb) -> {
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
        };

        return auditedOrderSyncLogREpository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }

    @Override
    public AuditedOrderSyncLog findOne(long syncId) {
        return auditedOrderSyncLogREpository.findOne(syncId);
    }
}
