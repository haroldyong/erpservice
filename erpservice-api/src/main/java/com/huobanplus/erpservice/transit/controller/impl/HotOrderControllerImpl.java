package com.huobanplus.erpservice.transit.controller.impl;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.transit.Common.ResultCode;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import com.huobanplus.erpservice.transit.controller.HotOrderController;
import com.huobanplus.erpservice.transit.utils.DesUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by allan on 2015/8/4.
 */
@Controller
@RequestMapping("/hotClientOrderApi")
public class HotOrderControllerImpl implements HotOrderController {
    @Autowired
    private ERPRegister erpRegister;

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
    @RequestMapping(value = "/obtainInventory", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult obtainInventory(ERPInfo erpInfo) {
        try {
            ERPInfo info = encryptInfo(erpInfo);
            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }
            if (erpHandler.eventSupported(InventoryEvent.class)) {
                InventoryEvent inventoryEvent = new InventoryEvent();
                inventoryEvent.setErpInfo(erpInfo);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(inventoryEvent, null);
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

    /**
     * 转换ERPInfo为明文状态
     *
     * @param preInfo
     * @return
     * @throws Exception
     */
    private ERPInfo encryptInfo(ERPInfo preInfo) throws Exception {
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(DesUtil.decrypt(preInfo.getName()));
        erpInfo.setType(DesUtil.decrypt(preInfo.getType()));
        erpInfo.setSysDataJson(DesUtil.decrypt(preInfo.getSysDataJson()));
        erpInfo.setValidation(DesUtil.decrypt(preInfo.getValidation()));

        return erpInfo;
    }
}
