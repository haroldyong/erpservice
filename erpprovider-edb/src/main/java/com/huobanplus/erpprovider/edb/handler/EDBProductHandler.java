package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import java.io.IOException;

/**
 * 货品相关
 * Created by allan on 2015/7/28.
 */
public interface EDBProductHandler {
    /**
     * 获取货品库存信息
     * @param info erp信息
     * @return 返回结果
     * @throws IOException IO 异常
     */
    EventResult getProInventoryInfo(ERPInfo info) throws IOException;
}
