/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdian.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.wangdian.common.Constant;
import com.huobanplus.erpprovider.wangdian.common.WangDianSysData;
import com.huobanplus.erpprovider.wangdian.formatdata.WangDianLogistic;
import com.huobanplus.erpprovider.wangdian.handler.WangDianOrderHandler;
import com.huobanplus.erpprovider.wangdian.util.WangDianSignUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class WangDianHandlerBuilder implements ERPHandlerBuilder {

    @Autowired
    private WangDianOrderHandler wangDianOrderHandler;
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
                    return wangDianOrderHandler.pushOrder(pushNewOrderEvent);
                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {

                String method = request.getParameter("Method");
                String sellerID = request.getParameter("SellerID");
                String requestSign = request.getParameter("Sign");
                String content = request.getParameter("Content");

                List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserTypeAndParamNameAndParamVal(providerType, erpUserType, "sellerId", sellerID);
                ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                WangDianSysData wangDianSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), WangDianSysData.class);

                ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(erpDetailConfig.getErpType(), erpDetailConfig.getErpSysData());

                // 签名验证
                try {
                    String sign = WangDianSignUtil.buildSign(content, wangDianSysData.getAppKey());
                    if (sign.equals(requestSign)) {

                        switch (method) {
                            case Constant.LOGISTICS_RETURN:
                                JSONObject jsonObject = JSON.parseObject(content);
                                JSONObject tradeList = jsonObject.getJSONObject("TradeList");
                                JSONArray jsonArray = tradeList.getJSONArray("Trade");

                                List<WangDianLogistic> wangDianLogistics = new ArrayList<>();

                                jsonArray.forEach(obj -> {
                                    WangDianLogistic wangDianLogistic = JSON.parseObject(obj.toString(), WangDianLogistic.class);
                                    wangDianLogistics.add(wangDianLogistic);
                                });
                                return wangDianOrderHandler.deliverOrder(wangDianLogistics, erpUserInfo, erpInfo);
                        }

                    } else {
                        JSONObject respJson = new JSONObject();
                        respJson.put("ResultCode", 1);
                        respJson.put("ResultMsg", "签名错误");
                        return EventResult.resultWith(EventResultEnum.ERROR, respJson);
                    }
                } catch (Exception e) {

                }

                return null;
            }
        };
    }


}
