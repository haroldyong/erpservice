package com.huobanplus.erpservice.transit.controller.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>类描述：<p/>
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
    public ApiResult createOrder(MallOrderBean orderInfo, ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(CreateOrderEvent.class)) {
                CreateOrderEvent createOrderEvent = new CreateOrderEvent();
                createOrderEvent.setErpInfo(info);
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
    @RequestMapping(value = "/obtainOrder", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult obtainOrder(ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(ObtainOrderEvent.class)) {
                ObtainOrderEvent obtainOrderEvent = new ObtainOrderEvent();
                obtainOrderEvent.setErpInfo(info);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(obtainOrderEvent, null);
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

    @Override
    @RequestMapping(value = "/orderDeliver", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderDeliver(MallOrderBean orderInfo, ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderDeliverEvent.class)) {
                OrderDeliverEvent orderDeliverEvent = new OrderDeliverEvent();
                orderDeliverEvent.setErpInfo(info);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(orderDeliverEvent, orderInfo);
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

    @Override
    @RequestMapping(value = "/orderUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderUpdate(MallOrderBean orderInfo, ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderUpdateEvent.class)) {
                OrderUpdateEvent orderUpdateEvent = new OrderUpdateEvent();
                orderUpdateEvent.setErpInfo(erpInfo);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(orderUpdateEvent, orderInfo);
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

    @Override
    @RequestMapping(value = "/orderStatusUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(OrderStatusUpdateEvent.class)) {
                OrderStatusUpdateEvent orderStatusUpdateEvent = new OrderStatusUpdateEvent();
                orderStatusUpdateEvent.setErpInfo(erpInfo);
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
