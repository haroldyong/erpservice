/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016-09-19.
 */
@Getter
@Setter
@Entity
@Table(name = "ERP_ChannelOrderSync_Log")
public class ChannelOrderSyncLog {
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

    /**
     * 订单同步状态
     */
    private boolean orderSyncStatus;// TODO: 2016-09-19
    /**
     * 同步时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;

}
