/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.entity.support;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by allan on 4/15/16.
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderEnum.OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderEnum.OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.getCode();
    }

    @Override
    public OrderEnum.OrderStatus convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return EnumHelper.getEnumType(OrderEnum.OrderStatus.class, integer);
    }
}
