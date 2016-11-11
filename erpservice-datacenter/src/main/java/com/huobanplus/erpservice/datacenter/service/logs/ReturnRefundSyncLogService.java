/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.entity.logs.ReturnRefundSyncLog;
import com.huobanplus.erpservice.datacenter.searchbean.ReturnRefundSearch;
import org.springframework.data.domain.Page;

/**
 * Created by wuxiongliu on 2016-11-11.
 */
public interface ReturnRefundSyncLogService {

    /**
     * 保存一条相信信息同步记录
     *
     * @param returnRefundSyncLog
     * @return
     */
    ReturnRefundSyncLog save(ReturnRefundSyncLog returnRefundSyncLog);

    /**
     * 根据id查询退货退款单
     *
     * @param id
     * @return
     */
    ReturnRefundSyncLog findById(Long id);

    /**
     * @param orderId
     * @return
     */
    ReturnRefundSyncLog findByOrderId(String orderId);

    /**
     * 分页查询
     *
     * @param pageIndex
     * @param pageSize
     * @param customerId
     * @param returnRefundSearch
     * @return
     */
    Page<ReturnRefundSyncLog> findAll(int pageIndex, int pageSize, int customerId, ReturnRefundSearch returnRefundSearch);
}
