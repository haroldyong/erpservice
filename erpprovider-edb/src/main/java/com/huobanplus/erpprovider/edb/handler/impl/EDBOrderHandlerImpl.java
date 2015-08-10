package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.*;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 订单处理事件实现类
 * Created by allan on 2015/7/24.
 */
@Component
public class EDBOrderHandlerImpl extends BaseHandler implements EDBOrderHandler {

    @Override
    public Monitor<EventResult> createOrder(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        HttpUtil htNetService = HttpUtil.getInstance();

        EDBCreateOrderInfo edbCreateOrderInfo = new EDBCreateOrderInfo();
        edbCreateOrderInfo.setTid(orderInfo.getTid());
        edbCreateOrderInfo.setOutTid(orderInfo.getOutTid());
        edbCreateOrderInfo.setShopId(orderInfo.getShopId());
        edbCreateOrderInfo.setStorageId(Integer.parseInt(orderInfo.getStorageId()));
        edbCreateOrderInfo.setBuyerId(orderInfo.getBuyerId());
        edbCreateOrderInfo.setBuyerEmail(orderInfo.getBuyerEmail());
        edbCreateOrderInfo.setBuyerAlipay(orderInfo.getBuyerAlipay());
        edbCreateOrderInfo.setSellerRemark(orderInfo.getServiceRemarks());
        edbCreateOrderInfo.setConsignee(orderInfo.getConsignee());
        edbCreateOrderInfo.setAddress(orderInfo.getAddress());
        edbCreateOrderInfo.setPostcode(orderInfo.getPost());
        edbCreateOrderInfo.setTelephone(orderInfo.getPhone());
        edbCreateOrderInfo.setMobilPhone(orderInfo.getReceiverMobile());
        edbCreateOrderInfo.setProvince(orderInfo.getProvince());
        edbCreateOrderInfo.setCity(orderInfo.getCity());
        edbCreateOrderInfo.setArea(orderInfo.getArea());
        edbCreateOrderInfo.setActualFreightGet(orderInfo.getRealIncomefreight());
        edbCreateOrderInfo.setActual_RP(orderInfo.getReferencePricePaid());
        edbCreateOrderInfo.setShipMethod(orderInfo.getSendingType());
        edbCreateOrderInfo.setExpress(orderInfo.getExpress());
        edbCreateOrderInfo.setIsInvoiceOpened(orderInfo.getInvoiceSituation());
        edbCreateOrderInfo.setInvoiceType(orderInfo.getInvoiceType());
        edbCreateOrderInfo.setInvoiceMoney(orderInfo.getInvoiceMoney());
        edbCreateOrderInfo.setInvoiceTitle(orderInfo.getInvoiceTitle());
        edbCreateOrderInfo.setInvoiceMsg(orderInfo.getInvoiceContent());
        edbCreateOrderInfo.setOrderType(orderInfo.getOrderType());
        edbCreateOrderInfo.setProcessStatus(orderInfo.getStatus());
        edbCreateOrderInfo.setPayStatus(orderInfo.getPayStatus());
        edbCreateOrderInfo.setDeliverStatus(orderInfo.getDeliveryStatus());
        edbCreateOrderInfo.setIsCOD(orderInfo.getIsCod());
        edbCreateOrderInfo.setServerCostCOD(orderInfo.getCodServiceFee());
        edbCreateOrderInfo.setOrderTotalMoney(orderInfo.getOrderTotalFee());
        edbCreateOrderInfo.setProductTotalMoney(orderInfo.getProTotalFee());
        edbCreateOrderInfo.setPayMethod(orderInfo.getPayMothed());
        edbCreateOrderInfo.setPayCommission(orderInfo.getPayCommission());
        edbCreateOrderInfo.setPayScore(orderInfo.getPayScore());
        edbCreateOrderInfo.setReturnScore(orderInfo.getReturnScore());
        edbCreateOrderInfo.setFavorableMoney(orderInfo.getOrderDisfee());
        edbCreateOrderInfo.setAlipayTransactionNo(orderInfo.getAlipayTransactionNo());
        edbCreateOrderInfo.setOutPayNo(orderInfo.getOutPayTid());
        edbCreateOrderInfo.setOutExpressMethod(orderInfo.getOutExpressMethod());
        edbCreateOrderInfo.setOrderDate(StringUtil.DateFormat(orderInfo.getOrderDate(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setPayDate(StringUtil.DateFormat(orderInfo.getPayDate(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setFinishDate(StringUtil.DateFormat(orderInfo.getFinishDate(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setPlatType(orderInfo.getPlatType());
        edbCreateOrderInfo.setDistributorNo(orderInfo.getDistributorId());
        edbCreateOrderInfo.setWuLiu(orderInfo.getWuLiu());
        edbCreateOrderInfo.setWuLiuNo(orderInfo.getWuLiuNo());
        edbCreateOrderInfo.setTerminalType(orderInfo.getTerminalType());
        edbCreateOrderInfo.setInMemo(orderInfo.getInternalNote());
        edbCreateOrderInfo.setOtherRemark(orderInfo.getOtherRemarks());
        edbCreateOrderInfo.setActualFreightPay(orderInfo.getRealPayFreight());
        edbCreateOrderInfo.setShipDatePlan(StringUtil.DateFormat(orderInfo.getAdvDistributTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setDeliverDatePlan(StringUtil.DateFormat(orderInfo.getBookDeliveryTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setIsScorePay(orderInfo.getPointPay());
        edbCreateOrderInfo.setIsNeedInvoice(orderInfo.getInvoiceIsopen());
        List<EDBOrderItem> edbOrderItemList = new ArrayList<>();
        for (MallOrderItem orderItem : orderInfo.getProductBeans()) {
            EDBOrderItem edbOrderItem = new EDBOrderItem();
            edbOrderItem.setBarCode(orderItem.getBarcode());
            edbOrderItem.setProductTitle(orderItem.getProName());
            edbOrderItem.setStandard(orderItem.getSpecification());
            edbOrderItem.setOutPrice(orderItem.getSellPrice());
            edbOrderItem.setFavoriteMoney(orderItem.getItemDiscountFee());
            edbOrderItem.setOrderGoodsNum(orderItem.getProNum());
            edbOrderItem.setGiftNum(orderItem.getGiftNum());
            edbOrderItem.setCostPrice(orderItem.getCostPrice());
            edbOrderItem.setTid(orderItem.getTid());
            edbOrderItem.setProductStockout(orderItem.getStockSituation());
            edbOrderItem.setIsBook(orderItem.getIsScheduled());
            edbOrderItem.setIsPreSell(orderItem.getIsBookPro());
            edbOrderItem.setIsGift(orderItem.getIsGifts());
            edbOrderItem.setAvgPrice(orderItem.getAveragePrice());
            edbOrderItem.setProductFreight(String.valueOf(orderItem.getFreight()));
            edbOrderItem.setShopId(orderItem.getShopId());
            edbOrderItem.setOutTid(orderItem.getOutTid());
            edbOrderItem.setOutProductId(orderItem.getOutProId());
            edbOrderItem.setOutBarCode(orderItem.getSecondBarcode());
            edbOrderItem.setProductIntro(orderItem.getProExplain());
            edbOrderItemList.add(edbOrderItem);
        }
        edbCreateOrderInfo.setProductInfos(edbOrderItemList);

        String xmlResult = new XmlMapper().writeValueAsString(edbCreateOrderInfo);
        int firstIndex = xmlResult.indexOf("<product_item>");
        int lastIndex = xmlResult.lastIndexOf("</product_item>");
        String firstPanel = xmlResult.substring(0, firstIndex);
        String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
        String xmlValues = "<info>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></info>";

        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.CREATE_ORDER, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlValues", xmlValues);

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = htNetService.doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> obtainOrderList(ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.GET_ORDER_INFO, sysData);
        requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_STATUS_UPDATE, sysData);
        requestData.put("num_id", orderInfo.getOrderCode());
        requestData.put("tid_type", orderInfo.getOrderType());
        requestData.put("import_mark", orderInfo.getImportMark());
        Map<String, String> signMap = new TreeMap<>(requestData);

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, "系统请求失败"));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> orderUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBOrderForUpdate orderForUpdate = new EDBOrderForUpdate();
        orderForUpdate.setTid(orderInfo.getTid());
        orderForUpdate.setOutTid(orderInfo.getOutTid());
        orderForUpdate.setExpress(orderInfo.getExpress());
        orderForUpdate.setExpressNo(orderInfo.getExpressNo());
        orderForUpdate.setExpressCode(orderInfo.getExpressCode());
        orderForUpdate.setPrinter(orderInfo.getPrinter());
        orderForUpdate.setCargoOperator(orderInfo.getCargoOperator());
        orderForUpdate.setCargoTime(StringUtil.DateFormat(orderInfo.getCargoTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setPrintTime(StringUtil.DateFormat(orderInfo.getPrintTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setInspecter(orderInfo.getInspecter());
        orderForUpdate.setInspectTime(StringUtil.DateFormat(orderInfo.getInspectTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setIsInspectDelivery(orderInfo.getIsInspectDelivery());
        orderForUpdate.setDeliveryOperator(orderInfo.getDeliveryOperator());
        orderForUpdate.setDeliveryTime(StringUtil.DateFormat(orderInfo.getDeliveryTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setGrossWeight(String.valueOf(orderInfo.getGrossWeight()));
        orderForUpdate.setInternalNote(orderInfo.getInternalNote());
        orderForUpdate.setOriginCode(orderInfo.getOriginCode());
        orderForUpdate.setDestCode(orderInfo.getDestCode());
        orderForUpdate.setBarCode(orderInfo.getBarCode());
        orderForUpdate.setInspectionNum(orderInfo.getInspectionNum());
        EDBUpdateOrder updateOrder = new EDBUpdateOrder(orderForUpdate);

        XmlMapper xmlMapper = new XmlMapper();
        String resultStr = xmlMapper.writeValueAsString(updateOrder);

        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_DELIVER, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(resultStr, "utf-8"));
        signMap.put("xmlValues", resultStr);
        requestData.put("sign", getSign(requestData, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);

        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> orderDeliver(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_DELIVER, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("OrderCode", URLEncoder.encode(orderInfo.getOrderCode(), "utf-8"));
        requestData.put("delivery_time", URLEncoder.encode(StringUtil.DateFormat(orderInfo.getDeliveryTime(), StringUtil.TIME_PATTERN), "utf-8"));
        requestData.put("express_no", URLEncoder.encode(orderInfo.getExpressNo(), "utf-8"));
        requestData.put("express", URLEncoder.encode(orderInfo.getExpress(), "utf-8"));
        requestData.put("weight", URLEncoder.encode(orderInfo.getWeight(), "utf-8"));
        signMap.put("OrderCode", orderInfo.getOrderCode());
        signMap.put("delivery_time", StringUtil.DateFormat(orderInfo.getDeliveryTime(), StringUtil.TIME_PATTERN));
        signMap.put("express_no", orderInfo.getExpressNo());
        signMap.put("express", orderInfo.getExpress());
        signMap.put("weight", orderInfo.getWeight());

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }
}
