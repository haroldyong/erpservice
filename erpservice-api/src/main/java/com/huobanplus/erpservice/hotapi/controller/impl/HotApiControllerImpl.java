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
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.hotapi.controller.HotApiController;
import com.huobanplus.erpservice.hotapi.handler.OrderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liual on 2015-11-05.
 */
public class HotApiControllerImpl implements HotApiController {
    @Autowired
    private OrderHandler orderHandler;

    @Override
    public ApiResult orderIndex(@PathVariable("eventType") String eventType, @RequestAttribute ERPUserInfo erpUserInfo, HttpServletRequest request) {
        switch (eventType) {
            case HotApiConstant.DELIVERY_INFO:
                break;
            case HotApiConstant.RETURN_INFO:
                break;
            case HotApiConstant.OBTAIN_ORDER_DETAIL:
                break;
            case HotApiConstant.OBTAIN_ORDER_LIST:
                break;
        }
        return null;
    }

    @Override
    public ApiResult proIndex() {
        return null;
    }
}
