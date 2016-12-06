/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.repository.logs.OrderDetailSyncLogRepository;
import com.huobanplus.erpservice.datacenter.searchbean.OrderDetailSyncSearch;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
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
 * Created by allan on 4/22/16.
 */
@Service
public class OrderDetailSyncLogServiceImpl implements OrderDetailSyncLogService {
    @Autowired
    private OrderDetailSyncLogRepository orderDetailSyncLogRepository;

    @Override
    public OrderDetailSyncLog save(OrderDetailSyncLog orderDetailSyncLog) {
        return orderDetailSyncLogRepository.save(orderDetailSyncLog);
    }

    @Override
    public OrderDetailSyncLog findByOrderId(String orderId) {
        return orderDetailSyncLogRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetailSyncLog findById(long id) {
        return orderDetailSyncLogRepository.findOne(id);
    }

    @Override
    public Page<OrderDetailSyncLog> findAll(int pageIndex, int pageSize, int customerId, OrderDetailSyncSearch orderDetailSyncSearch) {
        Specification<OrderDetailSyncLog> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            if (!StringUtils.isEmpty(orderDetailSyncSearch.getOrderId())) {
                predicates.add(cb.like(root.get("orderId").as(String.class), "%" + orderDetailSyncSearch.getOrderId() + '%'));
            }
            if (!StringUtils.isEmpty(orderDetailSyncSearch.getBeginTime())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(orderDetailSyncSearch.getBeginTime(), StringUtil.DATE_PATTERN)));
            }
            if (!StringUtils.isEmpty(orderDetailSyncSearch.getEndTime())) {
                predicates.add(cb.lessThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(orderDetailSyncSearch.getEndTime(), StringUtil.DATE_PATTERN)));
            }
            if (orderDetailSyncSearch.getSyncStatus() != -1) {
                predicates.add(cb.equal(root.get("detailSyncStatus").as(OrderSyncStatus.DetailSyncStatus.class),
                        EnumHelper.getEnumType(OrderSyncStatus.DetailSyncStatus.class, orderDetailSyncSearch.getSyncStatus())));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return orderDetailSyncLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }

    @Override
    public List<OrderDetailSyncLog> findOrderBySyncStatusAndProviderType(int customerId,
                                                                         OrderSyncStatus.DetailSyncStatus syncStatus,
                                                                         ERPTypeEnum.ProviderType providerType,
                                                                         Date beginTime) {
        return orderDetailSyncLogRepository.findByCustomerIdAndDetailSyncStatusAndProviderType(customerId, syncStatus, providerType, beginTime);
    }
}
