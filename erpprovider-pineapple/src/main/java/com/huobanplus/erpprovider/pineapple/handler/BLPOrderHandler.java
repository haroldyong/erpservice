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
     * @param platOrderNo 订单编号（不为空则查询单个订单的数据）
     * @param orderStatus 订单状态
     * @param pageSize 每页数据大小
     * @param pageIndex 页码
     * @param startTime 开始时间
     * @param method 方法名
     * @param erpUserInfo 使用者信息
     * @param endTime 截止时间
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

    /**
     *订单发货
     *
     * @param orderId 平台订单号
     * @param logiName 快递名称
     * @param logiNo 快递运单号
     * @param erpUserInfo erp使用者信息
     * @param method 方法名
     * @return
     */
    EventResult deliverOrder(String orderId, String logiName, String logiNo, ERPUserInfo erpUserInfo, String method);
}
