package com.huobanplus.erpservice.datacenter.erp.thirdparty;

import com.huobanplus.erpservice.datacenter.erp.HOTErp;
import com.huobanplus.erpservice.datacenter.event.HOTEvent;

/**
 * Created by Administrator on 2015/7/10.
 */
public interface ThirdPartyApi extends HOTErp {
    void addEvent(HOTEvent event);
}
