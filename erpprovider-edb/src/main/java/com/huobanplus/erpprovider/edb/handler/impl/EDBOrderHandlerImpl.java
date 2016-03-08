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
import com.huobanplus.erpprovider.edb.formatedb.*;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.search.EDBOrderSearch;
import com.huobanplus.erpprovider.edb.util.EDBConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.httputil.HttpUtil;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.entity.MallOrderBean;
import com.huobanplus.erpservice.datacenter.entity.MallOrderItemBean;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderScheduledLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
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
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderScheduledLogService scheduledLogService;
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult createOrder(Order orderInfo, ERPInfo info) {
        try {
            EDBCreateOrderInfo edbCreateOrderInfo = new EDBCreateOrderInfo();
            EDBSysData sysData = JSON.parseObject(info.getSysDataJson(), EDBSysData.class);
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
                edbCreateOrderInfo.setCity(shipAreaArray[1]);
                edbCreateOrderInfo.setArea(shipAreaArray[2]);
            }
            edbCreateOrderInfo.setActualFreightGet(orderInfo.getCostFreight());
            edbCreateOrderInfo.setActual_RP(orderInfo.getFinalAmount());
            edbCreateOrderInfo.setExpress(sysData.getExpress());
//            edbCreateOrderInfo.setOrderType(); //订单类型
            edbCreateOrderInfo.setProcessStatus(EnumHelper.getEnumName(EDBEnum.OrderStatusEnum.class, orderInfo.getOrderStatus()));
            edbCreateOrderInfo.setPayStatus(EnumHelper.getEnumName(EDBEnum.PayStatusEnum.class, orderInfo.getPayStatus()));
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


            return this.orderPush(edbCreateOrderInfo, sysData);
        } catch (IOException ex) {
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }
    }

    /**
     * 订单搜索轮询
     * fixedRate 轮询间隔 单位毫秒   60 000 = 60秒 也就是1分钟
     * initialDelay web容器启动后延迟多久才调用该轮询方法。单位毫秒   60 000 = 60秒 也就是1分钟。建议fixedRate 和 initialDelay 两个值设置成一样
     *
     * @return
     * @throws IOException
     */
//    @Scheduled(fixedRate = 60000, initialDelay = 60000)
//    @Override
//    public void obtainOrderList() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        //objectMapper.readValue(info.getSysDataJson(), EDBSysData.class);
//        System.out.println("正在轮询...");
//        //取出需要轮询的数据
//        List<MallOrderBean> orderList = orderService.findByRotaryStatus(1);
//        for (MallOrderBean order : orderList) {
//            EDBSysData sysData = objectMapper.readValue(order.getSysDataJson(), EDBSysData.class);
//            Map<String, String> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
//            requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
//            requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
//            requestData.put("out_tid", order.getOrderId());
//            Map<String, String> signMap = new TreeMap<>(requestData);
//            requestData.put("sign", getSign(signMap, sysData));
//
//            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
//
//            Map formatM = objectMapper.readValue(responseData, Map.class);
//
////            if (responseData == null) {
////                return new SimpleMonitor<>(new EventResult(0, responseData));
////            }
//            if (formatM.keySet().iterator().next().equals("Success")) {
//                //数据处理
//                List<Map> list = (List<Map>) ((Map) ((Map) formatM.get("Success")).get("items")).get("item");
//                MallOrderBean responseOrder = wrapMapToBean(list.get(0));
//                if (!responseOrder.getDeliveryStatus().equals(order.getDeliveryStatus())) {
//                    //todo 讲物流信息等必要数据推送给伙伴商城
//
//                    //todo 推送成功后，轮询状态设置为完成
//                    order.setRotaryStatus(2);
//                    orderService.save(order);
//                }
//            }
//        }
//    }
    public EventResult obtainOrderList(int pageIndex, EDBSysData sysData, EDBOrderSearch edbOrderSearch) {
        try {
            Map<String, Object> requestData = getSysRequestData(EDBConstant.GET_ORDER_INFO, sysData);
            //系统参数
            requestData.put("storage_id", sysData.getStorageId());
            requestData.put("shopid", sysData.getShopId());

//            requestData.put("date_type", EDBEnum.OrderDateType.DELIVERY_TIME.getName());
            requestData.put("begin_time", StringUtil.DateFormat(edbOrderSearch.getBeginTime(), StringUtil.TIME_PATTERN));
            requestData.put("end_time", StringUtil.DateFormat(edbOrderSearch.getEndTime(), StringUtil.TIME_PATTERN));
            requestData.put("page_no", pageIndex);
            requestData.put("page_size", EDBConstant.PAGE_SIZE);
//            requestData.put("order_status", EDBEnum.ShipStatusEnum.ALL_DELIVER);
            requestData.put("order_status", edbOrderSearch.getShipStatus().getName());
//            requestData.put("platform_status", EDBEnum.PlatformStatus.PAYED);
            requestData.put("platform_status", edbOrderSearch.getPlatformStatus().getName());
            requestData.put("proce_Status", edbOrderSearch.getProceStatus().getName());

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

    @Override
    public EventResult orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_STATUS_UPDATE, sysData);
        requestData.put("num_id", orderInfo.getOrderId());
//        requestData.put("tid_type", orderInfo.getOrderType());
//        requestData.put("import_mark", orderInfo.getImportMark());
        Map<String, Object> signMap = new TreeMap<>(requestData);

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return EventResult.resultWith(EventResultEnum.ERROR, responseData);
        }
        return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
    }

    @Override
    public EventResult orderUpdate(MallOrderBean orderInfo, ERPInfo info) {
        try {
            EDBOrderForUpdate orderForUpdate = new EDBOrderForUpdate();
            EventResult eventResult = this.getOrderDetail(orderInfo.getOrderId(), info);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "从edb获取数据失败--" + eventResult.getResultMsg(), null);
            }
            String tid = ((EDBOrderDetail) eventResult.getData()).getTid();

            orderForUpdate.setTid(tid);
            orderForUpdate.setOutTid(orderInfo.getOrderId());
            orderForUpdate.setExpress(orderInfo.getLogiName());
            orderForUpdate.setExpressNo(orderInfo.getLogiNo());
