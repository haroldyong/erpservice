/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-07-26.
 */
public class SignHelper {

    protected String buildSign(Map<String,Object> parameterMap) throws UnsupportedEncodingException {
        StringBuilder signStr = new StringBuilder();
        String parStr = "";
        Iterator iterator = parameterMap.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry ent=(Map.Entry )iterator.next();
            String key=ent.getKey().toString();
            String value=ent.getValue().toString();
            parStr += key+value;
        }
        return DigestUtils.md5Hex(signStr.toString().getBytes("utf-8")).toUpperCase();
    }
}
