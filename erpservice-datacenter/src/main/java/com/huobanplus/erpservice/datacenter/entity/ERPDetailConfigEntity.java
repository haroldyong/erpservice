/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import com.huobanplus.erpservice.datacenter.utils.ERPTypeEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-10-28.
 */
@Data
@Entity
@Table(name = "ERP_DetailConfigEntity")
public class ERPDetailConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ERPTypeEnum erpTypeEnum;
    private String sysDataJson;
    private int customerId;
    /**
     * 是否在使用，0表示未使用，1表示正在使用，每个商户只可以使用一个erp
     */
    private int isDefault;
}