//        orderForUpdate.setExpressCode(orderInfo.getExpressCoding());
//        orderForUpdate.setPrinter(orderInfo.getPrinter());
//        orderForUpdate.setCargoOperator(orderInfo.getDistributer());
//        orderForUpdate.setCargoTime(StringUtil.DateFormat(orderInfo.getDistributTime(), StringUtil.TIME_PATTERN));
//        orderForUpdate.setPrintTime(StringUtil.DateFormat(orderInfo.getPrintTime(), StringUtil.TIME_PATTERN));
//        orderForUpdate.setInspecter(orderInfo.getInspecter());
//        orderForUpdate.setInspectTime(StringUtil.DateFormat(orderInfo.getInspectTime(), StringUtil.TIME_PATTERN));
            //orderForUpdate.setIsInspectDelivery(orderInfo.getIsInspectDelivery());
//        orderForUpdate.setDeliveryOperator(orderInfo.getDeliveryOperator());
//            orderForUpdate.setDeliveryTime(StringUtil.DateFormat(orderInfo.getDeliverTime(), StringUtil.TIME_PATTERN));
            orderForUpdate.setGrossWeight(orderInfo.getWeight());
//        orderForUpdate.setInternalNote(orderInfo.getInnerLable());
//        orderForUpdate.setOriginCode(orderInfo.getOriginCode());
//        orderForUpdate.setDestCode(orderInfo.getDestCode());
            List<EDBProductForUpdate> productForUpdates = new ArrayList<>();
            for (MallOrderItemBean orderItem : orderInfo.getOrderItemBeans()) {
                EDBProductForUpdate productForUpdate = new EDBProductForUpdate();
                productForUpdate.setTid(tid);
                productForUpdate.setBarCode(orderItem.getBn());
//            productForUpdate.setInspectionNum(orderItem.getInspectionNum());
                productForUpdates.add(productForUpdate);
            }
            orderForUpdate.setProductForUpdates(productForUpdates);
            EDBUpdateOrder updateOrder = new EDBUpdateOrder(orderForUpdate);

            XmlMapper xmlMapper = new XmlMapper();
            String xmlResult = xmlMapper.writeValueAsString(updateOrder);
            int firstIndex = xmlResult.indexOf("<product_item>");
            int lastIndex = xmlResult.lastIndexOf("</product_item>");
            String firstPanel = xmlResult.substring(0, firstIndex);
            String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
            String xmlValues = firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></order>";

            EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

            Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_UPDATE, sysData);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
            signMap.put("xmlValues", xmlValues);
            requestData.put("sign", getSign(signMap, sysData));

            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);

            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR, responseData);
            }
            return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR.getResultCode(), EventResultEnum.ERROR.getResultMsg() + "--" + e.getMessage(), null);
        }
    }

    @Override
    public EventResult orderDeliver(String orderId, Date deliverTime, String expressNo, String express, String weight, ERPInfo info) {
        try {
            EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);
            //todo 通过orderId获取edb平台tid
            EventResult eventResult = this.getOrderDetail(orderId, info);
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, "从edb获取数据失败--" + eventResult.getResultMsg(), null);
            }
            String tid = ((EDBOrderDetail) eventResult.getData()).getTid();

            EDBOrderDeliver orderDeliver = new EDBOrderDeliver();
            orderDeliver.setOrderId(tid);
            orderDeliver.setDeliveryTime(StringUtil.DateFormat(deliverTime, StringUtil.TIME_PATTERN));
            orderDeliver.setExpress(URLEncoder.encode(express, "utf-8"));
            orderDeliver.setExpressNo(expressNo);
            orderDeliver.setWeight(URLEncoder.encode(weight, "utf-8"));

            String xmlValues = "<order>" + new XmlMapper().writeValueAsString(orderDeliver) + "</order>";

            Map<String, Object> requestData = getSysRequestData(EDBConstant.ORDER_DELIVER, sysData);
            Map<String, Object> signMap = new TreeMap<>(requestData);
            requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
            signMap.put("xmlValues", xmlValues);

            requestData.put("sign", getSign(signMap, sysData));

            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
            if (responseData == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
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
