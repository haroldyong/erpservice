/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.wangdianv2.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
public class WangDianV2SignBuilder {

    /**
     * 旺店通v2版本，签名构建工具
     *
     * @param params
     * @param secret
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String buildSign(Map<String, Object> params, String secret) throws UnsupportedEncodingException {
        if (secret == null) {
            secret = "";
        }

        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            if (next.getValue() == null) {
                continue;
            }
            sb.append(buildParam(2, next.getKey()))
                    .append(":")
                    .append(buildParam(4, next.getValue().toString())).append(";");
        }

        String tempStr = sb.toString();
        tempStr = tempStr.substring(0, tempStr.lastIndexOf(";"));
        String toEncodeStr = tempStr + secret;

        System.out.println("\n*******ToSign String************");
        System.out.println(tempStr);
        System.out.println("\n*******************");

        return DigestUtils.md5Hex(toEncodeStr.getBytes("utf-8"));
    }

    public static String buildParam(int len, String param) {
        int strLen = param.length();
        int zeroNum = len - String.valueOf(strLen).length();
        return buildZeroStr(zeroNum) + strLen + "-" + param;
    }


    public static String buildZeroStr(int zeroNum) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < zeroNum; i++) {
            sb.append("0");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, Object> param = new TreeMap<>();
//        param.put("sid","test");
//        param.put("appkey","apptest");
//        param.put("status","0");
//        param.put("img_url","0");
//        param.put("page_no","0");
//        param.put("page_size","40");
//        param.put("start_time","2015-01-01 10:00:00");
//        param.put("end_time","2015-01-01 11:00:00");
//        param.put("timestamp","1420084800");

        param.put("key1", "value1");
        param.put("key2", "value2");

        String sign = buildSign(param, "12345");// expect 2b65a10ed4154187f82880cfd841c09f
        System.out.println(sign);

    }
}
