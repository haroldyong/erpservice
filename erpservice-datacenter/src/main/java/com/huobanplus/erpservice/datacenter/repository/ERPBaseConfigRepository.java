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
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by liual on 2015-10-28.
 */
public interface ERPBaseConfigRepository extends JpaRepository<ERPBaseConfigEntity, Integer> {
    ERPBaseConfigEntity findByAppKeyAndToken(String appKey, String token);

    @Query("update ERPBaseConfigEntity baseConfig set baseConfig.token=?2 where baseConfig.customerId=?1")
    @Modifying
    void updateToken(int customerId, String token);

    @Query("update ERPBaseConfigEntity baseConfig set baseConfig.isOpen=?2 where baseConfig.customerId=?1")
    @Modifying
    void updateOpenStatus(int customerId, int isOpen);

    ERPBaseConfigEntity findByCustomerIdAndErpUserType(int customerId, ERPTypeEnum.UserType userType);
}
