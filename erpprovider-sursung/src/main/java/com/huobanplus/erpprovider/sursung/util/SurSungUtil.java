/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.util;

import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungUtil {

    public static String buildSign(String method, int time, String partnerId, String token, String partnerKey) throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();
        sb.append(method)
                .append(partnerId)
                .append("ts").append(time)
                .append("token").append(token)
                .append(partnerKey);

        return DigestUtils.md5Hex(sb.toString().getBytes("utf-8"));
    }

    public static String createRequestUrl(String method, int time, SurSungSysData surSungSysData)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(surSungSysData.getRequestUrl()).append("?")
                .append("ts=").append(time).append("&")
                .append("partnerid=").append(surSungSysData.getPartnerId()).append("&")
                .append("method=").append(method).append("&")
                .append("token=").append(surSungSysData.getToken()).append("&")
                .append("sign=").append(buildSign(method, time, surSungSysData.getPartnerId(),
                surSungSysData.getToken(), surSungSysData.getPartnerKey()));
        return sb.toString();
    }
}
