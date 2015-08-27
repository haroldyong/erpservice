package com.huobanplus.erpservice.htcomponent.controller.impl;

import com.huobanplus.erpservice.event.erpevent.InventoryEvent;
import com.huobanplus.erpservice.event.erpevent.ObtainGoodListEvent;
import com.huobanplus.erpservice.event.erpevent.ObtainOrderListEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.FailedBean;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.htcomponent.controller.HotProductApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by liual on 2015-08-26.
 */
@Controller
@RequestMapping("/hotErpApi")
@ResponseBody
public class HotProductApiControllerImpl implements HotProductApiController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public String obtainGood(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        Monitor<EventResult> monitor;
        if (erpHandler.eventSupported(ObtainGoodListEvent.class)) {
            try {
                ObtainGoodListEvent obtainGoodListEvent = new ObtainGoodListEvent();
                obtainGoodListEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                monitor = erpHandler.handleEvent(obtainGoodListEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取商品信息失败");
                failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                monitor = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取商品信息失败");
                failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                monitor = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取商品信息失败");
            failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            monitor = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
        }
        return monitor.get().getSystemResult();
    }

    @Override
    public String syncInventory(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        Monitor<EventResult> monitor;
        if (erpHandler.eventSupported(ObtainGoodListEvent.class)) {
            try {
                InventoryEvent inventoryEvent = new InventoryEvent();
                inventoryEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                monitor = erpHandler.handleEvent(inventoryEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("同步失败");
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                monitor = erpHandler.handleException(InventoryEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("同步失败");
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                monitor = erpHandler.handleException(InventoryEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("同步失败");
            failedBean.setCurrentEvent(InventoryEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            monitor = erpHandler.handleException(InventoryEvent.class, failedBean);
        }
        return monitor.get().getSystemResult();
    }
}
