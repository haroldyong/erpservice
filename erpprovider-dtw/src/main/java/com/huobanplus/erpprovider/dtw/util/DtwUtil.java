/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016-07-26.
 */
public class DtwUtil {

    /**
     *  支付宝 MD5签名 剔除掉sign，sign_type
     * @param parameterMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String aliBuildSign(Map<String,Object> parameterMap) throws UnsupportedEncodingException {
        StringBuilder signStr = new StringBuilder();
        Iterator iterator = parameterMap.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry ent=(Map.Entry )iterator.next();
            String key=ent.getKey().toString();
            String value=ent.getValue().toString();
            if(key.equals("sign")||key.equals("sign_type")){
                continue;
            }
            signStr.append(key).append(value);
        }
        return DigestUtils.md5Hex(signStr.toString().getBytes("utf-8")).toUpperCase();
    }

    /**
     *  微信支付 MD5签名
     * @param requestMap
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String weixinBuildSign(Map<String,Object> requestMap,String key) throws UnsupportedEncodingException {
        StringBuilder signStr = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = requestMap.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            if (!StringUtils.isEmpty(next.getValue())) {
                signStr.append(next.getKey())
                        .append("=")
                        .append(next.getValue())
                        .append("&");
            }
        }
        String stringSignTemp = signStr.append("key=").append(key).toString();
        return DigestUtils.md5Hex(stringSignTemp.getBytes("utf-8")).toUpperCase();
    }

    public static Map<String,Object> convertBeanToMap(Object object) throws IllegalAccessException {
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String,Object> result = new TreeMap<>();
        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            field.setAccessible(true);
            result.put(field.getName(),field.get(object));
        }
        return result;
    }
}
