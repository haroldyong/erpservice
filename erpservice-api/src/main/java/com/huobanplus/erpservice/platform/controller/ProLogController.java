/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.huobanplus.erpservice.common.SysConstant;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.datacenter.entity.logs.InventorySyncLog;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncDetailService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by allan on 8/11/16.
 */
@Controller
@RequestMapping("/erpService/platform")
public class ProLogController {
    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private InventorySyncDetailService inventorySyncDetailService;

    @RequestMapping(value = "/inventorySyncLogs", method = RequestMethod.GET)
    private String inventorySyncLogs(
            @RequestParam(required = false, defaultValue = "1") int pageIndex,
            @ModelAttribute(value = "beginTime") String beginTime,
            @ModelAttribute(value = "endTime") String endTime,
            @RequestAttribute int customerId,
            int erpUserType,
            Model model
    ) {
        Page<InventorySyncLog> inventorySyncLogs = inventorySyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, beginTime, endTime, customerId);
        model.addAttribute("inventorySyncLogs", inventorySyncLogs);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);

        return "logs/inventory_sync_log";
    }
}
