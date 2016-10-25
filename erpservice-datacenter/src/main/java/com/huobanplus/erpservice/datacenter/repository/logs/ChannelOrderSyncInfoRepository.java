/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.ChannelOrderSyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by wuxiongliu on 2016-10-11.
 */
@Repository
public interface ChannelOrderSyncInfoRepository extends JpaRepository<ChannelOrderSyncInfo, Long>, JpaSpecificationExecutor<ChannelOrderSyncInfo> {
}