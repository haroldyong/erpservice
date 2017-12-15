/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

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
    public Map<String, Object> getSysRequestData(GjbcSysData gjbcSysdata,String mark) throws UnsupportedEncodingException {
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("seller_name", Base64.encodeBase64String(gjbcSysdata.getName().getBytes("utf-8")));
        requestData.put("api_key", Base64.encodeBase64String(gjbcSysdata.getKey().getBytes("utf-8")));
        requestData.put("mark", Base64.encodeBase64String(mark.getBytes("utf-8")));
        requestData.put("confirm", Base64.encodeBase64String(GjbcConstant.CONFIRM_STATUS.getBytes("utf-8")));
        return requestData;
    }
}
