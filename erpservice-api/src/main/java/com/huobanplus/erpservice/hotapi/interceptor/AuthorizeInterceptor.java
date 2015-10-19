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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiResult apiResult = null;
        String appKey = request.getParameter("appKey");
        String token = request.getParameter("token");
        //todo 通过appKey和token得到对应商家customerId, secretKey, erpUserName
        int customerId = 5;
        if (customerId == 0) {
            apiResult = ApiResult.resultWith(ResultCode.BAD_APP_KEY_AND_TOKEN);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        String erpUserName = "huobanmall";
        String secretKey = "";
        //签名验证
        String requestSign = request.getParameter("sign");
        if (StringUtils.isEmpty(requestSign)) {
            apiResult = ApiResult.resultWith(ResultCode.EMPTY_SIGN_CODE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> signMap = new TreeMap<>();
        requestMap.forEach((action, strings) -> {
            if (!"sign".equals(action)) {
                if (strings != null && strings.length > 0) {
                    if (!StringUtils.isEmpty(strings[0]))
                        signMap.put(action, strings[0]);
                }
            }
        });

        String sign = SignBuilder.buildSign(signMap, null, secretKey);
        if (sign.equals(requestSign)) {
            //验证通过插入商户信息
            request.setAttribute("customerId", customerId);
            request.setAttribute("erpUserName", erpUserName);
            return true;
        } else {
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResult));
            return false;
        }
    }
}
