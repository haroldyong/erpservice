package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.common.GJBCSysData;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hxh on 2017-06-27.
 */

public class BaseHandler {
    /**
     * 得到公用系统参数的requestData
     *
     * @param gjbcSysdata
     * @return
     * @throws UnsupportedEncodingException
     */
    public Map<String, Object> getSysRequestData(GJBCSysData gjbcSysdata) throws UnsupportedEncodingException {
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("seller_name", Base64.encode(gjbcSysdata.getName().getBytes("utf-8")));
        requestData.put("api_key", Base64.encode(gjbcSysdata.getKey().getBytes("utf-8")));
        requestData.put("mark", Base64.encode(gjbcSysdata.getOrder().getBytes("utf-8")));
        requestData.put("confirm", Base64.encode(gjbcSysdata.getConfirm().getBytes("utf-8")));
        requestData.put("order", Base64.encode(gjbcSysdata.getOrder().getBytes("utf-8")));
        return requestData;
    }
}