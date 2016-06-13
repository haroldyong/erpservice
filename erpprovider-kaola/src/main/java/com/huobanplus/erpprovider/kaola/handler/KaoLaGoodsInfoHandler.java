package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
public interface KaoLaGoodsInfoHandler {


    /**
     *  查询所有商品的ID
     * @param kaoLaSysData
     * @return
     */
    EventResult queryAllGoodsId(KaoLaSysData kaoLaSysData);

    /**
     * 根据id查询商品信息
     * @param kaoLaSysData
     * @param skuId
     * @return
     */
    EventResult queryGoodsInfoById(KaoLaSysData kaoLaSysData,String skuId);

}
