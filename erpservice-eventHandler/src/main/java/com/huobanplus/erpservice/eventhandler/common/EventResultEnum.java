/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.common;

/**
 * Created by liual on 2015-10-15.
 */
public enum EventResultEnum {
    SUCCESS(2000, "处理成功"),
    ERROR(5000, "处理失败"),
    NO_SIGN(4000, "签名参数未传"),
    WRONG_SIGN(4001, "签名不正确"),
    UNSUPPORT_EVENT(4002, "不支持的ERP事件"),
    BAD_REQUEST_PARAM(5001, "请求参数不正确"),
    NO_DATA(3000, "未找到相关处理信息"),
    SYSTEM_BAD_REQUEST(5002, "服务器请求失败");

    private int resultCode;
    private String resultMsg;

    EventResultEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

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
}
