package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.search.KaoLaGoodsInfoSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
public interface KaoLaGoodsInfoHandler {

    /**
     *  查询所有商品信息
     * @param kaoLaSysData 考拉系统参数
     * @param kaoLaGoodsInfoSearch
     * @return
     */
    EventResult queryAllGoodsInfo(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch);

    /**
     *  查询所有商品的ID
     * @param kaoLaSysData
     * @param kaoLaGoodsInfoSearch
     * @return
     */
    EventResult queryAllGoodsId(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch);

    /**
     * 根据id查询商品信息
     * @param kaoLaSysData
     * @param kaoLaGoodsInfoSearch
     * @return
     */
    EventResult queryGoodsInfoById(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch);

}
