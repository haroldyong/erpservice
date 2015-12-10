/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.common.util;

import java.lang.reflect.Field;

/**
 * 类操作相关工具
 * Created by liual on 2015-12-07.
 */
public class ClassUtil {
    /**
     * 将一个实例中各个成员变量的值填充到目标实例中
     * 要求源实例中的成员变量在目标实例中均存在，并且成员变量的名称一一对应
     *
     * @param source
     * @param target
     * @throws ReflectiveOperationException
     */
    public static void cloneClass(Object source, Object target) throws ReflectiveOperationException {
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        Field[] fields = sourceClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object value = sourceClass.getDeclaredMethod("get" + fieldName).invoke(source);
            if (targetClass.getDeclaredField(fieldName).getType() == field.getType()) {
                targetClass.getDeclaredMethod("set" + fieldName).invoke(target, value);
            }
        }
    }
}
