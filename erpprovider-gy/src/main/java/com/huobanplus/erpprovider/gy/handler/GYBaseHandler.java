/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import java.io.UnsupportedEncodingException;

/**
 * create by elvis 2016/5/6
 */
public class GYBaseHandler {


    /**
     * 得到一个请求参数map
     *
     * @param sysData 网仓系统参数
     * @param now     当前时间--yyyy-MM-dd HH:mm:ss
     * @param method  接口方法
     * @param data    数据
     * @return
     * @throws UnsupportedEncodingException
     */
/*    protected Map<String, Object> getRequestData(ISCSSysData sysData, String now, String method, String data) throws UnsupportedEncodingException {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("v_appkey", sysData.getAppKey());
        requestData.put("v_timestamp", now);
        requestData.put("v_method", method);
        requestData.put("v_data", data);
        requestData.put("v_appsign", buildSign(sysData, now));
        return requestData;
    }*/
}
