package com.huobanplus.erpprovider.gy.handler;

import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.goods.*;
import com.huobanplus.erpprovider.gy.search.GYGoodsSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
public interface GYGoodsHandler {

    /**
     *  查询商品
     * @param goodsSearch
     * @param gySysData
     * @return
     */
    EventResult goodsQuery(GYGoodsSearch goodsSearch, GYSysData gySysData);


    /**
     *  新增商品
     * @param gyGoods
     * @param gySysData
     * @return
     */
    EventResult pushGoods(GYGoods gyGoods,GYSysData gySysData);

    /**
     *  修改商品
     * @param gyGoods
     * @param gySysData
     * @return
     */
    EventResult updateGoods(GYGoods gyGoods,GYSysData gySysData);

    /**
     *  删除商品
     * @param gyDeleteGoods
     * @param gySysData
     * @return
     */
    EventResult deleteGoods(GYDeleteGoods gyDeleteGoods,GYSysData gySysData);

    /**
     *  新增商品规格
     * @param gyGoodsSku
     * @param gySysData
     * @return
     */
    EventResult pushGoodsSku(GYGoodsSku gyGoodsSku,GYSysData gySysData);

    /**
     *  修改商品规格
     * @param gyGoodsSku
     * @param gySysData
     * @return
     */
    EventResult updateGoodsSku(GYGoodsSku gyGoodsSku,GYSysData gySysData);

    /**
     * 删除商品规格
     * @param gyDeleteSku
     * @param gySysData
     * @return
     */
    EventResult deleteGoodsSku(GYDeleteSku gyDeleteSku,GYSysData gySysData);

    /**
     *  新增商品条码
     * @param gyGoodsBarCode
     * @param gySysData
     * @return
     */
    EventResult pushGoodsBarCode(GYGoodsBarCode gyGoodsBarCode,GYSysData gySysData);

}
