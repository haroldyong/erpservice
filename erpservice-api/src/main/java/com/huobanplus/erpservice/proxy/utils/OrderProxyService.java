/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.utils;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 4/13/16.
 */
@Component
public class OrderProxyService {
    private static final Log log = LogFactory.getLog(OrderProxyService.class);
    @Autowired
    private ERPRegister erpRegister;

    /**
     * 处理事件
     *
     * @param erpBaseEvent ERP事件
     * @return
     */
    public ApiResult handleEvent(ERPBaseEvent erpBaseEvent) {
        ERPHandler erpHandler = erpRegister.getERPHandler(erpBaseEvent.getErpInfo());
        if (erpHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }
        //相关处理器处理时间推送订单至相应ERP系统
        EventResult eventResult = erpHandler.handleEvent(erpBaseEvent);
        ApiResult apiResult;
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            apiResult = ApiResult.resultWith(ResultCode.SUCCESS,eventResult.getData());
        } else {
            apiResult = ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, eventResult.getResultMsg(), null);
        }
        return apiResult;
    }
}
