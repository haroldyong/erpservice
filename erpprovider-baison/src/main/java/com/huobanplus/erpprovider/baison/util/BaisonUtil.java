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

import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpservice.common.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-11-10.
 */
public class BaisonUtil {

    /**
     * @param params TreeMap
     * @return
     */
    public static String buildSign(Map<String, Object> params) throws UnsupportedEncodingException {

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            stringBuilder.append(next.getKey()).append("=").append(next.getValue()).append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        System.out.println("\nsource string:" + stringBuilder.toString());
        return DigestUtils.md5Hex(stringBuilder.toString().getBytes("utf-8")).toLowerCase();
    }

    public static Map<String, Object> buildRequestMap(BaisonSysData baisonSysData, String serviceType, String data) throws UnsupportedEncodingException {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("key", baisonSysData.getBaisonAppkey());
        requestMap.put("requestTime", StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN2));// TODO: 2016-12-27
        requestMap.put("serviceType", serviceType);
        requestMap.put("data", data);
        requestMap.put("version", baisonSysData.getVersion());
        String sign = BaisonUtil.buildSign(requestMap);
        requestMap.put("sign", sign);
        return requestMap;
    }
}
