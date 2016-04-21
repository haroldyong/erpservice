/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity.logs;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by allan on 4/21/16.
 */
@Entity
@Table(name = "ERP_ShipSyncFailureOrder")
@Setter
@Getter
public class ShipSyncFailureOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 物流公司名称
     */
    private String logiName;
    /**
     * 物流公司编号
     */
    private String logiCode;
    /**
     * 物流单号
     */
    private String logiNo;
    @ManyToOne
    private OrderShipSyncLog orderShipSyncLog;
}
