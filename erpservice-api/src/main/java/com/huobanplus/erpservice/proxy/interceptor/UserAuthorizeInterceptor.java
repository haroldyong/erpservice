/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.interceptor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.commons.utils.CommonUtils;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.sandbox.common.SBConstant;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by liual on 2015-10-19.
 */
@Component
public class UserAuthorizeInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(UserAuthorizeInterceptor.class);
    @Autowired
    private ERPDetailConfigService detailConfigService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiResult apiResult;
        //签名验证
        String requestSign = request.getParameter("sign");

        if (StringUtils.isEmpty(requestSign)) {
            apiResult = ApiResult.resultWith(ResultCode.EMPTY_SIGN_CODE);
            response.getWriter().write(JSON.toJSONString(apiResult));
            return false;
        }
        Map<String, Object> signMap = CommonUtils.getSignMap(request);
        //得到商家的erp配置
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int userType = Integer.parseInt(request.getParameter("userType"));
        ERPTypeEnum.UserType erpUserType = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, userType);
        ERPDetailConfigEntity detailConfigEntity = detailConfigService.findByCustomerIdAndDefault(customerId, erpUserType);
        if (detailConfigEntity == null) {
            apiResult = ApiResult.resultWith(ResultCode.ERP_NOT_OPEN);
            response.getWriter().write(JSON.toJSONString(apiResult));
            return false;
        }
        String secretKey = detailConfigEntity.getErpUserType() == ERPTypeEnum.UserType.HUOBAN_MALL ? HBConstant.SECRET_KEY : SBConstant.SECRET_KEY;
        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, secretKey);
        if (true) {
            ERPInfo erpInfo = new ERPInfo(detailConfigEntity.getErpType(), detailConfigEntity.getErpSysData());
            ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserType, customerId);
            request.setAttribute("erpInfo", erpInfo);
            request.setAttribute("erpUserInfo", erpUserInfo);
            return true;
        } else {
            apiResult = ApiResult.resultWith(ResultCode.WRONG_SIGN_CODE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
    }
}
