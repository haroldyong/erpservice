package com.huobanplus.erpservice.event;

import com.huobanplus.erpservice.event.ERPEvent;
import com.huobanplus.erpservice.event.model.AfterSaleInfo;

/**
 * 售后处理事件
 */
public class AfterSaleEvent extends ERPEvent {

    public AfterSaleInfo edbTradReturnGet(){return new AfterSaleInfo();}
}
