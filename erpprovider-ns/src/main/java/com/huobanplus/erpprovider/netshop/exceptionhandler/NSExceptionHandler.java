/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.exceptionhandler;

import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * 网店管家异常处理器
 * Created by liual on 2015-10-21.
 */
public class NSExceptionHandler {
    public static EventResult handleException(String mType, EventResultEnum resultCode, String resultMsg) {
        switch (mType) {
            case Constant.OBTAIN_ORDER_LIST:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + resultMsg + "</Cause></Order>");
            case Constant.OBTAIN_ORDER_DETAIL:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Order><Result>0</Result><Cause>" + resultMsg + "</Cause></Order>");
            case Constant.DELIVER_INFO:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + resultMsg + "</Cause></Rsp>");
            case Constant.OBTAIN_GOOD_LIST:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>" + resultMsg + "</Cause></Rsp>");
            case Constant.SYNC_INVENTORY:
                return EventResult.resultWith(resultCode, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>" + resultMsg + "</Cause></Rsp>");
        }
        return null;
    }
}
