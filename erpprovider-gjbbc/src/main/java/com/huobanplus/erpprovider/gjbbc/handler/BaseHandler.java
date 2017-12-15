package com.huobanplus.erpprovider.gjbbc.handler;

import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpprovider.gjbbc.util.GjbbcConstant;
import org.apache.commons.codec.binary.Base64;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hxh on 2017-08-15.
 */
public class BaseHandler {
    public Map<String, Object> getSysRequestData(GjbbcSysData gjbbcSysdata,String mark) throws Exception {
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("seller", Base64.encodeBase64String(gjbbcSysdata.getName().getBytes("utf-8")));
        requestData.put("api_key", Base64.encodeBase64String(gjbbcSysdata.getKey().getBytes("utf-8")));
        requestData.put("mark", Base64.encodeBase64String(mark.getBytes("utf-8")));
        requestData.put("function", Base64.encodeBase64String(GjbbcConstant.FUNCTION_ORDER.getBytes("utf-8")));
        return requestData;
    }
}
