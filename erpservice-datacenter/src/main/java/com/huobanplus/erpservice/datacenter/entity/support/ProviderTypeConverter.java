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

package com.huobanplus.erpservice.datacenter.entity.support;

import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by wuxiongliu on 2016-12-23.
 */

@Converter(autoApply = true)
public class ProviderTypeConverter implements AttributeConverter<ERPTypeEnum.ProviderType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ERPTypeEnum.ProviderType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public ERPTypeEnum.ProviderType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return EnumHelper.getEnumType(ERPTypeEnum.ProviderType.class, dbData);
    }
}
