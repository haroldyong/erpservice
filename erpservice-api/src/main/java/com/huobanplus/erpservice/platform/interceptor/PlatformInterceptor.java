/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.platform.interceptor;

import com.huobanplus.erpservice.commons.utils.CookieHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liual on 2015-10-28.
 */
@Component
public class PlatformInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(PlatformInterceptor.class);

    @Autowired
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String erpUserType = request.getParameter("erpUserType");
        Integer customerId;
        if (erpUserType.equals("0")) {
            customerId = CookieHelper.getCookieValInteger(request, "UserID");
            if (customerId == null) {
                response.sendRedirect(environment.getProperty("supplier_login", "http://localhost:8080"));
                return false;
            }
            if (environment.acceptsProfiles("development")) {
                customerId = 3677;
            }
//            request.setAttribute("customerId", );
        } else {
            customerId = CookieHelper.getCookieValInteger(request, "supplierId");
            if (customerId == null) {
                response.sendRedirect(environment.getProperty("huobanplus_login", "http://login.huobanplus.com"));
                return false;
            }

            if (environment.acceptsProfiles("development")) {
                customerId = 6340;
            }
        }
        request.setAttribute("customerId", customerId);
        log.info("currentCustomerId=" + customerId);
        return true;
    }
}
