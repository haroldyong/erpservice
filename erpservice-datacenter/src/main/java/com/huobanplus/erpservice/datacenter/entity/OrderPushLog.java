/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by allan on 4/13/16.
 */
@Entity
@Table(name = "ERP_OrderPushLog")
@Setter
@Getter
public class OrderPushLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 提供者类型
     */
    private ERPTypeEnum.ProviderType providerType;
    /**
     * 使用者类型
     */
    private ERPTypeEnum.UserType userType;
    /**
     * 使用者商户id
     */
    private int customerId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    private String orderId;
    /**
     * 备注
     */
    @Column(length = 2000)
    private String remark;
    /**
     * 0表示失败,1表示成功
     */
    private int resultStatus;
    @Lob
    private String orderJsonData;
    @Lob
    private String erpInfo;
}
