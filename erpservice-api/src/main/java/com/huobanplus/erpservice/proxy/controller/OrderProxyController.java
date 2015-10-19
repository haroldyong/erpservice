/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 提供给erp使用者的api，erp通过此类接口推送数据到erp服务平台，并转交给相应的erp处理
 * Created by liual on 2015-10-19.
 */
@RequestMapping("/hotProxy/order")
public interface OrderProxyController {
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    ApiResult createOrder(String orderInfoJson, ERPInfo erpInfo) throws Exception;

    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    ApiResult updateOrder(String orderInfoJson, ERPInfo erpInfo) throws Exception;


}
