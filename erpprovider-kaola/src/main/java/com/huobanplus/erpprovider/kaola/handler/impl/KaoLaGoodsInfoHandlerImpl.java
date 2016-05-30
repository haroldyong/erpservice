package com.huobanplus.erpprovider.kaola.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.handler.KaoLaBaseHandler;
import com.huobanplus.erpprovider.kaola.handler.KaoLaGoodsInfoHandler;
import com.huobanplus.erpprovider.kaola.search.KaoLaGoodsInfoSearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
@Component
public class KaoLaGoodsInfoHandlerImpl extends KaoLaBaseHandler implements KaoLaGoodsInfoHandler {

    private static final Log log = LogFactory.getLog(KaoLaGoodsInfoHandlerImpl.class);

    @Override
    public EventResult queryAllGoodsInfo(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch){

        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", kaoLaGoodsInfoSearch.getChannelId());
        parameterMap.put("timestamp", kaoLaGoodsInfoSearch.getTimeStamp());
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryAllGoodsInfo",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    // TODO: 2016/5/9
                    String orderInfo = result.getString("goodsInfo ");
                    System.out.println("\n**********************Data********");
                    System.out.println(orderInfo);
                    System.out.println("**********************Data********");

                    JSONObject jsonObject = JSON.parseObject(orderInfo);
                    JSONArray jsonArray = jsonObject.getJSONArray("goodsInfo");
                    jsonArray.forEach(item->{
                        System.out.println(item.toString());
                    });

                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.get("recMsg").toString(),null);
                }

            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }

    }

    @Override
    public EventResult queryAllGoodsId(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch) {
        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", kaoLaGoodsInfoSearch.getChannelId());
        parameterMap.put("timestamp", kaoLaGoodsInfoSearch.getTimeStamp());
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryAllGoodsId",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    // TODO: 2016/5/9
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.get("recMsg").toString(),null);
                }

            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

    @Override
    public EventResult queryGoodsInfoById(KaoLaSysData kaoLaSysData,KaoLaGoodsInfoSearch kaoLaGoodsInfoSearch) {
        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", kaoLaGoodsInfoSearch.getChannelId());
        parameterMap.put("skuId",kaoLaGoodsInfoSearch.getSkuId());
        parameterMap.put("queryType",kaoLaGoodsInfoSearch.getQueryType());
        parameterMap.put("timestamp", kaoLaGoodsInfoSearch.getTimeStamp());
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryGoodsInfoById",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    // TODO: 2016/5/9
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    return EventResult.resultWith(EventResultEnum.ERROR,result.get("recMsg").toString(),null);
                }

            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }
}
