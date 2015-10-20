/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.providerapi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>接口描述</p>
 * <p>第三方请求我们使用</p>
 * <p>商品相关</p>
 * Created by liual on 2015-08-26.
 */
@RequestMapping("/hotErpApi")
public interface HotProductApiController {
    /**
     * 获取商品
     *
     * @param erpName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{erpName}/obtainGood", method = RequestMethod.POST)
    String obtainGood(@PathVariable("erpName") String erpName, HttpServletRequest request);

    /**
     * 同步库存接口
     *
     * @param erpName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{erpName}/syncInventory", method = RequestMethod.POST)
    String syncInventory(@PathVariable("erpName") String erpName, HttpServletRequest request);
}
