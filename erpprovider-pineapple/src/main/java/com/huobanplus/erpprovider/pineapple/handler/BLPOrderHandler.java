package com.huobanplus.erpprovider.pineapple.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-06-07.
 */
public interface BLPOrderHandler {

    /**
     * 获取订单列表
     *
     * @param platOrderNo 不为空则查询单个订单的数据
     * @param orderStatus
     * @param pageSize
     * @param pageIndex
     * @param startTime
     * @param method
     * @param erpUserInfo
     * @param endTime
     * @return
     */
    EventResult obtainOrderInfoList(String platOrderNo,int orderStatus, int pageSize, Integer pageIndex, String startTime,String method,ERPUserInfo erpUserInfo, String endTime);

    /**
     * 退款检测
     *
     * @param OrderId
     * @param erpUserInfo
     * @param method
     * @return
     */
    EventResult refundCheck(String OrderId,ERPUserInfo erpUserInfo, String method);
}
