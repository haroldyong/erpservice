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

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.ReturnRefundSyncLog;
import com.huobanplus.erpservice.datacenter.repository.logs.ReturnRefundSyncLogRepository;
import com.huobanplus.erpservice.datacenter.searchbean.ReturnRefundSearch;
import com.huobanplus.erpservice.datacenter.service.logs.ReturnRefundSyncLogService;
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
 * Created by wuxiongliu on 2016-11-11.
 */
@Service
public class ReturnRefundSyncLogServiceImpl implements ReturnRefundSyncLogService {

    @Autowired
    private ReturnRefundSyncLogRepository returnRefundSyncLogRepository;

    @Override
    public ReturnRefundSyncLog save(ReturnRefundSyncLog returnRefundSyncLog) {
        return returnRefundSyncLogRepository.save(returnRefundSyncLog);
    }

    @Override
    public ReturnRefundSyncLog findById(Long id) {
        return returnRefundSyncLogRepository.findOne(id);
    }

    @Override
    public ReturnRefundSyncLog findByOrderId(String orderId) {
        return returnRefundSyncLogRepository.findByOrderId(orderId);
    }

    @Override
    public Page<ReturnRefundSyncLog> findAll(int pageIndex, int pageSize, int customerId, ReturnRefundSearch returnRefundSearch) {
        Specification<ReturnRefundSyncLog> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            if (!StringUtils.isEmpty(returnRefundSearch.getOrderId())) {
                predicates.add(cb.like(root.get("orderId").as(String.class), "%" + returnRefundSearch.getOrderId() + '%'));
            }
            if (!StringUtils.isEmpty(returnRefundSearch.getBeginTime())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(returnRefundSearch.getBeginTime(), StringUtil.DATE_PATTERN)));
            }
            if (!StringUtils.isEmpty(returnRefundSearch.getEndTime())) {
                predicates.add(cb.lessThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(returnRefundSearch.getEndTime(), StringUtil.DATE_PATTERN)));
            }
            if (returnRefundSearch.getSyncStatus() != -1) {
                predicates.add(cb.equal(root.get("detailSyncStatus").as(OrderSyncStatus.DetailSyncStatus.class),
                        EnumHelper.getEnumType(OrderSyncStatus.DetailSyncStatus.class, returnRefundSearch.getSyncStatus())));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return returnRefundSyncLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }
}
