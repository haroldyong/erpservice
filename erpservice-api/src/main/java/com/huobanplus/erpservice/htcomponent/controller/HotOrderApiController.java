package com.huobanplus.erpservice.htcomponent.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>接口描述</p>
 * <p>第三方请求我们使用</p>
 * <p>订单相关</p>
 * Created by liual on 2015-08-25.
 */
@RequestMapping("/hotErpApi")
public interface HotOrderApiController {
    /**
     * 得到订单列表
     *
     * @param erpName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{erpName}/obtainOrderList", method = RequestMethod.POST)
    String obtainOrderList(@PathVariable("erpName") String erpName, HttpServletRequest request);

    /**
     * 得到订单详细信息
     *
     * @param erpName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{erpName}/obtainOrderDetail", method = RequestMethod.POST)
    String obtainOrderDetail(@PathVariable("erpName") String erpName, HttpServletRequest request);

    /**
     * 发货通知
     *
     * @param erpName
     * @param request
     * @return
     */
    @RequestMapping(value = "/{erpName}/deliverOrder", method = RequestMethod.POST)
    String deliverOrder(@PathVariable("erpName") String erpName, HttpServletRequest request);
}
