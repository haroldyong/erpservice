package com.huobanplus.erpprovider.gjbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.handler.BaseHandler;
import com.huobanplus.erpprovider.gjbc.handler.GJBCProductHandler;
import com.huobanplus.erpprovider.gjbc.response.GjbcInventorySearchListResponse;
import com.huobanplus.erpprovider.gjbc.search.GjbcInventorySearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by hot on 2017/11/29.
 */
@Component
public class GJBCProductHandlerImpl extends BaseHandler implements GJBCProductHandler {


    @Override
    public EventResult getProductInventoryInfo(GjbcSysData sysData, GjbcInventorySearch gjbcInventorySearch) throws UnsupportedEncodingException {
        //获得请求地址
        String requestUrl = sysData.getRequestUrl();
//组装系统级参数
        Map<String, Object> requestMap = getSysRequestData(sysData, "inventory");

//组装普通参数
        String data = Base64.encodeBase64String(JSON.toJSONString(gjbcInventorySearch).getBytes("utf-8"));
        requestMap.put("order", Base64.encodeBase64String(data.getBytes("utf-8")));
//请求数据
        HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestMap);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
            if ("ok".equals(jsonObject.get("sysResponseCode"))) {
                return EventResult.resultWith(EventResultEnum.SUCCESS, null, JSON.parseArray(jsonObject.getString("inventory"), GjbcInventorySearchListResponse.class));
            }
            return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
    }
}
