/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>类描述：<p/>
 * 库存信息数据库处理接口
 */
public interface MallInventoryRepository extends JpaRepository<MallInventoryBean, String>, JpaSpecificationExecutor {
}
