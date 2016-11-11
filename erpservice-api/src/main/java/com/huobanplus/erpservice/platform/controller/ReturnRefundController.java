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
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.logs.ReturnRefundSyncLog;
import com.huobanplus.erpservice.datacenter.searchbean.ReturnRefundSearch;
import com.huobanplus.erpservice.datacenter.service.logs.ReturnRefundSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.proxy.utils.OrderProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wuxiongliu on 2016-11-11.
 */

@Controller
@RequestMapping("/erpService/platform")
public class ReturnRefundController {

    @Autowired
    private ReturnRefundSyncLogService returnRefundSyncLogService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private OrderProxyService orderProxyService;

    @RequestMapping(value = "/returnRefundSyncs")
    public String returnRefundSyncs(@RequestParam(required = false, defaultValue = "1") int pageIndex,
                                    ReturnRefundSearch returnRefundSearch,
                                    @RequestAttribute int customerId,
                                    int erpUserType,
                                    @RequestParam(required = false, defaultValue = "-1") int syncStatus,
                                    Model model) {
        returnRefundSearch.setSyncStatus(syncStatus);
        Page<ReturnRefundSyncLog> returnRefundSyncLogs = returnRefundSyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE, customerId, returnRefundSearch);
        model.addAttribute("returnRefundSyncLogs", returnRefundSyncLogs);
        model.addAttribute("returnRefundSearch", returnRefundSearch);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        return "logs/return_refund_sync_list";
    }

    @RequestMapping(value = "/rePushReturnRefund", method = RequestMethod.POST)
    @ResponseBody
    private ApiResult rePushOrder(long id) {
        ReturnRefundSyncLog returnRefundSyncLog = returnRefundSyncLogService.findById(id);
        ERPInfo erpInfo = new ERPInfo(returnRefundSyncLog.getProviderType(), returnRefundSyncLog.getErpSysData());
        ERPUserInfo erpUserInfo = new ERPUserInfo(returnRefundSyncLog.getUserType(), returnRefundSyncLog.getCustomerId());
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setErpInfo(erpInfo);
        pushNewOrderEvent.setErpUserInfo(erpUserInfo);

        ERPUserHandler userHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (userHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        GetOrderDetailEvent getOrderDetailEvent = new GetOrderDetailEvent();
        getOrderDetailEvent.setErpUserInfo(erpUserInfo);
        getOrderDetailEvent.setErpInfo(erpInfo);
        getOrderDetailEvent.setOrderId(returnRefundSyncLog.getOrderId());
        EventResult eventResult = userHandler.handleEvent(getOrderDetailEvent);
        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(eventResult.getData()));
            return orderProxyService.handleEvent(pushNewOrderEvent);
        }
        return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST, eventResult.getResultMsg(), null);
    }

}
