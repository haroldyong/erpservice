/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.formatedb.EDBProStockInfo;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBProductHandler;
import com.huobanplus.erpprovider.edb.search.EDBStockSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/7/28.
 */
@Component
public class EDBProductHandlerImpl extends BaseHandler implements EDBProductHandler {
    private static final Log log = LogFactory.getLog(EDBProductHandlerImpl.class);

    @Override
    public EventResult getProInventoryInfo(EDBSysData sysData, EDBStockSearch storeSearch) throws IOException {
        try {
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_PRO_INFO, sysData);
            requestData.put("page_no", storeSearch.getPageIndex());
            requestData.put("page_size", EDBConstant.PAGE_SIZE);
            requestData.put("store_id", storeSearch.getStoreId());
            Map<String, Object> signMap = new TreeMap<>(requestData);
            requestData.put("sign", getSign(signMap, sysData));
            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);

            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent()).getJSONObject("Success");
                if (result != null) {
                    JSONArray resultArray = result.getJSONObject("items").getJSONArray("item");
                    List<EDBProStockInfo> proStockInfoList = JSON.parseArray(resultArray.toJSONString(), EDBProStockInfo.class);
                    return EventResult.resultWith(EventResultEnum.SUCCESS, proStockInfoList);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, "获取库存信息失败", null);
                }
            }
            return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, "获取库存信息失败-服务器请求失败", null);
        } catch (Exception e) {
            log.info("EDB获取库存信息失败");
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
