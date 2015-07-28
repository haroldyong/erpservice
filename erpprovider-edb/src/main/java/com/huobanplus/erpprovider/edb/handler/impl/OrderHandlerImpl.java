package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.EDBOrder;
import com.huobanplus.erpprovider.edb.bean.EDBOrderInfo;
import com.huobanplus.erpprovider.edb.eventResult.CreateOrderResult;
import com.huobanplus.erpprovider.edb.handler.OrderHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.AuthBean;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignStrategy;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 2015/7/24.
 */
@Component
public class OrderHandlerImpl implements OrderHandler {

    @Override
    public Monitor<EventResult> createOrder(OrderInfo orderInfo) throws IOException {
        HttpUtil htNetService = HttpUtil.getInstance();
        Map<Object, Object> map = new HashMap<>();
        EDBOrderInfo edbOrderInfo = new EDBOrderInfo();
        edbOrderInfo.setTid(orderInfo.getTid());
        edbOrderInfo.setOutTid(orderInfo.getOutTid());
        edbOrderInfo.setShopId(orderInfo.getShopId());
        edbOrderInfo.setStorageId(Integer.parseInt(orderInfo.getStorageId()));
        edbOrderInfo.setBuyerId(orderInfo.getBuyerId());
        edbOrderInfo.setBuyerEmail(orderInfo.getBuyerEmail());
        edbOrderInfo.setBuyerAlipay(orderInfo.getBuyerAlipay());
        edbOrderInfo.setSellerRemark(orderInfo.getServiceRemarks());
        edbOrderInfo.setConsignee(orderInfo.getConsignee());
        edbOrderInfo.setAddress(orderInfo.getAddress());
        edbOrderInfo.setPostcode(orderInfo.getPost());
        edbOrderInfo.setTelephone(orderInfo.getPhone());
        edbOrderInfo.setMobilPhone(orderInfo.getReceiverMobile());
        edbOrderInfo.setProvince(orderInfo.getProvince());
        edbOrderInfo.setCity(orderInfo.getCity());
        edbOrderInfo.setArea(orderInfo.getArea());
        edbOrderInfo.setActualFreightGet(orderInfo.getRealIncomefreight());
        edbOrderInfo.setActual_RP(orderInfo.getReferencePricePaid());
        edbOrderInfo.setShipMethod(orderInfo.getSendingType());
        edbOrderInfo.setExpress(orderInfo.getExpress());
        edbOrderInfo.setIsInvoiceOpened(orderInfo.getInvoiceSituation());
        edbOrderInfo.setInvoiceType(orderInfo.getInvoiceType());
        edbOrderInfo.setInvoiceMoney(orderInfo.getInvoiceMoney());
        edbOrderInfo.setInvoiceTitle(orderInfo.getInvoiceTitle());
        edbOrderInfo.setInvoiceMsg(orderInfo.getInvoiceContent());
        edbOrderInfo.setOrderType(orderInfo.getOrderType());
        edbOrderInfo.setProcessStatus(orderInfo.getStatus());
        edbOrderInfo.setPayStatus(orderInfo.getPayStatus());
        edbOrderInfo.setDeliverStatus(orderInfo.getDeliveryStatus());
        edbOrderInfo.setIsCOD(orderInfo.getIsCod());
        edbOrderInfo.setServerCostCOD(orderInfo.getCodServiceFee());
        edbOrderInfo.setOrderTotalMoney(orderInfo.getOrderTotalFee());
        edbOrderInfo.setProductTotalMoney(orderInfo.getProTotalFee());
        edbOrderInfo.setPayMethod(orderInfo.getPayMothed());
        edbOrderInfo.setPayCommission(orderInfo.getPayCommission());
        edbOrderInfo.setPayScore(orderInfo.getPayScore());
        edbOrderInfo.setReturnScore(orderInfo.getReturnScore());
        edbOrderInfo.setFavorableMoney(orderInfo.getOrderDisfee());
        edbOrderInfo.setAlipayTransactionNo(orderInfo.getAlipayTransactionNo());
        edbOrderInfo.setOutPayNo(orderInfo.getOutPayTid());
        edbOrderInfo.setOutExpressMethod(orderInfo.getOutExpressMethod());
        edbOrderInfo.setOrderDate(StringUtil.DateFormat(orderInfo.getOrderDate(), StringUtil.TIME_PATTERN));
        edbOrderInfo.setPayDate(StringUtil.DateFormat(orderInfo.getPayDate(), StringUtil.TIME_PATTERN));
        edbOrderInfo.setFinishDate(StringUtil.DateFormat(orderInfo.getFinishDate(), StringUtil.TIME_PATTERN));
        edbOrderInfo.setPlatType(orderInfo.getPlatType());
        edbOrderInfo.setDistributorNo(orderInfo.getDistributorId());
        edbOrderInfo.setWuLiu(orderInfo.getWuLiu());
        edbOrderInfo.setWuLiuNo(orderInfo.getWuLiuNo());
        edbOrderInfo.setTerminalType(orderInfo.getTerminalType());
        edbOrderInfo.setInMemo(orderInfo.getInternalNote());
        edbOrderInfo.setOtherRemark(orderInfo.getOtherRemarks());
        edbOrderInfo.setActualFreightPay(orderInfo.getRealPayFreight());
        edbOrderInfo.setShipDatePlan(StringUtil.DateFormat(orderInfo.getAdvDistributTime(), StringUtil.TIME_PATTERN));
        edbOrderInfo.setDeliverDatePlan(StringUtil.DateFormat(orderInfo.getBookDeliveryTime(), StringUtil.TIME_PATTERN));
        edbOrderInfo.setIsScorePay(orderInfo.getPointPay());
        edbOrderInfo.setIsNeedInvoice(orderInfo.getInvoiceIsopen());
        edbOrderInfo.setBarCode(orderInfo.getBarCode());
        edbOrderInfo.setProductTitle(orderInfo.getProductTitle());
        edbOrderInfo.setStandard(orderInfo.getStandard());
        edbOrderInfo.setOutPrice(orderInfo.getOutPrice());
        edbOrderInfo.setFavoriteMoney(orderInfo.getDiscountFee());
        edbOrderInfo.setOrderGoodsNum(orderInfo.getOrderGoodsNum());
        edbOrderInfo.setGiftNum(orderInfo.getGiftNum());
        edbOrderInfo.setCostPrice(orderInfo.getCostPrice());
        edbOrderInfo.setProductStockOut(orderInfo.getProductStockout());
        edbOrderInfo.setIsBook(orderInfo.getIsBook());
        edbOrderInfo.setIsPreSell(orderInfo.getIsAdvSale());
        edbOrderInfo.setIsGift(orderInfo.getIsGift());
        edbOrderInfo.setAvgPrice(orderInfo.getAvgPrice());
        edbOrderInfo.setProductFreight(orderInfo.getProductFreight());
        edbOrderInfo.setOutProductId(orderInfo.getOutProductId());
        edbOrderInfo.setOutBarCode(orderInfo.getOutBarCode());
        edbOrderInfo.setProductIntro(orderInfo.getProductIntro());
        EDBOrder edbOrder = new EDBOrder(edbOrderInfo);
        XmlMapper xmlMapper = new XmlMapper();
        String resultStr = xmlMapper.writeValueAsString(edbOrder);
        map.put("dbhost", Constant.DB_HOST);
        map.put("appkey", Constant.APP_KEY);
        map.put("method", Constant.CREATE_ORDER);
        map.put("format", Constant.FORMAT);
        map.put("timestamp", new Date().getTime());
        map.put("v", Constant.V);
        map.put("slencry", Constant.SLENCRY);
        map.put("ip", Constant.IP);
        map.put("xmlvalues", resultStr);
        String result = htNetService.doPost(Constant.REQUEST_URI, map);
        CreateOrderResult orderResult = xmlMapper.readValue(result, CreateOrderResult.class);
        return new SimpleMonitor<>(orderResult);
    }
}
