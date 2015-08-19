package com.huobanplus.erpservice.transit.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.datacenter.searchbean.MallOrderSearchBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.transit.Common.ResultCode;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import com.huobanplus.erpservice.transit.Common.HotBaseController;
import com.huobanplus.erpservice.transit.controller.HotOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

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
            ERPInfo info = encryptInfo(erpInfo);
            orderInfoJson = URLDecoder.decode(orderInfoJson, "utf-8");

            //签名验证
            if (StringUtils.isEmpty(sign)) {
                return new ApiResult(ResultCode.EMPTY_SIGN_CODE.getKey(), null, ResultCode.EMPTY_SIGN_CODE.getValue());
            }
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("name", info.getName());
            signMap.put("type", info.getType());
            signMap.put("validation", info.getValidation());
            signMap.put("sysDataJson", info.getSysDataJson());
            signMap.put("orderInfoJson", orderInfoJson);
            signMap.put("timestamp", info.getTimestamp());

            String checkSign = buildSign(signMap, signKey, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(CreateOrderEvent.class)) {
                CreateOrderEvent createOrderEvent = new CreateOrderEvent();
                createOrderEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
                orderInfo.setRotaryStatus(1);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(createOrderEvent, orderInfo);
                if (eventResultMonitor.get().getSystemStatus() == 1) {
                    orderService.save(orderInfo);
                    return new ApiResult(ResultCode.SUCCESS.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.SUCCESS.getValue());
                } else {
                    return new ApiResult(ResultCode.ERP_BAD_REQUEST.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.ERP_BAD_REQUEST.getValue());
                }
            } else {
                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
            }
        } catch (Exception e) {
            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
        }
    }

    @Override
    @RequestMapping(value = "/obtainOrder", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult obtainOrder(String orderSearchJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = encryptInfo(erpInfo);

            //签名验证
            if (StringUtils.isEmpty(sign)) {
                return new ApiResult(ResultCode.EMPTY_SIGN_CODE.getKey(), null, ResultCode.EMPTY_SIGN_CODE.getValue());
            }
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("name", info.getName());
            signMap.put("type", info.getType());
            signMap.put("validation", info.getValidation());
            signMap.put("sysDataJson", info.getSysDataJson());
            signMap.put("timestamp", info.getTimestamp());
            String checkSign = buildSign(signMap, signKey, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(ObtainOrderEvent.class)) {
                MallOrderSearchBean orderSearchBean = new ObjectMapper().readValue(orderSearchJson, MallOrderSearchBean.class);
                //带轮询->需要轮询
                MallOrderBean orderBean = orderService.findByOrderId(orderSearchBean.getOrderId());
                orderBean.setRotaryStatus(1);
                orderService.save(orderBean);
//
//                ObtainOrderEvent obtainOrderEvent = new ObtainOrderEvent();
//                obtainOrderEvent.setErpInfo(info);
//
//                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(obtainOrderEvent, orderSearchBean);
//                if (eventResultMonitor.get().getSystemStatus() == 1) {
//                    return new ApiResult(ResultCode.SUCCESS.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.SUCCESS.getValue());
//                } else {
//                    return new ApiResult(ResultCode.ERP_BAD_REQUEST.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.ERP_BAD_REQUEST.getValue());
//                }
                return new ApiResult(ResultCode.ADD_TO_ROTARY_QUEUE.getKey(), null, ResultCode.ADD_TO_ROTARY_QUEUE.getValue());
            } else {
                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
            }
        } catch (Exception e) {
            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
        }
    }

    @Override
    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderDeliver(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            orderInfoJson = URLDecoder.decode(orderInfoJson, "utf-8");
            //签名验证
            if (StringUtils.isEmpty(sign)) {
                return new ApiResult(ResultCode.EMPTY_SIGN_CODE.getKey(), null, ResultCode.EMPTY_SIGN_CODE.getValue());
            }
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("name", info.getName());
            signMap.put("type", info.getType());
            signMap.put("validation", info.getValidation());
            signMap.put("sysDataJson", info.getSysDataJson());
            signMap.put("orderInfoJson", orderInfoJson);
            signMap.put("timestamp", info.getTimestamp());
            String checkSign = buildSign(signMap, signKey, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderDeliverEvent.class)) {
                OrderDeliverEvent orderDeliverEvent = new OrderDeliverEvent();
                orderDeliverEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);

                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(orderDeliverEvent, orderInfo);
                if (eventResultMonitor.get().getSystemStatus() == 1) {
                    //本地数据更新
                    MallOrderBean preBean = orderService.findByOrderId(orderInfo.getOrderId());
                    preBean.setDeliveryTime(orderInfo.getDeliveryTime());
                    preBean.setExpress(orderInfo.getExpress());
                    preBean.setExpressNo(orderInfo.getExpressNo());
                    preBean.setTidNetWeight(orderInfo.getTidNetWeight());
                    orderService.save(preBean);

                    return new ApiResult(ResultCode.SUCCESS.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.SUCCESS.getValue());
                } else {
                    return new ApiResult(ResultCode.ERP_BAD_REQUEST.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.ERP_BAD_REQUEST.getValue());
                }
            } else {
                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
            }
        } catch (Exception e) {
            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
        }
    }

    @Override
    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderUpdate(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            orderInfoJson = URLDecoder.decode(orderInfoJson, "utf-8");
            //签名验证
            if (StringUtils.isEmpty(sign)) {
                return new ApiResult(ResultCode.EMPTY_SIGN_CODE.getKey(), null, ResultCode.EMPTY_SIGN_CODE.getValue());
            }
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("name", info.getName());
            signMap.put("type", info.getType());
            signMap.put("validation", info.getValidation());
            signMap.put("sysDataJson", info.getSysDataJson());
            signMap.put("orderInfoJson", orderInfoJson);
            signMap.put("timestamp", info.getTimestamp());
            String checkSign = buildSign(signMap, signKey, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderUpdateEvent.class)) {
                OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
                orderUpdateEvent.setErpInfo(info);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);

                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(orderUpdateEvent, orderInfo);
                if (eventResultMonitor.get().getSystemStatus() == 1) {

                    //本地数据更新
                    MallOrderBean preOrder = orderService.findByOrderId(orderInfo.getOrderId());
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
                    for (MallOrderItem orderItem : orderInfo.getOrderItems()) {
                        preOrder.getOrderItems().stream().filter(preOrderItem -> orderItem.getId() == preOrderItem.getId()).forEach(preOrderItem -> {
                            preOrderItem.setInspectionNum(orderItem.getInspectionNum());
                        });
                    }
                    orderService.save(preOrder);

                    return new ApiResult(ResultCode.SUCCESS.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.SUCCESS.getValue());
                } else {
                    return new ApiResult(ResultCode.ERP_BAD_REQUEST.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.ERP_BAD_REQUEST.getValue());
                }
            } else {
                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
            }
        } catch (Exception e) {
            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
        }
    }

    @Override
    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderStatusUpdate(String orderInfoJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            orderInfoJson = URLDecoder.decode(orderInfoJson, "utf-8");

            //签名验证
            if (StringUtils.isEmpty(sign)) {
                return new ApiResult(ResultCode.EMPTY_SIGN_CODE.getKey(), null, ResultCode.EMPTY_SIGN_CODE.getValue());
            }
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("name", info.getName());
            signMap.put("type", info.getType());
            signMap.put("validation", info.getValidation());
            signMap.put("sysDataJson", info.getSysDataJson());
            signMap.put("orderInfoJson", orderInfoJson);
            signMap.put("timestamp", info.getTimestamp());
            String checkSign = buildSign(signMap, signKey, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderStatusUpdateEvent.class)) {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent();
                orderStatusUpdateEvent.setErpInfo(erpInfo);
                MallOrderBean orderInfo = new ObjectMapper().readValue(orderInfoJson, MallOrderBean.class);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(orderStatusUpdateEvent, orderInfo);
                if (eventResultMonitor.get().getSystemStatus() == 1) {
                    return new ApiResult(ResultCode.SUCCESS.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.SUCCESS.getValue());
                } else {
                    return new ApiResult(ResultCode.ERP_BAD_REQUEST.getKey(), eventResultMonitor.get().getSystemResult(), ResultCode.ERP_BAD_REQUEST.getValue());
                }
            } else {
                return new ApiResult(ResultCode.EVENT_NOT_SUPPORT.getKey(), null, ResultCode.EVENT_NOT_SUPPORT.getValue());
            }
        } catch (Exception e) {
            return new ApiResult(ResultCode.SYSTEM_BAD_REQUEST.getKey(), e.getMessage(), ResultCode.SYSTEM_BAD_REQUEST.getValue());
        }
    }
}
