/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.repository.MallOutStoreRepository;
import com.huobanplus.erpservice.datacenter.service.MallOutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by allan on 2015/8/11.
 */
@Service
public class MallOutStoreServiceImpl implements MallOutStoreService {
    @Autowired
    private MallOutStoreRepository outStoreRepository;

    @Override
    public MallOutStoreBean save(MallOutStoreBean outStoreBean) {
        return outStoreRepository.save(outStoreBean);
    }

    @Override
    public MallOutStoreBean findByNo(String storageNo) {
        return outStoreRepository.findOne(storageNo);
    }
}
