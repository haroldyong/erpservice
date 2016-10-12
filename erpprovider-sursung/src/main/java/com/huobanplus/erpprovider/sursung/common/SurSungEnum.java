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
        WAIT_BUYER_PAY,//等待买家付款
        WAIT_SELLER_SEND_GOODS,//等待卖家发货
        WAIT_BUYER_CONFIRM_GOODS,//等待买家确认收货
        TRADE_FINISHED,//交易成功
        TRADE_CLOSED,//付款后交易关闭
        TRADE_CLOSED_BY_TAOBAO//付款前交易关闭
    }

}
