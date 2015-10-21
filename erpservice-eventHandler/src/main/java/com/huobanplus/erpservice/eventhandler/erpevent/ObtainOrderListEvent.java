/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent;

import lombok.Data;

/**
 * <p>获取订单信息事件<p/>
 */
@Data
public class ObtainOrderListEvent extends ERPBaseEvent {
    private int orderStatus;
    private int pageIndex;
    private int pageSize;
}
