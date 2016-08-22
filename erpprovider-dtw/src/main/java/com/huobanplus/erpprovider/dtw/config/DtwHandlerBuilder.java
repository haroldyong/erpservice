/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class DtwHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private DtwOrderHandler dtwOrderHandler;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;
    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        return new ERPHandler() {
            @Override
            public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                if (erpBaseEvent instanceof PushNewOrderEvent) {
                    PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                    return dtwOrderHandler.pushOrder(pushNewOrderEvent);
                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                String method = request.getParameter("method");
                try {
                    String requestSign = request.getParameter("sign");
                    if (StringUtils.isEmpty(requestSign)) {
                        return EventResult.resultWith(EventResultEnum.NO_SIGN, "签名参数未传", null);
                    }
                    // 签名验证
                    Map<String, String[]> paramMap = request.getParameterMap();
                    Map<String, Object> signMap = new TreeMap<>();
                    paramMap.forEach((key, value) -> {
                        if (!"sign".equals(key.toLowerCase())) {
                            if (value != null && value.length > 0)
                                signMap.put(key, value[0]);
                        }
                    });
                    List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserType(providerType, erpUserType);
                    ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                    DtwSysData dtwSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), DtwSysData.class);
                    String passKey = dtwSysData.getPassKey();
                    String sign = null;
                    try {
                        sign = SignBuilder.buildSignIgnoreEmpty(signMap, passKey, passKey);
                    } catch (UnsupportedEncodingException e) {
                        return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
                    }
                    if (sign.toUpperCase().equals(requestSign)) {
                        ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                        if (method.equals("sendDeliver")) {
                            String msgId = request.getParameter("Msgid");
                            String wayBill = request.getParameter("wayBill");
                            String weight = request.getParameter("Weight");
                            String state = request.getParameter("State");
                            return dtwOrderHandler.deliverOrder(msgId, wayBill, weight, state, erpUserInfo);
                        }
                    }

                } catch (Exception ex) {
                    return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
                }
                return null;
            }
        };
    }
}
