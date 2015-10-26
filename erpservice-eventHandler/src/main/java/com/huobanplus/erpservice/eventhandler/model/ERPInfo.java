/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 事件中携带的erp相关信息，用于erphandler选择合适的erp-provider处理
 * Created by allan on 2015/7/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERPInfo implements Serializable {
    private static final long serialVersionUID = 52479859658664542L;

    /**
     * erp名称
     */
    private String erpName;
    /**
     * erp系统级参数
     * <p>json格式</p>
     * <p><p/>
     */
    private String sysDataJson;
}
