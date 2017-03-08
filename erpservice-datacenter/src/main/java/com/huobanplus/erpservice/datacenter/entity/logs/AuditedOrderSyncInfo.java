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

package com.huobanplus.erpservice.datacenter.entity.logs;

import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by wuxiongliu on 2017-03-08.
 * 审核订单明细
 */

@Entity
@Table(name = "ERP_AuditedOrderSynfInfo")
@Setter
@Getter
public class AuditedOrderSyncInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AuditedOrderSyncLog auditedOrderSyncLog;

    private OrderSyncStatus.AuditedSyncStatus auditedSyncStatus;

    private String orderId;

    private String remark;

    @Lob
    private String auditedOrderJson;
}
