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

package com.huobanplus.erpprovider.wangdianv2.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.wangdianv2.common.WangDianV2SysData;
import com.huobanplus.erpprovider.wangdianv2.handler.WangDianV2ProductHandler;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2StockSearch;
import com.huobanplus.erpprovider.wangdianv2.util.WangDianV2SignBuilder;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wuxiongliu on 2017-02-24.
 */
@Component
public class WangDianV2ProductHandlerImpl implements WangDianV2ProductHandler {

    private static final Log log = LogFactory.getLog(WangDianV2ProductHandlerImpl.class);

    @Override
    public EventResult queryStock(WangDianV2StockSearch wangDianV2StockSearch, WangDianV2SysData wangDianV2SysData) {
        try {
            Date now = new Date();
            // 构建请求参数
            Map<String, Object> requestMap = new TreeMap<>();

            // 系统级参数
            requestMap.put("sid", wangDianV2SysData.getSid());
            requestMap.put("appkey", wangDianV2SysData.getAppKey());
            requestMap.put("shop_no", wangDianV2SysData.getShopNo());
            requestMap.put("timestamp", now.getTime() / 1000);

            // 应用级参数
            if (StringUtil.isNotEmpty(wangDianV2StockSearch.getWarehouseNo())) {
                requestMap.put("warehouse_no", wangDianV2StockSearch.getWarehouseNo());
            }
            if (StringUtil.isNotEmpty(wangDianV2StockSearch.getSpecNo())) {
                requestMap.put("spec_no", wangDianV2StockSearch.getSpecNo());
            }
            if (StringUtil.isNotEmpty(wangDianV2StockSearch.getBarcode())) {
                requestMap.put("barcode", wangDianV2StockSearch.getBarcode());
            }
            requestMap.put("start_time", wangDianV2StockSearch.getStartTime());
            requestMap.put("end_time", wangDianV2StockSearch.getEndTime());
            requestMap.put("page_no", wangDianV2StockSearch.getPageNo());
            requestMap.put("page_size", wangDianV2StockSearch.getPageSize());

            String sign = WangDianV2SignBuilder.buildSign(requestMap, wangDianV2SysData.getAppSecret());
            requestMap.put("sign", sign);
            log.info("order query request sign:" + sign);

            HttpResult httpResult = HttpClientUtil.getInstance().post(wangDianV2SysData.getRequestUrl() + "/openapi2/trade_query.php", requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject resp = JSON.parseObject(httpResult.getHttpContent());
                if (resp.getString("code").equals("0")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, resp);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, resp.getString("message"), null);
                }
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }

        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
