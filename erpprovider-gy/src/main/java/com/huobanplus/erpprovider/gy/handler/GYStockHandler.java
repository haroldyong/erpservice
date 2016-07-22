package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
public interface GYStockHandler {

    /**
     *  库存查询
     * @param gyStockSearch 库存查询实体
     * @param gySysData 管易系统参数
     * @return
     */
    EventResult stockQuery(GYStockSearch gyStockSearch, GYSysData gySysData);
}
