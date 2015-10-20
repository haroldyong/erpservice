/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


/**
 * 订单相关接口
 * Created by liual on 2015-10-14.
 */
@RequestMapping("/hotApi")
public interface OrderApiController {
    @RequestMapping("/deliverInfo")
    @ResponseBody
    ApiResult deliverInfo(
            String orderId,
            String logiName,
            String logiNo,
            @RequestParam(required = false, defaultValue = "0") int freight,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String dicDeliverItemsStr
    ) throws IOException;

    @RequestMapping("/returnInfo")
    @ResponseBody
    ApiResult returnInfo(
            String orderId,
            String logiName,
            String logiNo,
            String returnAddr,
            String returnMobile,
            String returnName,
            String returnZip,
            @RequestParam(required = false, defaultValue = "0") int freight,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String dicDeliverItemsStr
    ) throws IOException;

    @RequestMapping("/syncInventory")
    @ResponseBody
    ApiResult syncInventory(
            int goodId,
            int productId,
            String bn,
            int stock
    ) throws IOException;
}
