/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.controller;

import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 火图对外erp独立接口
 * <b>统一request参数</b>
 * <ul>
 * <li>appKey：接入码</li>
 * <li>token：商家token</li>
 * <li>timestamp：标准时间戳</li>
 * <li>sign：签名</li>
 * <li>eventType: 请求事件名称</li>
 * </ul>
 * Created by liual on 2015-11-05.
 */
@RequestMapping("/erpService/hotApi/rest")
public interface HotApiController {
    /**
     * <b>订单相关</b>
     * <ul>
     * <li>hbpDeliveryInfo:发货通知</li>
     * <li>hbpReturnInfo:退货通知</li>
     * <li>hbpOrderList:获得订单列表</li>
     * <li>hbpOrderDetail：获得订单详情</li>
     * <li>hbpCancelOrder：清关</li>
     * </ul>
     *
     * @param eventType
     * @param erpUserInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/index", method = RequestMethod.POST)
    @ResponseBody
    ApiResult orderIndex(
            String eventType,
            @RequestAttribute ERPUserInfo erpUserInfo,
            HttpServletRequest request
    );

    /**
     * <b>商品相关</b>
     * <ul>
     * <li>hbpSyncInventory: 库存同步</li>
     * <li>hbpGoodList: 货品列表</li>
     * </ul>
     *
     * @return
     */
    @RequestMapping("/rest/index/{eventType}")
    ApiResult proIndex();
}
