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

import com.huobanplus.erpservice.common.SysConstant;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncInfo;
import com.huobanplus.erpservice.datacenter.entity.logs.AuditedOrderSyncLog;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncInfoService;
import com.huobanplus.erpservice.datacenter.service.logs.AuditedOrderSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by wuxiongliu on 2017-03-08.
 */
@Controller
@RequestMapping("/erpService/platform")
public class AuditOrderLogController {

    @Autowired
    private AuditedOrderSyncLogService auditedOrderSyncLogService;
    @Autowired
    private AuditedOrderSyncInfoService auditedOrderSyncInfoService;

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

        Page<AuditedOrderSyncInfo> auditedOrderSyncInfos = auditedOrderSyncInfoService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, syncId, orderId);
        model.addAttribute("auditedOrderSyncInfoList", auditedOrderSyncInfos);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("syncId", syncId);

        return "logs/audited_order_sync_infos";
    }
}
