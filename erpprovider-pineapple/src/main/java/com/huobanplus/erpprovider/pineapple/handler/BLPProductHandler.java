package com.huobanplus.erpprovider.pineapple.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-06-19.
 */
public interface BLPProductHandler {
    /**
     * 商品库存同步
     *
     * @param platProductId 平台商品ID
     * @param quantity      库存数量
     * @param erpUserInfo   erp使用者这信息
     * @param method        调用方法名
     * @return
     */
    EventResult syncStock(String platProductId, int quantity, ERPUserInfo erpUserInfo, String method);
}
