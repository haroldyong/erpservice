package com.huobanplus.erpprovider.edb.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 创建sign签名
 * Created by allan on 2015/7/28.
 */
public class SignBuilder {
    /**
     * 创建一个sign签名
     *
     * @param params 代签名参数，key排序的map
     * @param prefix
     * @param suffix
     * @return
     */
    public static String buildSign(Map<String, String> params, String prefix, String suffix) {
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
        return DigestUtils.md5Hex(stringBuilder.toString()).toUpperCase();
    }
}
