package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.EDBOrder;
import com.huobanplus.erpprovider.edb.bean.EDBCreateOrderInfo;
import com.huobanplus.erpprovider.edb.handler.OrderHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpprovider.edb.util.XmlUtil;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/7/24.
 */
@Component
public class OrderHandlerImpl implements OrderHandler {

    @Override
    public Monitor<EventResult> createOrder(OrderInfo orderInfo) throws IOException {
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
        edbCreateOrderInfo.setBarCode(orderInfo.getBarCode());
        edbCreateOrderInfo.setProductTitle(orderInfo.getProductTitle());
        edbCreateOrderInfo.setStandard(orderInfo.getStandard());
        edbCreateOrderInfo.setOutPrice(orderInfo.getOutPrice());
        edbCreateOrderInfo.setFavoriteMoney(orderInfo.getDiscountFee());
        edbCreateOrderInfo.setOrderGoodsNum(orderInfo.getOrderGoodsNum());
        edbCreateOrderInfo.setGiftNum(orderInfo.getGiftNum());
        edbCreateOrderInfo.setCostPrice(orderInfo.getCostPrice());
        edbCreateOrderInfo.setProductStockOut(orderInfo.getProductStockout());
        edbCreateOrderInfo.setIsBook(orderInfo.getIsBook());
        edbCreateOrderInfo.setIsPreSell(orderInfo.getIsAdvSale());
        edbCreateOrderInfo.setIsGift(orderInfo.getIsGift());
        edbCreateOrderInfo.setAvgPrice(orderInfo.getAvgPrice());
        edbCreateOrderInfo.setProductFreight(orderInfo.getProductFreight());
        edbCreateOrderInfo.setOutProductId(orderInfo.getOutProductId());
        edbCreateOrderInfo.setOutBarCode(orderInfo.getOutBarCode());
        edbCreateOrderInfo.setProductIntro(orderInfo.getProductIntro());
        EDBOrder edbOrder = new EDBOrder(edbCreateOrderInfo);

        XmlMapper xmlMapper = new XmlMapper();
        String resultStr = xmlMapper.writeValueAsString(edbOrder);

        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.CREATE_ORDER);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("xmlvalues", resultStr);

        TreeMap<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        requestData.put("sign", SignBuilder.buildSign(signMap, Constant.APP_KEY, ""));

        String responseData = htNetService.doPost(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, XmlUtil.xml2Json(responseData)));
    }

    @Override
    public Monitor<EventResult> getOrderInfo() throws IOException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.GET_ORDER_INFO);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("page_no", "1");
        requestData.put("page_size", "10");
        //requestData.put("field", URLEncoder.encode(Constant.GET_ORDER_INFO_FIELD, "utf-8"));
        TreeMap<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        requestData.put("sign", SignBuilder.buildSign(signMap, Constant.APP_KEY, ""));

        String responseData = HttpUtil.getInstance().doPost(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        int firstRowIndex = responseData.indexOf("<Rows>");
        int lastRowIndex = responseData.lastIndexOf("</Rows>");
        String first = responseData.substring(0, firstRowIndex);
        String middle = responseData.substring(firstRowIndex, lastRowIndex + 7);
        String last = responseData.substring(lastRowIndex + 7, responseData.length());
        String resultXml = first + "<RowRoot>" + middle + "</RowRoot>" + last;
        String resultJson = XmlUtil.xml2Json(resultXml);
        return new SimpleMonitor<>(new EventResult(1, resultJson));
    }

    @Override
    public Monitor<EventResult> obtainOrderStatus() throws IOException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.GET_ORDER_INFO);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("page_no", "1");
        requestData.put("page_size", "10");
        //requestData.put("field", URLEncoder.encode(Constant.GET_ORDER_INFO_FIELD, "utf-8"));
        TreeMap<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        requestData.put("sign", SignBuilder.buildSign(signMap, Constant.APP_KEY, ""));

        String responseData = HttpUtil.getInstance().doPost(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        int firstRowIndex = responseData.indexOf("<Rows>");
        int lastRowIndex = responseData.lastIndexOf("</Rows>");
        String first = responseData.substring(0, firstRowIndex);
        String middle = responseData.substring(firstRowIndex, lastRowIndex + 7);
        String last = responseData.substring(lastRowIndex + 7, responseData.length());
        String resultXml = first + "<RowRoot>" + middle + "</RowRoot>" + last;
        String resultJson = XmlUtil.xml2Json(resultXml);
        return new SimpleMonitor<>(new EventResult(1, resultJson));
    }

    @Override
    public Monitor<EventResult> obtainOrderList() throws IOException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.GET_ORDER_INFO);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("page_no", "1");
        requestData.put("page_size", "10");
        //requestData.put("field", URLEncoder.encode(Constant.GET_ORDER_INFO_FIELD, "utf-8"));
        TreeMap<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        requestData.put("sign", SignBuilder.buildSign(signMap, Constant.APP_KEY, ""));

        String responseData = HttpUtil.getInstance().doPost(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        int firstRowIndex = responseData.indexOf("<Rows>");
        int lastRowIndex = responseData.lastIndexOf("</Rows>");
        String first = responseData.substring(0, firstRowIndex);
        String middle = responseData.substring(firstRowIndex, lastRowIndex + 7);
        String last = responseData.substring(lastRowIndex + 7, responseData.length());
        String resultXml = first + "<RowRoot>" + middle + "</RowRoot>" + last;
        String resultJson = XmlUtil.xml2Json(resultXml);
        return new SimpleMonitor<>(new EventResult(1, resultJson));
    }

    @Override
    public Monitor<EventResult> orderStatusUpdate(String numId, String tidType, String importMark) throws IOException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.GET_ORDER_INFO);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("num_id", numId);
        requestData.put("tid_type", tidType);
        requestData.put("import_mark", importMark);

        TreeMap<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        requestData.put("sign", SignBuilder.buildSign(signMap, Constant.APP_KEY, ""));

        String responseData = HttpUtil.getInstance().doPost(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        String resultJson = XmlUtil.xml2Json(responseData);
        return new SimpleMonitor<>(new EventResult(1, resultJson));
    }
}
