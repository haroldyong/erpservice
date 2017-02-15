/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.Data;

/**
 * Created by wuxiongliu on 2017-02-08.
 * 推送商家订单备注事件
 */
@Data
public class PushRemarkEvent extends ERPBaseEvent {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 商家备注
     */
    private String remark;
}
