/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.searchbean;

import com.huobanplus.erpservice.common.ienum.OrderEnum;
import lombok.Data;

/**
 * Created by allan on 12/17/15.
 */
@Data
public class OrderSearchInfo {
    private OrderEnum.OrderStatus orderStatus;
    private OrderEnum.ShipStatus shipStatus;
    private OrderEnum.PayStatus payStatus;
    private String beginTime;
    private String endTime;
    private String beginPayTime;
    private String endPayTime;
    private String beginUpdateTime;
    private String endUpdateTime;
    private String orderBy;
    private String orderType;

}
