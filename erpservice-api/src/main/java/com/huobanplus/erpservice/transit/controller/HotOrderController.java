package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by allan on 2015/8/4.
 */
@RequestMapping("/hotClientOrderApi")
public interface HotOrderController {
    /**
     * 创建订单
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param orderInfo 订单信息
     * @param erpInfo   参数为des加密后的参数
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    ApiResult createOrder(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * 获取订单列表
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param erpInfo
     * @return
     */
    @RequestMapping(value = "/obtainOrder", method = RequestMethod.GET)
    ApiResult obtainOrder(ERPInfo erpInfo);

    /**
     * 获取产品库存
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param erpInfo
     * @return
     */
    @RequestMapping(value = "/obtainInventory", method = RequestMethod.GET)
    ApiResult obtainInventory(ERPInfo erpInfo);

    /**
     * 订单发货
     *
     * @param orderInfo 根据不同erp传递不同的必须参数
     * @param erpInfo
     * @return
     */
    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
    ApiResult orderDeliver(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * 订单业务信息更新
     *
     * @param orderInfo
     * @param erpInfo
     * @return
     */
    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    ApiResult orderUpdate(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * 订单状态更新
     *
     * @param orderInfo
     * @param erpInfo
     * @return
     */
    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
    ApiResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo erpInfo);
}
