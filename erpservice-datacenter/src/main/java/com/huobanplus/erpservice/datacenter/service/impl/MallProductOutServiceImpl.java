/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.datacenter.repository.MallProductOutRepository;
import com.huobanplus.erpservice.datacenter.service.MallProductOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 2015/8/11.
 */
@Service
public class MallProductOutServiceImpl implements MallProductOutService {
    @Autowired
    private MallProductOutRepository productOutRepository;

    @Override
    public MallProductOutBean save(MallProductOutBean productOutBean) {
        return productOutRepository.save(productOutBean);
    }

    @Override
    public MallProductOutBean findById(long id) {
        return productOutRepository.findOne(id);
    }
}
