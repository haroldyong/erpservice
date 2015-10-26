/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.proxy.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpservice.common.util.DxDESCipher;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.proxy.common.CommonUtils;
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
public class UserAuthorizeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiResult apiResult;
        //签名验证
        String requestSign = request.getParameter("sign");

        if (StringUtils.isEmpty(requestSign)) {
            apiResult = ApiResult.resultWith(ResultCode.EMPTY_SIGN_CODE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> signMap = new TreeMap<>();
        requestMap.forEach((key, values) -> {
            if (!"sign".equals(key)) {
                if (values != null && values.length > 0) {
                    if (!StringUtils.isEmpty(values[0])) {
                        signMap.put(key, values[0]);
                    }
                }
            }
        });
        //得到商家的secretKey
        String sign = SignBuilder.buildSign(signMap, null, CommonUtils.SECRET_KEY);
        if (sign.equals(requestSign)) {
            //反序列化erpInfo
            String sysDataJson = request.getParameter("sysDataJson");
            String erpName = request.getParameter("erpName");
            sysDataJson = DxDESCipher.decrypt(sysDataJson);
            ERPInfo erpInfo = new ERPInfo(erpName, sysDataJson);
            request.setAttribute("erpInfo", erpInfo);
            return true;
        } else {
            apiResult = ApiResult.resultWith(ResultCode.WRONG_SIGN_CODE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
    }
}
