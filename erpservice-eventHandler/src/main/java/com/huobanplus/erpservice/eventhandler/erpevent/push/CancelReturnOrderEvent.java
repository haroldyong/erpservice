package com.huobanplus.erpservice.eventhandler.erpevent.push;

import com.huobanplus.erpservice.eventhandler.erpevent.ERPBaseEvent;
import lombok.Data;

/**
 * Created by wuxiongliu on 2016/4/27.
 */
@Data
public class CancelReturnOrderEvent extends ERPBaseEvent {

    private String orderReturnNo;
}
