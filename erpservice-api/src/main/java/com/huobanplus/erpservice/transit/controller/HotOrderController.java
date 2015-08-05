package com.huobanplus.erpservice.transit.controller;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.bean.ApiResult;

/**
 * Created by allan on 2015/8/4.
 */
public interface HotOrderController {
    /**
     * 创建订单
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param orderInfo 订单信息
     * @param erpInfo   参数为des加密后的参数
     */
    ApiResult createOrder(MallOrderBean orderInfo, ERPInfo erpInfo);

    /**
     * 获取订单列表
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param erpInfo
     * @return
     */
    ApiResult obtainOrder(ERPInfo erpInfo);

    /**
     * 获取产品库存
     * <p>erpInfo:需要传递参数为name，sysDateJson</p>
     * <p>参数均为des加密后的字符串</p>
     *
     * @param erpInfo
     * @return
     */
    ApiResult obtainInventory(ERPInfo erpInfo);
}
