/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.util;

import com.huobanplus.erpprovider.dtw.common.DtwConstant;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import javax.xml.namespace.QName;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2016-07-26.
 */
public class DtwUtil {

    /**
     * 支付宝 MD5签名 剔除掉sign，sign_type
     *
     * @param requestMap
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String aliBuildSign(Map<String, Object> requestMap) throws UnsupportedEncodingException {
        StringBuilder signStr = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = requestMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry next = (Map.Entry) iterator.next();
            String key = (String) next.getKey();
            if (key.equals("sign") || key.equals("sign_type")) {
                continue;
            }
            signStr.append(key).append(next.getValue());
        }
        return DigestUtils.md5Hex(signStr.toString().getBytes("utf-8")).toUpperCase();
    }

    /**
     * 微信支付 MD5签名
     *
     * @param requestMap
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String weixinBuildSign(Map<String, Object> requestMap, String key) throws UnsupportedEncodingException {
        StringBuilder signStr = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = requestMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (!StringUtils.isEmpty(next.getValue())) {
                signStr.append(next.getKey())
                        .append("=")
                        .append(next.getValue())
                        .append("&");
            }
        }
        String stringSignTemp = signStr.append("key=").append(key).toString();
        return DigestUtils.md5Hex(stringSignTemp.getBytes("utf-8")).toUpperCase();
    }

    public static Map<String, Object> convertBeanToMap(Object object) throws IllegalAccessException {
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String, Object> result = new TreeMap<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            result.put(field.getName(), field.get(object));
        }
        return result;
    }

    public static String requestCustomWebService(String content, String msgType, String dataDigest, String sendCode) throws Exception {
        Service service = new Service();

        Call call = (Call) service.createCall();
        call.setTimeout(100000);
        call.setTargetEndpointAddress(DtwConstant.CUSTOM_WEBSERVICE_URL);
        call.setOperationName(new QName(DtwConstant.CUSTOM_TARGET_NAMESPACE, "receive"));
        call.addParameter("content", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//报文密文
        call.addParameter("msgType", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//报文类型
        call.addParameter("dataDigest", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//签名
        call.addParameter("sendCode", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);//发送方代码
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
        return (String) call.invoke(new Object[]{content, msgType, dataDigest, sendCode});
    }
}
