package com.huobanplus.erpprovider.gy.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.stock.GYResponseStock;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYStockHandler;
import com.huobanplus.erpprovider.gy.search.GYStockSearch;
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
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){

                    JSONArray jsonArray = result.getJSONArray("stocks");
                    List<GYResponseStock> gyrStocks = new ArrayList<>();
                    jsonArray.forEach(stock->{
                        GYResponseStock gyrStock = JSON.parseObject(stock.toString(),GYResponseStock.class);
                        gyrStocks.add(gyrStock);
                    });

                    return EventResult.resultWith(EventResultEnum.SUCCESS,gyrStocks);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }
}
