package com.huobanplus.erpprovider.gjbbc.handler;

import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpprovider.gjbbc.search.GjbbcInventorySearch;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.io.UnsupportedEncodingException;

/**
 * Created by hot on 2017/11/30.
 */
public interface GJBBCProductHandler {
    EventResult getProductInventoryInfo(GjbbcSysData sysData, GjbbcInventorySearch gjbcInventorySearch) throws Exception;
}
