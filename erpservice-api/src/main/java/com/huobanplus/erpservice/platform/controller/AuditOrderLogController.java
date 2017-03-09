/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.platform.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpservice.common.SysConstant;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushAuditedOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by wuxiongliu on 2017-03-08.
 */
@Controller
@RequestMapping("/erpService/platform")
public class AuditOrderLogController {

    @Autowired
    private AuditedOrderSyncLogService auditedOrderSyncLogService;
    @Autowired
    private ERPRegister erpRegister;

    @RequestMapping(value = "/auditedOrderSyncs", method = RequestMethod.GET)
    private String orderShipSyncs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            String beginTime, String endTime,
            @RequestAttribute int customerId,
            int erpUserType,
            Model model
    ) {
        Page<AuditedOrderSyncLog> auditedOrderSyncLogs = auditedOrderSyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, beginTime, endTime, customerId);
        model.addAttribute("auditedOrderSyncLogs", auditedOrderSyncLogs);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("beginTime", beginTime);
        model.addAttribute("endTime", endTime);

        return "logs/audited_order_sync_list";
    }

    @RequestMapping(value = "/auditedOrderSyncInfoList", method = RequestMethod.GET)
    private String shipSyncFailureOrders(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            String orderId, long syncId,
            int erpUserType,
            Model model
    ) {

        AuditedOrderSyncLog auditedOrderSyncLog = auditedOrderSyncLogService.findOne(syncId);
        List<String> orderIds = JSON.parseArray(auditedOrderSyncLog.getOrderJson(), String.class);
        model.addAttribute("orderIds", orderIds);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("syncId", syncId);

        return "logs/audited_order_sync_infos";
    }

    @RequestMapping(value = "/reSyncAuditedOrder")
    @ResponseBody
    private ApiResult resyncAuditedOrder(long id) {
        AuditedOrderSyncLog auditedOrderSyncLog = auditedOrderSyncLogService.findOne(id);
        if (auditedOrderSyncLog == null) {
            return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST, "未找到该同步日志");
        }

        List<String> orderIds = JSON.parseArray(auditedOrderSyncLog.getOrderJson(), String.class);
        ERPUserInfo erpUserInfo = new ERPUserInfo(auditedOrderSyncLog.getUserType(), auditedOrderSyncLog.getCustomerId());
        ERPInfo erpInfo = new ERPInfo(auditedOrderSyncLog.getProviderType(), auditedOrderSyncLog.getErpSysData());
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

        PushAuditedOrderEvent pushAuditedOrderEvent = new PushAuditedOrderEvent();
        pushAuditedOrderEvent.setOrderIds(orderIds);
        pushAuditedOrderEvent.setErpUserInfo(erpUserInfo);
        pushAuditedOrderEvent.setErpInfo(erpInfo);

        EventResult eventResult = erpUserHandler.handleEvent(pushAuditedOrderEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            auditedOrderSyncLog.setAuditedSyncStatus(OrderSyncStatus.AuditedSyncStatus.SYNC_SUCCESS);
            auditedOrderSyncLogService.save(auditedOrderSyncLog);
            return ApiResult.resultWith(ResultCode.SUCCESS);
        } else {
            return ApiResult.resultWith(ResultCode.ERP_BAD_REQUEST);
        }
    }
}
