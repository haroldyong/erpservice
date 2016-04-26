/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <b>类描述：<b/>ERP事件父类，携带epr相关信息
 * Created by allan on 2015/7/13.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ERPBaseEvent {
    private ERPInfo erpInfo;
    private ERPUserInfo erpUserInfo;

    @Override
    public String toString() {
        return "ERPBaseEvent{" +
                "erpInfo=" + erpInfo +
                ", erpUserInfo=" + erpUserInfo +
                '}';
    }
}
