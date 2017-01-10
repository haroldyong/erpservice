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

import com.huobanplus.erpservice.datacenter.entity.logs.PurchaseOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrder;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
public interface PurchaseOrderSyncLogService {

    void batchSave(List<PurchaseOrder> purchaseOrderList);

    Page<PurchaseOrderSyncLog> findAll(int pageIndex, int pageSize, int customerId, String receiveNo, String blno);
}
