package com.huobanplus.erpprovider.lz.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lz.common.LzSysData;
import com.huobanplus.erpprovider.lz.formatlz.*;
import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.apache.commons.codec.binary.Base64;

import java.math.BigDecimal;
import java.util.*;

@Component
public class LzOrderHandlerImpl implements LzOrderHandler {
    private Log log = LogFactory.getLog(LzOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        log.info("lianzhong start to push order");
        try {
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            if (order.getPayStatus() != OrderEnum.PayStatus.PAYED.getCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "只有支付成功的需要推送", null);
            }
            LzSysData lzSysData = JSON.parseObject(erpInfo.getSysDataJson(), LzSysData.class);
            Date nowDate = new Date();

            EventResult eventResult = null;
            LzAllOrderStatus lzAllOrderStatus = new LzAllOrderStatus();
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

                eventResult = pushFourOrder(order, lzSysData, lzAllOrderStatus);
                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                lzAllOrderStatus = (LzAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(lzAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(lzAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(lzAllOrderStatus.isCustomOrderSyncStatus());
                orderDetailSyncLog.setCustomBackStatus(false);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
                orderDetailSyncLogService.save(orderDetailSyncLog);
            } else {
                orderDetailSyncLog.setOrderSyncStatus(lzAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(lzAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(lzAllOrderStatus.isCustomOrderSyncStatus());
                orderDetailSyncLog.setCustomBackStatus(lzAllOrderStatus.isCustomBackStatus());
                eventResult = pushFourOrder(order, lzSysData, lzAllOrderStatus);

                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                lzAllOrderStatus = (LzAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(lzAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(lzAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(lzAllOrderStatus.isCustomOrderSyncStatus());
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

    /**
     * 推送支付单 和 海关 平台订单
     *
     * @param order            订单
     * @param lzSysData        系统参数
     * @param lzAllOrderStatus 系统订单状态
     */
    private EventResult pushFourOrder(Order order, LzSysData lzSysData, LzAllOrderStatus lzAllOrderStatus) {
        StringBuilder errorMsg = new StringBuilder();
        //支付单推送
//        log.info("支付单推送start");
//        log.info("订单支付单状态：" + lzAllOrderStatus.isPayOrderSyncStatus());
//        if (!lzAllOrderStatus.isPayOrderSyncStatus()) {
//            EventResult PayResult = null;
//            OrderEnum.PaymentOptions enumTypeOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
//            if (enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB ||
//                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE ||
//                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_PC ||
//                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_CROSS_BORDER
//            ) {
//                PayResult = PushOrderAliPay(order, lzSysData);
//
//            } else if (enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP) {
//                PayResult = PushOrderWeiXin(order, lzSysData);
//            } else {
//                PayResult = EventResult.resultWith(EventResultEnum.ERROR, "不支持该支付", null);
//            }
//
//            if (PayResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                lzAllOrderStatus.setPayOrderSyncStatus(true);
//            } else {
//                lzAllOrderStatus.setPayOrderSyncStatus(false);
//                errorMsg.append("支付单:");
//                errorMsg.append(PayResult.getResultMsg()).append("|");
//            }
//            log.info("pay push end====>" + PayResult.getResultMsg());
//        }
//        log.info("支付单推送end");

        //平台推送
        if (!lzAllOrderStatus.isOrderSyncStatus()) {
            EventResult orderEvent = pushPlatformOrder(order, lzSysData);
            if (orderEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                lzAllOrderStatus.setOrderSyncStatus(true);
            } else {
                lzAllOrderStatus.setOrderSyncStatus(false);
                errorMsg.append("平台订单");
                errorMsg.append(orderEvent.getResultMsg()).append("|");
            }
            log.info("platform order push end=====>" + orderEvent.getResultMsg());
        }


        if (lzAllOrderStatus.isSyncSuccess()) {
            return EventResult.resultWith(EventResultEnum.SUCCESS, lzAllOrderStatus);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), lzAllOrderStatus);
    }

//    /**
//     * TODO
//     * @param order
//     * @param lzSysData
//     * @return
//     */
//    @Override
//    public EventResult PushOrderAliPay(Order order, LzSysData lzSysData) {
//        try {
////            log.info(JSON.toJSONString(lzSysData));
//            log.info("orderNo:" + order.getOrderId());
//
//            Map<String, Object> requestMap = new TreeMap<>();
//            requestMap.put("service", "alipay.acquire.customs");
//            requestMap.put("_input_charset", "utf-8");
//            requestMap.put("sign_type", "MD5");
//
//            requestMap.put("out_request_no", order.getOrderId());
////            requestMap.put("out_request_no", order.getUnionOrderId());
//            requestMap.put("trade_no", order.getPayNumber());
//            requestMap.put("amount", order.getOnlinePayAmount());
//            requestMap.put("partner", lzSysData.getAliPartner());
//            requestMap.put("merchant_customs_code", lzSysData.getECommerceCode());
//            requestMap.put("merchant_customs_name", lzSysData.getECommerceName());
//            requestMap.put("customs_place", GjbcEnum.CustomerEnum.ZONGSHU.toString());
//            requestMap.put("is_split", "T");
//            requestMap.put("sub_out_biz_no", order.getOrderId());
//
//
//            String sign = DtwUtil.aliBuildSign(requestMap, lzSysData.getAliKey());
//            requestMap.put("sign", sign);
//
//            HttpResult httpResult = HttpClientUtil.getInstance().get(GjbcConstant.ALI_PAY_URL, requestMap);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                String XmlData = httpResult.getHttpContent();
//                Element rootElement = DocumentHelper.parseText(XmlData).getRootElement();
//                Element isSuccess = rootElement.element("is_success");
//                if (isSuccess.getText().equals("T")) {
//                    Element aliPay_Element = rootElement.element("response").element("alipay");
//                    Element result_code = aliPay_Element.element("result_code");
//                    if (result_code.getText().equals("SUCCESS")) {
//                        log.info("ali pay order push success");
//                        return EventResult.resultWith(EventResultEnum.SUCCESS);
//                    } else {
//                        Element detailErrorDesdes = aliPay_Element.element("detail_error_des");
//                        log.info("ali pay order push failed:" + detailErrorDesdes.getText());
//                        return EventResult.resultWith(EventResultEnum.ERROR, detailErrorDesdes.getText(), null);
//                    }
//                } else {
//                    Element error_Elem = rootElement.element("error");
//                    log.info("ali pay order push failed:" + error_Elem.getText());
//                    return EventResult.resultWith(EventResultEnum.ERROR, error_Elem.getText(), null);
//                }
//            } else {
//                log.error("Server Request Failed:" + httpResult.getHttpContent());
//                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
//
//    }
//
//    /**
//     * TODO
//     * @param order
//     * @param lzSysData
//     * @return
//     */
//    @Override
//    public EventResult PushOrderWeiXin(Order order, LzSysData lzSysData) {
//        try {
//            WeiXinCustom weixinCustom = new WeiXinCustom();
//            weixinCustom.setAppid(lzSysData.getWeiXinAppId());
//            weixinCustom.setMchId(lzSysData.getWeixinMchId());
//            weixinCustom.setOutTradeNo(order.getOrderId());
//            weixinCustom.setTransactionId(order.getPayNumber());
//            weixinCustom.setCustoms(GjbcEnum.CustomerEnum.HANGZHOU.toString());
//            weixinCustom.setMchCustomsNo(lzSysData.getECommerceCode());
//            weixinCustom.setSubOrderNo(order.getOrderId());
//            weixinCustom.setFeeType("CNY");
//            weixinCustom.setCertType("IDCARD");
//            weixinCustom.setCertId(order.getBuyerPid());
//            weixinCustom.setName(order.getBuyerName());
//
//            //order_fee=transport_fee+product_fee  应付金额=物流费+商品价格
//            int orderFee = (int) (order.getOnlinePayAmount() * 100);
//            int transportFee = (int) (order.getCostFreight() * 100);
//            weixinCustom.setOrderFee(orderFee);// 单位转换成分
//            weixinCustom.setTransportFee(transportFee);// 单位转换成分
//            weixinCustom.setProductFee(orderFee - transportFee);// 单位转换成分
//            weixinCustom.setDuty(0);//关税为0
//            Map<String, Object> requestMap = new TreeMap<>();
//            requestMap.put("appId", weixinCustom.getAppid());
//            requestMap.put("mchId", weixinCustom.getMchId());
//            requestMap.put("outTradeNo", weixinCustom.getOutTradeNo());
//            requestMap.put("transactionId", weixinCustom.getTransactionId());
//            requestMap.put("customs", weixinCustom.getCustoms());
//            requestMap.put("mchCustomsNo", weixinCustom.getMchCustomsNo());
//            requestMap.put("subOrderNo", weixinCustom.getSubOrderNo());
//            requestMap.put("freeType", weixinCustom.getFeeType());
//            requestMap.put("certType", weixinCustom.getCertType());
//            requestMap.put("certId", weixinCustom.getCertId());
//            requestMap.put("name", weixinCustom.getName());
//            requestMap.put("orderFee", weixinCustom.getOrderFee());
//            requestMap.put("transportFee", weixinCustom.getTransportFee());
//            requestMap.put("ProductFee", weixinCustom.getProductFee());
//            requestMap.put("duty", weixinCustom.getDuty());
//            String sign = DtwUtil.weixinBuildSign(requestMap, lzSysData.getWeixinKey());
//            requestMap.put("sign", sign);
//            weixinCustom.setSign(sign);
//
//            String requestData = new XmlMapper().writeValueAsString(weixinCustom);
//            HttpResult httpResult = HttpClientUtil.getInstance().post(GjbcConstant.WEIXIN_PAY_URL, requestData);
//            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
//                String httpContent = httpResult.getHttpContent();
//                Element rootElement = DocumentHelper.parseText(httpContent).getRootElement();
//                Element returnCodeElem = rootElement.element("return_code");
//                if (returnCodeElem.getText().equals("SUCCESS")) {
//                    Element result_Code = rootElement.element("result_code");
//                    if (result_Code.getText().equals("SUCCESS")) {
//                        log.info("WeiXin pay order push success");
//                        return EventResult.resultWith(EventResultEnum.SUCCESS);
//                    } else {
//                        Element ErrorMessage = rootElement.element("err_code_des");
//                        log.info("WeiXin pay order push failed:" + ErrorMessage.getText());
//                        return EventResult.resultWith(EventResultEnum.ERROR, ErrorMessage.getText(), null);
//                    }
//                } else {
//                    Element return_msg = rootElement.element("return_msg");
//                    log.info("WeiXin pay order push failed:" + return_msg.getText());
//                    return EventResult.resultWith(EventResultEnum.ERROR, return_msg.getText(), null);
//                }
//            } else {
//                log.info("Server Request failed:" + httpResult.getHttpContent());
//                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
//    }


    @Override
    public EventResult pushPlatformOrder(Order order, LzSysData lzSysData) {
        try {
            log.info("push lz platform order start");

            LzOrderInfo lzOrderInfo = new LzOrderInfo();
            lzOrderInfo.setOrder_id(order.getOrderId());
            lzOrderInfo.setPayment_id(order.getPayNumber());
            lzOrderInfo.setPurchaser_acount(order.getUserLoginName());
            lzOrderInfo.setBuyer_name(order.getBuyerName());
            lzOrderInfo.setPurchaser_tel(order.getShipMobile());
            lzOrderInfo.setPurchaser_address("");
            lzOrderInfo.setCoupon_amount(new BigDecimal(order.getPmtAmount()).multiply(new BigDecimal(100)));

            lzOrderInfo.setOrder_create_time(order.getCreateTime());
            lzOrderInfo.setPay_time(order.getPayTime());

//            lzOrderInfo.setTransport_service_code();
//            lzOrderInfo.setTransport_order_id();
//            lzOrderInfo.setLogis_company_id();

            lzOrderInfo.setReceiver_id(RSA(order.getBuyerPid()));
            lzOrderInfo.setReceiver_zip(order.getShipZip());
            lzOrderInfo.setReceiver_province(order.getProvince());
            lzOrderInfo.setReceiver_city(order.getCity());
            lzOrderInfo.setReceiver_county(order.getDistrict());
            lzOrderInfo.setReceiver_address(order.getShipAddr());
            lzOrderInfo.setReceiver_name(order.getShipName());
            lzOrderInfo.setReceiver_mobile(order.getShipMobile());
            lzOrderInfo.setReceiver_phone(order.getShipTel());


            lzOrderInfo.setLogistic_condition(0);
            lzOrderInfo.setLogistic_type(0);

            //支付相关
            LzOrderPayInfo lzOrderPayInfo = new LzOrderPayInfo();
            lzOrderPayInfo.setPayment_id();
            //TODO config "支付宝(中国)网络技术有限公司"
            lzOrderPayInfo.setPayment_company_name();
            lzOrderPayInfo.setPay_amount();
            lzOrderPayInfo.setPay_currency_code("142");
            lzOrderPayInfo.setPlatform_id();
            lzOrderInfo.setPay_info(lzOrderPayInfo);

            //税费
            BigDecimal tax = new BigDecimal(order.getTaxAmount()).multiply(new BigDecimal(100));

            //推送费用
            LzOrderFeeInfo lzOrderFeeInfo = new LzOrderFeeInfo();
            lzOrderFeeInfo.setTax(String.valueOf(tax));
            lzOrderFeeInfo.setFreight(String.valueOf(toFen(order.getCostFreight()));
            lzOrderFeeInfo.setInsurance("0");
            lzOrderFeeInfo.setDeduction_amount(String.valueOf(toFen(order.getPmtAmount())));
            lzOrderInfo.setFee_info(lzOrderFeeInfo);

//            lzOrderInfo.setInvoice_flags();
//            lzOrderInfo.setMain_goods_name();
            lzOrderInfo.setMerchant_id();


            //订单金额
            double finalAmout = 0;
            int num = 0;
            List<OrderItem> orderItems = order.getOrderItems();
            List<LzOrderItem> lzOrderItems = new ArrayList<>();
            for (int i = 0; i < orderItems.size(); i++) {
                LzOrderItem lzOrderItem = new LzOrderItem();
                lzOrderItem.setSku_id(String.valueOf(orderItems.get(i).getProductId()));
                lzOrderItem.setProduct_no(orderItems.get(i).getProductBn());
                lzOrderItem.setQty(orderItems.get(i).getNum());
                lzOrderItem.setUnit_price(toFen(orderItems.get(i).getPrice()).intValue());
                lzOrderItem.setGoods_name(orderItems.get(i).getName());

                BigDecimal bigGoodsTotal = new BigDecimal(orderItems.get(i).getPrice() * orderItems.get(i).getNum());
                finalAmout += bigGoodsTotal.doubleValue();

                num += orderItems.get(i).getNum();
                lzOrderItems.add(lzOrderItem);
            }


            lzOrderInfo.setTotal_count(num);
            lzOrderInfo.setOrder_goods_amount(toFen(finalAmout));
            lzOrderInfo.setOrder_total_amount(toFen(order.getFinalAmount()));
            lzOrderInfo.setOrder_tax_amount(tax);
            lzOrderInfo.setNet_weight((int) order.getSuttleWeight());
            lzOrderInfo.setOrder_items(lzOrderItems);




            String gjbcOrderInfosJson = JSON.toJSONString(lzOrderInfo);
//TODO 请求方式
            Map<String, Object> requestMap = getSysRequestData(lzSysData, "order");
            String encode = Base64.encodeBase64String(gjbcOrderInfosJson.getBytes("utf-8"));

            requestMap.put("order", Base64.encodeBase64String(encode.getBytes("utf-8")));
            log.info("gjbc requestUrl====>" + lzSysData.getRequestUrl());
            HttpResult httpResult = HttpClientUtil.getInstance().post(lzSysData.getRequestUrl(), requestMap);
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
     * 元转分
     *
     * @param money
     * @return
     */
    private BigDecimal toFen(double money) {
        return new BigDecimal(money).multiply(new BigDecimal(100));
    }
}
