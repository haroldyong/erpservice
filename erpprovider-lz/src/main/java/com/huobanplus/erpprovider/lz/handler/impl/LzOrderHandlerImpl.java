package com.huobanplus.erpprovider.lz.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpprovider.lz.util.RSA;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Duplicates")
@Component
public class LzOrderHandlerImpl implements LzOrderHandler {
    private static final Log log = LogFactory.getLog(LzOrderHandlerImpl.class);

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        return null;
    }

    @Override
    public EventResult pushPlatformOrder(Order order, LzSysData lzSysData) {
        return null;
    }

    /**
     * 申请售后
     *
     * @param orderRefundStatusUpdate
     * @return
     * @anthor guomw
     */
    @Override
    public EventResult pushRefund(OrderRefundStatusUpdate orderRefundStatusUpdate) {
        try {
            LzSysData sysData = JSON.parseObject(orderRefundStatusUpdate.getErpInfo().getSysDataJson(), LzSysData.class);
            Map<String, Object> requestMap = new HashMap<>();
            OrderRefundStatusInfo info = orderRefundStatusUpdate.getOrderRefundStatusInfo();
            requestMap.put("order_id", info.getOrderId());
            String jsonStr = JSON.toJSONString(requestMap);

            PrivateKey privateKey = RSA.getPrivateKey(RSA.SIGN_ALGORITHMS);
            String sign = RSA.sign(privateKey, jsonStr, "utf-8");
            if (StringUtils.isBlank(sign)) {
                return EventResult.resultWith(EventResultEnum.ERROR, "数据签名错误", null);
            }
            Map<String, String> headerMap = getCommonHeaderParameter(sysData, sign);
            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl() + "/wms/declCancel", headerMap, jsonStr);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
                if ("true".equalsIgnoreCase(jsonObject.getString("success"))) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, jsonObject.getString("info"), null);
                }
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
            }
        } catch (Exception e) {
            String info = "LzOrderHandler pushRefund failed: " + JSON.toJSONString(orderRefundStatusUpdate) + " | " + e.getMessage();
            log.error(info, e);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
    }

    /**
     * 获取公共Header参数
     *
     * @param lzSysData
     * @param sign      签名
     * @return
     */
    private Map<String, String> getCommonHeaderParameter(LzSysData lzSysData, String sign) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json;charset=UTF-8");
        //WMS服务商分配
        headerMap.put("wmsid", lzSysData.getWmsId());
        //WMS服务商分配
        headerMap.put("storagid", lzSysData.getStorageId());
        /**
         * 商户号
         */
        headerMap.put("merchantid", lzSysData.getMerchantId());
        /**
         * 签名
         */
        headerMap.put("sign", sign);
        return headerMap;
    }
}
