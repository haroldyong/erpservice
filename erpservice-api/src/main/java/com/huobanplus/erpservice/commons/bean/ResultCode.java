/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.commons.bean;

/**
 * Created by allan on 2015/8/5.
 */
public enum ResultCode {
    SUCCESS(2000, "返回成功"),
    //    ADD_TO_ROTARY_QUEUE("2", "已加入轮询队列"),
    NO_SUCH_ERPHANDLER(6001, "没有找到支持的erp"),
    EVENT_NOT_SUPPORT(6002, "该erp不支持此事件"),
    SYSTEM_BAD_REQUEST(5000, "系统请求失败"),
    ERP_BAD_REQUEST(5001, "erp系统请求失败"),
    EMPTY_SIGN_CODE(4000, "签名参数不可为空"),
    WRONG_SIGN_CODE(4001, "签名验证不正确"),
    BAD_APP_KEY_AND_TOKEN(3000, "为找到对应appKey和Token的商家信息");

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

    ResultCode(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
}
