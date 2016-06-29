/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 将订单标识为退款状态
 * Created by allan on 6/29/16.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRefundStatusUpdate extends ERPBaseEvent {
    private OrderRefundStatusInfo orderRefundStatusInfo;
}
