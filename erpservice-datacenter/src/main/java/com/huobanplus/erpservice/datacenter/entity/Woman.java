/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by liual on 2015-11-07.
 */
@Entity
@Table(name = "Test_Woman")
@DiscriminatorValue(value = "1")
@Data
public class Woman extends People{
    private String ptest;
}
