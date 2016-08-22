/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.handler;

import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwWayBill;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface DtwOrderHandler {

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent 订单推送事件
     * @return 请求响应结果
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

    /**
     *  推送平台订单
     * @param order
     * @param dtwSysData
     * @return
     */
    EventResult pushPlatformOrder(Order order, DtwSysData dtwSysData);

    /**
     * @param order
     * @param dtwSysData
     * @return
     */
    EventResult pushPersonalDeclareOrder(Order order, DtwSysData dtwSysData);

    /**
     * 推送支付宝支付单到海关
     *
     * @return
     */
    EventResult pushAliPayOrder(Order order, DtwSysData dtwSysData);

    /**
     * 推送微信支付单到海关
     *
     * @return
     */
    EventResult pushWeixinPayOrder(Order order, DtwSysData dtwSysData);

    /**
     * 推送订单到海关
     *
     * @param order      订单实体
     * @param dtwSysData 系统参数
     * @return
     */
    EventResult pushCustomOrder(Order order, DtwSysData dtwSysData);

//    /**
//     *  个人物品信息申报推送
//     * @param dtwPersonalDelcareInfo
//     * @param dtwSysData
//     * @return
//     */
//    EventResult pushPersonalDeclareOrder(DtwPersonalDelcareInfo dtwPersonalDelcareInfo, DtwSysData dtwSysData);

    /**
     * 发货单推送
     *
     * @param Msgid       电商企业发货订单号\销售订单号
     * @param wayBill     运单号
     * @param weight      毛重
     * @param state       状态
     * @param erpUserInfo erp使用这信息
     * @return 请求响应结果
     */
    EventResult deliverOrder(String Msgid, String wayBill, String weight, String state, ERPUserInfo erpUserInfo);

    /**
     * 商品库存查询
     *
     * @param dtwStockSearch 库存查询实体
     * @param dtwSysData     大田系统参数
     * @return 请求响应结果
     */
    EventResult stockQuery(DtwStockSearch dtwStockSearch, DtwSysData dtwSysData);

    /**
     * 顾客面单 (使用综合订单模式则不使用此接口)
     *
     * @param dtwWayBill 顾客面单实体
     * @param dtwSysData 大田系统参数
     * @return 请求响应结果
     */
    EventResult wayBill(DtwWayBill dtwWayBill, DtwSysData dtwSysData);
}
