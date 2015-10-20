/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;

/**
 * ERP处理器生成器，由erp-provider具体实现
 * Created by allan on 2015/7/13.
 */
public interface ERPHandlerBuilder {
    /**
     * 生成具体的ERP处理器
     *
     * @param info 相关信息
     * @return 如果为空表示该Builder无法处理这个info
     */
    ERPHandler buildHandler(ERPInfo info);
}
