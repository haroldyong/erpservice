package com.huobanplus.erpservice.proxy.controller.impl;

import com.huobanplus.erpservice.eventhandler.erpevent.InventoryEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.proxy.common.CommonUtils;
import com.huobanplus.erpservice.proxy.common.HotBaseController;
import com.huobanplus.erpservice.proxy.controller.HotProductController;
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
            ERPInfo info = CommonUtils.encryptInfo(erpInfo);

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
            if (erpHandler.eventSupported(InventoryEvent.class)) {
                InventoryEvent inventoryEvent = new InventoryEvent();
                inventoryEvent.setErpInfo(erpInfo);
                EventResult eventResult = erpHandler.handleEvent(inventoryEvent, null);
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
