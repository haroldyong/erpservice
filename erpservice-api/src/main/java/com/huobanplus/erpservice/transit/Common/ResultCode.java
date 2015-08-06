package com.huobanplus.erpservice.transit.Common;

/**
 * Created by allan on 2015/8/5.
 */
public enum ResultCode {
    SUCCESS("1", "返回成功"),
    NO_SUCH_ERPHANDLER("60001", "没有找到支持的erp"),
    EVENT_NOT_SUPPORT("60002", "该erp不支持此事件"),
    SYSTEM_BAD_REQUEST("50000", "系统请求失败"),
    ERP_BAD_REQUEST("50001", "erp系统请求失败"),
    EMPTY_SIGN_CODE("40000", "签名参数不可为空"),
    WRONG_SIGN_CODE("40001", "签名验证不正确");
    private final String key;
    private final String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    ResultCode(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
