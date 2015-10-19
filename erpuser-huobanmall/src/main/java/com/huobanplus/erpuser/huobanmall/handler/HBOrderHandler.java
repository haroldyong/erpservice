package com.huobanplus.erpuser.huobanmall.handler;

import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * 订单相关处理
 * Created by liual on 2015-10-15.
 */
public interface HBOrderHandler {
    /**
     * 发货通知处理
     *
     * @param deliveryInfo
     * @return
     */
    EventResult deliverInfo(DeliveryInfo deliveryInfo);

    /**
     * 退货通知
     *
     * @param orderId
     * @param logiName
     * @param logiNo
     * @param returnAddr
     * @param returnMobile
     * @param returnName
     * @param returnZip
     * @param freight
     * @param remark
     * @param dicDeliverItemsStr
     * @return
     */
    EventResult returnInfo(String orderId, String logiName, String logiNo, String returnAddr, String returnMobile, String returnName, String returnZip, int freight, String remark, String dicDeliverItemsStr);

    /**
     * 库存同步
     *
     * @param goodId
     * @param productId
     * @param bn
     * @param stock
     * @return
     */
    EventResult syncInventory(int goodId, int productId, String bn, int stock);
}
