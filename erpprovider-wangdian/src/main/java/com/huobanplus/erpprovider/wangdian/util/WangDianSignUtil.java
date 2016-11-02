package com.huobanplus.erpprovider.wangdian.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
public class WangDianSignUtil {

    public static String  buildSign(String content, String key) throws UnsupportedEncodingException {
        String toSignStr = content+key;
        String md5Str =  DigestUtils.md5Hex(toSignStr.getBytes("utf-8")).toLowerCase();
        String base64Str = Base64.getEncoder().encodeToString(md5Str.getBytes("utf-8"));
        String sign = URLEncoder.encode(base64Str,"utf-8");
        return sign;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = "{\"test\":1}";
        String key = "12345";
        String sign = buildSign(content,key);
        System.out.println(sign);

    }
}
