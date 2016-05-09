package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.search.KaoLaGoodsInfoSearch;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
public interface KaoLaGoodsInfoHandler {

    EventResult queryAllGoodsInfo(KaoLaSysData sysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch);

    EventResult queryAllGoodsId();

    EventResult queryGoodsInfoById();

}
