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
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;

import java.util.List;

/**
 * Created by liual on 2015-10-29.
 */
public interface ERPSysDataInfoService {
    /**
     * 保存
     *
     * @param sysDataInfo
     * @return
     */
    ERPSysDataInfo save(ERPSysDataInfo sysDataInfo);

    /**
     * 删除某个商户下的某个erp的所有系统参数信息，新增钱操作
     *
     * @param customerId
     * @param providerType
     */
    void batchDelete(int customerId, ERPTypeEnum.ProviderType providerType);

    /**
     * 通过erp系统类型和erp使用者类型得到系统数据信息
     *
     * @param providerType
     * @param erpUserType
     * @return
     */
    List<ERPSysDataInfo> findByErpTypeAndErpUserType(ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType);
}
