package com.huobanplus.erpprovider.pineapple.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderDetailResult;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderItemResult;
import com.huobanplus.erpprovider.pineapple.bean.BLPOrderListResult;
import com.huobanplus.erpprovider.pineapple.exceptionhandler.BLPExceptionHandler;
import com.huobanplus.erpprovider.pineapple.handler.BLPOrderHandler;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderListInfo;
import com.huobanplus.erpservice.datacenter.model.OrderSearchInfo;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetOrderDetailListEvent;
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
public class BLPOrderHandlerImpl implements BLPOrderHandler{
    @Autowired
    private ERPRegister erpRegister;

    @Override
    public EventResult obtainOrderInfoList(String platOrderNo,int orderStatus, int pageSize, Integer pageIndex, String startTime,String method, ERPUserInfo erpUserInfo, String endTime) {
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        //得到erp使用者的事件处理器
        if (erpUserHandler == null) {
            return BLPExceptionHandler.handleException(method, EventResultEnum.NO_DATA, "未找到数据源信息");
        }
        if(!StringUtils.isEmpty(platOrderNo)){
           /**查询单个订单*/
            GetOrderDetailEvent orderDetailEvent = new GetOrderDetailEvent();
            orderDetailEvent.setErpUserInfo(erpUserInfo);
            orderDetailEvent.setOrderId(platOrderNo);
            EventResult eventResult = erpUserHandler.handleEvent(orderDetailEvent);
            if (eventResult == null) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            //todo 调用相关使用者获得订单详情
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
//            orderDetailResult.setGoodsFee();
            orderDetailResult.setTotalMoney(new BigDecimal(orderBean.getFinalAmount()));
            orderDetailResult.setFavourableMoney(new BigDecimal(orderBean.getPmtAmount()));
//            orderDetailResult.setCommissionValue(new BigDecimal(orderBean.));
            orderDetailResult.setTaxAmount(new BigDecimal(orderBean.getTaxAmount()));
//            orderDetailResult.setTariffAmount();
            orderDetailResult.setSendStyle(orderBean.getLogiName());
//            orderDetailResult.setQq(orderBean.);
            orderDetailResult.setPayTime(orderBean.getPayTime());
            orderDetailResult.setInvoiceTitle(orderBean.getTaxCompany());
//            orderDetailResult.setCodServiceFee();
            orderDetailResult.setCardType("身份证");
            orderDetailResult.setIdCard(orderBean.getBuyerPid());
            orderDetailResult.setIdCardTrueName(orderBean.getBuyerName());
            orderDetailResult.setReceiverName(orderBean.getShipName());
            orderDetailResult.setNick(orderBean.getUserNickname());
            OrderEnum.PaymentOptions payOption = EnumHelper.getEnumType(OrderEnum.PaymentOptions.class, orderBean.getPayStatus());
            orderDetailResult.setShouldPayType(payOption.getName());
//            orderDetailResult.setInvoiceTitle(orderBean.getInvoiceTitle());
            List<BLPOrderItemResult> orderItemResults = new ArrayList<>();
            orderBean.getOrderItems().forEach(item -> {
                BLPOrderItemResult orderItemResult = new BLPOrderItemResult();
                orderItemResult.setProductId(item.getProductId()+"");
//                orderItemResult.setSuborderNo(item.gets);
                orderItemResult.setTradeGoodsNo(item.getGoodId()+"");
                orderItemResult.setTradeGoodsName(item.getName());
                orderItemResult.setTradeGoodsSpec(item.getBrief());
                orderItemResult.setGoodsCount(item.getNum());
                orderItemResult.setPrice(new BigDecimal(item.getPrice()));
                orderItemResult.setRefundStatus(item.getRefundStatus()+"");
            });
            orderDetailResult.setGoodInfoList(orderItemResults);
            String resultXml = null;
            try {
                resultXml = new XmlMapper().writeValueAsString(orderDetailResult);
            } catch (JsonProcessingException e) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
            }
            int firstIndex = resultXml.indexOf("<goodinfos>");
            int lastIndex = resultXml.lastIndexOf("</goodinfos>");
            String firstPanel = resultXml.substring(0, firstIndex);
            String orderItemPanel = resultXml.substring(firstIndex + 6, lastIndex);
            String lastPanel = resultXml.substring(lastIndex + 7);
            String xmlResult = "<?xml version='1.0' encoding='UTF-8'?>" + firstPanel.replaceFirst("<Order xmlns=\"\">", "<Order>")
                    + orderItemPanel + lastPanel;
            return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
        }else {
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
                orderSearchInfo.setBeginUpdateTime(startTime);
                orderSearchInfo.setEndUpdateTime(endTime);
            }
            orderListEvent.setOrderSearchInfo(orderSearchInfo);
            //todo 调用相关使用者获得订单数据
            EventResult eventResult = erpUserHandler.handleEvent(orderListEvent);
            if (eventResult == null) {
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, eventResult.getResultMsg());
            }
            OrderListInfo orderListInfo = (OrderListInfo) eventResult.getData();
            List<Order> orderList = orderListInfo.getOrders();
            List<String> orderIdList = new ArrayList<>();
            orderList.forEach(order -> {
                orderIdList.add(order.getOrderId());
            });
            BLPOrderListResult orderListResult = new BLPOrderListResult();
            orderListResult.setNumTotalOrder(orderListInfo.getRecordCount());
            orderListResult.setPage(orderListInfo.getPageIndex());
            orderListResult.setSize(orderListInfo.getPageSize());
            orderListResult.setOrderNos(orderIdList);
            String orderResultXml = null;
            try {
                orderResultXml = new XmlMapper().writeValueAsString(orderListResult);
            } catch (JsonProcessingException e) {
                return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, "服务器错误--" + e.getMessage());
            }
            int firstIndex = orderResultXml.indexOf("<OrderNos>");
            int lastIndex = orderResultXml.lastIndexOf("</OrderNos>");
            String firstPanel = orderResultXml.substring(0, firstIndex);
            String orderPanel = orderResultXml.substring(firstIndex + 9, lastIndex);
            String lastPanel = orderResultXml.substring(lastIndex + 10);
            String xmlResult = "<?xml version='1.0' encoding='UTF-8'?>" + firstPanel.replaceFirst("<Order xmlns=\"\">", "<Order>")
                    + "<OrderList>" + orderPanel + "</OrderList>" + lastPanel;
            return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
        }
    }

    @Override
    public EventResult refundCheck(String OrderId, ERPUserInfo erpUserInfo, String method) {
        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        //得到erp使用者的事件处理器
        if (erpUserHandler == null) {
            return BLPExceptionHandler.handleException(method, EventResultEnum.NO_DATA, "未找到数据源信息");
        }
        /**查询单个订单*/
        GetOrderDetailEvent orderDetailEvent = new GetOrderDetailEvent();
        orderDetailEvent.setErpUserInfo(erpUserInfo);
        orderDetailEvent.setOrderId(OrderId);
        EventResult eventResult = erpUserHandler.handleEvent(orderDetailEvent);
        if (eventResult == null) {
            return BLPExceptionHandler.handleException(method, EventResultEnum.UNSUPPORT_EVENT, "不支持的ERP事件");
        }
        if (eventResult.getResultCode() != EventResultEnum.SUCCESS.getResultCode()) {
            return BLPExceptionHandler.handleException(method, EventResultEnum.ERROR, eventResult.getResultMsg());
        }
        //todo 调用相关使用者获得订单详情
        Order orderBean = (Order) eventResult.getData();

        return null;
    }

}
