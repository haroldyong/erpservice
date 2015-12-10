/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.hotsupplier.common;

import com.huobanplus.erpservice.eventhandler.model.BaseInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liual on 2015-11-05.
 */
public class SupConstant {
    public static final String SECRET_KEY = "66668888";

    public static final String HB_REQUEST_URL = "http://192.168.191.1:31105";

    public static final String SUP_REQUEST_URL = "";

    public static Map buildSignMap(BaseInfo baseInfo) {
        Map<String, Object> signMap = new TreeMap<>();
        Class baseInfoClass = baseInfo.getClass();
        Field[] fields = baseInfoClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String method = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
            try {
                Object returnValue = baseInfoClass.getMethod(method).invoke(baseInfo);
                if (returnValue != null) {
                    signMap.put(fieldName, returnValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return signMap;
    }
}
