package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.search.GjbcInventorySearch;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.io.UnsupportedEncodingException;

/**
 * Created by hot on 2017/11/28.
 */
public interface GJBCProductHandler {

    EventResult getProductInventoryInfo(ERPInfo erpInfo , GjbcSysData sysData, GjbcInventorySearch gjbcInventorySearch) throws UnsupportedEncodingException;
}
