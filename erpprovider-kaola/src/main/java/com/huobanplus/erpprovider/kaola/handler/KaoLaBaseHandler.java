package com.huobanplus.erpprovider.kaola.handler;

import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpservice.common.util.SignBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016/5/9.
 */
public class KaoLaBaseHandler {

    /**
     * 创建签名密钥
     *
     * @param sysData 考拉系统参数
     * @param parameterMap    参数列表
     * @return
     * @throws UnsupportedEncodingException
     */
//    protected String buildSign(KaoLaSysData sysData, Map<String,Object> parameterMap) throws UnsupportedEncodingException {
//        StringBuilder signStr = new StringBuilder();
//        String parStr = "";
//        Iterator iterator = parameterMap.entrySet().iterator();
//
//        while (iterator.hasNext()){
//            Map.Entry ent=(Map.Entry )iterator.next();
//            String key=ent.getKey().toString();
//            String value=ent.getValue().toString();
//            parStr += key+value;
//        }
//        signStr.append(sysData.getAppSecret()).append(parStr).append(sysData.getAppSecret());
//        return DigestUtils.md5Hex(signStr.toString().getBytes("utf-8")).toUpperCase();
//    }


    /**
     * 得到一个请求参数map
     *
     * @param sysData 考拉系统参数
     * @param parameterMap     请求参数列表map
     * @return
     * @throws UnsupportedEncodingException
     */
    protected Map<String, Object> getRequestData(KaoLaSysData sysData,Map<String,Object> parameterMap) throws UnsupportedEncodingException {
        parameterMap.put("sign",SignBuilder.buildSign(parameterMap,sysData.getAppSecret(),sysData.getAppSecret()));
        return parameterMap;
    }
}
