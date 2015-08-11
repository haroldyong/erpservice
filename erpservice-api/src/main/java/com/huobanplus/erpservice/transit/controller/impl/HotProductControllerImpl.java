package com.huobanplus.erpservice.transit.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.event.erpevent.InventoryEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.transit.Common.ResultCode;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import com.huobanplus.erpservice.transit.Common.HotBaseController;
import com.huobanplus.erpservice.transit.controller.HotProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.TreeMap;

/**
 * <p>类描述：API对接伙伴商城商品（库存）操作实现类</p>
 */
@Controller
public class HotProductControllerImpl extends HotBaseController implements HotProductController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    @RequestMapping(value = "/obtainInventory", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult obtainInventory(ERPInfo erpInfo, String sign) {
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
}
