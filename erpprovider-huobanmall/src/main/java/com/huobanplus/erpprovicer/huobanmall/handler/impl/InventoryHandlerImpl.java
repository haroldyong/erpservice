package com.huobanplus.erpprovicer.huobanmall.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovicer.huobanmall.common.SimpleMonitor;
import com.huobanplus.erpprovicer.huobanmall.handler.InventoryHandler;
import com.huobanplus.erpprovicer.huobanmall.util.Constant;
import com.huobanplus.erpprovicer.huobanmall.util.HttpUtil;
import com.huobanplus.erpprovicer.huobanmall.util.SignBuilder;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>类描述：<p/>
 * 伙伴商城库存事件实现
 */
public class InventoryHandlerImpl implements InventoryHandler {

    @Override
    public Monitor<EventResult> commitInventoryInfo(HttpServletRequest request) throws IOException {

        String sign = (String) request.getAttribute("sign");
        String secret = (String) request.getAttribute("secret");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));

        //库存参数
        Map<String, String> inventoryMap = new TreeMap<>();
        inventoryMap.put("inStorageNo", request.getParameter("inStorageNo"));
        inventoryMap.put("freight", request.getParameter("freight"));
        inventoryMap.put("freightAvgway", request.getParameter("freightAvgway"));
        inventoryMap.put("barCode", request.getParameter("barCode"));
        inventoryMap.put("instorageNum", request.getParameter("instorageNum"));
        inventoryMap.put("type", request.getParameter("type"));
        inventoryMap.put("dateType", request.getParameter("dateType"));
        inventoryMap.put("beginTime", request.getParameter("beginTime"));
        inventoryMap.put("endTime", request.getParameter("endTime"));
        inventoryMap.put("storageName", request.getParameter("storageName"));
        inventoryMap.put("instorageStatus", request.getParameter("instorageStatus"));
        inventoryMap.put("importMark", request.getParameter("importMark"));
        inventoryMap.put("typeNo", request.getParameter("typeNo"));
        inventoryMap.put("provider", request.getParameter("provider"));
        inventoryMap.put("storage", request.getParameter("storage"));
        inventoryMap.put("creater", request.getParameter("creater"));
        inventoryMap.put("createTime", request.getParameter("createTime"));
        inventoryMap.put("inTime", request.getParameter("inTime"));
        inventoryMap.put("qualityInspctor", request.getParameter("qualityInspctor"));
        inventoryMap.put("inspctTime", request.getParameter("inspctTime"));
        inventoryMap.put("inspctResult", request.getParameter("inspctResult"));
        inventoryMap.put("examiner", request.getParameter("examiner"));
        inventoryMap.put("examineTime", request.getParameter("examineTime"));
        inventoryMap.put("inReason", request.getParameter("inReason"));
        inventoryMap.put("cost", request.getParameter("cost"));
        inventoryMap.put("SourceTid", request.getParameter("SourceTid"));
        inventoryMap.put("purchaseFee", request.getParameter("purchaseFee"));
        inventoryMap.put("contractMoney", request.getParameter("contractMoney"));
        inventoryMap.put("relevantTid", request.getParameter("relevantTid"));
        inventoryMap.put("rate", request.getParameter("rate"));
        inventoryMap.put("currency", request.getParameter("currency"));
        inventoryMap.put("outContractTid", request.getParameter("outContractTid"));
        inventoryMap.put("logistics", request.getParameter("logistics"));
        inventoryMap.put("expressTid", request.getParameter("expressTid"));
        inventoryMap.put("freightPayer", request.getParameter("freightPayer"));
        inventoryMap.put("remarks", request.getParameter("remarks"));
        inventoryMap.put("freightMode", request.getParameter("freightMode"));
        inventoryMap.put("storageNo", request.getParameter("storageNo"));
        inventoryMap.put("listSource", request.getParameter("listSource"));
        inventoryMap.put("otherCost", request.getParameter("otherCost"));
        inventoryMap.put("outPactNo", request.getParameter("outPactNo"));
        inventoryMap.put("productItemNo", request.getParameter("productItemNo"));
        inventoryMap.put("locationNo", request.getParameter("locationNo"));
        inventoryMap.put("batch", request.getParameter("batch"));
        inventoryMap.put("expireTime", request.getParameter("expireTime"));
        inventoryMap.put("supplieNo", request.getParameter("supplieNo"));
        inventoryMap.put("YSInstorageNo", request.getParameter("YSInstorageNo"));
        inventoryMap.put("freightAvg", request.getParameter("freightAvg"));
        signMap.putAll(inventoryMap);
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign))
        {
            return new SimpleMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(inventoryMap);
            //todo 将获取的信息推送给ERP
            String result = HttpUtil.getInstance().doPost(null, null);

            if (result == null) {
                return new SimpleMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }

            return new SimpleMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
        }
    }
}
