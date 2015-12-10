/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller.impl;

import com.huobanplus.erpservice.common.util.EnumHelper;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.hotapi.controller.HotApiController;
import com.huobanplus.erpservice.hotapi.handler.OrderHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liual on 2015-11-05.
 */
@Controller
@RequestMapping("/hotApi/rest")
public class HotApiControllerImpl implements HotApiController {
    @Autowired
    private OrderHandler orderHandler;
    private static Logger logger = Logger.getLogger(HotApiControllerImpl.class);

    @Override
    @RequestMapping(value = "/order/index", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderIndex(String eventType, @RequestAttribute ERPUserInfo erpUserInfo, HttpServletRequest request) {
        switch (eventType) {
            case HotApiConstant.DELIVERY_INFO:
                return orderHandler.deliveryInfo(request, erpUserInfo);
            case HotApiConstant.RETURN_INFO:
                return orderHandler.returnInfo(request, erpUserInfo);
            case HotApiConstant.OBTAIN_ORDER_DETAIL:
                return orderHandler.obtainOrderDetail(request, erpUserInfo);
            case HotApiConstant.OBTAIN_ORDER_LIST:
                return orderHandler.obtainOrderList(request, erpUserInfo);
        }
        return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT, "不被支持的事件方法", null);
    }

    @Override
    @RequestMapping(value = "/product/index/{eventType}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult proIndex() {
        return null;
    }
}
