/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.jsonmodel;

import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by liual on 2015-12-07.
 */
@Setter
@Getter
public class OrderList {
    private int recordCount;
    private int pageIndex;
    private int pageSize;
    private String orderBy;
    private String orderType;
    private List<Order> orders;
}
