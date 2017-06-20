package com.huobanplus.erpprovider.pineapple.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.pineapple.handler.BLPProductHandler;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxh on 2017-06-19.
 */
@Service
public class BLPProductHandlerImpl implements BLPProductHandler {

    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult syncStock(String platProductID, String skuId, String outerId, String outSkuId, int quantity, ERPUserInfo erpUserInfo, String method) {
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (erpUserHandler == null) {
            return EventResult.resultWith(EventResultEnum.NO_DATA, "未找到数据源信息");
        }
        SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
        syncInventoryEvent.setErpUserInfo(erpUserInfo);
        List<ProInventoryInfo> inventoryInfoList = new ArrayList<>();
        ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
        proInventoryInfo.setInventory(quantity);
        proInventoryInfo.setSalableInventory(quantity);
        inventoryInfoList.add(proInventoryInfo);
        syncInventoryEvent.setInventoryInfoList(inventoryInfoList);
        EventResult eventResult = erpUserHandler.handleEvent(syncInventoryEvent);
        if (eventResult == null) {
            return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
        }
        if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
            return EventResult.resultWith(EventResultEnum.ERROR, eventResult.getResultMsg());
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("Quantity", quantity);
        return EventResult.resultWith(EventResultEnum.SUCCESS, resultJson.toJSONString());
    }
}
