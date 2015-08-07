package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import java.io.IOException;

/**
 * 出入库相关
 * Created by allan on 2015/8/7.
 */
public interface EDBStorageHandler {
    /**
     * 增加出库单信息
     *
     * @return
     */
    Monitor<EventResult> outStorageAdd(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException;
}
