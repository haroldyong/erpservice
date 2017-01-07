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

package com.huobanplus.erpprovider.dtw.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.dtw.common.DtwEnum;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPurchaseOrder;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPurchaseOrderItem;
import com.huobanplus.erpprovider.dtw.handler.DtwPuchaseHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.PurchaseOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrder;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrderItem;
import com.huobanplus.erpservice.datacenter.repository.logs.PurchaseOrderSyncLogRepository;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushPurchaseOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
@Component
public class DtwPurchaseHandlerImpl implements DtwPuchaseHandler {


    private static final Log log = LogFactory.getLog(DtwPurchaseHandlerImpl.class);

    @Autowired
    private PurchaseOrderSyncLogRepository purchaseOrderSyncLogRepository;

    @Override
    public EventResult purchaseOrderPush(DtwPurchaseOrder dtwPurchaseOrder, DtwSysData dtwSysData) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("data", JSON.toJSONString(dtwPurchaseOrder));

        try {
            HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl() + "/QBRorder", requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject result = JSON.parseObject(httpResult.getHttpContent());
                if (result.getString("ErrCode").equals("000")) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS);
                } else {
                    return EventResult.resultWith(EventResultEnum.ERROR, result.getString("ErrMsg"), null);
                }
            } else {
                log.error("Server Request Failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, "服务器请求失败", null);
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult pushPurchaseOrder(PushPurchaseOrderEvent pushPurchaseOrderEvent) {
        PurchaseOrder purchaseOrder = JSON.parseObject(pushPurchaseOrderEvent.getPurchaseOrderJson(), PurchaseOrder.class);
        log.info(pushPurchaseOrderEvent.getPurchaseOrderJson());
        ERPInfo erpInfo = pushPurchaseOrderEvent.getErpInfo();
        DtwSysData dtwSysData = JSON.parseObject(erpInfo.getSysDataJson(), DtwSysData.class);
        ERPUserInfo erpUserInfo = pushPurchaseOrderEvent.getErpUserInfo();
        Date now = new Date();

        PurchaseOrderSyncLog purchaseOrderSyncLog = purchaseOrderSyncLogRepository.findByReceiveNo(purchaseOrder.getReceiveNo());
        if (purchaseOrderSyncLog == null) {
            purchaseOrderSyncLog = new PurchaseOrderSyncLog();
            purchaseOrderSyncLog.setCreateTime(now);
        }

        purchaseOrderSyncLog.setCustomerId(erpUserInfo.getCustomerId());
        purchaseOrderSyncLog.setProviderType(erpInfo.getErpType());
        purchaseOrderSyncLog.setUserType(erpUserInfo.getErpUserType());
        purchaseOrderSyncLog.setReceiveNo(purchaseOrder.getReceiveNo());
        purchaseOrderSyncLog.setPurchaseOrderJson(pushPurchaseOrderEvent.getPurchaseOrderJson());
        purchaseOrderSyncLog.setErpSysData(erpInfo.getSysDataJson());
        purchaseOrderSyncLog.setSyncTime(now);
        purchaseOrderSyncLog.setBlno(purchaseOrder.getBolNo());
        purchaseOrderSyncLog.setSupplierId(purchaseOrder.getSupplierId());

        DtwPurchaseOrder dtwPurchaseOrder = convert2ErpPurchaseOrder(purchaseOrder, dtwSysData);
        EventResult eventResult = purchaseOrderPush(dtwPurchaseOrder, dtwSysData);

        try {
            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                purchaseOrderSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
            } else {
                purchaseOrderSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                purchaseOrderSyncLog.setErrorMsg(eventResult.getResultMsg());
            }

            purchaseOrderSyncLogRepository.save(purchaseOrderSyncLog);
            log.info("DtwPurchaseHandlerImpl-pushPurchaseOrder:PurchaseOrder push complete");
            return eventResult;
        } catch (Exception e) {
            log.info("DtwPurchaseHandlerImpl-pushPurchaseOrder-ERROR:" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    private DtwPurchaseOrder convert2ErpPurchaseOrder(PurchaseOrder purchaseOrder, DtwSysData dtwSysData) {
        DtwPurchaseOrder dtwPurchaseOrder = new DtwPurchaseOrder();

        dtwPurchaseOrder.setPassKey(dtwSysData.getPassKey());
        dtwPurchaseOrder.setMsgId(purchaseOrder.getReceiveNo());
        dtwPurchaseOrder.setSupplier(purchaseOrder.getSupplierId());
        dtwPurchaseOrder.setECommerceCode(dtwSysData.getECommerceCode());
        dtwPurchaseOrder.setECommerceName(dtwSysData.getECommerceName());
        dtwPurchaseOrder.setHawb(purchaseOrder.getBolNo());
        dtwPurchaseOrder.setMawb("");

        List<DtwPurchaseOrderItem> dtwPurchaseOrderItems = new ArrayList<>();

        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrder.getItems();
        for (int i = 0; i < purchaseOrderItems.size(); i++) {

            PurchaseOrderItem purchaseOrderItem = purchaseOrderItems.get(i);

            DtwPurchaseOrderItem dtwPurchaseOrderItem = new DtwPurchaseOrderItem();
            dtwPurchaseOrderItem.setMsgItem(i + 1);
            dtwPurchaseOrderItem.setPartNo(purchaseOrderItem.getProductBn());
            dtwPurchaseOrderItem.setPartName(purchaseOrderItem.getProductName());
            dtwPurchaseOrderItem.setSpec(purchaseOrderItem.getStandard());
            dtwPurchaseOrderItem.setInvoiceNo(purchaseOrderItem.getInvoiceNo());
            dtwPurchaseOrderItem.setHsCode(purchaseOrderItem.getGoodsBn());
            dtwPurchaseOrderItem.setBatch("");
            dtwPurchaseOrderItem.setQty(purchaseOrderItem.getQty());
            dtwPurchaseOrderItem.setUnit(purchaseOrderItem.getUnit());
            dtwPurchaseOrderItem.setDref1("");
            dtwPurchaseOrderItem.setDref2("");
            dtwPurchaseOrderItem.setDref3("");
            dtwPurchaseOrderItem.setDref4("");
            dtwPurchaseOrderItem.setCurrency(DtwEnum.CurrencyEnum.RMB.getCode());
            dtwPurchaseOrderItem.setOriginCountry(DtwEnum.CountryEnum.Germany.getCode());
            dtwPurchaseOrderItem.setAmount(purchaseOrderItem.getAmount());
            dtwPurchaseOrderItems.add(dtwPurchaseOrderItem);
        }

        dtwPurchaseOrder.setDtwPurchaseOrderItems(dtwPurchaseOrderItems);
        return dtwPurchaseOrder;
    }
}
