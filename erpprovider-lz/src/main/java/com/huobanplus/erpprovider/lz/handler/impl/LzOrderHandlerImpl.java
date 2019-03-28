package com.huobanplus.erpprovider.lz.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpprovider.lz.util.RSA;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.RequestUtils;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
     * 用户订单出库回调接口(WMS服务商 -> 电商平台（货主）)
     *
     * @param request
     * @return
     */
    @Override
    public EventResult orderDeliveryCallback(HttpServletRequest request) {
        //TODO:
        String orderId = request.getParameter("order_id");
        if (org.springframework.util.StringUtils.isEmpty(orderId)) {
            return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "未传入有效的orderId", null);
        }
        //订单状态 10：WMS接收订单成功 50：打单 100：分拣 200：打包 300：发货 400：分拣缺货 500：海关扣留
        Integer orderStatus = RequestUtils.getIntHeader(request, "order_status");
        int weight = 0;
        if (orderStatus.equals(300)) {
            //包裹重量 发货（300）时必填 单位：克
            weight = RequestUtils.getIntHeader(request, "weight");
            String logiName = request.getParameter("transport_service_code");
            if (org.springframework.util.StringUtils.isEmpty(logiName)) {
                //物流公司编码 仓配一体发货（300）时必填
                return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "未传入快递公司名称", null);
            }
            String logiNo = request.getParameter("transport_order_id");
            if (org.springframework.util.StringUtils.isEmpty(logiNo)) {
                //物流单号 仓配一体发货（300）时必填
                return EventResult.resultWith(EventResultEnum.BAD_REQUEST_PARAM, "未传入快递单号", null);
            }
        }

        return null;
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
