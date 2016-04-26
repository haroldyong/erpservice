/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by allan on 12/25/15.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PushNewOrderEvent extends ERPBaseEvent {
    private String orderInfoJson;

    @Override
    public String toString() {
        return "PushNewOrderEvent{" +
                "orderInfoJson='" + orderInfoJson + '\'' +
                '}';
    }
}
