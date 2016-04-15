/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler.impl;

import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.stereotype.Component;

/**
 * Created by liuzheng on 2016/4/14.
 */
@Component
public class SAPOrderHandlerImpl implements SAPOrderHandler {


    /**
     * 推送订单
     *
     * @param orderInfo 订单信息实体
     * @param sysData   SAP的系统数据
     * @return EventResult
     */
    @Override
    public EventResult pushOrder(Order orderInfo, SAPSysData sysData) {

        //信息重新封装


        return null;
    }

}
