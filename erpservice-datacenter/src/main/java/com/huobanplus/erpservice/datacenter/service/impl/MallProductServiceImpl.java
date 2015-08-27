package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallProductBean;
import com.huobanplus.erpservice.datacenter.repository.MallProductRepository;
import com.huobanplus.erpservice.datacenter.service.MallProductService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liual on 2015-08-27.
 */
public class MallProductServiceImpl implements MallProductService {
    @Autowired
    private MallProductRepository productRepository;

    @Override
    public MallProductBean findBySku(String bn, String skuId) {
        return productRepository.findBySku(bn, skuId);
    }
}
