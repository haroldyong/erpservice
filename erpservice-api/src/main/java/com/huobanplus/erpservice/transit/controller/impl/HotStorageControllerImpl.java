package com.huobanplus.erpservice.transit.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.event.erpevent.AddOutStoreEvent;
import com.huobanplus.erpservice.event.erpevent.ConfirmOutStoreEvent;
import com.huobanplus.erpservice.event.erpevent.InventoryEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.transit.Common.HotBaseController;
import com.huobanplus.erpservice.transit.Common.ResultCode;
import com.huobanplus.erpservice.transit.bean.ApiResult;
import com.huobanplus.erpservice.transit.controller.HotStorageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/8/7.
 */
@Controller
@RequestMapping("/hotClientStorageApi")
public class HotStorageControllerImpl extends HotBaseController implements HotStorageController {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public ApiResult outStoreAdd(String outStoreJson, ERPInfo erpInfo, String sign) {
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
            signMap.put("outStoreJson", outStoreJson);
            String checkSign = buildSign(signMap, null, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }

            if (erpHandler.eventSupported(AddOutStoreEvent.class)) {
                AddOutStoreEvent outStoreEvent = new AddOutStoreEvent();
                outStoreEvent.setErpInfo(erpInfo);
                MallOutStoreBean outStoreBean = new ObjectMapper().readValue(outStoreJson, MallOutStoreBean.class);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(outStoreEvent, outStoreBean);
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
    public ApiResult outStoreConfirm(String outStoreJson, ERPInfo erpInfo, String sign) {
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
            signMap.put("outStoreJson", outStoreJson);
            String checkSign = buildSign(signMap, null, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }

            if (erpHandler.eventSupported(ConfirmOutStoreEvent.class)) {
                ConfirmOutStoreEvent confirmOutStoreEvent = new ConfirmOutStoreEvent();
                confirmOutStoreEvent.setErpInfo(erpInfo);
                MallOutStoreBean outStoreBean = new ObjectMapper().readValue(outStoreJson, MallOutStoreBean.class);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(confirmOutStoreEvent, outStoreBean);
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
    public ApiResult outStoreWriteBack(String proOutJson, ERPInfo erpInfo, String sign) {
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
            signMap.put("outStoreJson", proOutJson);
            String checkSign = buildSign(signMap, null, null);

            if (!sign.equals(checkSign)) {
                return new ApiResult(ResultCode.WRONG_SIGN_CODE.getKey(), null, ResultCode.WRONG_SIGN_CODE.getValue());
            }

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return new ApiResult(ResultCode.NO_SUCH_ERPHANDLER.getKey(), null, ResultCode.NO_SUCH_ERPHANDLER.getValue());
            }

            if (erpHandler.eventSupported(ConfirmOutStoreEvent.class)) {
                ConfirmOutStoreEvent confirmOutStoreEvent = new ConfirmOutStoreEvent();
                confirmOutStoreEvent.setErpInfo(erpInfo);
                MallOutStoreBean outStoreBean = new ObjectMapper().readValue(proOutJson, MallOutStoreBean.class);
                Monitor<EventResult> eventResultMonitor = erpHandler.handleEvent(confirmOutStoreEvent, outStoreBean);
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
