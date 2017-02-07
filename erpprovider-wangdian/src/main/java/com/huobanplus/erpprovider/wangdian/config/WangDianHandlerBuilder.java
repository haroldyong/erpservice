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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class WangDianHandlerBuilder implements ERPHandlerBuilder {

    private static final Log log = LogFactory.getLog(WangDianHandlerBuilder.class);

    @Autowired
    private WangDianOrderHandler wangDianOrderHandler;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;
    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.WANGDIAN) {
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

                    log.info("wangdian delivery content:" + content);
                    try {

                        List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserTypeAndParamNameAndParamVal(providerType, erpUserType, "sellerId", sellerID);
                        if (sysDataInfos.size() == 0) {
                            JSONObject respJson = new JSONObject();
                            respJson.put("ResultCode", 1);
                            respJson.put("ResultMsg", "未找到该商户的相关配置");

                            JSONObject response = new JSONObject();
                            response.put("ResultList", respJson);
                            return EventResult.resultWith(EventResultEnum.ERROR, response);
                        }
                        ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                        WangDianSysData wangDianSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), WangDianSysData.class);

                        ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                        ERPInfo erpInfo = new ERPInfo(erpDetailConfig.getErpType(), erpDetailConfig.getErpSysData());

                        // 签名验证

                        String sign = WangDianSignUtil.buildSign(content, wangDianSysData.getAppKey());
                        if (sign.equals(requestSign)) {

                            switch (method) {
                                case Constant.LOGISTICS_RETURN:
                                    JSONObject jsonObject = JSON.parseObject(content);
                                    JSONObject tradeList = jsonObject.getJSONObject("TradeList");
                                    String jsonArrayStr = tradeList.getString("Trade");

                                    List<WangDianLogistic> wangDianLogistics = JSON.parseArray(jsonArrayStr, WangDianLogistic.class);
                                    return wangDianOrderHandler.deliverOrder(wangDianLogistics, erpUserInfo, erpInfo);
                            }

                        } else {
                            JSONObject respJson = new JSONObject();
                            respJson.put("ResultCode", 1);
                            respJson.put("ResultMsg", "签名错误");

                            JSONObject response = new JSONObject();
                            response.put("ResultList", respJson);

                            return EventResult.resultWith(EventResultEnum.ERROR, response);
                        }
                    } catch (Exception e) {
                        JSONObject respJson = new JSONObject();
                        respJson.put("ResultCode", 1);
                        respJson.put("ResultMsg", e.getMessage());

                        JSONObject response = new JSONObject();
                        response.put("ResultList", respJson);

                        return EventResult.resultWith(EventResultEnum.ERROR, response);
                    }
                    return null;
                }
            };
        }
        return null;
    }


}
