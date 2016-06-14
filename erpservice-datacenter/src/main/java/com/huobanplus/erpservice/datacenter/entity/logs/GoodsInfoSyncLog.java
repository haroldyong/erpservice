package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
@Entity
@Table(name = "ERP_GoodsInfoSync_Log")
@Setter
@Getter
public class GoodsInfoSyncLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * ERP提供者类型
     */
    private ERPTypeEnum.ProviderType providerType;
    /**
     * ERP使用者类型
     */
    private ERPTypeEnum.UserType userType;
    /**
     * 使用者商户id
     */
    private int customerId;
    /**
     * 本次同步订单数量
     */
    private int totalCount;
    /**
     * 成功数量
     */
    private int successCount;
    /**
     * 失败数量
     */
    private int failedCount;
//    /**
//     * 发货同步状态
//     */
//    private OrderSyncStatus.ShipSyncStatus shipSyncStatus;
    /**
     * 同步时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;
}
