/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.common.ienum;

/**
 * 枚举处理类
 *
 * @author Administrator
 */
public class EnumHelper {

    public static String getEnumName(Class<? extends ICommonEnum> cls, int value) {
        ICommonEnum ice = getEnumType(cls, value);
        if (ice != null) {
            return ice.getName();
//            try {
//                return new String(ice.getName().getBytes("UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                return "";
//            }
        }
        return "";
    }

    public static <T extends ICommonEnum> T getEnumType(Class<T> cls, int value) {
        for (T item : cls.getEnumConstants()) {
            if (item.getCode() == value) {
                return item;
            }
        }
        return null;
    }
}