/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.formatsap;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by admin on 2016/4/20.
 */
@Setter
@Getter
public class LogiInfo {

    /**
     * 交货单
     */
    private String zVBELN;

    /**
     * 物流单号
     */
    private String zWMOrder;

    /**
     * 导出状态
     */
    private String zType;

    /**
     * 交货单
     */
    private String yVBELN;

    /**
     * 微订单
     */
    private String zOrder;
    /**
     * 物流公司
     */
    private String zWMLogiName;
}
