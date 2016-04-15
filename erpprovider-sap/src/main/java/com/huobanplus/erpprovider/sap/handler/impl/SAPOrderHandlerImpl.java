package com.huobanplus.erpprovider.sap.handler.impl;

import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

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
    public EventResult pushOrder(Order orderInfo, SAPSysData sysData, ERPUserInfo erpUserInfo) {

        //信息重新封装




        return null;
    }




}
