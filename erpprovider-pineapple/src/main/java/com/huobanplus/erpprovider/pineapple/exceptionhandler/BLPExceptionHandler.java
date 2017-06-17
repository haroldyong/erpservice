package com.huobanplus.erpprovider.pineapple.exceptionhandler;

import com.huobanplus.erpprovider.pineapple.util.BLPConstant;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by hxh on 2017-06-16.
 */
public class BLPExceptionHandler {
    public static EventResult handleException(String method, EventResultEnum resultCode, String resultMsg) {
        switch (method) {
            case BLPConstant.OBTAIN_ORDER_LIST:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + resultMsg + "</Cause></Order>");
            case BLPConstant.DELIVER_INFO:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + resultMsg + "</Cause></Rsp>");
            case BLPConstant.REFUND_CHECK:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + resultMsg + "</Cause></Rsp>");
            case BLPConstant.SYNC_INVENTORY:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>" + resultMsg + "</Cause></Rsp>");
        }
        return null;
    }
}
