/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by allan on 6/29/16.
 */
@Setter
@Getter
public class OrderRefundStatusInfo extends BaseInfo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单货品id
     */
    private int itemId;
    /**
     * 退款状态
     */
    private int refundStatus;
}
