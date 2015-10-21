/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
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
     * @param orderStatus 订单状态，状态有3中。1，表示已付款（含货到付款）；0，表示未付款；-1表示问题单（问题单在管家里面如果没有递交会做关闭订单处理，递交的不做处理）
     * @param pageSize    返回数量
     * @param pageIndex   返回页数，为空则返回所有结果集
     * @return 返回订单详细信息结果
     * @throws IOException IO 异常
     */
    EventResult obtainOrderInfoList(int orderStatus, int pageSize, Integer pageIndex, ERPUserInfo erpUserInfo, String mType);

    /**
     * 获取订单信息（详情）
     *
     * @param orderId
     * @return
     * @throws IOException
     */
    EventResult obtainOrderInfo(String orderId, ERPUserInfo erpUserInfo, String mType);

    /**
     * 发货通知
     *
     * @param orderId
     * @param logiName
     * @param logiNo
     * @return
     * @throws IOException
     */
    EventResult deliverOrder(String orderId, String logiName, String logiNo, ERPUserInfo erpUserInfo, String mType);
}
