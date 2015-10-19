package com.huobanplus.erpservice.proxy.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.datacenter.service.MallOutStoreService;
import com.huobanplus.erpservice.datacenter.service.MallProductOutService;
import com.huobanplus.erpservice.eventhandler.erpevent.AddOutStoreEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.ConfirmOutStoreEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.OutStoreWriteBackEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.proxy.common.CommonUtils;
import com.huobanplus.erpservice.proxy.common.HotBaseController;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.proxy.controller.HotStorageController;
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
 * Created by allan on 2015/8/7.
 */
@Controller
@RequestMapping("/hotClientStorageApi")
public class HotStorageControllerImpl extends HotBaseController implements HotStorageController {
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private MallOutStoreService outStoreService;
    @Autowired
    private MallProductOutService productOutService;

    @Override
    @RequestMapping(value = "/outStoreAdd", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult outStoreAdd(String outStoreJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = CommonUtils.encryptInfo(erpInfo);

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }

            if (erpHandler.eventSupported(AddOutStoreEvent.class)) {
                AddOutStoreEvent outStoreEvent = new AddOutStoreEvent();
                outStoreEvent.setErpInfo(info);
                MallOutStoreBean outStoreBean = new ObjectMapper().readValue(outStoreJson, MallOutStoreBean.class);
                EventResult eventResult = erpHandler.handleEvent(outStoreEvent, outStoreBean);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                    //本地数据更新
                    outStoreService.save(outStoreBean);

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
    @RequestMapping(value = "/outStoreConfirm", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult outStoreConfirm(String outStoreJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = CommonUtils.encryptInfo(erpInfo);

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }

            if (erpHandler.eventSupported(ConfirmOutStoreEvent.class)) {
                ConfirmOutStoreEvent confirmOutStoreEvent = new ConfirmOutStoreEvent();
                confirmOutStoreEvent.setErpInfo(info);
                MallOutStoreBean outStoreBean = new ObjectMapper().readValue(outStoreJson, MallOutStoreBean.class);

                EventResult eventResult = erpHandler.handleEvent(confirmOutStoreEvent, outStoreBean);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                    //本地数据更新
                    MallOutStoreBean preBean = outStoreService.findByNo(outStoreBean.getStorageNo());
                    preBean.setFreight(outStoreBean.getFreight());
                    preBean.setFreightAvgWay(outStoreBean.getFreightAvgWay());
                    outStoreService.save(preBean);

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
    @RequestMapping(value = "/outStoreWriteBack", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult outStoreWriteBack(String proOutJson, ERPInfo erpInfo, String sign) {
        try {
            ERPInfo info = CommonUtils.encryptInfo(erpInfo);

            ERPHandler erpHandler = erpRegister.getERPHandler(info);
            if (erpHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }

            if (erpHandler.eventSupported(ConfirmOutStoreEvent.class)) {
                OutStoreWriteBackEvent outStoreWriteBackEvent = new OutStoreWriteBackEvent();
                outStoreWriteBackEvent.setErpInfo(info);
                MallProductOutBean productOutBean = new ObjectMapper().readValue(proOutJson, MallProductOutBean.class);
                EventResult eventResult = erpHandler.handleEvent(outStoreWriteBackEvent, productOutBean);
                if (eventResult.getResultCode() == ResultCode.SUCCESS.getResultCode()) {
                    //更新本地数据
                    MallProductOutBean preBean = productOutService.findById(productOutBean.getProductOutId());
                    preBean.setOutStorageNum(productOutBean.getOutStorageNum());
                    productOutService.save(preBean);

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
