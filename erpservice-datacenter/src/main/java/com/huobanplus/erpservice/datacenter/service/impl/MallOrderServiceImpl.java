/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.repository.MallOrderRepository;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
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
 * Created by allan on 2015/7/14.
 */
@Service
public class MallOrderServiceImpl implements MallOrderService {
    @Autowired
    private MallOrderRepository orderRepository;

    @Override
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

    public List<MallOrderBean> findAll(){
        return orderRepository.findAll();
    }

    @Override
    public List<MallOrderBean> findByRotaryStatus(int rotaryStatus) {
        return orderRepository.findByRotaryStatus(rotaryStatus);
    }
}
