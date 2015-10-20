/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 订单方法处理接口
 */
public interface NSOrderHandler {
    /**
     * 获取订单信息（列表）
     *
     * @param request 请求实体
     * @return 返回订单详细信息结果
     * @throws IOException IO 异常
     */
    EventResult obtainOrderInfoList(HttpServletRequest request) throws IOException;

    /**
     * 获取订单信息（详情）
     *
     * @param request
     * @return
     * @throws IOException
     */
    EventResult obtainOrderInfo(HttpServletRequest request) throws IOException;

    /**
     * 发货通知
     *
     * @param request
     * @return
     * @throws IOException
     */
    EventResult deliverOrder(HttpServletRequest request) throws IOException;
}
