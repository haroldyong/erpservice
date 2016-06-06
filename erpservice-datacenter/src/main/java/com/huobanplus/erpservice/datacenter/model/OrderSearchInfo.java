/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

/**
 * Created by liual on 2015-11-05.
 */
@Data
public class OrderSearchInfo extends BaseInfo {
    private Integer orderStatus;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer shipStatus;
    private Integer payStatus;
    /**
     * 下单时间开始时间
     */
    private String beginTime;
    /**
     * 下单时间结束时间
     */
    private String endTime;
    private String beginPayTime;
    private String endPayTime;
    private String beginUpdateTime;
    private String endUpdateTime;
    private String orderBy;
    private String orderType;
}
