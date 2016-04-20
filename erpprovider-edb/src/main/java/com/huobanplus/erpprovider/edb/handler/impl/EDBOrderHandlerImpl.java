/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.common.EDBEnum;
import com.huobanplus.erpprovider.edb.formatedb.EDBCreateOrderInfo;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderDeliver;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderDetail;
import com.huobanplus.erpprovider.edb.formatedb.EDBOrderItem;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.httputil.HttpUtil;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.entity.OrderSync;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.datacenter.service.OrderOperatorService;
import com.huobanplus.erpservice.datacenter.service.OrderSyncService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.DeliveryInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 订单处理事件实现类
 * Created by allan on 2015/7/24.
 */
@Component
public class EDBOrderHandlerImpl extends BaseHandler implements EDBOrderHandler {
    @Autowired
    private OrderSyncService orderSyncService;
    @Autowired
    private OrderOperatorService orderOperatorService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
        EDBSysData sysData = JSON.parseObject(erpInfo.getSysDataJson(), EDBSysData.class);
        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        try {
            EDBCreateOrderInfo edbCreateOrderInfo = new EDBCreateOrderInfo();
            edbCreateOrderInfo.setOutTid(orderInfo.getOrderId());
            edbCreateOrderInfo.setShopId(sysData.getShopId());
            edbCreateOrderInfo.setStorageId(Integer.parseInt(sysData.getStorageId()));
//            edbCreateOrderInfo.setBuyerId();
            edbCreateOrderInfo.setBuyerMsg(orderInfo.getMemo());
            edbCreateOrderInfo.setSellerRemark(orderInfo.getRemark());
            edbCreateOrderInfo.setConsignee(orderInfo.getShipName());
            edbCreateOrderInfo.setAddress(orderInfo.getShipAddr());
            edbCreateOrderInfo.setPostcode(orderInfo.getShipZip());
            edbCreateOrderInfo.setTelephone(orderInfo.getShipTel());
            edbCreateOrderInfo.setMobilPhone(orderInfo.getShipMobile());
            String shipArea = orderInfo.getShipArea();
            if (!StringUtils.isEmpty(shipArea)) {
                String[] shipAreaArray = shipArea.split("/");
                edbCreateOrderInfo.setProvince(shipAreaArray[0]);
                if (shipAreaArray.length > 1) {
                    edbCreateOrderInfo.setCity(shipAreaArray[1]);
                    if (shipAreaArray.length > 2) {
                        edbCreateOrderInfo.setArea(shipAreaArray[2]);
                    }
                }
            }
            edbCreateOrderInfo.setActualFreightGet(orderInfo.getCostFreight());
            edbCreateOrderInfo.setActual_RP(orderInfo.getFinalAmount());
            edbCreateOrderInfo.setExpress(sysData.getExpress());
//            edbCreateOrderInfo.setOrderType(); //订单类型
            edbCreateOrderInfo.setProcessStatus(EnumHelper.getEnumName(EDBEnum.OrderStatusEnum.class, orderInfo.getOrderStatus()));
            edbCreateOrderInfo.setPayStatus(EnumHelper.getEnumName(EDBEnum.PayStatusEnum.class, orderInfo.getPayStatus()));
//            edbCreateOrderInfo.setPayStatus("买家已经申请退款,等待卖家同意");
//            edbCreateOrderInfo.setPayStatus("已发货");
            edbCreateOrderInfo.setDeliverStatus(EnumHelper.getEnumName(EDBEnum.ShipStatusEnum.class, orderInfo.getShipStatus()));
            edbCreateOrderInfo.setOrderTotalMoney(orderInfo.getFinalAmount());
            edbCreateOrderInfo.setProductTotalMoney(orderInfo.getCostItem());
            edbCreateOrderInfo.setPayMethod(orderInfo.getPaymentName());
            edbCreateOrderInfo.setFavorableMoney(orderInfo.getPmtAmount());
            edbCreateOrderInfo.setOutExpressMethod(orderInfo.getLogiName());
            edbCreateOrderInfo.setOrderDate(orderInfo.getCreateTime());
            edbCreateOrderInfo.setPayDate(orderInfo.getPayTime());
//            edbCreateOrderInfo.setDistributorNo(); 分销商编号
            edbCreateOrderInfo.setWuLiu(orderInfo.getLogiName());
            edbCreateOrderInfo.setWuLiuNo(orderInfo.getLogiNo());
            edbCreateOrderInfo.setActualFreightPay(orderInfo.getCostFreight());
            List<EDBOrderItem> edbOrderItemList = new ArrayList<>();
            for (OrderItem orderItem : orderInfo.getOrderItems()) {
                EDBOrderItem edbOrderItem = new EDBOrderItem();
                edbOrderItem.setBarCode(orderItem.getProductBn());
                edbOrderItem.setProductTitle(orderItem.getName());
                edbOrderItem.setStandard(orderItem.getStandard());
                edbOrderItem.setOutPrice(orderItem.getAmount());
                edbOrderItem.setOrderGoodsNum(orderItem.getNum());
                edbOrderItem.setCostPrice(orderItem.getAmount());
                edbOrderItem.setShopId(sysData.getShopId());
                edbOrderItem.setOutTid(orderInfo.getOrderId());
                edbOrderItem.setOutBarCode(orderItem.getProductBn());
                edbOrderItem.setProductIntro(orderItem.getBrief());
                edbOrderItemList.add(edbOrderItem);
            }
            edbCreateOrderInfo.setProductInfos(edbOrderItemList);

            Date now = new Date();
            //订单同步日志
            OrderOperatorLog orderOperatorLog = new OrderOperatorLog();
            orderOperatorLog.setProviderType(erpInfo.getErpType());
            orderOperatorLog.setUserType(erpUserInfo.getErpUserType());
            orderOperatorLog.setCustomerId(erpUserInfo.getCustomerId());
            orderOperatorLog.setCreateTime(now);
            orderOperatorLog.setOrderId(orderInfo.getOrderId());
//            orderOperatorLog.setOrderJsonData(pushNewOrderEvent.getOrderInfoJson());
            orderOperatorLog.setEventInfo(JSON.toJSONString(pushNewOrderEvent));

            //订单同步记录
            OrderSync orderSync = orderSyncService.getOrderSync(orderInfo.getOrderId(), erpUserInfo.getCustomerId());
            orderSync.setOrderStatus(EnumHelper.getEnumType(OrderEnum.OrderStatus.class, orderInfo.getOrderStatus()));
            orderSync.setPayStatus(EnumHelper.getEnumType(OrderEnum.PayStatus.class, orderInfo.getPayStatus()));
            orderSync.setShipStatus(EnumHelper.getEnumType(OrderEnum.ShipStatus.class, orderInfo.getShipStatus()));
            orderSync.setProviderType(ERPTypeEnum.ProviderType.EDB);
            orderSync.setUserType(erpUserInfo.getErpUserType());
            orderSync.setRemark(orderOperatorLog.getRemark());
            EventResult eventResult = this.orderPush(edbCreateOrderInfo, sysData);

            if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                orderOperatorLog.setResultStatus(true);
                orderOperatorLog.setRemark(pushNewOrderEvent.getEventType().getName() + "成功");
                switch (pushNewOrderEvent.getEventType()) {
                    case PUSH_NEW_ORDER:
                        orderSync.setOutPayStatus("已付款");
                        orderSync.setOutShipStatus("未发货");
                        orderSync.setOrderSyncStatus(OrderSyncStatus.DELIVERYED);
                        break;
                    case DELIVERY_SYNC:
                        orderSync.setOutPayStatus("已付款");
                        orderSync.setOutShipStatus("已发货");
                        break;
                }
            } else {
                orderOperatorLog.setResultStatus(false);
                switch (pushNewOrderEvent.getEventType()) {
                    case PUSH_NEW_ORDER:
                        orderSync.setOrderSyncStatus(OrderSyncStatus.WAITING_FOR_PUSHING);
                        break;
                }
                orderOperatorLog.setRemark(pushNewOrderEvent.getEventType().getName() + "失败");
            }
            orderSync.setResultStatus(orderOperatorLog.isResultStatus());
            orderSync.setRemark(orderOperatorLog.getRemark());

