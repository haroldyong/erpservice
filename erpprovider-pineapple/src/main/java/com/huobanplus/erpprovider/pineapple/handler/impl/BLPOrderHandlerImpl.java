package com.huobanplus.erpprovider.pineapple.handler.impl;

import com.huobanplus.erpprovider.pineapple.handler.BLPOrderHandler;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hxh on 2017-06-07.
 */
public class BLPOrderHandlerImpl implements BLPOrderHandler{
    @Autowired
    private ERPRegister erpRegister;


    @Override
    public EventResult obtainOrderInfoList(int orderStatus, int pageSize, Integer pageIndex, String startTime, ERPUserInfo erpUserInfo, String endTime) {
        return null;
    }
}
