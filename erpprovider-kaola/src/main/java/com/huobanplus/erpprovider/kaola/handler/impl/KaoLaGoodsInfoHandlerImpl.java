package com.huobanplus.erpprovider.kaola.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.formatkaola.KaoLaGoodsInfo;
import com.huobanplus.erpprovider.kaola.handler.KaoLaBaseHandler;
import com.huobanplus.erpprovider.kaola.handler.KaoLaGoodsInfoHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
@Component
public class KaoLaGoodsInfoHandlerImpl extends KaoLaBaseHandler implements KaoLaGoodsInfoHandler {

    private static final Log log = LogFactory.getLog(KaoLaGoodsInfoHandlerImpl.class);

    @Override
    public EventResult queryAllGoodsId(KaoLaSysData kaoLaSysData) {
        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", kaoLaSysData.getChannelId());
        parameterMap.put("timestamp", StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryAllGoodsId",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    JSONArray goodsIdArray = result.getJSONArray("goodsInfo");
                    return EventResult.resultWith(EventResultEnum.SUCCESS,goodsIdArray);
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
    public EventResult queryGoodsInfoById(KaoLaSysData kaoLaSysData,String skuId) {
        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        parameterMap.put("channelId", kaoLaSysData.getChannelId());
        parameterMap.put("skuId",skuId);
        parameterMap.put("queryType",0);
        parameterMap.put("timestamp", StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        parameterMap.put("v",kaoLaSysData.getV());
        parameterMap.put("sign_method","md5");
        parameterMap.put("app_key",kaoLaSysData.getAppKey());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryGoodsInfoById",requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if(result.getString("recCode").equals("200")){
                    System.out.println("\n************Data***********************");
                    System.out.println(result.getString("goodsInfo"));
                    KaoLaGoodsInfo kaoLaGoodsInfo = JSONObject.parseObject(result.getString("goodsInfo"),KaoLaGoodsInfo.class);
                    System.out.println("************Data***********************");
                    return EventResult.resultWith(EventResultEnum.SUCCESS,kaoLaGoodsInfo);
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
