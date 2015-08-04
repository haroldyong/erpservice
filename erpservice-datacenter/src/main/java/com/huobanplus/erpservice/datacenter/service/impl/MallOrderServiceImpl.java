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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public Page<MallOrderBean> findAll(Integer orderStatus, Integer payStatus, String orderId, int pageIndex, int pageSize) {
        Specification<MallOrderBean> specification = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (orderStatus != null) {
                list.add(cb.equal(root.get("orderStatus").as(Integer.class), orderStatus));
            }
            if (payStatus != null) {
                list.add(cb.equal(root.get("payStatus").as(Integer.class), payStatus));
            }
            if (!StringUtils.isEmpty(orderId)) {
                list.add(cb.equal(root.get("orderCode").as(String.class), orderId));
            }
            return cb.and(list.toArray(new Predicate[list.size()]));
        };
        return orderRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize));
    }
}
