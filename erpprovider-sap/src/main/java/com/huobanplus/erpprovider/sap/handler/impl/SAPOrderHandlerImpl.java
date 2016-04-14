package com.huobanplus.erpprovider.sap.handler.impl;

import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by liuzheng on 2016/4/14.
 */
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
