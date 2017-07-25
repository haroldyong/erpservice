package com.huobanplus.erpprovider.gjbc.handler;

import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.util.GjbcConstant;
import org.apache.commons.codec.binary.Base64;

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
    public Map<String, Object> getSysRequestData(GjbcSysData gjbcSysdata) throws UnsupportedEncodingException {
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("seller_name", Base64.encodeBase64String(gjbcSysdata.getName().getBytes("utf-8")));
        requestData.put("api_key", Base64.encodeBase64String(gjbcSysdata.getKey().getBytes("utf-8")));
        requestData.put("mark", Base64.encodeBase64String(GjbcConstant.MARK_STATUS.getBytes("utf-8")));
        requestData.put("confirm", Base64.encodeBase64String(GjbcConstant.CONFIRM_STATUS.getBytes("utf-8")));
        return requestData;
    }
}
