/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by liual on 2015-10-28.
 */
public interface ERPDetailConfigRepository extends JpaRepository<ERPDetailConfigEntity, Integer>, ERPDetailConfigRepositoryCustom {
    ERPDetailConfigEntity findByCustomerIdAndErpType(int customerId, ERPTypeEnum erpTypeEnum);

    @Query("select detailConfig from ERPDetailConfigEntity detailConfig where detailConfig.customerId=?1 and detailConfig.isDefault=1")
    ERPDetailConfigEntity findByCustomerIdAndDefault(int customerId);

    @Query("update ERPDetailConfigEntity detailConfig set detailConfig.isDefault=0 where detailConfig.customerId=?1")
    @Modifying
    void setUnDefault(int customerId);

    @Query("update ERPDetailConfigEntity detailConfig set detailConfig.isDefault=?2 where detailConfig.id=?1")
    @Modifying
    void setDefaultById(int id, int isDefault);

    List<ERPDetailConfigEntity> findByCustomerId(int customerId);
}
