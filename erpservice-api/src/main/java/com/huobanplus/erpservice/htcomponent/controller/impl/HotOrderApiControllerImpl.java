package com.huobanplus.erpservice.htcomponent.controller.impl;

import com.huobanplus.erpservice.event.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.event.erpevent.ObtainOrderDetailEvent;
import com.huobanplus.erpservice.event.erpevent.ObtainOrderListEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.FailedBean;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.htcomponent.controller.HotOrderApiController;
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
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        Monitor<EventResult> monitor;
        if (erpHandler.eventSupported(ObtainOrderListEvent.class)) {
            try {
                ObtainOrderListEvent obtainOrderListEvent = new ObtainOrderListEvent();
                obtainOrderListEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                monitor = erpHandler.handleEvent(obtainOrderListEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取订单信息失败");
            failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
        }
        return monitor.get().getSystemResult();
    }

    @Override
    @RequestMapping(value = "/{erpName}/obtainOrderDetail")
    @ResponseBody
    public String obtainOrderDetail(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        Monitor<EventResult> monitor;
        if (erpHandler.eventSupported(ObtainOrderListEvent.class)) {
            try {
                ObtainOrderListEvent obtainOrderListEvent = new ObtainOrderListEvent();
                obtainOrderListEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                monitor = erpHandler.handleEvent(obtainOrderListEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取订单信息失败");
            failedBean.setCurrentEvent(ObtainOrderListEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            monitor = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);
        }
        return monitor.get().getSystemResult();
    }

    @Override
    @RequestMapping("/{erpName}/createOrder")
    @ResponseBody
    public String createOrder(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler.eventSupported(CreateOrderEvent.class)) {
            try {
                CreateOrderEvent createOrderEvent = new CreateOrderEvent();
                createOrderEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                Monitor<EventResult> monitor = erpHandler.handleEvent(createOrderEvent, request);
                return monitor.get().getSystemResult();
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("创建订单失败");
                failedBean.setCurrentEvent(CreateOrderEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(CreateOrderEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("创建订单失败");
                failedBean.setCurrentEvent(CreateOrderEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(CreateOrderEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("创建订单失败");
            failedBean.setCurrentEvent(CreateOrderEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(CreateOrderEvent.class, failedBean);
        }
        return null;
    }
}
