/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gy.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.gy.common.GYConstant;
import com.huobanplus.erpprovider.gy.common.GYSysData;
import com.huobanplus.erpprovider.gy.formatgy.goods.*;
import com.huobanplus.erpprovider.gy.handler.GYBaseHandler;
import com.huobanplus.erpprovider.gy.handler.GYGoodsHandler;
import com.huobanplus.erpprovider.gy.search.GYGoodsSearch;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by wuxiongliu on 2016/6/17.
 */
@Component
public class GYGoodsHandlerImpl extends GYBaseHandler implements GYGoodsHandler {

    private static final Log log = LogFactory.getLog(GYGoodsHandlerImpl.class);

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult goodsQuery(GYGoodsSearch goodsSearch, GYSysData gySysData) {
        try {

           String requestData = getRequestData(gySysData, goodsSearch, GYConstant.GOODS_QUERY);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                System.out.println("\n************************************************");
                System.out.println(result.toJSONString());
                System.out.println("************************************************");
                if(result.getBoolean("success")){

                    GyRespGoodsSearch gyRespGoodsSearch = JSONObject.parseObject(httpResult.getHttpContent(), GyRespGoodsSearch.class);

//                    List<GYResponseGoodsItem> gyResponseGoodsItems = new ArrayList<>();
//
//                    JSONArray jsonArray = result.getJSONArray("items");
//                    jsonArray.forEach(item->{
//                        System.out.println("********************");
//                        System.out.println(item);
//                        GYResponseGoodsItem gyResponseGoodsItem = JSONObject.parseObject(item.toString(),GYResponseGoodsItem.class);
//                        gyResponseGoodsItems.add(gyResponseGoodsItem);
//                    });
                    return EventResult.resultWith(EventResultEnum.SUCCESS, gyRespGoodsSearch);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult pushGoods(GYGoods gyGoods, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyGoods, GYConstant.GOODS_ADD);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult updateGoods(GYGoods gyGoods, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyGoods, GYConstant.GOODS_UPDATE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult deleteGoods(GYDeleteGoods gyDeleteGoods, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyDeleteGoods, GYConstant.GOODS_DELETE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult pushGoodsSku(GYGoodsSku gyGoodsSku, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyGoodsSku, GYConstant.GOODS_SKU_ADD);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult updateGoodsSku(GYGoodsSku gyGoodsSku, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyGoodsSku, GYConstant.GOODS_SKU_UPDATE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult deleteGoodsSku(GYDeleteSku gyDeleteSku, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyDeleteSku, GYConstant.GOODS_SKU_DELETE);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }

    @SuppressWarnings("Duplicates")
    @Override
    public EventResult pushGoodsBarCode(GYGoodsBarCode gyGoodsBarCode, GYSysData gySysData) {
        try {

            String requestData = getRequestData(gySysData, gyGoodsBarCode, GYConstant.GOODS_BARCODE_ADD);
            HttpResult httpResult = HttpClientUtil.getInstance().post(gySysData.getRequestUrl(),requestData);
            if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
                JSONObject result = JSONObject.parseObject(httpResult.getHttpContent());
                if(result.getBoolean("success")){
                    // TODO: 2016/6/17
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                }else{
                    log.info("错误信息："+result.getString("errorDesc"));
                    return EventResult.resultWith(EventResultEnum.ERROR,result.getString("errorDesc"),null);
                }
            }else{
                return EventResult.resultWith(EventResultEnum.ERROR,httpResult.getHttpContent(),null);
            }
        } catch (IOException e) {
            log.error(e);
            return EventResult.resultWith(EventResultEnum.ERROR,e.getMessage(),null);
        }
    }
}
