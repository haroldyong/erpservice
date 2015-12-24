/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.commons.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 12/11/15.
 */
public class CommonUtils {
    public static Map<String,Object> getSignMap(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, java.lang.Object> signMap = new TreeMap<>();
        requestMap.forEach((key, value) -> {
            if (!"sign".equals(key)) {
                if (value != null && value.length > 0) {
                    if (!StringUtils.isEmpty(value[0]))
                        signMap.put(key, value[0]);
                }
            }
        });
        return signMap;
    }
}
