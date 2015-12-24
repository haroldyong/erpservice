/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.impl;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.repository.ERPDetailConfigRepository;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liual on 2015-10-29.
 */
@Service
public class ERPDetailConfigServiceImpl implements ERPDetailConfigService {
    @Autowired
    private ERPDetailConfigRepository detailConfigRepository;

    @Override
    @Transactional
    public ERPDetailConfigEntity save(ERPDetailConfigEntity erpDetailConfigEntity) {
        return detailConfigRepository.save(erpDetailConfigEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ERPDetailConfigEntity findByCustomerIdAndType(int customerId, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType userType) {
        return detailConfigRepository.findByCustomerIdAndErpTypeAndErpUserType(customerId, providerType, userType);
    }

    @Override
    @Transactional(readOnly = true)
    public ERPDetailConfigEntity findByCustomerIdAndDefault(int customerId, ERPTypeEnum.UserType userType) {
        return detailConfigRepository.findByCustomerIdAndDefault(customerId, userType);
    }

    @Override
    @Transactional
    public void setDefault(int id, int customerId, ERPTypeEnum.UserType userType) {
        //先把商户的所有erp设为非默认
        detailConfigRepository.setUnDefault(customerId, userType);
        //把某个erp设为默认
        detailConfigRepository.setDefaultById(id, 1);
    }

    @Override
    @Transactional
    public void setUnDefault(int customerId, ERPTypeEnum.UserType userType) {
        detailConfigRepository.setUnDefault(customerId, userType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ERPDetailConfigEntity> findByCustomerId(int customerId, ERPTypeEnum.UserType userType) {
        return detailConfigRepository.findByCustomerId(customerId, userType);
    }

    @Override
    public ERPDetailConfigEntity findBySysData(List<ERPSysDataInfo> sysDataInfos, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
        return detailConfigRepository.findBySysData(sysDataInfos, providerType, erpUserType);
    }

    @Override
    public List<ERPDetailConfigEntity> findByErpTypeAndDefault(ERPTypeEnum.ProviderType erpType) {
        return detailConfigRepository.findByErpTypeAndDefault(erpType);
    }


}
