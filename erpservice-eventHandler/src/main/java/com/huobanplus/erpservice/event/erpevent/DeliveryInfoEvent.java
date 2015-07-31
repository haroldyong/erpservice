package com.huobanplus.erpservice.event.erpevent;

import com.huobanplus.erpservice.event.model.AuthBean;
import com.huobanplus.erpservice.event.model.DeliveryInfo;

/**
 * <b>类描述：<b/>物流信息事件
 * Created by allan on 2015/7/21.
 */
public class DeliveryInfoEvent extends ERPBaseEvent {

    /**
     * 物流信息实体
     */
    private DeliveryInfo deliveryInfo;

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
}
