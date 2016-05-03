/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lgj.common.LGJConstant;
import com.huobanplus.erpprovider.lgj.common.LGJSysData;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by elvis on 4/20/16.
 */
public class LGJBaseHandler {

    private static final Log log = LogFactory.getLog(LGJBaseHandler.class);
    /**
     * 获得安全码
     * @param sysData LGJ系统参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getSecretStr(LGJSysData sysData) throws UnsupportedEncodingException {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> requestData = new HashMap<>();
        data.put("func", LGJConstant.GET_SECRETSTR_FUNC);// FIXME: 2016/4/29
        data.put("username",sysData.getUsername());
        data.put("password",sysData.getPassword());
        data.put("api_name",sysData.getApiName());
        data.put("api_secret",sysData.getApiSecret());
        requestData.put("param", JSON.toJSONString(data));


        System.out.println("-------------------------------");
        System.out.println(requestData.get("param"));


        HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
            if("0".equals(resultJson.getString("result").trim())){
                return resultJson.getString("safecode");
            }
            log.error(resultJson.getString("msg")+":错误代码是"+resultJson.getString("result"));
        }
        return null;
    }

    /**
     * 获得Token
     *
     * @param sysData 系统参数
     * @return Token
     */
    public String getToken(LGJSysData sysData){
        String safecode =null;
        try {
            safecode = getSecretStr(sysData);
            if(safecode==null)
                return null;
        } catch (UnsupportedEncodingException e) {
            log.error("获得安全码时："+e.toString());
            return null;
        }
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> requestData = new HashMap<>();
        data.put("func", LGJConstant.GET_TOKEN_FUN);// FIXME: 2016/4/29
        data.put("username",sysData.getUsername());
        data.put("password",sysData.getPassword());
        data.put("api_name",sysData.getApiName());
        data.put("api_secret",sysData.getApiSecret());
        data.put("safecode",safecode);
        requestData.put("param", JSON.toJSONString(data));
        HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getHost(), requestData);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject resultJson = JSON.parseObject(httpResult.getHttpContent());
            if("0".equals(resultJson.getString("result").trim())){
                return resultJson.getString("token");
            }
            log.error(resultJson.getString("msg")+":错误代码是"+resultJson.getString("result"));
        }
        return null;
    }

    /**
     *  得到LGJ需要的请求参数JSON格式的MAP
     * @param map 发送请求是需要的参数
     * @return
     */
    protected Map<String, Object> getParamMap(Map<String, Object> map){
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("param", JSON.toJSONString(map));
        return requestData;
    }
    protected Map<String, Object> getParamMap(String JsonString){
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("param", JsonString);

        System.out.println();
        System.out.println(requestData.get("param"));

        return requestData;
    }

}
