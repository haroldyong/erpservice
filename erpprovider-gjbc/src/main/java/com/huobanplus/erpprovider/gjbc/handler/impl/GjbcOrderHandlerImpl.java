/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbc.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.dtw.util.Arith;
import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.gjbc.common.GjbcEnum;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomBody;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomGoodsPurchaser;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomHead;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrder;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderDetail;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderHead;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomOrderInfoList;
import com.huobanplus.erpprovider.gjbc.formatgjbc.CustomSign;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcAllOrderStatus;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcGoodsItemsInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.GjbcOrderInfo;
import com.huobanplus.erpprovider.gjbc.formatgjbc.WeiXinCustom;
import com.huobanplus.erpprovider.gjbc.handler.BaseHandler;
import com.huobanplus.erpprovider.gjbc.handler.GjbcOrderHandler;
import com.huobanplus.erpprovider.gjbc.util.GjbcConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.repository.CountryInfoRepository;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by montage on 2017/6/26.
 */

@Component
public class GjbcOrderHandlerImpl extends BaseHandler implements GjbcOrderHandler {

    private final static Log log = LogFactory.getLog(GjbcOrderHandlerImpl.class);

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Autowired
    private CountryInfoRepository countryInfoRepository;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        log.info("gjbc start to push order");
        try {
            ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
            ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
            Order order = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
            if (order.getPayStatus() != OrderEnum.PayStatus.PAYED.getCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "只有支付成功的需要推送", null);
            }
            GjbcSysData gjbcSysData = JSON.parseObject(erpInfo.getSysDataJson(), GjbcSysData.class);
            Date nowDate = new Date();

            EventResult eventResult = null;
            GjbcAllOrderStatus gjbcAllOrderStatus = new GjbcAllOrderStatus();
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

