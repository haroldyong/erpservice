package com.huobanplus.erpprovider.gjbbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpprovider.gjbbc.handler.BaseHandler;
import com.huobanplus.erpprovider.gjbbc.handler.GJBBCProductHandler;
import com.huobanplus.erpprovider.gjbbc.response.GjbbcInventorySearchListResponse;
import com.huobanplus.erpprovider.gjbbc.search.GjbbcInventorySearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by hot on 2017/11/30.
 */
@Service
public class GJBBCProductHandlerImpl extends BaseHandler implements GJBBCProductHandler {
    @Override
    public EventResult getProductInventoryInfo(GjbbcSysData sysData, GjbbcInventorySearch gjbcInventorySearch) throws Exception {
        //获得请求地址
        String requestUrl = sysData.getRequestUrl();

//组装系统级参数
        Map<String, Object> requestMap = getSysRequestData(sysData, "select_inventory");

//组装普通参数
        String data = Base64.encodeBase64String(JSON.toJSONString(gjbcInventorySearch).getBytes("utf-8"));
        requestMap.put("order", data);
//请求数据
        HttpResult httpResult = HttpClientUtil.getInstance().post(requestUrl, requestMap);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
            if ("ok".equals(jsonObject.get("sysResponseCode"))) {
                return EventResult.resultWith(EventResultEnum.SUCCESS, null, JSON.parseArray(jsonObject.getString("inventory"),GjbbcInventorySearchListResponse.class));
            }
            return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
    }
}
