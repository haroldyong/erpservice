/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.handler;

import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;

import java.util.Date;
import java.util.Map;

/**
 * Created by allan on 22/03/2017.
 */
public class WangDianV2HandlerBase {
    protected void setSysDataRequestMap(Map<String, Object> requestMap, WangDianV2SysData wangDianV2SysData) {
        requestMap.put("sid", wangDianV2SysData.getWangdianv2Sid());
        requestMap.put("appkey", wangDianV2SysData.getAppKey());
        requestMap.put("timestamp", new Date().getTime() / 1000);
    }
}