                eventResult = pushFourOrder(order, gjbcSysData, gjbcAllOrderStatus);
                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                gjbcAllOrderStatus = (GjbcAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(gjbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbcAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(gjbcAllOrderStatus.isCustomOrderSyncStatus());
                orderDetailSyncLog.setCustomBackStatus(false);
                orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
                orderDetailSyncLogService.save(orderDetailSyncLog);
            } else {
                orderDetailSyncLog.setOrderSyncStatus(gjbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbcAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(gjbcAllOrderStatus.isCustomOrderSyncStatus());
                orderDetailSyncLog.setCustomBackStatus(gjbcAllOrderStatus.isCustomBackStatus());
                eventResult = pushFourOrder(order, gjbcSysData, gjbcAllOrderStatus);

                if (eventResult.getResultCode() == EventResultEnum.ERROR.getResultCode()) {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
                } else {
                    orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
                }
                gjbcAllOrderStatus = (GjbcAllOrderStatus) eventResult.getData();
                orderDetailSyncLog.setOrderSyncStatus(gjbcAllOrderStatus.isOrderSyncStatus());
                orderDetailSyncLog.setPayOrderSyncStatus(gjbcAllOrderStatus.isPayOrderSyncStatus());
                orderDetailSyncLog.setCustomOrderSyncStatus(gjbcAllOrderStatus.isCustomOrderSyncStatus());
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
     * @param order              订单
     * @param gjbcSysData        系统参数
     * @param gjbcAllOrderStatus 系统订单状态
     */
    private EventResult pushFourOrder(Order order, GjbcSysData gjbcSysData, GjbcAllOrderStatus gjbcAllOrderStatus) {
        StringBuilder errorMsg = new StringBuilder();
        //支付单推送
        if (!gjbcAllOrderStatus.isPayOrderSyncStatus()) {
            EventResult PayResult = null;
            OrderEnum.PaymentOptions enumTypeOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
            if (enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_PC ||
                    enumTypeOptions == OrderEnum.PaymentOptions.ALIPAY_CROSS_BORDER
                    ) {
                PayResult = PushOrderAliPay(order, gjbcSysData);
            } else if (enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY || enumTypeOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP) {
                PayResult = PushOrderWeiXin(order, gjbcSysData);
            } else {
                PayResult = EventResult.resultWith(EventResultEnum.ERROR, "不支持该支付", null);
            }

            if (PayResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                gjbcAllOrderStatus.setPayOrderSyncStatus(true);
            } else {
                gjbcAllOrderStatus.setPayOrderSyncStatus(false);
                errorMsg.append("支付单:");
                errorMsg.append(PayResult.getResultMsg()).append("|");
            }
            log.info("pay push end====>" + PayResult.getResultMsg());
        }


        //平台推送
        if (!gjbcAllOrderStatus.isOrderSyncStatus()) {
            EventResult orderEvent = pushPlatformOrder(order, gjbcSysData);
            if (orderEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                gjbcAllOrderStatus.setOrderSyncStatus(true);
            } else {
                gjbcAllOrderStatus.setOrderSyncStatus(false);
                errorMsg.append("平台订单");
                errorMsg.append(orderEvent.getResultMsg()).append("|");
            }
            log.info("platform order push end=====>" + orderEvent.getResultMsg());
        }

//        //海关推送
//        if (!gjbcAllOrderStatus.isCustomOrderSyncStatus()) {
//            EventResult eventResultCustom = PushOrderCustom(order, gjbcSysData);
//            if (eventResultCustom.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                gjbcAllOrderStatus.setCustomOrderSyncStatus(true);
//            } else {
//                gjbcAllOrderStatus.setCustomOrderSyncStatus(false);
//                errorMsg.append("海关订单");
//                errorMsg.append(eventResultCustom.getResultMsg()).append("|");
//            }
//        }
        if (gjbcAllOrderStatus.isSyncSuccess()) {
            return EventResult.resultWith(EventResultEnum.SUCCESS, gjbcAllOrderStatus);
        }
        return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), gjbcAllOrderStatus);
    }

    @Override
    public EventResult pushPlatformOrder(Order order, GjbcSysData gjbcSysData) {
        try {
            log.info("push platform order start");

            Map<String, Object> requestMap = new TreeMap<>();
            GjbcOrderInfo gjbcOrderInfo = new GjbcOrderInfo();
            gjbcOrderInfo.setIs_bc(1);
            gjbcOrderInfo.setOrder_sn(order.getOrderId());
//            gjbcOrderInfo.setOrder_sn(order.getUnionOrderId()); // TODO: 26/07/2017 测试测试
            /*发件人信息*/
            String[] sendInfo = gjbcSysData.getSenderInfo().split(",");
            gjbcOrderInfo.setSender_name(sendInfo[0]);
            gjbcOrderInfo.setSender_city(sendInfo[1]);
            gjbcOrderInfo.setSender_address(sendInfo[2]);
            gjbcOrderInfo.setSender_phone(sendInfo[3]);
            gjbcOrderInfo.setSender_country_code("110");
            gjbcOrderInfo.setBuyer_name(order.getShipName());
            gjbcOrderInfo.setBuyer_phone(order.getShipMobile());
            gjbcOrderInfo.setOrder_name(order.getBuyerName());
            gjbcOrderInfo.setOrder_idcard(order.getBuyerPid());
            gjbcOrderInfo.setOrder_phone(order.getShipMobile());
            gjbcOrderInfo.setCustoms_insured(0);
            gjbcOrderInfo.setCustoms_discount(order.getPmtAmount());
            gjbcOrderInfo.setOrder_uname(order.getUserLoginName());
//            gjbcOrderInfo.setProvince_code(order.getProvince());
            gjbcOrderInfo.setBuyer_address(order.getProvince() + "^^^" + order.getCity() + "^^^" + order.getDistrict() + "^^^" + order.getShipAddr());
            gjbcOrderInfo.setBuyer_idcard(order.getBuyerPid());
            gjbcOrderInfo.setCurr(GjbcEnum.CurrencyEnum.CNY.getCode());
            OrderEnum.PaymentOptions paymentOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
            gjbcOrderInfo.setP_name(paymentOptions.getName());
            gjbcOrderInfo.setP_no(order.getPayNumber());
            String payTime = order.getPayTime();
            gjbcOrderInfo.setP_time(StringUtil.DateFormat(payTime, StringUtil.TIME_PATTERN).getTime());
//            if (!StringUtils.isEmpty(payTime)) {
//                SimpleDateFormat sdf = new SimpleDateFormat(GjbcConstant.TIME_PATTERN);
//                try {
//                    Date payDate = sdf.parse(payTime);
//                    gjbcOrderInfo.setP_time((int) payDate.getTime());
//                } catch (ParseException e) {
//                    log.info("Exception：" + e.getMessage());
//                    return EventResult.resultWith(EventResultEnum.ERROR, "时间格式转换异常", null);
//                }
//            }
            gjbcOrderInfo.setSh_fee(order.getCostFreight());
            gjbcOrderInfo.setCus_tax(order.getTaxAmount());
            gjbcOrderInfo.setPweb(gjbcSysData.getPWeb());
            gjbcOrderInfo.setWeb_name(GjbcConstant.WEB_NAME);
            gjbcOrderInfo.setRe_no(GjbcConstant.RECORD_NUMBER);
            gjbcOrderInfo.setRe_no_qg(GjbcConstant.RECORD_NUMBER);
            gjbcOrderInfo.setRe_name(GjbcConstant.RECORD_NAME);
            gjbcOrderInfo.setWarehouseCode(gjbcSysData.getWarehouseCode());
            gjbcOrderInfo.setExpress_code(order.getLogiCode());
            double totalWight = 0;
            double totalSuttleWeight = 0;
            double finalAmout = 0;
            List<OrderItem> orderItems = order.getOrderItems();
            GjbcGoodsItemsInfo[] goodsItemsInfos = new GjbcGoodsItemsInfo[orderItems.size()];
            for (int i = 0; i < orderItems.size(); i++) {
                GjbcGoodsItemsInfo gjbcGoodsItemsInfo = new GjbcGoodsItemsInfo();
                gjbcGoodsItemsInfo.setGoods_seq(i + 1);
                /* 商品条形码  */
                gjbcGoodsItemsInfo.setGoods_barcode(orderItems.get(i).getProductBn());
                gjbcGoodsItemsInfo.setGoods_unit(GjbcEnum.UnitEnum.KG.getCode());
                gjbcGoodsItemsInfo.setGoods_size(GjbcEnum.UnitEnum.JIAN.getCode());

                gjbcGoodsItemsInfo.setGoods_gweight(orderItems.get(i).getSuttleWeight() / 1000);
                gjbcGoodsItemsInfo.setGoods_name(orderItems.get(i).getName());
                gjbcGoodsItemsInfo.setBrand(orderItems.get(i).getBrand());
                gjbcGoodsItemsInfo.setGoods_spec(orderItems.get(i).getStandard());
                gjbcGoodsItemsInfo.setGoods_num(String.valueOf(orderItems.get(i).getNum()));
                gjbcGoodsItemsInfo.setGoods_price(orderItems.get(i).getPrice());
//            gjbcGoodsItemsInfo.setYcg_code(GjbcEnum.CountryEnum.CHINA.getCode());
                /* 原产国代码 */
                String countryCode = orderItems.get(i).getGoodBn().substring(0, 3);
                gjbcGoodsItemsInfo.setYcg_code(countryCode);
                /* 商品HS编码 */
                gjbcGoodsItemsInfo.setHs_code(orderItems.get(i).getGoodBn().substring(3));
                gjbcGoodsItemsInfo.setCurr(String.valueOf(GjbcEnum.CurrencyEnum.CNY.getCode()));
                gjbcGoodsItemsInfo.setGoods_hg_num2(orderItems.get(i).getNum());
                if (!StringUtils.isEmpty(orderItems.get(i).getPackageInfo())) {
                    gjbcGoodsItemsInfo.setGoods_hg_num2(orderItems.get(i).getNum() * Integer.valueOf(orderItems.get(i).getPackageInfo()));
                }
                BigDecimal bigGoodsTotal = new BigDecimal(orderItems.get(i).getPrice() * orderItems.get(i).getNum());
                finalAmout += bigGoodsTotal.doubleValue();
                gjbcGoodsItemsInfo.setGoods_total(bigGoodsTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            /*商品平台货号*/
                gjbcGoodsItemsInfo.setGoods_commonid(orderItems.get(i).getProductId());
            /* 毛重*/
                log.info("毛重====>" + orderItems.get(i).getWeight());
                totalWight += orderItems.get(i).getNum() * orderItems.get(i).getWeight();
           /* 净重*/
                totalSuttleWeight += orderItems.get(i).getNum() * orderItems.get(i).getSuttleWeight();
                goodsItemsInfos[i] = gjbcGoodsItemsInfo;
            }
            gjbcOrderInfo.setOrder_amount(finalAmout);
            BigDecimal bigTotalWight = new BigDecimal(totalWight / 1000);
            gjbcOrderInfo.setPkg_gweight(bigTotalWight.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            BigDecimal bigTotalSuttleWeight = new BigDecimal(totalSuttleWeight / 1000);
            gjbcOrderInfo.setGoods_nweight(bigTotalSuttleWeight.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            gjbcOrderInfo.setOrder_goods(goodsItemsInfos);
            String gjbcOrderInfosJson = JSON.toJSONString(gjbcOrderInfo);

            requestMap = getSysRequestData(gjbcSysData);
            String encode = Base64.encodeBase64String(gjbcOrderInfosJson.getBytes("utf-8"));

            requestMap.put("order", Base64.encodeBase64String(encode.getBytes("utf-8")));
            log.info("gjbc requestUrl====>" + gjbcSysData.getRequestUrl());
            HttpResult httpResult = HttpClientUtil.getInstance().post(gjbcSysData.getRequestUrl(), requestMap);
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


    @Override
    public EventResult PushOrderAliPay(Order order, GjbcSysData gjbcSysData) {
        try {
            Map<String, Object> requestMap = new TreeMap<>();
            requestMap.put("service", "alipay.acquire.customs");
            requestMap.put("_input_charset", "utf-8");
            requestMap.put("sign_type", "MD5");

            requestMap.put("out_request_no", order.getOrderId());
//            requestMap.put("out_request_no", order.getUnionOrderId());
            requestMap.put("trade_no", order.getPayNumber());
            requestMap.put("amount", order.getOnlinePayAmount());
            requestMap.put("partner", gjbcSysData.getAliPartner());
            requestMap.put("merchant_customs_code", gjbcSysData.getECommerceCode());
            requestMap.put("merchant_customs_name", gjbcSysData.getECommerceName());
            requestMap.put("customs_place", GjbcEnum.CustomerEnum.ZONGSHU.toString());
//            requestMap.put("")


            String sign = DtwUtil.aliBuildSign(requestMap, gjbcSysData.getAliKey());
            requestMap.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().get(GjbcConstant.ALI_PAY_URL, requestMap);
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
    public EventResult PushOrderWeiXin(Order order, GjbcSysData gjbcSysData) {
        try {
            WeiXinCustom weixinCustom = new WeiXinCustom();
            weixinCustom.setAppid(gjbcSysData.getWeiXinAppId());
            weixinCustom.setMchId(gjbcSysData.getWeixinMchId());
            weixinCustom.setOutTradeNo(order.getOrderId());
            weixinCustom.setTransactionId(order.getPayNumber());
            weixinCustom.setCustoms(GjbcEnum.CustomerEnum.HANGZHOU.toString());
            weixinCustom.setMchCustomsNo(gjbcSysData.getECommerceCode());
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
            String sign = DtwUtil.weixinBuildSign(requestMap, gjbcSysData.getWeixinKey());
            requestMap.put("sign", sign);
            weixinCustom.setSign(sign);

            String requestData = new XmlMapper().writeValueAsString(weixinCustom);
            HttpResult httpResult = HttpClientUtil.getInstance().post(GjbcConstant.WEIXIN_PAY_URL, requestData);
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

//    @Override
//    public EventResult PushOrderCustom(Order order, GjbcSysData gjbcSysData) {
//        try {
//            CustomOrder customOrder = converToCustomOrder(order, gjbcSysData);
//            if (customOrder == null) {
//                return EventResult.resultWith(EventResultEnum.ERROR, "支付方式暂时不支持", null);
//            }
//            String valueAsString = new XmlMapper().writeValueAsString(customOrder);
//            valueAsString += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
//            int start = valueAsString.indexOf("<jkfOrderDetail>");
//            int end = valueAsString.lastIndexOf("</jkfOrderDetail>");
//            valueAsString = valueAsString.substring(0, start) + "<jkfOrderDetailList>" + valueAsString.substring(start + 16, end) + "</jkfOrderDetailList>" + valueAsString.substring(end + 17);
//
//            //海关接口rsa加密
//            byte[] bytes = valueAsString.getBytes("utf-8");
//            byte[] privateKeyCode = java.util.Base64.getDecoder().decode(gjbcSysData.getRsaPrivateKey().getBytes("utf-8"));
//            byte[] aesKeyCode = Base64.getDecoder().decode(gjbcSysData.getAesKey().getBytes("utf-8"));
//            String sign = new String(Base64.getEncoder().encode(AESUtil.encrypt(bytes, privateKeyCode)), "utf-8");
//            String encData = new String(Base64.getEncoder().encode(RSAUtil.sign(bytes, aesKeyCode)), "utf-8");
//            String result = DtwUtil.requestCustomWebService(gjbcSysData.getCustomUrl(), encData, "IMPORTORDER", sign, gjbcSysData.getECommerceCode());
//            Element rootElement = DocumentHelper.parseText(result).getRootElement();
//            Element element = rootElement.element("body").element("list").element("jkfResult").element("chkMark");
//
//            //数据无误且成功推送到海关
//            if (element.getText().equals("1")) {
//                log.info("customerOrder push success");
//                return EventResult.resultWith(EventResultEnum.SUCCESS);
//            } else {
//                String xpath = "/mo/body/list/jkfResult/resultList/jkfResultDetail/resultInfo";
//                List<Element> selectNodes = DocumentHelper.parseText(result).selectNodes(xpath);
//                StringBuffer errorMsg = new StringBuffer();
//                selectNodes.stream().forEach(a -> errorMsg.append(a.getText()).append(";"));
//                log.info("customerOrder push failed" + errorMsg);
//                return EventResult.resultWith(EventResultEnum.ERROR, errorMsg.toString(), null);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
//        }
//    }


    private CustomOrder converToCustomOrder(Order order, GjbcSysData gjbcSysData) {
        CustomHead customHead = new CustomHead();
        customHead.setBusinessType("IMPORTORDER");

        CustomSign customSign = new CustomSign();
        customSign.setCompanyCode(gjbcSysData.getECommerceCode());
        customSign.setBusinessNo(order.getOrderId());//业务编号 使用订单编号
        customSign.setBusinessType("IMPORTORDER");
        customSign.setDeclareType("1");// 固定填写1
        customSign.setNote("");

        CustomOrderHead customOrderHead = new CustomOrderHead();
        customOrderHead.setCommerceCode(gjbcSysData.getECommerceCode());
        customOrderHead.setCommerceName(gjbcSysData.getECommerceName());
        customOrderHead.setIeFlag("I");
        customOrderHead.setPayType("03");
        OrderEnum.PaymentOptions paymentOptions = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, order.getPayType());
        if (paymentOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE_WEB || paymentOptions == OrderEnum.PaymentOptions.ALIPAY_PC || paymentOptions == OrderEnum.PaymentOptions.ALIPAY_MOBILE) {
            customOrderHead.setCompanyCode(GjbcConstant.ALI_PAY_CUSTOM_CODE);
        } else if (paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY_V3 || paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY
                || paymentOptions == OrderEnum.PaymentOptions.WEIXINPAY_APP) {
            customOrderHead.setPayCompanyCode(GjbcConstant.WEIXIN_PAY_CUSTOM_CODE);
        } else {
            return null;
        }

        customOrderHead.setPayNumber(order.getPayNumber());
        customOrderHead.setOrderTotalAmount(order.getFinalAmount());
        customOrderHead.setOrderNo(order.getOrderId());
        customOrderHead.setFeeAmount(order.getCostFreight());
        customOrderHead.setInsureAmount(0.0);
        customOrderHead.setCompanyCode(gjbcSysData.getECommerceCode());
        customOrderHead.setCompanyName(gjbcSysData.getECommerceName());
        customOrderHead.setTradeTime(order.getPayTime());
        customOrderHead.setCurrCode(String.valueOf(GjbcEnum.CurrencyEnum.CNY.getCode()));
        customOrderHead.setDiscount(Arith.sub(order.getFinalAmount(), order.getOnlinePayAmount()));

        customOrderHead.setConsigneeEmail(order.getShipEmail());
        customOrderHead.setConsigneeTel(order.getShipMobile());
        customOrderHead.setConsignee(order.getShipName());
        customOrderHead.setConsigneeAddress(order.getShipAddr());
        customOrderHead.setTotalCount(order.getItemNum());
        customOrderHead.setSenderCountry(GjbcEnum.CountryEnum.HONGKANG.getCode());

        String[] split = gjbcSysData.getSenderInfo().split(",");
        customOrderHead.setSenderName(split[0]);
        customOrderHead.setPurchaserId(String.valueOf(order.getMemberId()));
        customOrderHead.setLogisCompanyCode(GjbcConstant.LOGISTIC_CODE);
        customOrderHead.setLogisCompanyName(GjbcConstant.LOGISTIC_NAME);
        customOrderHead.setZipCode(order.getShipZip());
        customOrderHead.setNote(order.getMemo());
        customOrderHead.setRate("1");//人民币，统一填写1
        customOrderHead.setUserProcotol(GjbcConstant.USER_PROCOTOL);

        List<CustomOrderDetail> customOrderDetails = new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            CustomOrderDetail customOrderDetail = new CustomOrderDetail();
            customOrderDetail.setGoodsOrder(i);
            customOrderDetail.setGoodsName(orderItem.getName());
            customOrderDetail.setGoodsModel(orderItem.getStandard());
            customOrderDetail.setCodeTs(orderItem.getGoodBn().substring(3));
            customOrderDetail.setOriginCountry(GjbcEnum.CountryEnum.CHINA.getCode());
            customOrderDetail.setUnitPrice(orderItem.getPrice());
            customOrderDetail.setGoodsCount(orderItem.getNum());
            customOrderDetail.setGoodsUnit(GjbcEnum.UnitEnum.JIAN.getCode());
            customOrderDetail.setCurrency(String.valueOf(GjbcEnum.CurrencyEnum.CNY.getCode()));
            customOrderDetails.add(customOrderDetail);
        }

        CustomGoodsPurchaser customGoodsPurchaser = new CustomGoodsPurchaser();
        customGoodsPurchaser.setId(String.valueOf(order.getMemberId()));
        customGoodsPurchaser.setName(order.getBuyerName());
        customGoodsPurchaser.setEmail("");//注册邮箱 非必填
        customGoodsPurchaser.setTelNumber(order.getUserLoginName());
        customGoodsPurchaser.setPaperType("01");
        customGoodsPurchaser.setPaperNumber(order.getBuyerPid().toUpperCase());
        customOrderHead.setOrderTaxAmount(order.getTaxAmount());
        customOrderHead.setOrderGoodsAmount(order.getCostItem());
        customOrderHead.setTotalAmount(order.getCostItem());

        CustomOrderInfoList customOrderInfoList = new CustomOrderInfoList();
        CustomOrderInfo customOrderInfo = new CustomOrderInfo();
        customOrderInfo.setCustomSign(customSign);
        customOrderInfo.setCustomOrderHead(customOrderHead);
        customOrderInfo.setCustomOrderDetailList(customOrderDetails);
        customOrderInfo.setCustomGoodsPurchaser(customGoodsPurchaser);
        customOrderInfoList.setCustomOrderInfo(customOrderInfo);

        CustomBody customBody = new CustomBody();
        customBody.setOrerInfoList(customOrderInfoList);

        CustomOrder customOrder = new CustomOrder();
        customOrder.setBody(customBody);
        customOrder.setHead(customHead);


        log.info("\n**************CustomerOrder fee info**********************");
        log.info("tax:" + order.getTaxAmount());
        log.info("goodsAmount：" + customOrderHead.getOrderGoodsAmount());
        log.info("totalAmount：" + customOrderHead.getTotalAmount());
        log.info("order totalAmount:" + customOrderHead.getOrderTotalAmount());
        log.info("\n**********************************************************");
        //TODO 海关推送
        return customOrder;
    }
}
