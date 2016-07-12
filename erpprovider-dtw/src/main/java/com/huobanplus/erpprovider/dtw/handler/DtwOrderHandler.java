package com.huobanplus.erpprovider.dtw.handler;

import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.search.DtwStockSearch;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
public interface DtwOrderHandler {

    /**
     *  推送订单
     * @param pushNewOrderEvent
     * @return
     */
    EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent);

//    /**
//     *  个人物品信息申报推送
//     * @param dtwPersonalDelcareInfo
//     * @param dtwSysData
//     * @return
//     */
//    EventResult pushPersonalDeclareOrder(DtwPersonalDelcareInfo dtwPersonalDelcareInfo, DtwSysData dtwSysData);

    EventResult deliverOrder(String Msgid,String wayBill, String weight,String state,ERPUserInfo erpUserInfo);

    EventResult stockQuery(List<DtwStockSearch> dtwStockSearchList, DtwSysData dtwSysData);
}
