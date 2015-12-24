/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/8/7.
 */
public class BaseHandler {
    /**
     * 得到包含公用系统参数的requestData
     *
     * @return
     */
    protected Map<String, Object> getSysRequestData(String method, EDBSysData sysData) {
        Map<String, Object> requestData = new TreeMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), EDBConstant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", sysData.getDbHost());
        requestData.put("appkey", sysData.getAppKey());
        requestData.put("format", EDBConstant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", EDBConstant.V);
        requestData.put("slencry", EDBConstant.SLENCRY);
        requestData.put("ip", sysData.getIp());
        requestData.put("method", method);
        return requestData;
    }

    /**
     * 得到sign签名
     *
     * @param signMap
     * @return
     */
    protected String getSign(Map signMap, EDBSysData sysData) throws UnsupportedEncodingException {
        signMap.put("appscret", sysData.getAppSecret());
        signMap.put("token", sysData.getToken());
        return SignBuilder.buildSign(signMap, sysData.getAppKey(), "");
    }
}
