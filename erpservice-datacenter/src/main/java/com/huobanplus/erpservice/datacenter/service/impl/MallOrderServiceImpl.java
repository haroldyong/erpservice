package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.repository.MallOrderRepository;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
