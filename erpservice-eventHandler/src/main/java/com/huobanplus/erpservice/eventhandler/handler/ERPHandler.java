/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.handler;

import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.FailedBean;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import java.io.IOException;

/**
 * 事件处理器
 * <p>由epr-provider具体实现</p>
 * Created by allan on 2015/7/13.
 */
public interface ERPHandler {
    /**
     * 是否支持该事件
     *
     * @param baseEventClass 某事件
     * @return true表示支持
     */
    boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass);

    /**
     * 处理事件
     * <p>ERP-Provider会具体执行一个事件的处理，比如访问第三方API或者保存到数据。</p>
     * <p>所以结果可能不是立刻可以获取的，但应该可以检测它的运行情况。</p>
     *
     * @param erpBaseEvent 处理该事件
     * @return 事件处理结果
     * @throws IOException            网络操作异常
     * @throws IllegalAccessException 接口访问不合法异常
     */
    EventResult handleEvent(ERPBaseEvent erpBaseEvent, Object data) throws IOException, IllegalAccessException, IllegalArgumentException;

    /**
     * 处理异常信息
     *
     * @param baseEventClass 事件类
     * @param failedBean     异常信息实体
     * @return
     */
    EventResult handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean);
}
