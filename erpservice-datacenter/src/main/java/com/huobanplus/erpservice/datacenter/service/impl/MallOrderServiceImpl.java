/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.datacenter.repository.MallOrderRepository;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
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
 * Created by allan on 2015/7/14.
 */
@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Autowired
    private MallOrderRepository orderRepository;

    @Override
    @Transactional
    public MallOrderBean save(MallOrderBean orderBean) {
        return orderRepository.save(orderBean);
    }

    @Override
    public MallOrderBean findByOrderId(String orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    public Page<MallOrderBean> findAll(Integer orderStatus, Integer payStatus, String orderId, String sysData, int pageIndex, int pageSize) {
        Specification<MallOrderBean> specification = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (orderStatus != null) {
                list.add(cb.equal(root.get("status").as(Integer.class), orderStatus));
            }
            if (payStatus != null) {
                list.add(cb.equal(root.get("payStatus").as(Integer.class), payStatus));
            }
            if (!StringUtils.isEmpty(orderId)) {
                list.add(cb.like(root.get("orderId").as(String.class), "%" + orderId + "%"));
            }
            list.add(cb.equal(root.get("sysDataJson").as(String.class), sysData));
            return cb.and(list.toArray(new Predicate[list.size()]));
        };
        return orderRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }

    @Override
    public Page<MallOrderBean> findAll(OrderSearchInfo orderSearchInfo, int pageIndex, int pageSize) {
        Specification<MallOrderBean> specification = ((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (orderSearchInfo.getOrderStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatus").as(OrderEnum.OrderStatus.class), orderSearchInfo.getOrderStatus()));
            }
            if (orderSearchInfo.getShipStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("shipStatus").as(OrderEnum.ShipStatus.class), orderSearchInfo.getShipStatus()));
            }
            if (orderSearchInfo.getPayStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("payStatus").as(OrderEnum.PayStatus.class), orderSearchInfo.getPayStatus()));
            }
            if (!StringUtils.isEmpty(orderSearchInfo.getBeginTime())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getBeginTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtils.isEmpty(orderSearchInfo.getEndTime())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getEndTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtils.isEmpty(orderSearchInfo.getBeginPayTime())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("payTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getBeginPayTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtil.isEmpty(orderSearchInfo.getEndPayTime())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("payTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getEndPayTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtil.isEmpty(orderSearchInfo.getBeginUpdateTime())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdateTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getBeginUpdateTime(), StringUtil.TIME_PATTERN)));
            }
            if (!StringUtil.isEmpty(orderSearchInfo.getEndUpdateTime())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastUpdateTime").as(Date.class), StringUtil.DateFormat(orderSearchInfo.getEndUpdateTime(), StringUtil.TIME_PATTERN)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
        Sort.Direction direction = Sort.Direction.ASC;
        if("desc".equals(orderSearchInfo.getOrderType())){
            direction = Sort.Direction.DESC;
        }
        return orderRepository.findAll(specification,new PageRequest(pageIndex-1,pageSize,new Sort(direction,orderSearchInfo.getOrderBy())));
    }

    public List<MallOrderBean> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<MallOrderBean> findByRotaryStatus(int rotaryStatus) {
        return null;
    }
}
