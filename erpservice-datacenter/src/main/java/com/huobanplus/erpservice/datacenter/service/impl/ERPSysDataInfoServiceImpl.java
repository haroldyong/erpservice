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
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.repository.ERPSysDataInfoRepository;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liual on 2015-10-29.
 */
@Service
public class ERPSysDataInfoServiceImpl implements ERPSysDataInfoService {
    @Autowired
    private ERPSysDataInfoRepository sysDataInfoRepository;

    @Override
    @Transactional
    public ERPSysDataInfo save(ERPSysDataInfo sysDataInfo) {
        return sysDataInfoRepository.save(sysDataInfo);
    }

    @Override
    @Transactional
    public void batchDelete(int customerId, ERPTypeEnum.ProviderType providerType) {
        sysDataInfoRepository.batchDelete(customerId, providerType);
    }

    @Override
    public List<ERPSysDataInfo> findByErpTypeAndErpUserType(ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
        return sysDataInfoRepository.findByErpTypeAndErpUserType(providerType, erpUserType);
    }

    @Override
    public List<ERPSysDataInfo> findByErpTypeAndErpUserTypeAndCustomerId(ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType, int customerId) {
        return sysDataInfoRepository.findByErpTypeAndErpUserTypeAndCustomerId(providerType, erpUserType, customerId);
    }

    @Override
    public List<ERPSysDataInfo> findByErpTypeAndErpUserTypeAndParamNameAndParamVal(ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType, String paramName, String paramVal) {
        return sysDataInfoRepository.findByErpTypeAndErpUserTypeAndParamNameAndParamValue(providerType, erpUserType, paramName, paramVal);
    }
}
