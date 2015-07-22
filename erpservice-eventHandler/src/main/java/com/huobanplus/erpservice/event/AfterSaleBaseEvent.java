package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.event.model.AfterSaleInfo;

/**
 * 售后处理事件
 */
public class AfterSaleBaseEvent extends ERPBaseEvent {

    public AfterSaleInfo edbTradReturnGet(){return new AfterSaleInfo();}
}
