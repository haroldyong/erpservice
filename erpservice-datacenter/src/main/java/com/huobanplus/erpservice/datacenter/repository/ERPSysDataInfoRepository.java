/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by liual on 2015-10-29.
 */
public interface ERPSysDataInfoRepository extends JpaRepository<ERPSysDataInfo, Integer> {
    @Query("delete from ERPSysDataInfo sysData where sysData.customerId=?1 and sysData.erpType = ?2")
    @Modifying
    void batchDelete(int customerId, ERPTypeEnum erpTypeEnum);
}
