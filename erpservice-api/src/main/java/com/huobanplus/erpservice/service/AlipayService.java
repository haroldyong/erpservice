package com.huobanplus.erpservice.service;

import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpservice.datacenter.model.AlipayOrder;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * @Author hxh
 * @Date 2018/1/2 11:05
 */
public interface AlipayService {
    /**
     * 推送支付宝支付单到海关
     *
     * @param order 订单实体类
     * @return
     */
    EventResult PushOrderAliPay(AlipayOrder order);

}
