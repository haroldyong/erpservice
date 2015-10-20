/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Created by liual on 2015-08-26.
 */
public interface MallGoodRepository extends JpaRepository<MallGoodBean, Long>, JpaSpecificationExecutor {
    MallGoodBean findByBn(String bn);
}
