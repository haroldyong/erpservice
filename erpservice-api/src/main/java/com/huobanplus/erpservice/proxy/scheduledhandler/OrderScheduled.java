/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.scheduledhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 未提交成功的数据处理器--订单相关
 * Created by liual on 2015-10-20.
 */
@Component
public class OrderScheduled {
    @Autowired
    private MallOrderService orderService;
    @Autowired
    private ERPRegister erpRegister;

    @Scheduled
    public void pushFailedOrder() throws IOException, IllegalAccessException {
        List<MallOrderBean> orderList = orderService.findAll();
        for (MallOrderBean order : orderList) {
            ERPInfo erpInfo = new ObjectMapper().readValue(order.getErpInfo(), ERPInfo.class);
            ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
            if (erpHandler != null) {
                if (erpHandler.eventSupported(CreateOrderEvent.class)) {
                    //推送给erp
                    erpHandler.handleEvent(new CreateOrderEvent(), order);
                }
            }
        }
    }
}