            orderSyncService.save(orderSync);
            orderOperatorService.save(orderOperatorLog);
            return eventResult;
        } catch (IOException ex) {
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }
    }

    public EventResult obtainOrderList(int pageIndex, EDBSysData sysData, EDBOrderSearch edbOrderSearch) {
        try {
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
            //系统参数
            requestData.put("storage_id", sysData.getStorageId());
            requestData.put("shopid", sysData.getShopId());

//            requestData.put("date_type", EDBEnum.OrderDateType.DELIVERY_TIME.getName());
            requestData.put("begin_time", edbOrderSearch.getBeginTime());
            requestData.put("end_time", edbOrderSearch.getEndTime());
//            requestData.put("begin_time", "2016-03-07");
//            requestData.put("end_time", "2016-03-10");
            requestData.put("page_no", pageIndex);
            requestData.put("page_size", EDBConstant.PAGE_SIZE);
            requestData.put("order_status", edbOrderSearch.getShipStatus().getName());
            requestData.put("platform_status", edbOrderSearch.getPlatformStatus().getName());
            requestData.put("proce_Status", edbOrderSearch.getProceStatus().getName());
//            requestData.put("out_tid", "2016033087939569");

            Map<String, Object> signMap = new TreeMap<>(requestData);
            String sign = getSign(signMap, sysData);
            requestData.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent()).getJSONObject("Success");

                if (jsonObject == null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
                }
                return EventResult.resultWith(EventResultEnum.SUCCESS, jsonObject);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (IOException ex) {
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }

    }

