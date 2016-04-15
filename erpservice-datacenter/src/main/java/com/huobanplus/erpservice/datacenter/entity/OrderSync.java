/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by allan on 4/15/16.
 */
@Entity
@Table(name = "ERP_OrderSync")
@Setter
@Getter
public class OrderSync {
    @Id
    private String orderId;
    /**
     * 同步状态
     */
    private OrderSyncStatus orderSyncStatus;
    /**
     * 订单状态
     */
    private OrderEnum.OrderStatus orderStatus;
    /**
     * 支付状态
     */
    private OrderEnum.PayStatus payStatus;
    /**
     * 发货状态
     */
    private OrderEnum.ShipStatus shipStatus;
    /**
     * ERP系统支付状态
     */
    private String outPayStatus;
    /**
     * ERP系统发货状态
     */
    private String outShipStatus;
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
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 最后一次同步时间
     */
    private Date lastSyncDate;
    private String remark;
    /**
     * 最近一次同步结果
     */
    private boolean resultStatus;

}
