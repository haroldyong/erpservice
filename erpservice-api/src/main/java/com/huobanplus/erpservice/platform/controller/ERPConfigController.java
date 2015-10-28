/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liual on 2015-10-28.
 */
@Controller
@RequestMapping("/erpService")
public class ERPConfigController {
    @Autowired
    private ERPBaseConfigService baseConfigService;

    @RequestMapping("/erpConfig")
    public String erpConfig(@RequestAttribute int customerId, Model model) {
        ERPBaseConfigEntity baseConfig = null;
        if (customerId > 0) {
            baseConfig = baseConfigService.findByCustomerId(customerId);
        }
        model.addAttribute("baseConfig", baseConfig);
        return "erp_config";
    }

    @RequestMapping(value = "/setOpenStatus", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult setOpenStatus(@RequestAttribute int customerId) {
        ERPBaseConfigEntity baseConfig;
        if (customerId > 0) {
            baseConfig = baseConfigService.findByCustomerId(customerId);
            if (baseConfig == null) {
                baseConfig = new ERPBaseConfigEntity();
                baseConfig.setCustomerId(customerId);
                baseConfig.setIsOpen(1);
                baseConfigService.save(baseConfig);

            } else {
                int isOpen = baseConfig.getIsOpen() == 0 ? 1 : 0;
                baseConfigService.updateOpenStatus(customerId, isOpen);
            }
            return ApiResult.resultWith(ResultCode.SUCCESS);
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getToken(@RequestAttribute int customerId) {
        return ApiResult.resultWith(ResultCode.SUCCESS, StringUtil.createRandomStr32());
    }

    @RequestMapping("/saveConfig")
    public String saveConfig(@RequestAttribute int customerId, String appKey, String token, Model model) {
        String result;
        try {
            ERPBaseConfigEntity baseConfig = baseConfigService.findByCustomerId(customerId);
            baseConfig.setToken(token);
            baseConfig.setAppKey(appKey);
            baseConfigService.save(baseConfig);
            model.addAttribute("baseConfig", baseConfig);
            result = "success";
        } catch (Exception e) {
            result = "error";
        }
        model.addAttribute("result", result);
        return "erp_config";
    }
}
