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
 * 商品处理事件接口
 */
public interface NSProductHandler {

    /**
     * 获取商品信息
     *
     * @param request 请求实体
     * @return 返回返回结果值统一处理信息
     * @throws IOException IO 异常
     */
    EventResult obtainGoods(HttpServletRequest request) throws IOException;

    /**
     * 数据同步
     *
     * @param request
     * @return
     * @throws IOException
     */
    EventResult syncInventory(HttpServletRequest request) throws IOException;
}
