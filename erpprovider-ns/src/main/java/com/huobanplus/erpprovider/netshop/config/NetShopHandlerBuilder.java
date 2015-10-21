/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.config;

import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.*;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.eventhandler.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * <b>类描述：具体处理事件<b/>
 */
@Component
public class NetShopHandlerBuilder implements ERPHandlerBuilder {
    @Autowired
    private NSOrderHandler nsOrderHandler;
    @Autowired
    private NSProductHandler productHandler;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if (!"netShop".equals(info.getName())) {
            //不是网店管家
            return null;
        }
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
            public EventResult handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException, IllegalArgumentException {
                HttpServletRequest request = (HttpServletRequest) data;

                if (erpBaseEvent instanceof DeliveryInfoEvent) {
                    return nsOrderHandler.deliverOrder(request);
                } else if (erpBaseEvent instanceof ObtainOrderDetailEvent) {
                    return nsOrderHandler.obtainOrderInfo(request);
                } else if (erpBaseEvent instanceof ObtainOrderListEvent) {
                    return nsOrderHandler.obtainOrderInfoList(request);
                } else if (erpBaseEvent instanceof ObtainGoodListEvent) {
                    return productHandler.obtainGoods(request);
                } else if (erpBaseEvent instanceof InventoryEvent) {
                    return productHandler.syncInventory(request);
                }
                return null;
            }

            @Override
            public EventResult handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {
//
                if (baseEventClass == DeliveryInfoEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                } else if (baseEventClass == ObtainOrderListEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>");
                } else if (baseEventClass == ObtainOrderDetailEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Order>");
                } else if (baseEventClass == ObtainGoodListEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                } else if (baseEventClass == InventoryEvent.class) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>" + failedBean.getFailedMsg() + "</Cause></Rsp>");
                }
                return null;
            }

            @Override
            public EventResult handleRequest(HttpServletRequest request) throws IOException {
                String requestSign = request.getParameter("sign");
                if (StringUtils.isEmpty(requestSign)) {
                    return EventResult.resultWith(EventResultEnum.NO_SIGN);
                }
                //签名验证
                Map<String, String[]> paramMap = request.getParameterMap();
                Map<String, String> signMap = new TreeMap<>();
                paramMap.forEach((key, value) -> {
                    if (!"sign".equals(key.toLowerCase())) {
                        if (value != null && value.length > 0)
                            signMap.put(key, value[0]);
                    }
                });
                //调用伙伴商城api得到secretKey
                String secretKey = "";
                String sign = SignBuilder.buildSign(signMap, secretKey, secretKey);
                if (sign.equals(requestSign)) {
                    //开始处理
                    String method = request.getParameter("mType");
                    switch (method) {
                        case Constant.OBTAIN_ORDER_LIST:
                            nsOrderHandler.obtainOrderInfoList(request);
                            break;
                        case Constant.OBTAIN_ORDER_DETAIL:
                            nsOrderHandler.obtainOrderInfo(request);
                            break;
                        case Constant.DELIVER_INFO:
                            nsOrderHandler.deliverOrder(request);
                            break;
                        case Constant.OBTAIN_GOOD_LIST:
                            productHandler.obtainGoods(request);
                            break;
                        case Constant.SYNC_INVENTORY:
                            productHandler.syncInventory(request);
                            break;
                    }
                    return null;
                } else {
                    return EventResult.resultWith(EventResultEnum.WRONG_SIGN);
                }
            }

            @Override
            public EventResult handleException(EventResult eventResult) {
                return null;
            }
        };
    }
}
