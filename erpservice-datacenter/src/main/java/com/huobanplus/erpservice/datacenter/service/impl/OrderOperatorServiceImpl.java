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
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.repository.OrderOperatorLogRepository;
import com.huobanplus.erpservice.datacenter.searchbean.OrderPushSearch;
import com.huobanplus.erpservice.datacenter.service.OrderOperatorService;
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
 * Created by allan on 4/13/16.
 */
@Service
public class OrderOperatorServiceImpl implements OrderOperatorService {
    @Autowired
    private OrderOperatorLogRepository orderOperatorLogRepository;

    @Override
    public OrderOperatorLog save(OrderOperatorLog orderOperatorLog) {
        return orderOperatorLogRepository.save(orderOperatorLog);
    }

    @Override
    public Page<OrderOperatorLog> findAll(int pageIndex, int pageSize, OrderPushSearch orderPushSearch, int customerId) {
        Specification<OrderOperatorLog> specification = ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            predicates.add(cb.equal(root.get("userType").as(ERPTypeEnum.UserType.class),
                    EnumHelper.getEnumType(ERPTypeEnum.UserType.class, orderPushSearch.getErpUserType())));
            if (!StringUtils.isEmpty(orderPushSearch.getBeginTime())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
                        StringUtil.DateFormat(orderPushSearch.getBeginTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtils.isEmpty(orderPushSearch.getEndTime())) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
                        StringUtil.DateFormat(orderPushSearch.getEndTime(), StringUtil.TIME_PATTERN)));
            }
            if (orderPushSearch.getResultStatus() != -1) {
                predicates.add(cb.equal(root.get("resultStatus").as(Integer.class), orderPushSearch.getResultStatus()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        return orderOperatorLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<OrderOperatorLog> findByOrderId(String orderId) {
        return orderOperatorLogRepository.findByOrderId(orderId);
    }


    @Override
    public OrderOperatorLog findById(long id) {
        return orderOperatorLogRepository.findOne(id);
    }
}
