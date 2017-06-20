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
     * @param platProductID 平台商品ID
     * @param skuId         平台子规格ID
     * @param outerId       外部商家编码
     * @param outSkuId      库存数量
     * @param quantity      外部商家SKU编号
     * @param erpUserInfo   erp使用者这信息
     * @param method        调用方法名
     * @return
     */
    EventResult syncStock(String platProductID, String skuId, String outerId, String outSkuId, int quantity, ERPUserInfo erpUserInfo, String method);
}
