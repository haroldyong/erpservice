package com.huobanplus.erpservice.datacenter.service.logs;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.GoodsInfoSyncLog;
import org.springframework.data.domain.Page;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
public interface GoodsInfoSyncLogService {

    /**
     * 保存一个商品同步日志
     *
     * @param goodsInfoSyncLog
     * @return
     */
    GoodsInfoSyncLog save(GoodsInfoSyncLog goodsInfoSyncLog);

    /**
     * 得到最后一次同步记录
     *
     * @param customerId
     * @param providerType
     * @return
     */
    GoodsInfoSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType);

    /**
     * 按条件查询日志
     *
     * @return
     */
    Page<GoodsInfoSyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId);
}
