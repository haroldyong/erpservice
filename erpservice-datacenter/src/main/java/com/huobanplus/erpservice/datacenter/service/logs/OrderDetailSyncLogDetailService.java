/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLogDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by allan on 6/30/16.
 */
public interface OrderDetailSyncLogDetailService {
    @Transactional
    OrderDetailSyncLogDetail save(OrderDetailSyncLogDetail orderDetailSyncLogDetail);

    List<OrderDetailSyncLogDetail> findBySyncLogId(long syncLogId);
}
