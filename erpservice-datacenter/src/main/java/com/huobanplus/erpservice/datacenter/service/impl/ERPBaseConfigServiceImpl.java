/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.repository.ERPBaseConfigRepository;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liual on 2015-10-28.
 */
@Service
public class ERPBaseConfigServiceImpl implements ERPBaseConfigService {
    @Autowired
    private ERPBaseConfigRepository erpBaseConfigRepository;

    @Override
    public void save(ERPBaseConfigEntity erpBaseConfig) {
        erpBaseConfig.setAppKey(StringUtil.createRandomStr(8));
        erpBaseConfig.setToken(StringUtil.createRandomStr32());
        erpBaseConfigRepository.save(erpBaseConfig);
    }

    @Override
    public ERPBaseConfigEntity findByAppKeyAndToken(String appKey, String token) {
        return erpBaseConfigRepository.findByAppKeyAndToken(appKey, token);
    }

    @Override
    public void updateToken(int customerId) {
        String token = StringUtil.createRandomStr32();
        erpBaseConfigRepository.updateToken(customerId, token);
    }

    @Override
    public void updateOpenStatus(int customerId, int isOpen) {
        erpBaseConfigRepository.updateOpenStatus(customerId, isOpen);
    }

    @Override
    public ERPBaseConfigEntity findByCustomerId(int customerId) {
        return erpBaseConfigRepository.findOne(customerId);
    }
}
