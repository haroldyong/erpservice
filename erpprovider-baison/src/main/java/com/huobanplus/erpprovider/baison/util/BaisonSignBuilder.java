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

package com.huobanplus.erpprovider.baison.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-11-10.
 */
public class BaisonSignBuilder {

    /**
     * @param params TreeMap
     * @param prefix
     * @param suffix
     * @return
     */
    public static String buildSign(Map<String, Object> params, String prefix, String suffix) throws UnsupportedEncodingException {
        if (prefix == null)
            prefix = "";
        if (suffix == null)
            suffix = "";

        StringBuilder stringBuilder = new StringBuilder(prefix);
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            stringBuilder.append(next.getKey()).append(next.getValue());
        }
        stringBuilder.append(suffix);
        System.out.println("\nsource string:" + stringBuilder.toString());
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8")).toLowerCase();
    }
}
