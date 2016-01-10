/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作Cookie
 * Created by Administrator on 2015/5/21.
 */
public class CookieHelper {
    /**
     * 得到cookie的值，返回String
     *
     * @param request
     * @param key
     * @return
     */
    public static String getCookieVal(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 得到cookie的值，返回int
     *
     * @param request
     * @param key
     * @return
     */
    public static int getCookieValInteger(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return Integer.parseInt(cookie.getValue());
                }
            }
        }
        return 0;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param key
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(1209600);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
