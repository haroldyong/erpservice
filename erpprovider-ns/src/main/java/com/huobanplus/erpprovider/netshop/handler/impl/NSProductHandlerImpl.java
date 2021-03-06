/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.bean.NSGoodDetailResult;
import com.huobanplus.erpprovider.netshop.bean.NSGoodItemResult;
import com.huobanplus.erpprovider.netshop.bean.NSGoodResult;
import com.huobanplus.erpprovider.netshop.exceptionhandler.NSExceptionHandler;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpservice.datacenter.model.MallGoods;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.ObtainGoodListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.ObtainGoodListInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品事件实现类
 */
@Component
public class NSProductHandlerImpl implements NSProductHandler {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult obtainGoods(String goodType, String goodBn, String goodName, int pageSize, Integer pageIndex, ERPUserInfo erpUserInfo, String mType) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            ObtainGoodListEvent obtainGoodListEvent = new ObtainGoodListEvent();
            obtainGoodListEvent.setErpUserInfo(erpUserInfo);
            ObtainGoodListInfo obtainGoodListInfo = new ObtainGoodListInfo();
            obtainGoodListInfo.setGoodType(goodType);
            obtainGoodListInfo.setGoodBn(goodBn);
            obtainGoodListInfo.setGoodName(goodName);
            obtainGoodListInfo.setPageSize(pageSize);
            obtainGoodListInfo.setPageIndex(pageIndex);
            obtainGoodListEvent.setObtainGoodListInfo(obtainGoodListInfo);
            EventResult eventResult = erpUserHandler.handleEvent(obtainGoodListEvent);
            if (eventResult == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }

            List<MallGoods> goodList = (List<MallGoods>) eventResult.getData();
            NSGoodResult goodResult = new NSGoodResult();
            goodResult.setResult(1);
            goodResult.setTotalCount(goodList.size());
            List<NSGoodDetailResult> detailResults = new ArrayList<>();
            goodList.forEach(good -> {
                NSGoodDetailResult goodDetailResult = new NSGoodDetailResult();
                goodDetailResult.setItemID(good.getBn());
                goodDetailResult.setItemName(good.getGoodName());
                goodDetailResult.setOuterID(good.getBn());
                goodDetailResult.setNum(good.getNum());
                goodDetailResult.setPrice(good.getPrice());
                goodDetailResult.setIsSku(good.getIsSku());
                goodDetailResult.setRemark(good.getRemark());
                List<NSGoodItemResult> itemResults = new ArrayList<>();
                good.getProductBeans().forEach(pro -> {
                    NSGoodItemResult goodItemResult = new NSGoodItemResult();
                    goodItemResult.setUnit(pro.getSkuName());
                    goodItemResult.setSkuID(pro.getSkuId());
                    goodItemResult.setNum(pro.getNum());
                    goodItemResult.setSkuOuterID(pro.getSkuId());
                    goodItemResult.setSkuPrice(pro.getPrice());
                    itemResults.add(goodItemResult);
                });
                goodDetailResult.setItemResults(itemResults);
                detailResults.add(goodDetailResult);
            });
            goodResult.setDetailResults(detailResults);
            String xmlResult = new XmlMapper().writeValueAsString(goodResult);
            xmlResult = xmlResult.replaceFirst("<Ware>", "");
            int wareIndex = xmlResult.lastIndexOf("</Ware>");
            String a = xmlResult.substring(0, wareIndex);
            String b = xmlResult.substring(wareIndex + 7);
            xmlResult = a + b;
            //fomat item
            xmlResult = xmlResult.replaceFirst("<Item>", "<Items>");
            int itemIndex = xmlResult.lastIndexOf("</Item>");
            String a1 = xmlResult.substring(0, itemIndex);
            String b1 = xmlResult.substring(itemIndex, xmlResult.length());
            xmlResult = a1 + b1.replaceFirst("</Item>", "</Items>");
            return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }

    @Override
    public EventResult syncInventory(String goodBn, String proBn, int quantity, ERPUserInfo erpUserInfo, String mType) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.NO_DATA, "未找到数据源信息");
            }
            SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
            syncInventoryEvent.setErpUserInfo(erpUserInfo);
            List<ProInventoryInfo> inventoryInfoList = new ArrayList<>();
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setInventory(quantity);
            proInventoryInfo.setSalableInventory(quantity);
            proInventoryInfo.setGoodBn(goodBn);
            proInventoryInfo.setProductBn(proBn);
            inventoryInfoList.add(proInventoryInfo);
            syncInventoryEvent.setInventoryInfoList(inventoryInfoList);

            EventResult eventResult = erpUserHandler.handleEvent(syncInventoryEvent);
            if (eventResult == null) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            return EventResult.resultWith(EventResultEnum.SUCCESS, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result><GoodsType>OnSale</GoodsType><Cause></Cause></Rsp>");
        } catch (Exception e) {
            return NSExceptionHandler.handleException(mType, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
        }
    }
}
