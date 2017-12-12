/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpuser.huobanmall.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.model.*;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpuser.huobanmall.common.ApiResult;
import com.huobanplus.erpuser.huobanmall.common.HBConstant;
import com.huobanplus.erpuser.huobanmall.handler.HBGoodHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 8/3/16.
 */
@Service
public class HBGoodHandlerImpl implements HBGoodHandler {

    private Log log = LogFactory.getLog(HBGoodHandlerImpl.class);

    @Override
    public EventResult syncProInventory(List<ProInventoryInfo> proInventoryInfoList, ERPUserInfo erpUserInfo) {
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("inventoryInfoListJson", JSON.toJSONString(proInventoryInfoList));
        signMap.put("customerId", erpUserInfo.getCustomerId());
        signMap.put("userType",erpUserInfo.getErpUserType().getCode());

        log.info("enter syncProInventory ");
//        log.info(JSON.toJSONString(signMap));
        try {
            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, HBConstant.SECRET_KEY);
            signMap.put("sign", sign);

//            log.info(HBConstant.REQUEST_URL + "/ErpGood/BatchSyncInventory");

            HttpResult httpResult = HttpClientUtil.getInstance().post(HBConstant.REQUEST_URL + "/ErpGood/BatchSyncInventory", signMap);
            log.info(httpResult.getHttpStatus());
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                log.info(httpResult.getHttpContent());
                ApiResult<List<ProInventoryInfo>> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<List<ProInventoryInfo>>>() {
                });
//                log.info("syncProInventory next");

                if (apiResult.getCode() == 200) {
//                    log.info("syncProInventory ok");
                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
                }

//                log.info(apiResult.getMsg());
                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
            }
//            log.info("syncProInventory error");
            return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
        } catch (IOException e) {
            log.info(e);
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult obtainProductListInfo(ProductSearchInfo productSearchInfos, ERPUserInfo erpUserInfo) {
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("productSearchInfo", JSON.toJSONString(productSearchInfos));
        signMap.put("customerId", erpUserInfo.getCustomerId());
        signMap.put("timestamp", new Date().getTime());
        try {
            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, HBConstant.SECRET_KEY);
            signMap.put("sign", sign);
            HttpResult httpResult = HttpClientUtil.getInstance().post(HBConstant.REQUEST_URL + "/ErpGood/todo", signMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                ApiResult<ProductListInfo> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<ProductListInfo>>() {
                });
                if (apiResult.getCode() == 200) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
                }
                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
            }
            return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }


    @Override
    public EventResult obtainAllProductList(ERPUserInfo erpUserInfo)
    {
        Map<String, Object> signMap = new TreeMap<>();
        signMap.put("customerId", erpUserInfo.getCustomerId());
        signMap.put("userType",erpUserInfo.getErpUserType().getCode());
        signMap.put("timestamp", System.currentTimeMillis());
        try {
            String sign = SignBuilder.buildSignIgnoreEmpty(signMap, null, HBConstant.SECRET_KEY);
            signMap.put("sign", sign);
            HttpResult httpResult = HttpClientUtil.getInstance().post(HBConstant.REQUEST_URL + "/ErpGood/ObtainAllProductList", signMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                ApiResult<List<SkusInfo>> apiResult = JSON.parseObject(httpResult.getHttpContent(), new TypeReference<ApiResult<List<SkusInfo>>>() {
                });
                if (apiResult.getCode() == 200) {

//                    log.info(HBConstant.REQUEST_URL + "/ErpGood/ObtainAllProductList/" + erpUserInfo.getCustomerId());

                    //log.info("obtainAllProductList result:" + apiResult.getData());
                    return EventResult.resultWith(EventResultEnum.SUCCESS, apiResult.getData());
                }
                log.info(apiResult.getMsg());
                return EventResult.resultWith(EventResultEnum.ERROR, apiResult.getMsg(), null);
            }
            else
            {
                log.info(httpResult.getHttpStatus());
            }

            return EventResult.resultWith(EventResultEnum.SYSTEM_BAD_REQUEST, httpResult.getHttpContent(), null);
        } catch (IOException e) {
            log.info(e);
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }


}
