package com.huobanplus.erpservice.datacenter.repository.logs;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.GoodsInfoSyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
@Repository
public interface GoodsInfoSyncLogRepository extends JpaRepository<GoodsInfoSyncLog, Long>, JpaSpecificationExecutor<GoodsInfoSyncLog> {
    GoodsInfoSyncLog findTopByCustomerIdAndProviderTypeOrderByIdDesc(int customerId, ERPTypeEnum.ProviderType providerType);
}
