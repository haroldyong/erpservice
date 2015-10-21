/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderUpdateEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.proxy.common.CommonUtils;
import com.huobanplus.erpservice.proxy.common.HotBaseController;
import com.huobanplus.erpservice.proxy.common.ProxyBaseController;
import com.huobanplus.erpservice.proxy.controller.OrderProxyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liual on 2015-10-19.
 */
@Controller
@RequestMapping("/hotProxy/order")
public class OrderProxyControllerImpl extends ProxyBaseController implements OrderProxyController {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private MallOrderService orderService;

    @Override
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createOrder(String orderInfoJson, ERPInfo erpInfo) throws Exception {
        erpInfo = CommonUtils.encryptInfo(erpInfo);

        //如果开通了erp，交由erp处理器推送到指定erp
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(CreateOrderEvent.class)) {
            MallOrderBean orderBean = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
            CreateOrderEvent createOrderEvent = new CreateOrderEvent();
            createOrderEvent.setErpInfo(erpInfo);
            createOrderEvent.setOrderInfo(orderBean);
            EventResult eventResult = erpHandler.handleEvent(createOrderEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                //如果未推送成功，则保存数据到erp数据服务平台，交由相关处理器处理
                orderBean.setErpInfo(new ObjectMapper().writeValueAsString(erpInfo));
                orderService.save(orderBean);
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "推送给erp时失败，将交给相关处理进行第二次尝试", null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }

    @Override
    public ApiResult updateOrder(String orderInfoJson, ERPInfo erpInfo) throws Exception {
        erpInfo = CommonUtils.encryptInfo(erpInfo);

        //如果开通了erp，交由erp处理器推送到指定erp
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        if (erpHandler.eventSupported(OrderUpdateEvent.class)) {
            MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
            OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
            orderUpdateEvent.setErpInfo(erpInfo);
            EventResult eventResult = erpHandler.handleEvent(orderUpdateEvent);
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                return ApiResult.resultWith(ResultCode.SUCCESS);
            } else {
                //如果不成功，保存本地数据，由相关处理器处理
                MallOrderBean preOrder = orderService.findByOrderId(orderInfo.getOrderId());
                if (preOrder == null) {
                    orderService.save(orderInfo);
                } else {
                    preOrder.setExpress(orderInfo.getExpress());
                    preOrder.setExpressNo(orderInfo.getExpressNo());
                    preOrder.setExpressCoding(orderInfo.getExpressCoding());
                    preOrder.setPrinter(orderInfo.getPrinter());
                    preOrder.setDistributer(orderInfo.getDistributer());
                    preOrder.setDistributTime(orderInfo.getDistributTime());
                    preOrder.setPrintTime(orderInfo.getPrintTime());
                    preOrder.setInspecter(orderInfo.getInspecter());
                    preOrder.setInspectTime(orderInfo.getInspectTime());
                    preOrder.setDeliveryOperator(orderInfo.getDeliveryOperator());
                    preOrder.setDeliveryTime(orderInfo.getDeliveryTime());
                    preOrder.setGrossWeight(orderInfo.getGrossWeight());
                    preOrder.setInnerLable(orderInfo.getInnerLable());
                    orderService.save(orderInfo);
                }
                return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "推送给erp时失败，将交给相关处理进行第二次尝试", null);
            }
        } else {
            return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
        }
    }
}
