/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.gjbbc.common.GjbbcEnum;
import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpprovider.gjbbc.formatgjbbc.GjbbcAllOrderStatus;
import com.huobanplus.erpprovider.gjbbc.formatgjbbc.GjbbcOrderInfo;
import com.huobanplus.erpprovider.gjbbc.formatgjbbc.GjbbcOrderItem;
import com.huobanplus.erpprovider.gjbbc.formatgjbbc.WeiXinCustom;
import com.huobanplus.erpprovider.gjbbc.handler.BaseHandler;
import com.huobanplus.erpprovider.gjbbc.handler.GjbbcOrderHandler;
import com.huobanplus.erpprovider.gjbbc.util.GjbbcConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hxh on 2017-08-15.
 */
@Service
public class GjbbcOrderHandlerImpl extends BaseHandler implements GjbbcOrderHandler {
    private final static Log log = LogFactory.getLog(GjbbcOrderHandlerImpl.class);
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        log.info("gjbbc start to push order");
        try {
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            if (order.getPayStatus() != OrderEnum.PayStatus.PAYED.getCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "只有支付成功的需要推送", null);
            }
            GjbbcSysData gjbbcSysData = JSON.parseObject(erpInfo.getSysDataJson(), GjbbcSysData.class);
            Date nowDate = new Date();
            EventResult eventResult = null;
            GjbbcAllOrderStatus gjbbcAllOrderStatus = new GjbbcAllOrderStatus();
            OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(order.getOrderId());
            if (orderDetailSyncLog == null) {
                orderDetailSyncLog = new OrderDetailSyncLog();
                orderDetailSyncLog.setCreateTime(nowDate);
                orderDetailSyncLog.setCustomerId(erpUserInfo.getCustomerId());
                orderDetailSyncLog.setUserType(erpUserInfo.getErpUserType());
                orderDetailSyncLog.setProviderType(erpInfo.getErpType());
                orderDetailSyncLog.setOrderId(order.getOrderId());
                orderDetailSyncLog.setErpSysData(erpInfo.getSysDataJson());
                orderDetailSyncLog.setOrderInfoJson(pushNewOrderEvent.getOrderInfoJson());
                orderDetailSyncLog.setSyncTime(nowDate);

                eventResult = pushTwoOrder(order, gjbbcSysData, gjbbcAllOrderStatus);
                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                gjbbcAllOrderStatus = (GjbbcAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(gjbbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbbcAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
                orderDetailSyncLogService.save(orderDetailSyncLog);
            } else {
                orderDetailSyncLog.setOrderSyncStatus(gjbbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbbcAllOrderStatus.isPayOrderSyncStatus());
                eventResult = pushTwoOrder(order, gjbbcSysData, gjbbcAllOrderStatus);
                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                gjbbcAllOrderStatus = (GjbbcAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(gjbbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbbcAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
                orderDetailSyncLogService.save(orderDetailSyncLog);
            }
            log.info("order push all complete");
            return eventResult;
        } catch (Exception e) {
            log.info("Exception" + e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult PushOrderAliPay(Order order, GjbbcSysData gjbbcSysData) {
        try {
            Map<String, Object> requestMap = new TreeMap<>();
            requestMap.put("service", "alipay.acquire.customs");
            requestMap.put("_input_charset", "utf-8");
            requestMap.put("sign_type", "MD5");

            requestMap.put("out_request_no", order.getOrderId());
//            requestMap.put("out_request_no", order.getUnionOrderId());
            requestMap.put("trade_no", order.getPayNumber());
            requestMap.put("amount", order.getOnlinePayAmount());
            requestMap.put("partner", gjbbcSysData.getAliPartner());
            requestMap.put("merchant_customs_code", gjbbcSysData.getECommerceCode());
            requestMap.put("merchant_customs_name", gjbbcSysData.getECommerceName());
            requestMap.put("customs_place", GjbbcEnum.CustomerEnum.ZONGSHU.toString());
//            requestMap.put("")
            requestMap.put("is_split", "T");
            requestMap.put("sub_out_biz_no", order.getOrderId());


            String sign = DtwUtil.aliBuildSign(requestMap, gjbbcSysData.getAliKey());
            requestMap.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().get(GjbbcConstant.ALI_PAY_URL, requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String XmlData = httpResult.getHttpContent();
                Element rootElement = DocumentHelper.parseText(XmlData).getRootElement();
                Element isSuccess = rootElement.element("is_success");
                if (isSuccess.getText().equals("T")) {
                    Element aliPay_Element = rootElement.element("response").element("alipay");
                    Element result_code = aliPay_Element.element("result_code");
                    if (result_code.getText().equals("SUCCESS")) {
                        log.info("ali pay order push success");
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    } else {
                        Element detailErrorDesdes = aliPay_Element.element("detail_error_des");
                        log.info("ali pay order push failed:" + detailErrorDesdes.getText());
                        return EventResult.resultWith(EventResultEnum.ERROR, detailErrorDesdes.getText(), null);
                    }
                } else {
                    Element error_Elem = rootElement.element("error");
                    log.info("ali pay order push failed:" + error_Elem.getText());
                    return EventResult.resultWith(EventResultEnum.ERROR, error_Elem.getText(), null);
                }
            } else {
                log.error("Server Request Failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult PushOrderWeiXin(Order order, GjbbcSysData gjbbcSysData) {
        try {
            WeiXinCustom weixinCustom = new WeiXinCustom();
            weixinCustom.setAppid(gjbbcSysData.getWeiXinAppId());
            weixinCustom.setMchId(gjbbcSysData.getWeixinMchId());
            weixinCustom.setOutTradeNo(order.getOrderId());
            weixinCustom.setTransactionId(order.getPayNumber());
            weixinCustom.setCustoms(GjbbcEnum.CustomerEnum.HANGZHOU.toString());
            weixinCustom.setMchCustomsNo(gjbbcSysData.getECommerceCode());
            weixinCustom.setSubOrderNo(order.getOrderId());
            weixinCustom.setFeeType("CNY");
            weixinCustom.setCertType("IDCARD");
            weixinCustom.setCertId(order.getBuyerPid());
            weixinCustom.setName(order.getBuyerName());

            //order_fee=transport_fee+product_fee  应付金额=物流费+商品价格
            int orderFee = (int) (order.getOnlinePayAmount() * 100);
            int transportFee = (int) (order.getCostFreight() * 100);
            weixinCustom.setOrderFee(orderFee);// 单位转换成分
            weixinCustom.setTransportFee(transportFee);// 单位转换成分
            weixinCustom.setProductFee(orderFee - transportFee);// 单位转换成分
            weixinCustom.setDuty(0);//关税为0
            Map<String, Object> requestMap = new TreeMap<>();
            requestMap.put("appId", weixinCustom.getAppid());
            requestMap.put("mchId", weixinCustom.getMchId());
            requestMap.put("outTradeNo", weixinCustom.getOutTradeNo());
            requestMap.put("transactionId", weixinCustom.getTransactionId());
            requestMap.put("customs", weixinCustom.getCustoms());
            requestMap.put("mchCustomsNo", weixinCustom.getMchCustomsNo());
            requestMap.put("subOrderNo", weixinCustom.getSubOrderNo());
            requestMap.put("freeType", weixinCustom.getFeeType());
            requestMap.put("certType", weixinCustom.getCertType());
            requestMap.put("certId", weixinCustom.getCertId());
            requestMap.put("name", weixinCustom.getName());
            requestMap.put("orderFee", weixinCustom.getOrderFee());
            requestMap.put("transportFee", weixinCustom.getTransportFee());
            requestMap.put("ProductFee", weixinCustom.getProductFee());
            requestMap.put("duty", weixinCustom.getDuty());
            String sign = DtwUtil.weixinBuildSign(requestMap, gjbbcSysData.getWeixinKey());
            requestMap.put("sign", sign);
            weixinCustom.setSign(sign);

            String requestData = new XmlMapper().writeValueAsString(weixinCustom);
            HttpResult httpResult = HttpClientUtil.getInstance().post(GjbbcConstant.WEIXIN_PAY_URL, requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String httpContent = httpResult.getHttpContent();
                Element rootElement = DocumentHelper.parseText(httpContent).getRootElement();
                Element returnCodeElem = rootElement.element("return_code");
                if (returnCodeElem.getText().equals("SUCCESS")) {
                    Element result_Code = rootElement.element("result_code");
                    if (result_Code.getText().equals("SUCCESS")) {
                        log.info("WeiXin pay order push success");
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    } else {
                        Element ErrorMessage = rootElement.element("err_code_des");
                        log.info("WeiXin pay order push failed:" + ErrorMessage.getText());
                        return EventResult.resultWith(EventResultEnum.ERROR, ErrorMessage.getText(), null);
                    }
                } else {
                    Element return_msg = rootElement.element("return_msg");
                    log.info("WeiXin pay order push failed:" + return_msg.getText());
                    return EventResult.resultWith(EventResultEnum.ERROR, return_msg.getText(), null);
                }
            } else {
                log.info("Server Request failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    @Override
    public EventResult pushPlatformOrder(Order order, GjbbcSysData gjbbcSysData) {
        try {
            log.info("push platform order start");
            Map<String, Object> requestMap = new TreeMap<>();
            GjbbcOrderInfo gjbbcOrderInfo = new GjbbcOrderInfo();
            gjbbcOrderInfo.setOrderSn(order.getOrderId());
            gjbbcOrderInfo.setBuyerName(order.getBuyerName());
            gjbbcOrderInfo.setBuyerPhone(order.getShipMobile());
            gjbbcOrderInfo.setBuyerIdcard(order.getBuyerPid());
            gjbbcOrderInfo.setCountryCode(String.valueOf(142));
            gjbbcOrderInfo.setProvinceCode("code");
            gjbbcOrderInfo.setBuyerAddress(order.getProvince() + "^^^" + order.getCity() + "^^^" + order.getDistrict() + "^^^" + order.getShipAddr());
            String[] sendInfo = gjbbcSysData.getSenderInfo().split(",");
            gjbbcOrderInfo.setSenderName(sendInfo[0]);
            gjbbcOrderInfo.setSenderPhone(sendInfo[1]);
            gjbbcOrderInfo.setSenderCountryCode(sendInfo[2]);
            gjbbcOrderInfo.setSenderProvinceCode(sendInfo[3]);
            gjbbcOrderInfo.setSenderAddress(sendInfo[4]);
            gjbbcOrderInfo.setMainDesc("desc");
            OrderEnum.PaymentOptions paymentOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
            gjbbcOrderInfo.setPName(paymentOptions.getName());
            gjbbcOrderInfo.setPNo(order.getUnionOrderId());
            gjbbcOrderInfo.setPTime(StringUtil.DateFormat(order.getPayTime(), StringUtil.TIME_PATTERN).getTime());
            gjbbcOrderInfo.setPWeb(gjbbcSysData.getPWeb());
            gjbbcOrderInfo.setCustomsTax(order.getTaxAmount());
            gjbbcOrderInfo.setShippingFee(order.getCostFreight());
            gjbbcOrderInfo.setCustomsDiscout(order.getPmtAmount());
            gjbbcOrderInfo.setOrderName(order.getBuyerName());
            gjbbcOrderInfo.setOrderIdNum(order.getBuyerPid());
            gjbbcOrderInfo.setOrderTel(order.getShipMobile());
            gjbbcOrderInfo.setOrderAccountNum(order.getOrderName());
            gjbbcOrderInfo.setRecordNoQg(gjbbcSysData.getECommerceCode());
            gjbbcOrderInfo.setRecordName(gjbbcSysData.getECommerceName());
            gjbbcOrderInfo.setPayState(2);
            List<OrderItem> orderItems = order.getOrderItems();
            List<GjbbcOrderItem> orderGoods = new ArrayList<>();
            orderItems.forEach(orderItem -> {
                GjbbcOrderItem gjbbcOrderItem = new GjbbcOrderItem();
                gjbbcOrderItem.setCustomsGoodsId(Integer.parseInt(orderItem.getForeignBn()));
                gjbbcOrderItem.setGoodsNum(String.valueOf(orderItem.getNum()));
                gjbbcOrderItem.setGoodsPrice(orderItem.getPrice());
                gjbbcOrderItem.setTradeCurr("142");
                orderGoods.add(gjbbcOrderItem);
            });
            gjbbcOrderInfo.setOrderGoods(orderGoods);
            String gjbbcOrderJson = JSON.toJSONString(gjbbcOrderInfo);
            System.out.println(gjbbcOrderJson);
            String encode = Base64.encodeBase64String(gjbbcOrderJson.getBytes("utf-8"));
            requestMap = getSysRequestData(gjbbcSysData);
            requestMap.put("order", encode);
            log.info("gjbbc requestUrl====>" + gjbbcSysData.getRequestUrl());
            HttpResult httpResult = HttpClientUtil.getInstance().post(gjbbcSysData.getRequestUrl(), requestMap);
            log.info("platform httpResult====>" + httpResult.getHttpContent());
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
                if ("OK".equals(jsonObject.get("flag"))) {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, jsonObject.getString("info"), null);
                }
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("info"), null);
            }
            return EventResult.resultWith(EventResultEnum.ERROR, "请求服务器错误", null);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }

    /**
     * 推送支付单和平台订单
     *
     * @param order
     * @param gjbbcSysData
     * @param gjbbcAllOrderStatus
     * @return
     */
    public EventResult pushTwoOrder(Order order, GjbbcSysData gjbbcSysData, GjbbcAllOrderStatus gjbbcAllOrderStatus) {
        StringBuilder errorMsg = new StringBuilder();
        //支付单推送
        if (!gjbbcAllOrderStatus.isPayOrderSyncStatus()) {
            EventResult PayResult = null;
            OrderEnum.PaymentOptions enumTypeOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
            if (enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_PC ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_CROSS_BORDER
                    ) {
                PayResult = PushOrderAliPay(order, gjbbcSysData);
            } else if (enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP) {
                PayResult = PushOrderWeiXin(order, gjbbcSysData);
            } else {
                PayResult = EventResult.resultWith(EventResultEnum.ERROR, "不支持该支付", null);
            }

            if (PayResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                gjbbcAllOrderStatus.setPayOrderSyncStatus(true);
            } else {
                gjbbcAllOrderStatus.setPayOrderSyncStatus(false);
                errorMsg.append("支付单:");
                errorMsg.append(PayResult.getResultMsg()).append("|");
            }
            log.info("pay push end====>" + PayResult.getResultMsg());
        }
        //平台推送
        if (!gjbbcAllOrderStatus.isOrderSyncStatus()) {
            EventResult orderEvent = pushPlatformOrder(order, gjbbcSysData);
            if (orderEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                gjbbcAllOrderStatus.setOrderSyncStatus(true);
            } else {
                gjbbcAllOrderStatus.setOrderSyncStatus(false);
                errorMsg.append("平台订单");
                errorMsg.append(orderEvent.getResultMsg()).append("|");
            }
            log.info("platform order push end=====>" + orderEvent.getResultMsg());
        }
        if (gjbbcAllOrderStatus.isSyncSuccess()) {
            return EventResult.resultWith(EventResultEnum.SUCCESS, gjbbcAllOrderStatus);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), gjbbcAllOrderStatus);
    }
}
