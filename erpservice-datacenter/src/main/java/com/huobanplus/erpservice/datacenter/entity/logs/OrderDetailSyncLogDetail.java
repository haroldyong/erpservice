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
import com.huobanplus.erpservice.datacenter.common.OrderSyncLogType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by allan on 6/30/16.
 */
@Entity
@Table(name = "ERP_OrderDetailSync_Log_Detail")
@Setter
@Getter
public class OrderDetailSyncLogDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private OrderDetailSyncLog orderDetailSyncLog;
    /**
     * 同步状态
     */
    private OrderSyncStatus.DetailSyncStatus detailSyncStatus;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 商户id
     */
    private int customerId;
    private ERPTypeEnum.ProviderType providerType;
    private ERPTypeEnum.UserType userType;
    @Lob
    private String erpSysData;
    /**
     * 订单同步类型
     */
    private OrderSyncLogType.OrderDetailSyncType orderSyncLogType;
    /**
     * 备注
     */
    private String remark;
}
