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
    private String data;

    public static EventResult resultWith(int resultCode, String resultMsg, String data) {
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

    public static EventResult resultWith(EventResultEnum resultEnum, String data) {
        EventResult eventResult = new EventResult();
        eventResult.resultCode = resultEnum.getResultCode();
        eventResult.resultMsg = resultEnum.getResultMsg();
        eventResult.data = data;
        return eventResult;
    }
}
