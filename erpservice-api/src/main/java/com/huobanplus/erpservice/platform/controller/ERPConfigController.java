/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.platform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.ERPSysDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liual on 2015-10-28.
 */
@Controller
@RequestMapping("/erpService")
public class ERPConfigController {
    @Autowired
    private ERPBaseConfigService baseConfigService;
    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private ERPSysDataInfoService sysDataInfoService;

    @RequestMapping("/erpConfig")
    public String erpConfig(
            @RequestAttribute int customerId,
            int erpUserType,
            HttpServletRequest request,
            Model model
    ) {
        ERPBaseConfigEntity baseConfig = null;
        List<ERPDetailConfigEntity> lstDetailConfig = new ArrayList<>();
        int erpType = -1;
        if (customerId > 0) {
            baseConfig = baseConfigService.findByCustomerId(customerId);
            if (baseConfig != null && baseConfig.getIsOpen() == 1) {
                lstDetailConfig = detailConfigService.findByCustomerId(customerId);
                for (ERPDetailConfigEntity item : lstDetailConfig) {
                    if (item.getIsDefault() == 1) {
                        erpType = item.getErpType().getCode();
                        break;
                    }
                }
            }
        }

        model.addAttribute("baseConfig", baseConfig);
        model.addAttribute("lstDetailConfig", lstDetailConfig);
        model.addAttribute("erpType", erpType);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("result", request.getSession().getAttribute("resultCode"));
        request.getSession().removeAttribute("resultCode");
        return "erp_config";
    }

    @RequestMapping(value = "/setOpenStatus", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult setOpenStatus(@RequestAttribute int customerId, int erpUserType) {
        ERPBaseConfigEntity baseConfig;
        if (customerId > 0) {
            baseConfig = baseConfigService.findByCustomerId(customerId);
            if (baseConfig == null) {
                baseConfig = new ERPBaseConfigEntity();
                baseConfig.setCustomerId(customerId);
                baseConfig.setIsOpen(1);
                baseConfig.setAppKey(StringUtil.createRandomStr(8));
                baseConfig.setToken(StringUtil.createRandomStr32());
                ERPTypeEnum.UserType erpUserTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, erpUserType);
                baseConfig.setErpUserType(erpUserTypeEnum);
            } else {
                int isOpen = baseConfig.getIsOpen() == 0 ? 1 : 0;
                baseConfig.setToken(StringUtil.createRandomStr32());
                baseConfig.setIsOpen(isOpen);
            }
            baseConfigService.save(baseConfig);
            return ApiResult.resultWith(ResultCode.SUCCESS);
        } else {
            return ApiResult.resultWith(ResultCode.SYSTEM_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getToken() {
        return ApiResult.resultWith(ResultCode.SUCCESS, StringUtil.createRandomStr32());
    }

    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
    public String saveConfig(
            @RequestAttribute int customerId,
            String token,
            String secretKey,
            int erpType,
            int erpUserType,
            String sysDataJson,
            HttpServletRequest request,
            Model model
    ) {
        String result;
        try {
            ERPBaseConfigEntity baseConfig = baseConfigService.findByCustomerId(customerId);
            baseConfig.setToken(token.trim());
            baseConfig.setSecretKey(secretKey.trim());
            baseConfigService.save(baseConfig);
            if (erpType != -1) {
                ERPTypeEnum.ProviderType providerType = EnumHelper.getEnumType(ERPTypeEnum.ProviderType.class, erpType);
                ERPTypeEnum.UserType erpUserTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, erpUserType);
                ERPDetailConfigEntity detailConfig = detailConfigService.findByCustomerIdAndType(customerId, providerType);
                JSONObject jsonObject = JSON.parseObject(sysDataJson);
                if (detailConfig == null) {
                    detailConfig = new ERPDetailConfigEntity();
                    detailConfig.setErpType(providerType);
                    detailConfig.setErpUserType(erpUserTypeEnum);
                    detailConfig.setIsDefault(0);
                    detailConfig.setCustomerId(customerId);
                }
                int index = 0;
                Class configClass = detailConfig.getClass();
                sysDataInfoService.batchDelete(customerId, providerType);
                for (Map.Entry<String, Object> item : jsonObject.entrySet()) {
                    configClass.getDeclaredMethod("setP" + index, String.class).invoke(detailConfig, item.getValue());
                    ERPSysDataInfo erpSysDataInfo = new ERPSysDataInfo();
                    erpSysDataInfo.setErpType(providerType);
                    erpSysDataInfo.setParamName(item.getKey());
                    erpSysDataInfo.setCustomerId(customerId);
                    erpSysDataInfo.setColumnName("P" + index);
                    erpSysDataInfo.setErpUserType(erpUserTypeEnum);
                    erpSysDataInfo.setParamValue((String) item.getValue());
                    sysDataInfoService.save(erpSysDataInfo);
                    index++;
                }
                detailConfig.setErpBaseConfig(baseConfig);
                detailConfig.setErpSysData(sysDataJson);
                detailConfig = detailConfigService.save(detailConfig);
                //设为默认
                detailConfigService.setDefault(detailConfig.getId(), customerId);
                model.addAttribute("detailConfig", detailConfig);
            } else {
                detailConfigService.setUnDefault(customerId);
            }

            result = "success";
        } catch (Exception e) {
            result = "error";
        }
        request.getSession().setAttribute("resultCode", result);
        return "redirect: erpConfig?erpUserType=" + erpUserType;
    }
}
