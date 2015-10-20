/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.userhandler;

import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import java.io.IOException;

/**
 * erp使用者事件处理器
 * Created by liual on 2015-10-15.
 */
public interface ERPUserHandler {
    /**
     * 处理事件
     *
     * @param erpBaseEvent
     * @param data
     * @return
     * @throws IOException
     */
    EventResult handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException;

    /**
     * 异常处理
     *
     * @param baseEventClass
     * @param failedBean
     * @return
     */
    EventResult handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean);
}
