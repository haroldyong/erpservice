package com.huobanplus.erpprovider.pineapple.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-06-07.
 */
public interface BLPOrderHandler {

    /**
     * 获取订单列表
     * @param orderStatus
     * @param pageSize
     * @param pageIndex
     * @param startTime
     * @param erpUserInfo
     * @param endTime
     * @return
     */
    EventResult obtainOrderInfoList(int orderStatus, int pageSize, Integer pageIndex, String startTime,ERPUserInfo erpUserInfo, String endTime);

}
