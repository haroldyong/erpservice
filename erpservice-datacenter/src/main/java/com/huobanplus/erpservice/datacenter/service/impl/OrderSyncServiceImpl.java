/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.OrderSync;
import com.huobanplus.erpservice.datacenter.repository.OrderSyncRepository;
import com.huobanplus.erpservice.datacenter.searchbean.OrderSyncSearch;
import com.huobanplus.erpservice.datacenter.service.OrderSyncService;
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
 * Created by allan on 4/15/16.
 */
@Service
public class OrderSyncServiceImpl implements OrderSyncService {
    @Autowired
    private OrderSyncRepository orderSyncRepository;

    @Override
    public OrderSync save(OrderSync orderSync) {
        return orderSyncRepository.save(orderSync);
    }

    public OrderSync getOrderSync(String orderId, int customerId) {
        Date now = new Date();
        OrderSync orderSync = this.findByOrderId(orderId);
        if (orderSync == null) {
            orderSync = new OrderSync();
            orderSync.setOrderId(orderId);
            orderSync.setCustomerId(customerId);
            orderSync.setCreateTime(now);
            orderSync.setLastSyncDate(now);
        } else {
            orderSync.setLastSyncDate(now);
        }
        return orderSync;
    }

    @Override
    public Page<OrderSync> findAll(int pageIndex, int pageSize, int customerId, OrderSyncSearch orderSyncSearch) {
        Specification<OrderSync> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            predicates.add(cb.equal(root.get("userType").as(ERPTypeEnum.UserType.class),
                    EnumHelper.getEnumType(ERPTypeEnum.UserType.class, orderSyncSearch.getErpUserType())));
            if (!StringUtils.isEmpty(orderSyncSearch.getOrderId())) {
                predicates.add(cb.like(root.get("orderId").as(String.class), "%" + orderSyncSearch.getOrderId() + "%"));
            }
            if (!StringUtils.isEmpty(orderSyncSearch.getBeginTime())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
                        StringUtil.DateFormat(orderSyncSearch.getBeginTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtils.isEmpty(orderSyncSearch.getEndTime())) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
                        StringUtil.DateFormat(orderSyncSearch.getEndTime(), StringUtil.TIME_PATTERN)));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return orderSyncRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public OrderSync findByOrderId(String orderId) {
        return orderSyncRepository.findOne(orderId);
    }
}
