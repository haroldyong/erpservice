package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.datacenter.model.CancelOrderInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushCancelOrderEvent  extends ERPBaseEvent {
    private CancelOrderInfo cancelOrderInfo;
}
