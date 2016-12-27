/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.baison.common.BaisonConstant;
import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.formatdata.BaisonStockSearch;
import com.huobanplus.erpprovider.baison.handler.BaisonGoodsHandler;
import com.huobanplus.erpprovider.baison.util.BaisonUtil;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wuxiongliu on 2016-12-27.
 */
@Component
public class BaisonGoodsHandlerImpl implements BaisonGoodsHandler {

    @Override
    public EventResult queryGoodsStock(BaisonStockSearch baisonStockSearch, BaisonSysData baisonSysData) {
        try {
            Map<String, Object> requestMap = BaisonUtil.buildRequestMap(baisonSysData, BaisonConstant.GET_STOCK, JSON.toJSONString(baisonStockSearch));
            HttpResult httpResult = HttpClientUtil.getInstance().get(baisonSysData.getRequestUrl(), requestMap);

            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject respData = JSON.parseObject(httpResult.getHttpContent());
                if (respData.getString("status").equals("api-success")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, respData.getString("message"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {

        }
        return null;
    }
}
