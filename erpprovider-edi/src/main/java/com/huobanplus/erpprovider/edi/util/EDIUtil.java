/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edi.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by wuxiongliu on 2016-09-19.
 */
public class EDIUtil {

    public static String buildSign(String data, String secretKey, String time) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        sb.append(data)
                .append(secretKey)
                .append(time);
//        URLEncoder.encode();
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] md5Data = md.digest(sb.toString().getBytes("utf-8"));
        String base64Data = Base64.getEncoder().encodeToString(md5Data);
        return URLEncoder.encode(base64Data.toUpperCase(), "utf-8");
    }
}
