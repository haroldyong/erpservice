package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.*;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.common.util.HttpUtil;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Autowired
    private MallOrderService orderService;

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
        String xmlValues = "<order>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></order>";

        EDBSysData sysData = new ObjectMapper().readValue(info.getSysDataJson(), EDBSysData.class);

        Map<String, String> requestData = getSysRequestData(Constant.CREATE_ORDER, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlvalues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlvalues", xmlValues);

        requestData.put("sign", getSign(signMap, sysData));

        String responseData = htNetService.doPost(sysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    /**
     * 订单搜索轮询
     * fixedRate 轮询间隔 单位毫秒   60 000 = 60秒 也就是1分钟
     * initialDelay web容器启动后延迟多久才调用该轮询方法。单位毫秒   60 000 = 60秒 也就是1分钟。建议fixedRate 和 initialDelay 两个值设置成一样
     *
     * @return
     * @throws IOException
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    @Override
    public void obtainOrderList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EDBSysData sysData = null;//objectMapper.readValue(info.getSysDataJson(), EDBSysData.class);
        System.out.println("正在轮询...");
        //取出需要轮询的数据
        List<MallOrderBean> orderList = orderService.findByRotaryStatus(1);
        for (MallOrderBean order : orderList) {
            Map<String, String> requestData = getSysRequestData(Constant.GET_ORDER_INFO, sysData);
            requestData.put("begin_time", URLEncoder.encode(StringUtil.DateFormat(new Date(0), StringUtil.DATE_PATTERN), "utf-8"));
            requestData.put("end_time", URLEncoder.encode(StringUtil.DateFormat(new Date(), StringUtil.DATE_PATTERN), "utf-8"));
            requestData.put("out_tid", order.getOrderId());
            Map<String, String> signMap = new TreeMap<>(requestData);
            requestData.put("sign", getSign(signMap, sysData));

            String responseData = HttpUtil.getInstance().doPost(sysData.getRequestUrl(), requestData);

            Map formatM = objectMapper.readValue(responseData, Map.class);

//            if (responseData == null) {
//                return new SimpleMonitor<>(new EventResult(0, responseData));
//            }
            if (formatM.keySet().iterator().next().equals("Success")) {
                //数据处理
                List<Map> list = (List<Map>) ((Map) ((Map) formatM.get("Success")).get("items")).get("item");
                MallOrderBean responseOrder = wrapMapToBean(list.get(0));
                if (!responseOrder.getDeliveryStatus().equals(order.getDeliveryStatus())) {
                    //todo 讲物流信息等必要数据推送给伙伴商城

                    //todo 推送成功后，轮询状态设置为完成
                    order.setRotaryStatus(2);
                    orderService.save(order);
                }
            }
        }
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

    private MallOrderBean wrapMapToBean(Map map) {
        MallOrderBean orderBean = new MallOrderBean();
        orderBean.setStorageId((String) map.get("storage_id"));
        orderBean.setTid((String) map.get("tid"));
        orderBean.setTransactionId((String) map.get("transaction_id"));
        orderBean.setOutTid((String) map.get("out_tid"));
        orderBean.setOutPayTid((String) map.get("out_pay_tid"));
        orderBean.setShopId((String) map.get("shopid"));
        orderBean.setBuyerId((String) map.get("buyer_id"));
        orderBean.setBuyerName((String) map.get("buyer_name"));
        orderBean.setType((String) map.get("type"));
        orderBean.setStatus((String) map.get("status"));
        orderBean.setAbnormalStatus((String) map.get("abnormal_status"));
        orderBean.setReceiverName((String) map.get("receiver_name"));
        orderBean.setReceiverMobile((String) map.get("receiver_mobile"));
        orderBean.setPhone((String) map.get("phone"));
        orderBean.setProvince((String) map.get("province"));
        orderBean.setCity((String) map.get("city"));
        orderBean.setDistrict((String) map.get("district"));
        orderBean.setAddress((String) map.get("address"));
        orderBean.setPost((String) map.get("post"));
        orderBean.setEmail((String) map.get("email"));
        orderBean.setIsBill((Integer) map.get("is_bill"));
        orderBean.setInvoiceName((String) map.get("invoice_name"));
        orderBean.setInvoiceSituation(((Number) map.get("invoice_situation")).intValue());
        orderBean.setInvoiceTitle((String) map.get("invoice_title"));
        orderBean.setInvoiceType((String) map.get("invoice_type"));
        orderBean.setInvoiceContent((String) map.get("invoice_content"));
        orderBean.setProTotalFee(((Number) map.get("pro_totalfee")).doubleValue());
        orderBean.setOrderTotalFee(((Number) map.get("order_totalfee")).doubleValue());
        orderBean.setRefundTotalFee(String.valueOf(map.get("refund_totalfee")));
        orderBean.setExpressNo((String) map.get("express_no"));
        orderBean.setExpress((String) map.get("express"));
        orderBean.setExpressCoding((String) map.get("express_coding"));
        orderBean.setOnlineExpress((String) map.get("online_express"));
        orderBean.setSendingType((String) map.get("sending_type"));
        orderBean.setRealIncomefreight(((Number) map.get("real_income_freight")).doubleValue());
        orderBean.setRealPayFreight(((Number) map.get("real_pay_freight")).doubleValue());
        orderBean.setGrossWeight((String) map.get("gross_weight"));
        orderBean.setGrossWeightFreight(((Number) map.get("gross_weight_freight")).doubleValue());
        orderBean.setNetWeightWreight((String) map.get("net_weight_freight"));
        orderBean.setOrderCreater((String) map.get("order_creater"));
        orderBean.setBusinessMan((String) map.get("business_man"));
        orderBean.setReviewOrdersOperator((String) map.get("review_orders_operator"));
//                orderBean.setReviewOrdersTime(map.get("review_orders_time"));
        orderBean.setAdvDistributer((String) map.get("adv_distributer"));
        orderBean.setAdvDistributTime(StringUtil.DateFormat((String) map.get("adv_distribut_time"), Constant.TIME_PATTERN));
        orderBean.setInspecter((String) map.get("inspecter"));
        orderBean.setInspectTime(StringUtil.DateFormat((String) map.get("inspect_time"), Constant.TIME_PATTERN));
        orderBean.setCancelOperator((String) map.get("cancel_operator"));
        orderBean.setCancelTime(StringUtil.DateFormat((String) map.get("cancel_time"), Constant.TIME_PATTERN));
        orderBean.setBookDeliveryTime(StringUtil.DateFormat((String) map.get("book_delivery_time"), Constant.TIME_PATTERN));
        orderBean.setDeliveryOperator((String) map.get("delivery_time"));
        orderBean.setLocker((String) map.get("locker"));
        orderBean.setLockTime(StringUtil.DateFormat((String) map.get("lock_time"), Constant.TIME_PATTERN));
        orderBean.setBookFileTime(StringUtil.DateFormat((String) map.get("book_file_time"), Constant.TIME_PATTERN));
        orderBean.setFileOperator((String) map.get("file_operator"));
        orderBean.setFileTime(StringUtil.DateFormat((String) map.get("file_time"), Constant.TIME_PATTERN));
        orderBean.setFinishTime(StringUtil.DateFormat((String) map.get("finish_time"), Constant.TIME_PATTERN));
        orderBean.setModityTime(StringUtil.DateFormat((String) map.get("modity_time"), Constant.TIME_PATTERN));
        orderBean.setDeliveryStatus((String) map.get("delivery_status"));
        List<Map> proList = (List<Map>) map.get("tid_item");
        List<MallOrderItem> orderItems = new ArrayList<>();
        for (Map proMap : proList) {
            MallOrderItem orderItem = new MallOrderItem();
            orderItem.setStorageId((String) proMap.get("storage_id"));
            orderItem.setProDetailCode((String) proMap.get("pro_detail_code"));
            orderItem.setProName((String) proMap.get("pro_name"));
            orderItem.setBarcode((String) proMap.get("barcode"));
            orderItem.setIsCancel((String) proMap.get("iscancel"));
            orderItem.setStockSituation((String) proMap.get("stock_situation"));
            orderItem.setBookStorage(((Number) proMap.get("book_storage")).intValue());
            orderItem.setProNum(((Number) proMap.get("pro_num")).intValue());
            orderItem.setSendNum(((Number) proMap.get("send_num")).intValue());
            orderItem.setRefundNum(((Number) proMap.get("refund_num")).intValue());
            orderItem.setRefundReNum(((Number) proMap.get("refund_renum")).intValue());
            orderItem.setInspectionNum(((Number) proMap.get("inspection_num")).intValue());
            orderItem.setTimeInventory(((Number) proMap.get("timeinventory")).intValue());
            orderItem.setOutTid((String) proMap.get("out_tid"));
            orderItem.setOutProId((String) proMap.get("out_proid"));
            orderItem.setDistributer((String) proMap.get("distributer"));
            orderItem.setDistributTime(StringUtil.DateFormat((String) map.get("distribut_time"), Constant.TIME_PATTERN));
            orderItem.setBookInventory(((Number) proMap.get("book_inventory")).intValue());
            orderItems.add(orderItem);
        }

        return orderBean;
    }
}
