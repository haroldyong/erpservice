/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * 创建sign签名
 * Created by allan on 2015/7/28.
 */
public class SignBuilder {
    /**
     * 创建一个sign签名
     *
     * @param params 代签名参数，key排序的map
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回加鉴权后生成的Sign
     */
    public static String buildSign(Map<String, String> params, String prefix, String suffix) throws UnsupportedEncodingException {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";
        StringBuilder stringBuilder = new StringBuilder(prefix);
//        Collections.sort(new ArrayList(params.entrySet()), new Comparator<Map.Entry<String, String>>() {
//            @Override
//            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
//                return o1.getKey().compareTo(o2.getKey());
//            }
//        });
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8")).toUpperCase();
    }
}
