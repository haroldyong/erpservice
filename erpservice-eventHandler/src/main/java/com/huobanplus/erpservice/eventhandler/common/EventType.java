/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by allan on 4/15/16.
 */
public enum EventType implements ICommonEnum {
    PUSH_NEW_ORDER(0, "推送新订单"),
    CANCEL_ORDER(1, "取消订单"),
    RETURN(2, "退货推送");

    private int code;
    private String name;

    EventType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
