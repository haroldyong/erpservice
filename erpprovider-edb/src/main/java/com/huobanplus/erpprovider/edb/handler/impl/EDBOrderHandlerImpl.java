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
import com.huobanplus.erpservice.datacenter.searchbean.MallOrderSearchBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        edbCreateOrderInfo.setBuyerEmail(orderInfo.getEmail());
        edbCreateOrderInfo.setBuyerAlipay(orderInfo.getAlipayId());
        edbCreateOrderInfo.setSellerRemark(orderInfo.getServiceRemarks());
        edbCreateOrderInfo.setConsignee(orderInfo.getReceiverName());
        edbCreateOrderInfo.setAddress(orderInfo.getAddress());
        edbCreateOrderInfo.setPostcode(orderInfo.getPost());
        edbCreateOrderInfo.setTelephone(orderInfo.getPhone());
        edbCreateOrderInfo.setMobilPhone(orderInfo.getReceiverMobile());
        edbCreateOrderInfo.setProvince(orderInfo.getProvince());
        edbCreateOrderInfo.setCity(orderInfo.getCity());
        edbCreateOrderInfo.setArea(orderInfo.getDistrict());
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
        edbCreateOrderInfo.setPayCommission(orderInfo.getCommissionFee());
        edbCreateOrderInfo.setPayScore(orderInfo.getCostPoint());
        edbCreateOrderInfo.setReturnScore(orderInfo.getPoint());
        edbCreateOrderInfo.setFavorableMoney(orderInfo.getOrderDisfee());
        edbCreateOrderInfo.setAlipayTransactionNo(orderInfo.getAlipayTransactionNo());
        edbCreateOrderInfo.setOutPayNo(orderInfo.getOutPayTid());
        //edbCreateOrderInfo.setOutExpressMethod(orderInfo.getOutExpressMethod());
        edbCreateOrderInfo.setOrderDate(StringUtil.DateFormat(orderInfo.getTidTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setPayDate(StringUtil.DateFormat(orderInfo.getPayTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setFinishDate(StringUtil.DateFormat(orderInfo.getFinishTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setPlatType(orderInfo.getPlatType());
        edbCreateOrderInfo.setDistributorNo(orderInfo.getDistributorId());
        edbCreateOrderInfo.setWuLiu(orderInfo.getExpress());
        edbCreateOrderInfo.setWuLiuNo(orderInfo.getExpressNo());
        //edbCreateOrderInfo.setTerminalType(orderInfo.getTerminalType());
        edbCreateOrderInfo.setInMemo(orderInfo.getInnerLable());
        edbCreateOrderInfo.setOtherRemark(orderInfo.getOtherRemarks());
        edbCreateOrderInfo.setActualFreightPay(orderInfo.getRealPayFreight());
        edbCreateOrderInfo.setShipDatePlan(StringUtil.DateFormat(orderInfo.getAdvDistributTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setDeliverDatePlan(StringUtil.DateFormat(orderInfo.getBookDeliveryTime(), StringUtil.TIME_PATTERN));
        edbCreateOrderInfo.setIsScorePay(orderInfo.getPointPay());
        edbCreateOrderInfo.setIsNeedInvoice(orderInfo.getIsBill());
        List<EDBOrderItem> edbOrderItemList = new ArrayList<>();
        for (MallOrderItem orderItem : orderInfo.getOrderItems()) {
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
    public Monitor<EventResult> obtainOrderList(MallOrderSearchBean orderSearchBean, ERPInfo info) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EDBSysData sysData = objectMapper.readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.GET_ORDER_INFO, sysData);
//        requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
//        requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
        requestData.put("page_no", "1");
        requestData.put("page_size", "2");
        //添加搜索条件
        if (orderSearchBean != null) {
            if (!StringUtils.isEmpty(orderSearchBean.getOrderId())) {
                requestData.put("out_tid", orderSearchBean.getOrderId());
            }
        }
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);

        Map formatM = objectMapper.readValue(responseData, Map.class);

        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        if (formatM.keySet().iterator().next().equals("Success")) {
            MallOrderBean orderBean = new MallOrderBean();
            //数据处理
            List<Map> list = (List<Map>) ((Map) ((Map) formatM.get("Success")).get("items")).get("item");

        } else {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> orderStatusUpdate(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_STATUS_UPDATE, sysData);
        requestData.put("num_id", orderInfo.getOrderId());
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
        orderForUpdate.setExpressCode(orderInfo.getExpressCoding());
        orderForUpdate.setPrinter(orderInfo.getPrinter());
        orderForUpdate.setCargoOperator(orderInfo.getDistributer());
        orderForUpdate.setCargoTime(StringUtil.DateFormat(orderInfo.getDistributTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setPrintTime(StringUtil.DateFormat(orderInfo.getPrintTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setInspecter(orderInfo.getInspecter());
        orderForUpdate.setInspectTime(StringUtil.DateFormat(orderInfo.getInspectTime(), StringUtil.TIME_PATTERN));
        //orderForUpdate.setIsInspectDelivery(orderInfo.getIsInspectDelivery());
        orderForUpdate.setDeliveryOperator(orderInfo.getDeliveryOperator());
        orderForUpdate.setDeliveryTime(StringUtil.DateFormat(orderInfo.getDeliveryTime(), StringUtil.TIME_PATTERN));
        orderForUpdate.setGrossWeight(String.valueOf(orderInfo.getGrossWeightFreight()));
        orderForUpdate.setInternalNote(orderInfo.getInnerLable());
//        orderForUpdate.setOriginCode(orderInfo.getOriginCode());
//        orderForUpdate.setDestCode(orderInfo.getDestCode());
        List<EDBProductForUpdate> productForUpdates = new ArrayList<>();
        for (MallOrderItem orderItem : orderInfo.getOrderItems()) {
            EDBProductForUpdate productForUpdate = new EDBProductForUpdate();
            productForUpdate.setTid(orderItem.getTid());
            productForUpdate.setBarCode(orderItem.getBarcode());
            productForUpdate.setInspectionNum(orderItem.getInspectionNum());
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

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_UPDATE, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlValues", xmlValues);
        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);

        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> orderDeliver(MallOrderBean orderInfo, ERPInfo info) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        EDBOrderDeliver orderDeliver = new EDBOrderDeliver();
        orderDeliver.setOrderId(orderInfo.getOrderId());
        orderDeliver.setDeliveryTime(StringUtil.DateFormat(orderInfo.getDeliveryTime(), StringUtil.TIME_PATTERN));
        orderDeliver.setExpress(URLEncoder.encode(orderInfo.getExpress(), "utf-8"));
        orderDeliver.setExpressNo(orderInfo.getExpressNo());
        if (!StringUtils.isEmpty(orderInfo.getTidNetWeight()))
            orderDeliver.setExpressNo(URLEncoder.encode(orderInfo.getTidNetWeight(), "utf-8"));

        String xmlValues = "<order>" + new XmlMapper().writeValueAsString(orderDeliver) + "</order>";

        Map<String, String> requestData = getSysRequestData(Constant.ORDER_DELIVER, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlValues", xmlValues);

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }
}
