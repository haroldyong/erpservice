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
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.proxy.common.HotBaseController;
import com.huobanplus.erpservice.proxy.controller.HotOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>类描述：</p>
 * API对接伙伴商城订单操作实现类
 */
@Controller
@RequestMapping("/hotClientOrderApi")
public class HotOrderControllerImpl extends HotBaseController implements HotOrderController {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private MallOrderService orderService;

    @Override
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createOrder(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = erpInfo;

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
            if (erpHandler.eventSupported(CreateOrderEvent.class)) {
                CreateOrderEvent createOrderEvent = new CreateOrderEvent();
                createOrderEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
//                orderInfo.setRotaryStatus(1);
                orderInfo.setErpInfo(new ObjectMapper().writeValueAsString(info));
                EventResult eventResult = erpHandler.handleEvent(createOrderEvent);
                if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    orderService.save(orderInfo);
                    return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
                } else {
                    return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST);
                }
            } else {
                return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
            }
        } catch (Exception e) {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST);
        }
    }

//    @Override
//    @RequestMapping(value = "/obtainOrder", method = RequestMethod.POST)
//    @ResponseBody
//    public ApiResult obtainOrder(String orderSearchJson, ERPInfo erpInfo, String sign) {
//        try {
//            ERPInfo info = encryptInfo(erpInfo);
//
//            //签名验证
//            if (StringUtils.isEmpty(sign)) {
//                return ApiResult.resultWith(ResultCode.EMPTY_SIGN_CODE);
//            }
//            Map<String, String> signMap = new TreeMap<>();
//            signMap.put("name", info.getName());
//            signMap.put("type", info.getType());
//            signMap.put("validation", info.getValidation());
//            signMap.put("sysDataJson", info.getSysDataJson());
//            signMap.put("timestamp", info.getTimestamp());
//            String checkSign = buildSign(signMap, signKey, null);
//
//            if (!sign.equals(checkSign)) {
//                return ApiResult.resultWith(ResultCode.WRONG_SIGN_CODE);
//            }
//
//            ERPHandler erpHandler = erpRegister.getERPHandler(info);
//            if (erpHandler == null) {
//                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
//            }
//            if (erpHandler.eventSupported(ObtainOrderListEvent.class)) {
//                MallOrderSearchBean orderSearchBean = new ObjectMapper().readValue(orderSearchJson, MallOrderSearchBean.class);
//                //带轮询->需要轮询
//                MallOrderBean orderBean = orderService.findByOrderId(orderSearchBean.getOrderId());
//                orderBean.setRotaryStatus(1);
//                orderService.save(orderBean);
//                return new ApiResult(ResultCode.ADD_TO_ROTARY_QUEUE.getKey(), null, ResultCode.ADD_TO_ROTARY_QUEUE.getValue());
//            } else {
//                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
//            }
//        } catch (Exception e) {
//            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
//        }
//    }

    @Override
    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderDeliver(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = erpInfo;
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
            if (erpHandler.eventSupported(OrderDeliverEvent.class)) {
                OrderDeliverEvent orderDeliverEvent = new OrderDeliverEvent();
                orderDeliverEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);

                EventResult eventResult = erpHandler.handleEvent(orderDeliverEvent);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                    //本地数据更新
                    MallOrderBean preBean = orderService.findByOrderId(orderInfo.getOrderId());
//                    preBean.setDeliveryTime(orderInfo.getDeliveryTime());
//                    preBean.setExpress(orderInfo.getExpress());
                    preBean.setExpressNo(orderInfo.getExpressNo());
//                    preBean.setTidNetWeight(orderInfo.getTidNetWeight());
                    orderService.save(preBean);

                    return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
                } else {
                    return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getData());
                }
            } else {
                return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
            }
        } catch (Exception e) {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderUpdate(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = erpInfo;
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
            if (erpHandler.eventSupported(OrderUpdateEvent.class)) {
                OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
                orderUpdateEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);

                EventResult eventResult = erpHandler.handleEvent(orderUpdateEvent);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {

                    //本地数据更新
                    MallOrderBean preOrder = orderService.findByOrderId(orderInfo.getOrderId());
//                    preOrder.setExpress(orderInfo.getExpress());
//                    preOrder.setExpressNo(orderInfo.getExpressNo());
//                    preOrder.setExpressCoding(orderInfo.getExpressCoding());
//                    preOrder.setPrinter(orderInfo.getPrinter());
//                    preOrder.setDistributer(orderInfo.getDistributer());
//                    preOrder.setDistributTime(orderInfo.getDistributTime());
//                    preOrder.setPrintTime(orderInfo.getPrintTime());
//                    preOrder.setInspecter(orderInfo.getInspecter());
//                    preOrder.setInspectTime(orderInfo.getInspectTime());
//                    preOrder.setDeliveryOperator(orderInfo.getDeliveryOperator());
//                    preOrder.setDeliveryTime(orderInfo.getDeliveryTime());
//                    preOrder.setGrossWeight(orderInfo.getGrossWeight());
//                    preOrder.setInnerLable(orderInfo.getInnerLable());
//                    for (MallOrderItemBean orderItem : orderInfo.getOrderItems()) {
//                        preOrder.getOrderItems().stream().filter(preOrderItem -> orderItem.getId() == preOrderItem.getId()).forEach(preOrderItem -> {
//                            preOrderItem.setInspectionNum(orderItem.getInspectionNum());
//                        });
//                    }
                    orderService.save(preOrder);

                    return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
                } else {
                    return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST);
                }
            } else {
                return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
            }
        } catch (Exception e) {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderStatusUpdate(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = erpInfo;
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
            if (erpHandler.eventSupported(OrderStatusUpdateEvent.class)) {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent();
                orderStatusUpdateEvent.setErpInfo(erpInfo);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
                EventResult eventResult = erpHandler.handleEvent(orderStatusUpdateEvent);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                    return ApiResult.resultWith(ResultCode.SUCCESS, eventResult.getData());
                } else {
                    return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getData());
                }
            } else {
                return ApiResult.resultWith(ResultCode.EVENT_NOT_SUPPORT);
            }
        } catch (Exception e) {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, e.getMessage());
        }
    }
}
