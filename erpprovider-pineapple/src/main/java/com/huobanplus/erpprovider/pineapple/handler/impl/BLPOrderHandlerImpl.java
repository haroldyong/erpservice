package com.huobanplus.erpprovider.pineapple.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderDetailResult;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderItemResult;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderListResult;
import com.huobanplus.erpprovider.pineapple.handler.BLPOrderHandler;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderListInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushDeliveryInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hxh on 2017-06-07.
 */
@Service
public class BLPOrderHandlerImpl implements BLPOrderHandler {
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult obtainOrderInfoList(String platOrderNo, int orderStatus, int pageSize, Integer pageIndex, String startTime, String method, ERPUserInfo erpUserInfo, String endTime) {

        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            //得到erp使用者的事件处理器
            if (erpUserHandler == null) {
                return EventResult.resultWith(EventResultEnum.NO_DATA, "未找到数据源信息", null);
            }
        /*判断是否查询单个订单*/
            if (!StringUtils.isEmpty(platOrderNo)) {
            /*查询单个订单*/
                GetOrderDetailEvent orderDetailEvent = new GetOrderDetailEvent();
                orderDetailEvent.setErpUserInfo(erpUserInfo);
                orderDetailEvent.setOrderId(platOrderNo);
            /*获取订单*/
                EventResult eventResult = erpUserHandler.handleEvent(orderDetailEvent);
                if (eventResult == null) {
                    return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件", null);
                }
                if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                    return EventResult.resultWith(EventResultEnum.ERROR, eventResult.getResultMsg());
                }
            /*查询订单详情*/
                Order orderBean = (Order) eventResult.getData();
                BLPOrderDetailResult orderDetailResult = new BLPOrderDetailResult();
                orderDetailResult.setPayOrderNo(platOrderNo);
                OrderEnum.PayStatus payStatus = EnumHelper.getEnumType(OrderEnum.PayStatus.class, orderBean.getPayStatus());
                orderDetailResult.setTradeStatus(payStatus.getName());
                orderDetailResult.setTradeTime(orderBean.getCreateTime());
                orderDetailResult.setTradeStatusDescription(orderBean.getRemark());
                orderDetailResult.setPayOrderNo(orderBean.getPayNumber());
                orderDetailResult.setCountry("中国");
                orderDetailResult.setProvince(orderBean.getProvince());
                orderDetailResult.setCity(orderBean.getCity());
                orderDetailResult.setArea(orderBean.getShipArea());
                orderDetailResult.setTown(orderBean.getDistrict());
                orderDetailResult.setAddress(orderBean.getShipAddr());
                orderDetailResult.setZip(orderBean.getShipZip());
                orderDetailResult.setPhone(orderBean.getShipTel());
                orderDetailResult.setMobile(orderBean.getShipMobile());
                orderDetailResult.setEmail(orderBean.getShipEmail());
                orderDetailResult.setCustomerRemark(orderBean.getMemo());
                orderDetailResult.setPostFee(new BigDecimal(orderBean.getCostFreight()));
                orderDetailResult.setTotalMoney(new BigDecimal(orderBean.getFinalAmount()));
                orderDetailResult.setFavourableMoney(new BigDecimal(orderBean.getPmtAmount()));
                orderDetailResult.setTaxAmount(new BigDecimal(orderBean.getTaxAmount()));
                orderDetailResult.setSendStyle(orderBean.getLogiName());
                orderDetailResult.setPayTime(orderBean.getPayTime());
                orderDetailResult.setInvoiceTitle(orderBean.getTaxCompany());
                orderDetailResult.setCardType("身份证");
                orderDetailResult.setIdCard(orderBean.getBuyerPid());
                orderDetailResult.setIdCardTrueName(orderBean.getBuyerName());
                orderDetailResult.setReceiverName(orderBean.getShipName());
                orderDetailResult.setNick(orderBean.getUserNickname());
                OrderEnum.PaymentOptions payOption = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, orderBean.getPayStatus());
                orderDetailResult.setShouldPayType(payOption.getName());
                List<BLPOrderItemResult> orderItemResults = new ArrayList<>();
                orderBean.getOrderItems().forEach(item -> {
                    BLPOrderItemResult orderItemResult = new BLPOrderItemResult();
                    orderItemResult.setProductId(item.getProductId() + "");
                    orderItemResult.setTradeGoodsNo(item.getGoodId() + "");
                    orderItemResult.setTradeGoodsName(item.getName());
                    orderItemResult.setTradeGoodsSpec(item.getBrief());
                    orderItemResult.setGoodsCount(item.getNum());
                    orderItemResult.setPrice(new BigDecimal(item.getPrice()));
                    OrderEnum.AfterSalesStatus afterSalesStatus = EnumHelper.getEnumType(OrderEnum.AfterSalesStatus.class, item.getRefundStatus());
                    orderItemResult.setRefundStatus(afterSalesStatus.getName());
                    orderItemResults.add(orderItemResult);
                });
                orderDetailResult.setGoodInfoList(orderItemResults);
                String resultString = JSON.toJSONString(orderDetailResult);
                return EventResult.resultWith(EventResultEnum.SUCCESS, resultString);
            } else {
            /*查询订单列表*/
                GetOrderDetailListEvent orderListEvent = new GetOrderDetailListEvent();
                OrderSearchInfo orderSearchInfo = new OrderSearchInfo();
                orderListEvent.setErpUserInfo(erpUserInfo);
                orderSearchInfo.setPayStatus(orderStatus);
                orderSearchInfo.setPageSize(pageSize);
                orderSearchInfo.setPageIndex(pageIndex);
                if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
                    Date beginTime = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMonths(2));
                    Date lastTime = new Date();
                    orderSearchInfo.setBeginTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
                    orderSearchInfo.setEndTime(StringUtil.DateFormat(lastTime, StringUtil.TIME_PATTERN));
                } else {
                    orderSearchInfo.setBeginTime(startTime);
                    orderSearchInfo.setEndTime(endTime);
                }
                orderListEvent.setOrderSearchInfo(orderSearchInfo);
            /*获取订单*/
                EventResult eventResult = erpUserHandler.handleEvent(orderListEvent);
                if (eventResult == null) {
                    return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
                }
                if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                    return EventResult.resultWith(EventResultEnum.ERROR, eventResult.getResultMsg());
                }
                OrderListInfo orderListInfo = (OrderListInfo) eventResult.getData();
                List<Order> orderList = orderListInfo.getOrders();
                List<String> orderIdList = new ArrayList<>();
            /*遍历得到所有订单编号*/
                orderList.forEach(order -> {
                    orderIdList.add(order.getOrderId());
                });
                BLPOrderListResult orderListResult = new BLPOrderListResult();
                orderListResult.setNumTotalOrder(orderListInfo.getRecordCount());
                orderListResult.setPage(orderListInfo.getPageIndex());
                orderListResult.setSize(orderListInfo.getPageSize());
                orderListResult.setOrderNos(orderIdList);
                String orderResultString = JSON.toJSONString(orderListResult);
                return EventResult.resultWith(EventResultEnum.SUCCESS, orderResultString);
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, "服务器错误" + e.getMessage(), null);
        }
    }


    @Override
    public EventResult deliverOrder(String orderId, String logiName, String logiNo, ERPUserInfo erpUserInfo, String method) {
        try {
            ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (erpUserHandler == null) {
                return EventResult.resultWith(EventResultEnum.NO_DATA, "未找到数据源", null);
            }
            PushDeliveryInfoEvent pushDeliveryInfoEvent = new PushDeliveryInfoEvent();
            OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
            orderDeliveryInfo.setOrderId(orderId);
            orderDeliveryInfo.setLogiName(logiName);
            orderDeliveryInfo.setLogiNo(logiNo);
            pushDeliveryInfoEvent.setDeliveryInfo(orderDeliveryInfo);
            pushDeliveryInfoEvent.setErpUserInfo(erpUserInfo);
            EventResult eventResult = erpUserHandler.handleEvent(pushDeliveryInfoEvent);
            if (eventResult == null) {
                return EventResult.resultWith(EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件", null);
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return EventResult.resultWith(EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("message", "发货成功");
            return EventResult.resultWith(EventResultEnum.SUCCESS, "发货成功", jsonResult.toJSONString());
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, "服务器错误" + e.getMessage(), null);
        }
    }

}
