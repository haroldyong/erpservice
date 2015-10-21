/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;

import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import lombok.Data;

/**
 * erp 返回结果
 * Created by allan on 2015/7/13.
 */
@Data
public class EventResult {
    private int resultCode;
    private String resultMsg;
    private Object data;

    public static EventResult resultWith(int resultCode, String resultMsg, Object data) {
        EventResult eventResult = new EventResult();
        eventResult.resultCode = resultCode;
        eventResult.resultMsg = resultMsg;
        eventResult.data = data;
        return eventResult;
    }

    public static EventResult resultWith(EventResultEnum resultEnum) {
        EventResult eventResult = new EventResult();
        eventResult.resultCode = resultEnum.getResultCode();
        eventResult.resultMsg = resultEnum.getResultMsg();
        return eventResult;
    }

    public static EventResult resultWith(EventResultEnum resultEnum, Object data) {
        EventResult eventResult = new EventResult();
        eventResult.resultCode = resultEnum.getResultCode();
        eventResult.resultMsg = resultEnum.getResultMsg();
        eventResult.data = data;
        return eventResult;
    }

    public static EventResult resultWith(EventResultEnum resultEnum, String resultMsg, Object data) {
        EventResult eventResult = new EventResult();
        eventResult.resultCode = resultEnum.getResultCode();
        eventResult.resultMsg = resultMsg;
        eventResult.data = data;
        return eventResult;
    }
}
