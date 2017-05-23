/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.stock.GYStockInfo;
import com.huobanplus.erpprovider.gy.formatgy.stock.GYStockResponse;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYStockHandler;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
import com.huobanplus.erpprovider.gy.search.GYStockSearchNew;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Component
public class GYStockHandlerImpl extends GYBaseHandler implements GYStockHandler {
    private static final Log log = LogFactory.getLog(GYStockHandlerImpl.class);

    @Override
    public EventResult stockQuery(GYStockSearch gyStockSearch, GYSysData gySysData) {
        try {
            String requestData = getRequestData(gySysData, gyStockSearch, GYConstant.STOCK_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if (result.getBoolean("success")) {

                    JSONArray jsonArray = result.getJSONArray("stocks");
                    List<GYStockInfo> gyrStocks = new ArrayList<>();
                    jsonArray.forEach(stock -> {
                        GYStockInfo gyrStock = JSON.parseObject(stock.toString(), GYStockInfo.class);
                        gyrStocks.add(gyrStock);
                    });

                    GYStockResponse stockResponse = new GYStockResponse(gyrStocks, gyrStocks.size());
                    return EventResult.resultWith(EventResultEnum.SUCCESS, stockResponse);
                } else {
                    log.info("错误信息：" + result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("errorDesc"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult stockQueryNew(GYStockSearchNew stockSearchNew, GYSysData gySysData) {
        try {
            String requestData = getRequestData(gySysData, stockSearchNew, GYConstant.STOCK_QUERY_NEW);

            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if (result.getBoolean("success")) {
                    List<GYStockInfo> stockInfoList = JSON.parseArray(result.getString("stocks"), GYStockInfo.class);
                    int totalCount = result.getInteger("total");

                    GYStockResponse stockResponse = new GYStockResponse(stockInfoList, totalCount);
                    return EventResult.resultWith(EventResultEnum.SUCCESS, stockResponse);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("errorDesc"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
            }
        } catch (IOException e) {
            log.info("stockQueryNew exception:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
