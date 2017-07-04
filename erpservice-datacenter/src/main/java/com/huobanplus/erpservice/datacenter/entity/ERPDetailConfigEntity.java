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
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 每个商户,erp使用者类型和是否正在使用确定一个erp详细设置
 * 也就是说,相同一个商户,即使供应商也是分销商,
 * 那么作为一个供应商可以在后台设置多个类型的erp,但一个生效
 * 作为一个分销商,同样也是如此
 * Created by liual on 2015-10-28.
 */
@Entity
@Data
@Table(name = "ERP_DetailConfig")
public class ERPDetailConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private ERPTypeEnum.ProviderType erpType;
    private ERPTypeEnum.UserType erpUserType;
    @Lob
    private String erpSysData;
    /**
     * 是否在使用，0表示未使用，1表示正在使用，每个商户只可以使用一个erp
     */
    private int isDefault;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private ERPBaseConfigEntity erpBaseConfig;
    private int customerId;

    private String p0;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String p5;
    private String p6;
    private String p7;
    private String p8;
    private String p9;
    private String p10;
    private String p11;
    private String p12;
    private String p13;
    private String p14;
    private String p15;
    private String p16;
}

