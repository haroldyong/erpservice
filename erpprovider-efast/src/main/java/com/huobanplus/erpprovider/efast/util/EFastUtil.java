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

package com.huobanplus.erpprovider.efast.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2017-03-07.
 */
public class EFastUtil {

    public static String buildSign(Map<String, Object> paramMap, String prefix, String suffix) throws UnsupportedEncodingException {

        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";

        StringBuilder stringBuilder = new StringBuilder(prefix);
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        System.out.println("toSign:" + stringBuilder.toString());
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8"));
    }

    public static String buildRequestUrl(String baseUrl, Map<String, Object> paramMap) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("?");
        Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            stringBuilder.append(next.getKey()).append("=").append(next.getValue()).append("&");
        }
        String requestUrl = stringBuilder.substring(0, stringBuilder.lastIndexOf("&"));
        return requestUrl;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, Object> requestMap = new TreeMap<>();
        requestMap.put("app_act", "[接口名称]");
        requestMap.put("app_key", "testerp_appkey");
        requestMap.put("format", "json");
        requestMap.put("timestamp", "2015-04-26 00:00:07");
        requestMap.put("v", "2.0");

        String sign = buildSign(requestMap, "test", "test");
        System.out.println(sign);

        String requestUrl = buildRequestUrl("http://www.baidu.com/index", requestMap);
        System.out.println(requestUrl);
    }


}
