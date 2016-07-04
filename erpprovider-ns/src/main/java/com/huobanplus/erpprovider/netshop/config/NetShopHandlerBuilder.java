/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.config;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.netshop.bean.NSSysData;
import com.huobanplus.erpprovider.netshop.exceptionhandler.NSExceptionHandler;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <b>类描述：具体处理事件<b/>
 */
@Component
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;
    @Autowired
    private NSOrderHandler nsOrderHandler;
    @Autowired
    private NSProductHandler productHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (info.getErpType() == ERPTypeEnum.ProviderType.NETSHOP) {
            return new ERPHandler() {

                @Override
                public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {

                    if (baseEventClass == DeliveryInfoEvent.class) {
                        return true;
                    } else if (baseEventClass == ObtainOrderListEvent.class) {
                        return true;
                    } else if (baseEventClass == ObtainOrderDetailEvent.class) {
                        return true;
                    } else if (baseEventClass == ObtainGoodListEvent.class) {
                        return true;
                    } else if (baseEventClass == InventoryEvent.class) {
                        return true;
                    }
                    return false;
                }

                @Override
                public EventResult handleEvent(ERPBaseEvent erpBaseEvent) {
                    return null;
                }

                @Override
                public EventResult handleRequest(HttpServletRequest request, ERPTypeEnum.ProviderType providerType, ERPTypeEnum.UserType erpUserType) {
                    String method = request.getParameter("mType");
                    try {
                        String requestSign = request.getParameter("sign");
                        if (StringUtils.isEmpty(requestSign)) {
                            return NSExceptionHandler.handleException(method, EventResultEnum.NO_SIGN, "签名参数未传");
                        }
                        //签名验证
                        Map<String, String[]> paramMap = request.getParameterMap();
                        Map<String, Object> signMap = new TreeMap<>();
                        paramMap.forEach((key, value) -> {
                            if (!"sign".equals(key.toLowerCase())) {
                                if (value != null && value.length > 0)
                                    signMap.put(key, value[0]);
                            }
                        });
                        //通过uCode得到指定erp配置信息
                        List<ERPSysDataInfo> sysDataInfos = sysDataInfoService.findByErpTypeAndErpUserType(providerType, erpUserType);
                        ERPDetailConfigEntity erpDetailConfig = detailConfigService.findBySysData(sysDataInfos, providerType, erpUserType);
                        NSSysData nsSysData = JSON.parseObject(erpDetailConfig.getErpSysData(), NSSysData.class);
                        String secretKey = nsSysData.getSecret();
                        String sign;
                        try {
                            sign = SignBuilder.buildSignIgnoreEmpty(signMap, secretKey, secretKey);
                        } catch (UnsupportedEncodingException e) {
                            return NSExceptionHandler.handleException(method, EventResultEnum.ERROR, e.getMessage());
                        }
                        if (sign.equals(requestSign)) {
                            //开始处理
                            //得到erpUserInfo
                            ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, erpDetailConfig.getCustomerId());
                            switch (method) {
                                case Constant.OBTAIN_ORDER_LIST:
                                    int orderStatus = Integer.parseInt(request.getParameter("OrderStatus"));
                                    int pageSize = Integer.parseInt(request.getParameter("PageSize"));
                                    Integer pageIndex = null;
                                    String pageIndexStr = request.getParameter("Page");
                                    String startUpdateTime = request.getParameter("Start_Modified");
                                    String endUpdateTime = request.getParameter("End_Modified");
                                    if (!StringUtils.isEmpty(pageIndexStr)) {
                                        pageIndex = Integer.valueOf(pageIndexStr);
                                    }
                                    return nsOrderHandler.obtainOrderInfoList(orderStatus, pageSize, pageIndex, erpUserInfo, method,startUpdateTime,endUpdateTime);
                                case Constant.OBTAIN_ORDER_DETAIL:
                                    String orderId = request.getParameter("OrderNO");
                                    if (StringUtils.isEmpty(orderId)) {
                                        return NSExceptionHandler.handleException(method, EventResultEnum.BAD_REQUEST_PARAM, "OrderNO未传");
                                    }
                                    return nsOrderHandler.obtainOrderInfo(orderId, erpUserInfo, method);
                                case Constant.DELIVER_INFO:
                                    String deliverOrderId = request.getParameter("OrderNO");
                                    String logiName = request.getParameter("SndStyle");
                                    String logiNo = request.getParameter("BillID");
                                    if (StringUtils.isEmpty(deliverOrderId)) {
                                        return NSExceptionHandler.handleException(method, EventResultEnum.BAD_REQUEST_PARAM, "OrderNO未传");
                                    }
                                    return nsOrderHandler.deliverOrder(deliverOrderId, logiName, logiNo, erpUserInfo, method);
                                case Constant.OBTAIN_GOOD_LIST:
                                    String goodsType = request.getParameter("GoodsType");
                                    String goodBn = request.getParameter("OuterID");
                                    if (StringUtils.isEmpty(goodBn)) {
                                        return NSExceptionHandler.handleException(method, EventResultEnum.BAD_REQUEST_PARAM, "OuterID未传");
                                    }
                                    String goodsName = request.getParameter("GoodsName");
                                    int goodPageSize = Integer.parseInt(request.getParameter("PageSize"));
                                    Integer goodPageIndex = null;
                                    String goodPageIndexStr = request.getParameter("Page");
                                    if (!StringUtils.isEmpty(goodPageIndexStr)) {
                                        goodPageIndex = Integer.valueOf(goodPageIndex);
                                    }
                                    return productHandler.obtainGoods(goodsType, goodBn, goodsName, goodPageSize, goodPageIndex, erpUserInfo, method);
                                case Constant.SYNC_INVENTORY:
                                    String syncGoodBn = request.getParameter("ItemID");
                                    String syncProBn = request.getParameter("SkuID");
                                    int stock = Integer.parseInt(request.getParameter("Quantity"));
                                    return productHandler.syncInventory(syncGoodBn, syncProBn, stock, erpUserInfo, method);
                            }
                            return NSExceptionHandler.handleException(method, EventResultEnum.NO_DATA, "未找到数据源信息");
                        } else {
                            return NSExceptionHandler.handleException(method, EventResultEnum.WRONG_SIGN, "签名错误");
                        }
                    } catch (Exception ex) {
                        return NSExceptionHandler.handleException(method, EventResultEnum.ERROR, "服务器错误--" + ex.getMessage());
                    }
                }
            };
        }
        return null;
    }
}
