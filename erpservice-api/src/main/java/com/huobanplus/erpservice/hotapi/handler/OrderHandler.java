/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.hotapi.handler;

import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by liual on 2015-11-05.
 */
public interface OrderHandler {
    /**
     * 发货通知
     * <p>接受参数</p>
     * <ul>
     * <li>orderId</li>
     * <li>logiName</li>
     * <li>logiNo</li>
     * <li>freight</li>
     * <li>remark</li>
     * <li>dicDeliverItemsStr</li>
     * </ul>
     *
     * @param erpUserInfo
     * @return
     */
    ApiResult deliveryInfo(HttpServletRequest request, ERPUserInfo erpUserInfo);

    /**
     * 退货通知
     * <p>接受参数：</p>
     * <ul>
     * <li>orderId</li>
     * <li>logiName</li>
     * <li>logiNo</li>
     * <li>returnAddr</li>
     * <li>returnMobile</li>
     * <li>returnName</li>
     * <li>returnZip</li>
     * <li>freight</li>
     * <li>remark</li>
     * <li>dicDeliverItemsStr</li>
     * </ul>
     *
     * @param erpUserInfo
     * @return
     */
    ApiResult returnInfo(HttpServletRequest request, ERPUserInfo erpUserInfo);

    /**
     * 获取订单列表
     * <p>接受参数：</p>
     * <ul>
     * <li>pageIndex</li>
     * <li>pageSize</li>
     * <li>orderStatus(可选)</li>
     * <li>shipStatus（可选）</li>
     * <li>payStatus（可选）</li>
     * <li>beginTime（格式：yyyy-MM-dd HH:mm:ss）</li>
     * <li>endTime（格式：yyyy-MM-dd HH:mm:ss）</li>
     * </ul>
     *
     * @param request
     * @param erpUserInfo
     * @return
     */
    ApiResult obtainOrderList(HttpServletRequest request, ERPUserInfo erpUserInfo) throws ParseException;
}
