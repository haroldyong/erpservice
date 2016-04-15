/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.utils;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.service.OrderPushLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by allan on 4/13/16.
 */
@Component
public class OrderProxyService {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private OrderPushLogService orderPushLogService;

    public ApiResult pushOrder(String orderInfoJson, ERPInfo erpInfo) {
        //如果开通了erp，交由erp处理器推送到指定erp
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(PushNewOrderEvent.class)) {
            Order order = JSON.parseObject(orderInfoJson, Order.class);
            PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
            pushNewOrderEvent.setErpInfo(erpInfo);
            pushNewOrderEvent.setOrderInfo(order);
            EventResult eventResult = erpHandler.handleEvent(pushNewOrderEvent);
            OrderOperatorLog orderOperatorLog = orderPushLogService.findByOrderId(order.getOrderId());
            if (orderOperatorLog == null) {
                orderOperatorLog = new OrderOperatorLog();
                orderOperatorLog.setOrderId(order.getOrderId());
                orderOperatorLog.setProviderType(erpInfo.getErpType());
                ERPTypeEnum.UserType userType = order.getSupplierId() > 0 ? ERPTypeEnum.UserType.HUOBAN_SUPPLIER : ERPTypeEnum.UserType.HUOBAN_MALL;
                orderOperatorLog.setUserType(userType);
                orderOperatorLog.setCustomerId(order.getSupplierId() > 0 ? order.getSupplierId() : order.getCustomerId());
                orderOperatorLog.setCreateTime(new Date());
            }

            ApiResult apiResult;
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderOperatorLog.setResultStatus(1);
                apiResult = ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                orderOperatorLog.setResultStatus(0);
                orderOperatorLog.setOrderJsonData(orderInfoJson);
                orderOperatorLog.setErpInfo(JSON.toJSONString(erpInfo));
                //如果未推送成功，则保存数据到erp数据服务平台，交由相关处理器处理

//                ClassUtil.cloneClass(order, orderBean);
//                orderBean.setErpInfo(JSON.toJSONString(erpInfo));
//                orderService.save(orderBean);
                apiResult = ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "推送给erp时失败", null);
            }
            orderPushLogService.save(orderOperatorLog);
            return apiResult;
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }
}
