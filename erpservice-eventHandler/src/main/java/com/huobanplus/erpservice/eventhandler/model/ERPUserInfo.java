/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * erp使用者信息
 * Created by liual on 2015-10-15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ERPUserInfo implements Serializable {
    private static final long serialVersionUID = 52479852258664542L;

//    public ERPUserInfo(ERPTypeEnum.UserType erpUserType, int customerId) {
//        this.erpUserType = erpUserType;
//        this.customerId = customerId;
//    }

    /**
     * erp使用者类型
     * <ul>
     * <li>huobanmall</li>
     * <li>supplier</li>
     * </ul>
     */
    private ERPTypeEnum.UserType erpUserType;

    /**
     * 商家id
     */
    private int customerId;

    /**
     * 供应商的customerId
     */
//    private Integer customerIdForSuppery;
}
