package com.huobanplus.erpservice.eventhandler.erpevent;

import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import lombok.Data;

/**
 * <b>类描述：<b/>物流信息事件
 * Created by allan on 2015/7/21.
 */
@Data
public class DeliveryInfoEvent extends ERPBaseEvent {

    /**
     * 物流信息实体
     */
    private DeliveryInfo deliveryInfo;
}
