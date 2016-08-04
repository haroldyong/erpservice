/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by allan on 8/2/16.
 */
@Entity
@Table(name = "ERP_InventorySync_Log")
@Setter
@Getter
public class InventorySyncLog {
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
     * 商户id
     */
    private int customerId;
    /**
     * 同步数量
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
    /**
     * 同步状态
     */
    private OrderSyncStatus.InventorySyncStatus inventorySyncStatus;
    /**
     * 同步时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;
}
