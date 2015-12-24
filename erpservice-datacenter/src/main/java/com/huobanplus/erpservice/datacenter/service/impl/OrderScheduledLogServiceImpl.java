/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.entity.OrderScheduledLog;
import com.huobanplus.erpservice.datacenter.repository.OrderScheduledLogRepository;
import com.huobanplus.erpservice.datacenter.service.OrderScheduledLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 12/23/15.
 */
@Service
public class OrderScheduledLogServiceImpl implements OrderScheduledLogService {
    @Autowired
    private OrderScheduledLogRepository orderScheduledLogRepository;

    @Override
    public OrderScheduledLog save(OrderScheduledLog scheduledLog) {
        return orderScheduledLogRepository.save(scheduledLog);
    }

    @Override
    public OrderScheduledLog findFirst(int customerId) {
        return orderScheduledLogRepository.findFirstByCustomerIdOrderByCreateTimeDesc(customerId);
    }
}
