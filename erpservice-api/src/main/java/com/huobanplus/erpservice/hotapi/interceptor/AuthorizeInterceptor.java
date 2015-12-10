/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.service.ERPBaseConfigService;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liual on 2015-10-19.
 */
@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private ERPBaseConfigService baseConfigService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiResult apiResult = null;
        String appKey = request.getParameter("appKey");
        String token = request.getParameter("token");
        //todo 通过appKey和token得到对应商家customerId, secretKey, erpUserName
        ERPBaseConfigEntity baseConfig = baseConfigService.findByAppKeyAndToken(appKey, token);
        if (baseConfig == null) {
            apiResult = ApiResult.resultWith(ResultCode.BAD_APP_KEY_AND_TOKEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        if (baseConfig.getIsOpen() == 0) {
            apiResult = ApiResult.resultWith(ResultCode.ERP_NOT_OPEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        //签名验证
        String requestSign = request.getParameter("sign");
        if (StringUtils.isEmpty(requestSign)) {
            apiResult = ApiResult.resultWith(ResultCode.EMPTY_SIGN_CODE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> signMap = new TreeMap<>();
        requestMap.forEach((action, strings) -> {
            if (!"sign".equals(action)) {
                if (strings != null && strings.length > 0) {
                    if (!StringUtils.isEmpty(strings[0]))
                        signMap.put(action, strings[0]);
                }
            }
        });

        String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, baseConfig.getSecretKey());
        if (sign.equals(requestSign)) {
            //验证通过插入商户信息
            ERPUserInfo erpUserInfo = new ERPUserInfo(baseConfig.getErpUserType(), baseConfig.getCustomerId());
            request.setAttribute("erpUserInfo", erpUserInfo);
            return true;
        } else {
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
    }
}
