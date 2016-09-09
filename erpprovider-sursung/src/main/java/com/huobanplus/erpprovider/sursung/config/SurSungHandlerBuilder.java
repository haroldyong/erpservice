/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.common.SurSungConstant;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungInventory;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungLogistic;
import com.huobanplus.erpprovider.sursung.handler.SurSungOrderHandler;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class SurSungHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;
    @Autowired
    private ERPDetailConfigService detailConfigService;

    private static final Log log = LogFactory.getLog(SurSungHandlerBuilder.class);

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.SURSUNG) {
            return new ERPHandler() {

                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    if (erpBaseEvent instanceof PushNewOrderEvent) {
                        PushNewOrderEvent pushNewOrderEvent = (PushNewOrderEvent) erpBaseEvent;
                        return surSungOrderHandler.pushOrder(pushNewOrderEvent);
                    }
                    return null;
                }

                @Override
                public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                    log.info("************SurSung**********");
                    List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserType(providerType, erpUserType);
                    ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                    SurSungSysData surSungSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), SurSungSysData.class);

                    String method = request.getParameter("method");
                    String partnerId = request.getParameter("partnerid");
                    String token = request.getParameter("token");
                    int ts = Integer.valueOf(request.getParameter("ts"));
                    String sign = request.getParameter("sign");

                    ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                    ERPInfo erpInfo = new ERPInfo(erpDetailConfig.getErpType(), erpDetailConfig.getErpSysData());

                    try {
                        InputStream in = request.getInputStream();
                        String postBody = StreamUtils.copyToString(in, Charset.forName("utf-8"));
//                        String buildSign = SurSungUtil.buildSign(method, ts, partnerId, token, "erp");

                        switch (method) {
                            case SurSungConstant.LOGISTIC_UPLOAD:
                                SurSungLogistic surSungLogistic = JSON.parseObject(postBody, SurSungLogistic.class);
                                return surSungOrderHandler.logisticUpload(surSungLogistic, erpUserInfo, erpInfo);
                            case SurSungConstant.INVENTORY_UPLOAD:
                                List<SurSungInventory> surSungInventoryList = JSON.parseArray(postBody, SurSungInventory.class);
                                return surSungOrderHandler.inventoryUpload(surSungInventoryList, erpUserInfo, erpInfo);
                        }

                    } catch (Exception e) {
                        log.info("error:" + e.getMessage());
                        return EventResult.resultWith(EventResultEnum.ERROR, "{\"code\":-1,\"msg\":" + e.getMessage() + " }");
                    }
                    return null;
                }
            };
        }
        return null;
    }
}
