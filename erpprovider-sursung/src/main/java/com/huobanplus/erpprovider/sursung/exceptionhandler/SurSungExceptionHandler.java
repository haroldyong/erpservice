/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.exceptionhandler;

import com.huobanplus.erpprovider.sursung.common.SurSungResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;

/**
 * Created by wuxiongliu on 2016-09-09.
 */
public class SurSungExceptionHandler {

    /**
     * handler request 的统一异常处理
     *
     * @param isSuccess 处理结果是否成功
     * @param errorMsg  异常信息
     * @return
     */
    public static EventResult handleException(boolean isSuccess, String errorMsg) {

        SurSungResult surSungResult = new SurSungResult();
        if (isSuccess) {
            surSungResult.setCode(0);
            surSungResult.setMsg("同步成功");
            return EventResult.resultWith(EventResultEnum.SUCCESS, surSungResult);
        } else {
            surSungResult.setCode(-1);
            surSungResult.setMsg(errorMsg);
            return EventResult.resultWith(EventResultEnum.ERROR, surSungResult);
        }
    }
}
