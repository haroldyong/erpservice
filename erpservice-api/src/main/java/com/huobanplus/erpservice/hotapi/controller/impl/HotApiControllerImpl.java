/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller.impl;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.hotapi.controller.HotApiController;
import com.huobanplus.erpservice.hotapi.handler.OrderHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by liual on 2015-11-05.
 */
@RequestMapping("/hotApi")
public class HotApiControllerImpl implements HotApiController {
    @Autowired
    private OrderHandler orderHandler;
    private static Logger logger = Logger.getLogger(HotApiControllerImpl.class);

    @Override
    @RequestMapping("/rest/index/{eventType}")
    public ApiResult index(@PathVariable("eventType") String eventType, @RequestAttribute ERPUserInfo erpUserInfo, HttpServletRequest request) {
        switch (eventType) {
            case HotApiConstant.DELIVERY_INFO:
                //发货
                orderHandler.deliveryInfo(request, erpUserInfo);
                break;
            case HotApiConstant.RETURN_INFO:
                //退货
                orderHandler.returnInfo(request, erpUserInfo);
                break;
            case HotApiConstant.OBTAIN_ORDER_DETAIL:
                //获取订单详情
                orderHandler.obtainOrderDetail(request, erpUserInfo);
                break;
            case HotApiConstant.OBTAIN_ORDER_LIST:
                //获取订单列表
                orderHandler.obtainOrderList(request, erpUserInfo);
                break;
            default:
                break;
        }
        return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT, "不被支持的事件方法", null);
    }

    @Override
    public ApiResult proIndex() {
        return null;
    }
}