//    @Override
//    public EventResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException {
//        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);
//
//        Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_STATUS_UPDATE, sysData);
//        requestData.put("num_id", orderInfo.getOrderId());
////        requestData.put("tid_type", orderInfo.getOrderType());
////        requestData.put("import_mark", orderInfo.getImportMark());
//        Map<String, Object> signMap = new TreeMap<>(requestData);
//
//        requestData.put("sign", getSign(signMap, sysData));
//
//        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
//        if (responseData == null) {
//            return EventResult.resultWith(EventResultEnum.ERROR, responseData);
//        }
//        return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
//    }
//
//    @Override
//    public EventResult orderUpdate(MallOrderBean orderInfo, ERPInfo info) {
//        try {
//            EDBOrderForUpdate orderForUpdate = new EDBOrderForUpdate();
//            EventResult eventResult = this.getOrderDetail(orderInfo.getOrderId(), info);
//            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
//                return EventResult.resultWith(EventResultEnum.ERROR, "从edb获取数据失败--" + eventResult.getResultMsg(), null);
//            }
//            String tid = ((EDBOrderDetail) eventResult.getData()).getTid();
//
//            orderForUpdate.setTid(tid);
//            orderForUpdate.setOutTid(orderInfo.getOrderId());
//            orderForUpdate.setExpress(orderInfo.getLogiName());
//            orderForUpdate.setExpressNo(orderInfo.getLogiNo());
////        orderForUpdate.setExpressCode(orderInfo.getExpressCoding());
////        orderForUpdate.setPrinter(orderInfo.getPrinter());
////        orderForUpdate.setCargoOperator(orderInfo.getDistributer());
////        orderForUpdate.setCargoTime(StringUtil.DateFormat(orderInfo.getDistributTime(), StringUtil.TIME_PATTERN));
////        orderForUpdate.setPrintTime(StringUtil.DateFormat(orderInfo.getPrintTime(), StringUtil.TIME_PATTERN));
////        orderForUpdate.setInspecter(orderInfo.getInspecter());
////        orderForUpdate.setInspectTime(StringUtil.DateFormat(orderInfo.getInspectTime(), StringUtil.TIME_PATTERN));
//            //orderForUpdate.setIsInspectDelivery(orderInfo.getIsInspectDelivery());
////        orderForUpdate.setDeliveryOperator(orderInfo.getDeliveryOperator());
////            orderForUpdate.setDeliveryTime(StringUtil.DateFormat(orderInfo.getDeliverTime(), StringUtil.TIME_PATTERN));
//            orderForUpdate.setGrossWeight(orderInfo.getWeight());
////        orderForUpdate.setInternalNote(orderInfo.getInnerLable());
////        orderForUpdate.setOriginCode(orderInfo.getOriginCode());
////        orderForUpdate.setDestCode(orderInfo.getDestCode());
//            List<EDBProductForUpdate> productForUpdates = new ArrayList<>();
//            for (MallOrderItemBean orderItem : orderInfo.getOrderItemBeans()) {
//                EDBProductForUpdate productForUpdate = new EDBProductForUpdate();
//                productForUpdate.setTid(tid);
//                productForUpdate.setBarCode(orderItem.getBn());
////            productForUpdate.setInspectionNum(orderItem.getInspectionNum());
//                productForUpdates.add(productForUpdate);
//            }
//            orderForUpdate.setProductForUpdates(productForUpdates);
//            EDBUpdateOrder updateOrder = new EDBUpdateOrder(orderForUpdate);
//
//            XmlMapper xmlMapper = new XmlMapper();
//            String xmlResult = xmlMapper.writeValueAsString(updateOrder);
//            int firstIndex = xmlResult.indexOf("<product_item>");
//            int lastIndex = xmlResult.lastIndexOf("</product_item>");
//            String firstPanel = xmlResult.substring(0, firstIndex);
//            String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
//            String xmlValues = firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></order>";
//
//            EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);
//
//            Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_UPDATE, sysData);
//            Map<String, Object> signMap = new TreeMap<>(requestData);
//            requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
//            signMap.put("xmlValues", xmlValues);
//            requestData.put("sign", getSign(signMap, sysData));
//
//            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
//
//            if (responseData == null) {
//                return EventResult.resultWith(EventResultEnum.ERROR, responseData);
//            }
//            return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
//        } catch (Exception e) {
//            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
//        }
//    }

    @Override
    public EventResult orderDeliver(PushDeliveryInfoEvent pushDeliveryInfoEvent) {
        try {
            EDBSysData sysData = JSON.parseObject(pushDeliveryInfoEvent.getErpInfo().getSysDataJson(), EDBSysData.class);
            DeliveryInfo deliveryInfo = pushDeliveryInfoEvent.getDeliveryInfo();
            EDBOrderDeliver orderDeliver = new EDBOrderDeliver();
            orderDeliver.setOrderId(deliveryInfo.getOrderId());
//            orderDeliver.setDeliveryTime(StringUtil.DateFormat(deliverTime, StringUtil.TIME_PATTERN));
            orderDeliver.setExpress(deliveryInfo.getLogiName());
            orderDeliver.setExpressNo(deliveryInfo.getLogiNo());

            String xmlValues = "<order>" + new XmlMapper().writeValueAsString(orderDeliver) + "</order>";

            Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_DELIVER, sysData);
            requestData.put("xmlValues", xmlValues);
            requestData.put("sign", getSign(requestData, sysData));

            HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {

                return EventResult.resultWith(EventResultEnum.SUCCESS);
            }

            return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult getOrderDetail(String orderId, ERPInfo erpInfo) {
        try {
            EDBSysData sysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
            requestData.put("out_tid", orderId);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            String beginTime = StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN);
            String endTime = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            requestData.put("begin_time", URLEncoder.encode(beginTime, StringUtil.UTF8));
            requestData.put("end_time", URLEncoder.encode(endTime, StringUtil.UTF8));
            signMap.put("begin_time", beginTime);
            signMap.put("end_time", endTime);

            requestData.put("sign", getSign(signMap, sysData));
            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            JSONObject jsonObject = JSON.parseObject(responseData);
            //如果成功
            //得到EDBOrderDetail
            if (jsonObject.getJSONObject("Success") != null) {
                JSONArray jsonArray = jsonObject.getJSONObject("items").getJSONArray("item");
                if (jsonArray.size() == 0) {
                    return EventResult.resultWith(EventResultEnum.NO_DATA, "未找到相关订单数据", null);
                }
                String orderInfoJson = jsonArray.getJSONObject(0).toJSONString();
                EDBOrderDetail orderDetail = JSON.parseObject(orderInfoJson, EDBOrderDetail.class);
                return EventResult.resultWith(EventResultEnum.SUCCESS, orderDetail);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
            }


        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult cancelOrder(String orderId, ERPInfo erpInfo) {
        try {
            EDBSysData sysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
            EventResult eventResult = this.getOrderDetail(orderId, erpInfo);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "从edb获取数据失败--" + eventResult.getResultMsg(), null);
            }
            String tid = ((EDBOrderDetail) eventResult.getData()).getTid();
            Map<String, Object> requestData = getSysRequestData(EDBConstant.CANCEL_ORDER, sysData);
            requestData.put("tid", tid);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            requestData.put("sign", getSign(signMap, sysData));
            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            JSONObject jsonObject = JSON.parseObject(responseData);
            return EventResult.resultWith(EventResultEnum.SUCCESS);
        } catch (IOException e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult orderPush(EDBCreateOrderInfo edbCreateOrderInfo, EDBSysData sysData) throws JsonProcessingException, UnsupportedEncodingException {
        String xmlResult = new XmlMapper().writeValueAsString(edbCreateOrderInfo);
        int firstIndex = xmlResult.indexOf("<product_item>");
        int lastIndex = xmlResult.lastIndexOf("</product_item>");
        String firstPanel = xmlResult.substring(0, firstIndex);
        String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
        String xmlValues = ("<order>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></order>").replaceAll(" xmlns=\"\"", "");


        Map<String, Object> requestData = getSysRequestData(EDBConstant.CREATE_ORDER, sysData);
        requestData.put("xmlvalues", xmlValues);
        Map<String, Object> signMap = new TreeMap<>(requestData);
//            requestData.put("xmlvalues", URLEncoder.encode(xmlValues, "utf-8"));
//            signMap.put("xmlvalues", xmlValues);

        requestData.put("sign", getSign(signMap, sysData));

        HttpResult httpResult = HttpClientUtil.getInstance().post(sysData.getRequestUrl(), requestData);
        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            System.out.println("订单:" + edbCreateOrderInfo.getOutTid() + "已推送edb");
            JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
            if (jsonObject.getJSONObject("Success") == null) {
                return EventResult.resultWith(EventResultEnum.ERROR, jsonObject.getString("error_msg"), null);
            }
            return EventResult.resultWith(EventResultEnum.SUCCESS);
        }

        return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
    }


//
//    private MallOrderBean wrapMapToBean(Map map) {
//        MallOrderBean orderBean = new MallOrderBean();
//        orderBean.setStorageId((String) map.get("storage_id"));
//        orderBean.setTid((String) map.get("tid"));
//        orderBean.setTransactionId((String) map.get("transaction_id"));
//        orderBean.setOutTid((String) map.get("out_tid"));
//        orderBean.setOutPayTid((String) map.get("out_pay_tid"));
//        orderBean.setShopId((String) map.get("shopid"));
//        orderBean.setBuyerId((String) map.get("buyer_id"));
//        orderBean.setBuyerName((String) map.get("buyer_name"));
//        orderBean.setType((String) map.get("type"));
//        orderBean.setStatus((String) map.get("status"));
//        orderBean.setAbnormalStatus((String) map.get("abnormal_status"));
//        orderBean.setReceiverName((String) map.get("receiver_name"));
//        orderBean.setReceiverMobile((String) map.get("receiver_mobile"));
//        orderBean.setPhone((String) map.get("phone"));
//        orderBean.setProvince((String) map.get("province"));
//        orderBean.setCity((String) map.get("city"));
//        orderBean.setDistrict((String) map.get("district"));
//        orderBean.setAddress((String) map.get("address"));
//        orderBean.setPost((String) map.get("post"));
//        orderBean.setEmail((String) map.get("email"));
//        orderBean.setIsBill((Integer) map.get("is_bill"));
//        orderBean.setInvoiceName((String) map.get("invoice_name"));
//        orderBean.setInvoiceSituation(((Number) map.get("invoice_situation")).intValue());
//        orderBean.setInvoiceTitle((String) map.get("invoice_title"));
//        orderBean.setInvoiceType((String) map.get("invoice_type"));
//        orderBean.setInvoiceContent((String) map.get("invoice_content"));
//        orderBean.setProTotalFee(((Number) map.get("pro_totalfee")).doubleValue());
//        orderBean.setOrderTotalFee(((Number) map.get("order_totalfee")).doubleValue());
//        orderBean.setRefundTotalFee(String.valueOf(map.get("refund_totalfee")));
//        orderBean.setExpressNo((String) map.get("express_no"));
//        orderBean.setExpress((String) map.get("express"));
//        orderBean.setExpressCoding((String) map.get("express_coding"));
//        orderBean.setOnlineExpress((String) map.get("online_express"));
//        orderBean.setSendingType((String) map.get("sending_type"));
//        orderBean.setRealIncomefreight(((Number) map.get("real_income_freight")).doubleValue());
//        orderBean.setRealPayFreight(((Number) map.get("real_pay_freight")).doubleValue());
//        orderBean.setGrossWeight((String) map.get("gross_weight"));
//        orderBean.setGrossWeightFreight(((Number) map.get("gross_weight_freight")).doubleValue());
//        orderBean.setNetWeightWreight((String) map.get("net_weight_freight"));
//        orderBean.setOrderCreater((String) map.get("order_creater"));
//        orderBean.setBusinessMan((String) map.get("business_man"));
//        orderBean.setReviewOrdersOperator((String) map.get("review_orders_operator"));
////                orderBean.setReviewOrdersTime(map.get("review_orders_time"));
//        orderBean.setAdvDistributer((String) map.get("adv_distributer"));
//        orderBean.setAdvDistributTime(StringUtil.DateFormat((String) map.get("adv_distribut_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setInspecter((String) map.get("inspecter"));
//        orderBean.setInspectTime(StringUtil.DateFormat((String) map.get("inspect_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setCancelOperator((String) map.get("cancel_operator"));
//        orderBean.setCancelTime(StringUtil.DateFormat((String) map.get("cancel_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setBookDeliveryTime(StringUtil.DateFormat((String) map.get("book_delivery_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setDeliveryOperator((String) map.get("delivery_time"));
//        orderBean.setLocker((String) map.get("locker"));
//        orderBean.setLockTime(StringUtil.DateFormat((String) map.get("lock_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setBookFileTime(StringUtil.DateFormat((String) map.get("book_file_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setFileOperator((String) map.get("file_operator"));
//        orderBean.setFileTime(StringUtil.DateFormat((String) map.get("file_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setFinishTime(StringUtil.DateFormat((String) map.get("finish_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setModityTime(StringUtil.DateFormat((String) map.get("modity_time"), EDBConstant.TIME_PATTERN));
//        orderBean.setDeliveryStatus((String) map.get("delivery_status"));
//        List<Map> proList = (List<Map>) map.get("tid_item");
//        List<MallOrderItemBean> orderItems = new ArrayList<>();
//        for (Map proMap : proList) {
//            MallOrderItemBean orderItem = new MallOrderItemBean();
//            orderItem.setStorageId((String) proMap.get("storage_id"));
//            orderItem.setProDetailCode((String) proMap.get("pro_detail_code"));
//            orderItem.setProName((String) proMap.get("pro_name"));
//            orderItem.setBarcode((String) proMap.get("barcode"));
//            orderItem.setIsCancel((String) proMap.get("iscancel"));
//            orderItem.setStockSituation((String) proMap.get("stock_situation"));
//            orderItem.setBookStorage(((Number) proMap.get("book_storage")).intValue());
//            orderItem.setProNum(((Number) proMap.get("pro_num")).intValue());
//            orderItem.setSendNum(((Number) proMap.get("send_num")).intValue());
//            orderItem.setRefundNum(((Number) proMap.get("refund_num")).intValue());
//            orderItem.setRefundReNum(((Number) proMap.get("refund_renum")).intValue());
//            orderItem.setInspectionNum(((Number) proMap.get("inspection_num")).intValue());
//            orderItem.setTimeInventory(((Number) proMap.get("timeinventory")).intValue());
//            orderItem.setOutTid((String) proMap.get("out_tid"));
//            orderItem.setOutProId((String) proMap.get("out_proid"));
//            orderItem.setDistributer((String) proMap.get("distributer"));
//            orderItem.setDistributTime(StringUtil.DateFormat((String) map.get("distribut_time"), EDBConstant.TIME_PATTERN));
//            orderItem.setBookInventory(((Number) proMap.get("book_inventory")).intValue());
//            orderItems.add(orderItem);
//        }
//
//        return orderBean;
//    }
}
