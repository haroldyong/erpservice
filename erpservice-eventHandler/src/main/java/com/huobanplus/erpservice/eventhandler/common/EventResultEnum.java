package com.huobanplus.erpservice.eventhandler.common;

/**
 * Created by liual on 2015-10-15.
 */
public enum EventResultEnum {
    SUCCESS(2000, "处理成功"),
    ERROR(5000, "处理失败"),
    NO_DATA(3000, "未找到相关处理信息");

    private int resultCode;
    private String resultMsg;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    EventResultEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
