/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.entity.MallOrderItemBean;
import com.huobanplus.erpservice.datacenter.repository.MallOrderItemRepository;
import com.huobanplus.erpservice.datacenter.service.MallOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 12/10/15.
 */
@Service
public class MallOrderItemServiceImpl implements MallOrderItemService {
    @Autowired
    private MallOrderItemRepository orderItemRepository;

    @Override
    public MallOrderItemBean save(MallOrderItemBean orderItemBean) {
        return orderItemRepository.save(orderItemBean);
    }
}
