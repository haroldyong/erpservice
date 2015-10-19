/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.handler.impl;

import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.handler.HBOrderHandler;
import org.springframework.stereotype.Service;

/**
 * Created by liual on 2015-10-19.
 */
@Service
public class HBOrderHandlerImpl implements HBOrderHandler {

    @Override
    public EventResult deliverInfo(DeliveryInfo deliveryInfo) {
        if (deliveryInfo == null)
            return EventResult.resultWith(EventResultEnum.NO_DATA);
        
        return null;
    }

    @Override
    public EventResult returnInfo(String orderId, String logiName, String logiNo, String returnAddr, String returnMobile, String returnName, String returnZip, int freight, String remark, String dicDeliverItemsStr) {
        return null;
    }

    @Override
    public EventResult syncInventory(int goodId, int productId, String bn, int stock) {
        return null;
    }
}
