/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler;

import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 库存相关处理
 * Created by allan on 2015/8/2.
 */
public interface NSInventoryHandler {
    /**
     * 库存同步
     *
     * @param request 请求实体
     * @return 返回一个统一结果处理实体
     * @throws IOException IO异常
     */
    EventResult synsInventory(HttpServletRequest request) throws IOException;
}
