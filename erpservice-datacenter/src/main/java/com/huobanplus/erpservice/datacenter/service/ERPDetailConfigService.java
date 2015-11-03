/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;

import java.util.List;

/**
 * Created by liual on 2015-10-29.
 */
public interface ERPDetailConfigService {
    /**
     * 保存
     *
     * @param erpDetailConfigEntity
     * @return
     */
    ERPDetailConfigEntity save(ERPDetailConfigEntity erpDetailConfigEntity);

    /**
     * 根据商户id和类型得到对应的erp详细配置信息
     *
     * @param customerId
     * @param providerType
     * @return
     */
    ERPDetailConfigEntity findByCustomerIdAndType(int customerId, ERPTypeEnum.ProviderType providerType);

    /**
     * 得到商户使用的erp详细配置信息
     *
     * @param customerId
     * @return
     */
    ERPDetailConfigEntity findByCustomerIdAndDefault(int customerId);

    /**
     * 设置某个为默认
     *
     * @param id
     */
    void setDefault(int id, int customerId);

    /**
     * 全部设为非默认
     *
     * @param customerId
     */
    void setUnDefault(int customerId);

    /**
     * 得到商户设置过的所有erp系统
     *
     * @param customerId
     * @return
     */
    List<ERPDetailConfigEntity> findByCustomerId(int customerId);

    /**
     * 通过系统参数过的erp详细配置信息
     *
     * @param sysDataInfos
     * @return
     */
    List<ERPDetailConfigEntity> findBySysData(List<ERPSysDataInfo> sysDataInfos);
}
