/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.util.SignHelper;
import com.sun.javafx.collections.MappingChange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by elvis 2016/5/6
 */
public class GYBaseHandler {


    /**
     * 得到一个请求参数map
     * @param sysData 系统参数
     * @param clazz 请求数据实体
     * @return
     */
    protected <T> Map<String, Object> getRequestData(GYSysData sysData,T clazz) throws IOException {

        //通过标准Json 格式获得sign
        String JsonStr = JSON.toJSONString(clazz);
        JSONObject jsonObject = JSON.parseObject(JsonStr);
        jsonObject.put("appkey",sysData.getAppKey());
        jsonObject.put("sessionkey",sysData.getSessionkey());
        jsonObject.put("method",sysData.getMethod());

        String paramStr = JSON.toJSONString(jsonObject);
        SignHelper signHelper = new SignHelper();
        String sign = signHelper.sign(paramStr,sysData.getSecret());
        jsonObject.put("sign",sign);

        String reqestStr = JSON.toJSONString(jsonObject);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> requestData = mapper.readValue(reqestStr,Map.class);//转成map

        return requestData;
    }
}
