/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.common;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public interface SurSungEnum {

    enum OrderStatus {
        WAIT_BUYER_PAY,
        WAIT_SELLER_SEND_GOODS,
        WAIT_BUYER_CONFIRM_GOODS,
        TRADE_FINISHED,
        TRADE_CLOSED,
        TRADE_CLOSED_BY_TAOBAO
    }

}
