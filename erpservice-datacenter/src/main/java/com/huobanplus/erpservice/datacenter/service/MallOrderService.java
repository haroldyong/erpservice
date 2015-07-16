package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;

/**
 * 订单业务
 * Created by allan on 2015/7/10.
 */
public interface MallOrderService {
    /**
     * 保存订单
     *
     * @param orderBean
     * @return
     */
    MallOrderBean save(MallOrderBean orderBean);

    /**
     * 得到订单详情
     *
     * @param orderId
     * @return
     */
    MallOrderBean findByOrderId(String orderId);
}
