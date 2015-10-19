package com.huobanplus.erpservice.htcomponent.controller.impl;

import com.huobanplus.erpservice.eventhandler.erpevent.DeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.InventoryEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainGoodListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainOrderListEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
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
    @RequestMapping(value = "/{erpName}/obtainGood")
    @ResponseBody
    public String obtainGood(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        EventResult eventResult;
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
            failedBean.setFailedMsg("获取商品信息失败--erp信息无效");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);

            return eventResult.getData();
        }
        if (erpHandler.eventSupported(ObtainGoodListEvent.class)) {
            try {
                ObtainGoodListEvent obtainGoodListEvent = new ObtainGoodListEvent();
                obtainGoodListEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                eventResult = erpHandler.handleEvent(obtainGoodListEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
                failedBean.setFailedMsg("获取商品信息失败--IO处理发生异常");
                eventResult = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
                failedBean.setFailedMsg("获取商品信息失败--网络请求参数错误");
                eventResult = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(ObtainGoodListEvent.class.getName());
            failedBean.setFailedMsg("获取商品信息失败--对应的erp无法处理该事件");
            eventResult = erpHandler.handleException(ObtainGoodListEvent.class, failedBean);
        }
        return eventResult.getData();
    }

    @Override
    @RequestMapping(value = "/{erpName}/syncInventory")
    @ResponseBody
    public String syncInventory(@PathVariable("erpName") String erpName, HttpServletRequest request) {
        EventResult eventResult;
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler == null) {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(InventoryEvent.class.getName());
            failedBean.setFailedMsg("库存同步失败--erp信息无效");
            eventResult = erpHandler.handleException(ObtainOrderListEvent.class, failedBean);

            return eventResult.getData();
        }
        if (erpHandler.eventSupported(InventoryEvent.class)) {
            try {
                InventoryEvent inventoryEvent = new InventoryEvent();
                inventoryEvent.setErpInfo(erpInfo);
                //处理生成订单信息接口
                eventResult = erpHandler.handleEvent(inventoryEvent, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("库存同步失败--IO处理发生异常");
                eventResult = erpHandler.handleException(InventoryEvent.class, failedBean);
            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("库存同步失败--网络请求参数错误");
                eventResult = erpHandler.handleException(InventoryEvent.class, failedBean);
            }
        } else {
            FailedBean failedBean = new FailedBean();
            failedBean.setCurrentEvent(InventoryEvent.class.getName());
            failedBean.setFailedMsg("库存同步失败--对应的erp无法处理该事件");
            eventResult = erpHandler.handleException(InventoryEvent.class, failedBean);
        }
        return eventResult.getData();
    }
}
