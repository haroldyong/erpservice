package com.huobanplus.erpservice.htcomponent.controller.impl;

import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.FailedBean;
import com.huobanplus.erpservice.htcomponent.controller.HotApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * api处理方法实现类
 */
@Controller
public class HotApiControllerImpl implements HotApiController {
    @Autowired
    private ERPRegister erpRegister;

    /**
     * <p>ERP qpi 端处理订单新增事件。<p/>
     * @param info erp 信息
     * @param request http请求，网络请求携带参数。
     */
    @Override
    public void createOrder(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(CreateOrderEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(CreateOrderEvent.class, request);
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
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("创建订单失败");
            failedBean.setCurrentEvent(CreateOrderEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(CreateOrderEvent.class, failedBean);
        }
    }

    /**
     * <p>获取库存信息</p>
     * @param info erp信息
     * @param request 请求实体
     */
    @Override
    public void getProInventory(@PathVariable("erpInfo") String info, HttpServletRequest request){

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(InventoryEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(InventoryEvent.class, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取库存信息失败");
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(InventoryEvent.class, failedBean);

            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取库存信息失败");
                failedBean.setCurrentEvent(InventoryEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(InventoryEvent.class, failedBean);
            }
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取库存信息失败");
            failedBean.setCurrentEvent(InventoryEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(InventoryEvent.class, failedBean);
        }
    }

    /**
     * 获取订单信息
     * @param info erp信息
     * @param request 请求实体
     */
    @Override
    public void getOrderInfo(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(ObtainOrderEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(ObtainOrderEvent.class, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(ObtainOrderEvent.class, failedBean);

            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取订单信息失败");
                failedBean.setCurrentEvent(ObtainOrderEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(ObtainOrderEvent.class, failedBean);
            }
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取订单信息失败");
            failedBean.setCurrentEvent(ObtainOrderEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(ObtainOrderEvent.class, failedBean);
        }
    }

    /**
     * 修改订单信息
     * @param info erp信息
     * @param request 请求实体
     */
    @Override
    public void modifyOrderInfo(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(ModifyOrderInfoEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(ModifyOrderInfoEvent.class, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("修改订单信息失败");
                failedBean.setCurrentEvent(ModifyOrderInfoEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(ModifyOrderInfoEvent.class, failedBean);

            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("修改订单信息失败");
                failedBean.setCurrentEvent(ModifyOrderInfoEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(ModifyOrderInfoEvent.class, failedBean);
            }
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("修改订单信息失败");
            failedBean.setCurrentEvent(ModifyOrderInfoEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(ModifyOrderInfoEvent.class, failedBean);
        }
    }

    /**
     * 获取商品信息
     * @param info erp信息
     * @param request 请求实体
     */
    @Override
    public void obtainProductInfo(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(ProductInfoEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(ProductInfoEvent.class, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取商品信息失败");
                failedBean.setCurrentEvent(ProductInfoEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(ProductInfoEvent.class, failedBean);

            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("获取商品信息失败");
                failedBean.setCurrentEvent(ProductInfoEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(ProductInfoEvent.class, failedBean);
            }
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("获取商品信息失败");
            failedBean.setCurrentEvent(ProductInfoEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(ProductInfoEvent.class, failedBean);
        }
    }

    /**
     * 修改物流信息
     * @param info erp信息
     * @param request 请求实体
     */
    @Override
    public void modifyDeliveryInfo(@PathVariable("erpInfo") String info, HttpServletRequest request) {

        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(info);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if(erpHandler.eventSupported(DeliveryInfoEvent.class))
        {
            try
            {
                //处理生成订单信息接口
                erpHandler.handleEvent(DeliveryInfoEvent.class, request);
            } catch (IOException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("修改物流信息失败");
                failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
                failedBean.setFailedMsg("IO处理发生异常");
                erpHandler.handleException(DeliveryInfoEvent.class, failedBean);

            } catch (IllegalAccessException e) {
                FailedBean failedBean = new FailedBean();
                failedBean.setResultMsg("修改物流信息失败");
                failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
                failedBean.setFailedMsg("网络请求参数错误");
                erpHandler.handleException(DeliveryInfoEvent.class, failedBean);
            }
        }
        else
        {
            FailedBean failedBean = new FailedBean();
            failedBean.setResultMsg("修改物流信息失败");
            failedBean.setCurrentEvent(DeliveryInfoEvent.class.getName());
            failedBean.setFailedMsg("erp信息无效");
            erpHandler.handleException(DeliveryInfoEvent.class, failedBean);
        }
    }
}
