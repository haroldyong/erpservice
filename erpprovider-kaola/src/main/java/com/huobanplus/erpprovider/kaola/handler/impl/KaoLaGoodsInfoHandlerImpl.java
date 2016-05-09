package com.huobanplus.erpprovider.kaola.handler.impl;

import com.alibaba.fastjson.JSON;
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
 * Created by hzbc on 2016/5/9.
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

        System.out.println("************data*************");
        System.out.println(kaoLaGoodsInfoSearch.getTimeStamp());

        try {
            Map<String,Object> requestData = getRequestData(kaoLaSysData,parameterMap);
            System.out.println(requestData.get("sign").toString());
            System.out.println("************data*************");
            HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getHost()+"/queryAllGoodsInfo",requestData);
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
    public EventResult queryAllGoodsId() {
        return null;
    }

    @Override
    public EventResult queryGoodsInfoById() {
        return null;
    }
}
