package com.huobanplus.erpservice.providerapi.controller.impl;

import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.providerapi.controller.HotOrderApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by liual on 2015-08-25.
 */
@Controller
@RequestMapping("/hotErpApi")
public class HotOrderApiControllerImpl implements HotOrderApiController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    @RequestMapping(value = "/{erpName}/obtainOrderList", method = RequestMethod.POST)
    @ResponseBody
    public String obtainOrderList(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        EventResult eventResult;
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
            failedBean.setFailedMsg("获取订单列表失败--erp信息无效");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);

            return eventResult.getData();
        }
        if (erpHandler.eventSupported(ObtainOrderListEvent.class)) {
            try {
                ObtainOrderListEvent obtainOrderListEvent = new ObtainOrderListEvent();
                obtainOrderListEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                eventResult = erpHandler.handleEvent(obtainOrderListEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("获取订单列表失败--IO处理发生异常");
                eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("获取订单列表失败--网络请求参数错误");
                eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
            failedBean.setFailedMsg("获取订单列表失败--对应的erp无法处理该事件");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
        }
        return eventResult.getData();
    }

    @Override
    @RequestMapping(value = "/{erpName}/obtainOrderDetail")
    @ResponseBody
    public String obtainOrderDetail(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        EventResult eventResult;
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainOrderDetailEvent.class.getName());
            failedBean.setFailedMsg("获取订单详情失败--erp信息无效");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);

            return eventResult.getData();
        }
        if (erpHandler.eventSupported(ObtainOrderDetailEvent.class)) {
            try {
                ObtainOrderDetailEvent obtainOrderDetailEvent = new ObtainOrderDetailEvent();
                obtainOrderDetailEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                eventResult = erpHandler.handleEvent(obtainOrderDetailEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainOrderDetailEvent.class.getName());
                failedBean.setFailedMsg("获取订单详情失败--IO处理发生异常");
                eventResult = erpHandler.handleException(ObtainOrderDetailEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainOrderDetailEvent.class.getName());
                failedBean.setFailedMsg("获取订单详情失败--网络请求参数错误");
                eventResult = erpHandler.handleException(ObtainOrderDetailEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainOrderDetailEvent.class.getName());
            failedBean.setFailedMsg("获取订单详情失败--对应的erp无法处理该事件");
            eventResult = erpHandler.handleException(ObtainOrderDetailEvent.class, failedBean);
        }
        return eventResult.getData();
    }

    @Override
    @RequestMapping(value = "/{erpName}/deliverOrder")
    @ResponseBody
    public String deliverOrder(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        EventResult eventResult;
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
            failedBean.setFailedMsg("发货通知失败--erp信息无效");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);

            return eventResult.getData();
        }
        if (erpHandler.eventSupported(DeliveryInfoEvent.class)) {
            try {
                DeliveryInfoEvent deliveryInfoEvent = new DeliveryInfoEvent();
                deliveryInfoEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                eventResult = erpHandler.handleEvent(deliveryInfoEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
                failedBean.setFailedMsg("发货通知失败--IO处理发生异常");
                eventResult = erpHandler.handleException(DeliveryInfoEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
                failedBean.setFailedMsg("发货通知失败--网络请求参数错误");
                eventResult = erpHandler.handleException(DeliveryInfoEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
            failedBean.setFailedMsg("发货通知失败--对应的erp无法处理该事件");
            eventResult = erpHandler.handleException(DeliveryInfoEvent.class, failedBean);
        }
        return eventResult.getData();
    }

}
