/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.common.ienum;

/**
 * Created by allan on 8/12/16.
 */
public class EnumHelperV2 {

    public static Object getEnumName(Class<? extends ICommonEnumV2> cls, Object code) {
        ICommonEnumV2 ice = getEnumType(cls, code);
        if (ice != null) {
            return ice.getName();
//            try {
//                return new String(ice.getName().getBytes("UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                return "";
//            }
        }
        return null;
    }

    public static <T extends ICommonEnumV2> T getEnumType(Class<T> cls, Object code) {
        for (T item : cls.getEnumConstants()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
