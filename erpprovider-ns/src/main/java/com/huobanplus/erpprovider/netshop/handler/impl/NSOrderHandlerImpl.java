package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.handler.NSOrderHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * 订单信息处理实现类
 */
@Component
public class NSOrderHandlerImpl implements NSOrderHandler {

    @Resource
    private MallOrderService mallOrderService;

    @Override
    public Monitor<EventResult> commitOrderInfo(HttpServletRequest request) throws IOException {

        String sign = (String) request.getAttribute("sign");
        String secret = (String) request.getAttribute("secret");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));

        //订单参数
        Map<String, String> orderMap = new TreeMap<>();
        orderMap.put("orderId", request.getParameter("orderId"));
        orderMap.put("storageId", request.getParameter("storageId"));
        orderMap.put("tid", request.getParameter("tid"));
        orderMap.put("transactionId", request.getParameter("transactionId"));
        orderMap.put("customerId", request.getParameter("customerId"));
        orderMap.put("distributorId", request.getParameter("distributorId"));
        orderMap.put("shopName", request.getParameter("shopName"));
        orderMap.put("outTid", request.getParameter("outTid"));
        orderMap.put("orderType", request.getParameter("orderType"));
        orderMap.put("outPayTid", request.getParameter("outPayTid"));
        orderMap.put("voucherId", request.getParameter("voucherId"));
        orderMap.put("shopId", request.getParameter("shopId"));
        orderMap.put("serialNum", request.getParameter("serialNum"));
        orderMap.put("orderChannel", request.getParameter("orderChannel"));
        orderMap.put("orderFrom", request.getParameter("orderFrom"));
        orderMap.put("buyerId", request.getParameter("buyerId"));
        orderMap.put("buyerName", request.getParameter("buyerName"));
        orderMap.put("type", request.getParameter("type"));
        orderMap.put("status", request.getParameter("status"));
        orderMap.put("abnormalStatus", request.getParameter("abnormalStatus"));
        orderMap.put("mergeStatus", request.getParameter("mergeStatus"));
        orderMap.put("receiverName", request.getParameter("receiverName"));
        orderMap.put("receiverMobile", request.getParameter("receiverMobile"));
        orderMap.put("phone", request.getParameter("phone"));
        orderMap.put("province", request.getParameter("province"));
        orderMap.put("city", request.getParameter("city"));
        orderMap.put("district", request.getParameter("district"));
        orderMap.put("address", request.getParameter("address"));
        orderMap.put("post", request.getParameter("post"));
        orderMap.put("email", request.getParameter("email"));
        orderMap.put("isBill", request.getParameter("isBill"));
        orderMap.put("invoiceName", request.getParameter("invoiceName"));
        orderMap.put("invoiceSituation", request.getParameter("invoiceSituation"));
        orderMap.put("invoiceTitle", request.getParameter("invoiceTitle"));
        orderMap.put("invoiceType", request.getParameter("invoiceType"));
        orderMap.put("invoiceContent", request.getParameter("invoiceContent"));
        orderMap.put("invoiceMoney", request.getParameter("invoiceMoney"));
        orderMap.put("proTotalFee", request.getParameter("proTotalFee"));
        orderMap.put("orderTotalFee", request.getParameter("orderTotalFee"));
        orderMap.put("referencePricePaid", request.getParameter("referencePricePaid"));
        orderMap.put("invoiceFee", request.getParameter("invoiceFee"));
        orderMap.put("codFee", request.getParameter("codFee"));
        orderMap.put("otherFee", request.getParameter("otherFee"));
        orderMap.put("refundTotalFee", request.getParameter("refundTotalFee"));
        orderMap.put("discountFee", request.getParameter("discountFee"));
        orderMap.put("discount", request.getParameter("discount"));
        orderMap.put("channelDisfee", request.getParameter("channelDisfee"));
        orderMap.put("merchantDisFee", request.getParameter("merchantDisFee"));
        orderMap.put("orderDisfee", request.getParameter("orderDisfee"));
        orderMap.put("commissionFee", request.getParameter("commissionFee"));
        orderMap.put("isCod", request.getParameter("isCod"));
        orderMap.put("pointPay", request.getParameter("pointPay"));
        orderMap.put("costPoint", request.getParameter("costPoint"));
        orderMap.put("point", request.getParameter("point"));
        orderMap.put("superiorPoint", request.getParameter("superiorPoint"));
        orderMap.put("royaltyFee", request.getParameter("royaltyFee"));
        orderMap.put("externalPoint", request.getParameter("externalPoint"));
        orderMap.put("expressNo", request.getParameter("expressNo"));
        orderMap.put("tradeGifadd", request.getParameter("tradeGifadd"));
        orderMap.put("express", request.getParameter("express"));
        orderMap.put("expressCoding", request.getParameter("expressCoding"));
        orderMap.put("onlineExpress", request.getParameter("onlineExpress"));
        orderMap.put("sendingType", request.getParameter("sendingType"));
        orderMap.put("realIncomefreight", request.getParameter("realIncomefreight"));
        orderMap.put("realPayFreight", request.getParameter("realPayFreight"));
        orderMap.put("grossWeight", request.getParameter("grossWeight"));
        orderMap.put("grossWeightFreight", request.getParameter("grossWeightFreight"));
        orderMap.put("netWeightWreight", request.getParameter("netWeightWreight"));
        orderMap.put("freightExplain", request.getParameter("freightExplain"));
        orderMap.put("totalWeight", request.getParameter("totalWeight"));
        orderMap.put("tidNetWeight", request.getParameter("tidNetWeight"));
        orderMap.put("tidTime", request.getParameter("tidTime"));
        orderMap.put("payTime", request.getParameter("payTime"));
        orderMap.put("getTime", request.getParameter("getTime"));
        orderMap.put("orderCreater", request.getParameter("orderCreater"));
        orderMap.put("businessMan", request.getParameter("businessMan"));
        orderMap.put("paymentReceivedOperator", request.getParameter("paymentReceivedOperator"));
        orderMap.put("paymentReceivedTime", request.getParameter("paymentReceivedTime"));
        orderMap.put("reviewOrdersOperator", request.getParameter("reviewOrdersOperator"));
        orderMap.put("reviewOrdersTime", request.getParameter("reviewOrdersTime"));
        orderMap.put("financeReviewOperator", request.getParameter("financeReviewOperator"));
        orderMap.put("financeReviewTime", request.getParameter("financeReviewTime"));
        orderMap.put("advancePrinter", request.getParameter("advancePrinter"));
        orderMap.put("printer", request.getParameter("printer"));
        orderMap.put("printTime", request.getParameter("printTime"));
        orderMap.put("isPrint", request.getParameter("isPrint"));
        orderMap.put("advDistributer", request.getParameter("advDistributer"));
        orderMap.put("advDistributTime", request.getParameter("advDistributTime"));
        orderMap.put("distributer", request.getParameter("distributer"));
        orderMap.put("distributTime", request.getParameter("distributTime"));
        orderMap.put("inspecter", request.getParameter("inspecter"));
        orderMap.put("inspectTime", request.getParameter("inspectTime"));
        orderMap.put("cancelOperator", request.getParameter("cancelOperator"));
        orderMap.put("cancelTime", request.getParameter("cancelTime"));
        orderMap.put("revokeCanceler", request.getParameter("revokeCanceler"));
        orderMap.put("revokeCancelTime", request.getParameter("revokeCancelTime"));
        orderMap.put("packager", request.getParameter("packager"));
        orderMap.put("packTime", request.getParameter("packTime"));
        orderMap.put("weighOperator", request.getParameter("weighOperator"));
        orderMap.put("weighTime", request.getParameter("weighTime"));
        orderMap.put("bookDeliveryTime", request.getParameter("bookDeliveryTime"));
        orderMap.put("deliveryOperator", request.getParameter("deliveryOperator"));
        orderMap.put("deliveryTime", request.getParameter("deliveryTime"));
        orderMap.put("locker", request.getParameter("locker"));
        orderMap.put("lockTime", request.getParameter("lockTime"));
        orderMap.put("bookFileTime", request.getParameter("bookFileTime"));
        orderMap.put("fileOperator", request.getParameter("fileOperator"));
        orderMap.put("fileTime", request.getParameter("fileTime"));
        orderMap.put("finishTime", request.getParameter("finishTime"));
        orderMap.put("modityTime", request.getParameter("modityTime"));
        orderMap.put("isPromotion", request.getParameter("isPromotion"));
        orderMap.put("promotionPlan", request.getParameter("promotionPlan"));
        orderMap.put("outPromotionDetail", request.getParameter("outPromotionDetail"));
        orderMap.put("goodReceiveTime", request.getParameter("goodReceiveTime"));
        orderMap.put("receiveTime", request.getParameter("receiveTime"));
        orderMap.put("verificatyTime", request.getParameter("verificatyTime"));
        orderMap.put("enableInteStoTime", request.getParameter("enableInteStoTime"));
        orderMap.put("enableInteDeliveryTime", request.getParameter("enableInteDeliveryTime"));
        orderMap.put("alipayId", request.getParameter("alipayId"));
        orderMap.put("alipayStatus", request.getParameter("alipayStatus"));
        orderMap.put("payStatus", request.getParameter("payStatus"));
        orderMap.put("payMothed", request.getParameter("payMothed"));
        orderMap.put("platformStatus", request.getParameter("platformStatus"));
        orderMap.put("rate", request.getParameter("rate"));
        orderMap.put("currency", request.getParameter("currency"));
        orderMap.put("deliveryStatus", request.getParameter("deliveryStatus"));
        orderMap.put("buyerMessage", request.getParameter("buyerMessage"));
        orderMap.put("serviceRemarks", request.getParameter("serviceRemarks"));
        orderMap.put("innerLable", request.getParameter("innerLable"));
        orderMap.put("distributorMark", request.getParameter("distributorMark"));
        orderMap.put("systemRemarks", request.getParameter("systemRemarks"));
        orderMap.put("otherRemarks", request.getParameter("otherRemarks"));
        orderMap.put("message", request.getParameter("message"));
        orderMap.put("messageTime", request.getParameter("messageTime"));
        orderMap.put("isStock", request.getParameter("isStock"));
        orderMap.put("relatedOrders", request.getParameter("relatedOrders"));
        orderMap.put("relatedOrdersType", request.getParameter("relatedOrdersType"));
        orderMap.put("importMark", request.getParameter("importMark"));
        orderMap.put("deliveryName", request.getParameter("deliveryName"));
        orderMap.put("isNewCustomer", request.getParameter("isNewCustomer"));
        orderMap.put("distributorLevel", request.getParameter("distributorLevel"));
        orderMap.put("codServiceFee", request.getParameter("codServiceFee"));
        orderMap.put("expressColFee", request.getParameter("expressColFee"));
        orderMap.put("productNum", request.getParameter("productNum"));
        orderMap.put("sku", request.getParameter("sku"));
        orderMap.put("itemNum", request.getParameter("itemNum"));
        orderMap.put("singleNum", request.getParameter("singleNum"));
        orderMap.put("flagColor", request.getParameter("flagColor"));
        orderMap.put("isFlag", request.getParameter("isFlag"));
        orderMap.put("taobaoDeliveryOrderStatus", request.getParameter("taobaoDeliveryOrderStatus"));
        orderMap.put("taobaoDeliveryStatus", request.getParameter("taobaoDeliveryStatus"));
        orderMap.put("taobaoDeliveryMethod", request.getParameter("taobaoDeliveryMethod"));
        orderMap.put("orderProcessTime", request.getParameter("orderProcessTime"));
        orderMap.put("isBreak", request.getParameter("isBreak"));
        orderMap.put("breaker", request.getParameter("breaker"));
        orderMap.put("breakTime", request.getParameter("breakTime"));
        orderMap.put("breakExplain", request.getParameter("breakExplain"));
        orderMap.put("platSendStatus", request.getParameter("platSendStatus"));
        orderMap.put("platType", request.getParameter("platType"));
        orderMap.put("isAdvSale", request.getParameter("isAdvSale"));
        orderMap.put("provincCode", request.getParameter("provincCode"));
        orderMap.put("cityCode", request.getParameter("cityCode"));
        orderMap.put("areaCode", request.getParameter("areaCode"));
        orderMap.put("lastReturnedTime", request.getParameter("lastReturnedTime"));
        orderMap.put("lastRefundTime", request.getParameter("lastRefundTime"));
        orderMap.put("deliverCentre", request.getParameter("deliverCentre"));
        orderMap.put("deliverStation", request.getParameter("deliverStation"));
        orderMap.put("isPreDeliveryNotice", request.getParameter("isPreDeliveryNotice"));
        orderMap.put("jdDeliveryTime", request.getParameter("jdDeliveryTime"));
        orderMap.put("sortingCode", request.getParameter("sortingCode"));
        orderMap.put("codSettlementVouchernumber", request.getParameter("codSettlementVouchernumber"));
        /**
         * 货到付款结算凭证号
         */
        orderMap.put("codSettlementVouchernumber", request.getParameter("codSettlementVouchernumber"));

        signMap.putAll(orderMap);
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            ///ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(orderMap);
            //String result = HttpUtil.getInstance().doPost(null, null);
            //将订单信息保存到数据库
            MallOrderBean orderBean = new MallOrderBean();
            orderBean.setPayStatus(orderMap.get("payStatus"));
            orderBean.setDeliveryStatus(orderMap.get("shipStatus"));
            orderBean.setSendingType(orderMap.get("deliverMethod"));
            orderBean.setTotalWeight(Double.parseDouble(orderMap.get("weight")));
            orderBean.setItemNum(Integer.parseInt(orderMap.get("itemNum")));
            orderBean.setFinishTime(new Date(Long.parseLong(orderMap.get("actTime"))));
            orderBean.setBuyerName(orderMap.get("shipName"));
            orderBean.setAddress(orderMap.get("shipAddr"));
            orderBean.setPost(orderMap.get("shipZip"));
            orderBean.setPhone(orderMap.get("shipTel"));
            orderBean.setEmail(orderMap.get("shipEmail"));
            orderBean.setJdDeliveryTime(new Date(Long.parseLong(orderMap.get("shipTime"))));
            orderBean.setReceiverMobile(orderMap.get("shipMobile"));
            orderBean.setProTotalFee(Double.parseDouble(orderMap.get("costItem")));
            orderBean.setInvoiceTitle(orderMap.get("taxCompany"));
            orderBean.setOtherFee(orderMap.get("costPayment"));
            orderBean.setDiscount(Double.parseDouble(orderMap.get("discount")));
            orderBean.setOrderTotalFee(Double.parseDouble(orderMap.get("totalAmount")));
            orderBean.setFileTime(new Date(Long.parseLong(orderMap.get("lastChangeTime"))));
            orderBean.setIsCod(Integer.parseInt(orderMap.get("cashOnDly")));
            orderBean.setPayTime(new Date(Long.parseLong(orderMap.get("payTime"))));
            MallOrderBean result = mallOrderService.save(orderBean);
            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            else
            {
                return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
            }


        }
    }

    @Override
    public Monitor<EventResult> orderStatusInfo(HttpServletRequest request) throws IOException {

        String sign = (String) request.getAttribute("sign");
        String secret = (String) request.getAttribute("secret");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));

        //订单参数
        Map<String, String> orderMap = new TreeMap<>();
        orderMap.put("orderId", request.getParameter("orderId"));
        orderMap.put("syncStatus", request.getParameter("syncStatus"));
        orderMap.put("memberId", request.getParameter("memberId"));
        orderMap.put("orderStatus", request.getParameter("orderStatus"));
        orderMap.put("payStatus", request.getParameter("payStatus"));
        orderMap.put("shipStatus", request.getParameter("shipStatus"));
        orderMap.put("memberStatus", request.getParameter("memberStatus"));
        orderMap.put("isDelivery", request.getParameter("isDelivery"));
        orderMap.put("deliverMethodId", request.getParameter("deliverMethodId"));
        orderMap.put("deliverMethod", request.getParameter("deliverMethod"));
        orderMap.put("deliverArea", request.getParameter("deliverArea"));
        orderMap.put("weight", request.getParameter("weight"));
        orderMap.put("orderName", request.getParameter("orderName"));
        orderMap.put("itemNum", request.getParameter("itemNum"));
        orderMap.put("actTime", request.getParameter("actTime"));
        orderMap.put("createTime", request.getParameter("createTime"));
        orderMap.put("createIP", request.getParameter("createIP"));
        orderMap.put("shipName", request.getParameter("shipName"));
        orderMap.put("shipArea", request.getParameter("shipArea"));
        orderMap.put("shipAddr", request.getParameter("shipAddr"));
        orderMap.put("shipZip", request.getParameter("shipZip"));
        orderMap.put("shipTel", request.getParameter("shipTel"));
        orderMap.put("shipEmail", request.getParameter("shipEmail"));
        orderMap.put("shipTime", request.getParameter("shipTime"));
        orderMap.put("shipMobile", request.getParameter("shipMobile"));
        orderMap.put("costItem", request.getParameter("costItem"));
        orderMap.put("isTax", request.getParameter("isTax"));
        orderMap.put("costTax", request.getParameter("costTax"));
        orderMap.put("taxCompany", request.getParameter("taxCompany"));
        orderMap.put("costFreight", request.getParameter("costFreight"));
        orderMap.put("isProtect", request.getParameter("isProtect"));
        orderMap.put("costProtect", request.getParameter("costProtect"));
        orderMap.put("costPayment", request.getParameter("costPayment"));
        orderMap.put("scoreU", request.getParameter("scoreU"));
        orderMap.put("discount", request.getParameter("discount"));
        orderMap.put("usePmt", request.getParameter("usePmt"));
        orderMap.put("totalAmount", request.getParameter("totalAmount"));
        orderMap.put("finalAmount", request.getParameter("finalAmount"));
        orderMap.put("pmtAmount", request.getParameter("pmtAmount"));
        orderMap.put("payed", request.getParameter("payed"));
        orderMap.put("memo", request.getParameter("memo"));
        orderMap.put("lastChangeTime", request.getParameter("lastChangeTime"));
        orderMap.put("customerId", request.getParameter("customerId"));
        orderMap.put("cashOnDly", request.getParameter("cashOnDly"));
        orderMap.put("onlinePayType", request.getParameter("onlinePayType"));
        orderMap.put("scoreUAmount", request.getParameter("scoreUAmount"));
        orderMap.put("payAgentId", request.getParameter("payAgentId"));
        orderMap.put("payAgentScore", request.getParameter("payAgentScore"));
        orderMap.put("payAgentScoreAmount", request.getParameter("payAgentScoreAmount"));
        orderMap.put("payAgentPayed", request.getParameter("payAgentPayed"));
        orderMap.put("hasPayed", request.getParameter("hasPayed"));
        orderMap.put("hasPayedScore", request.getParameter("hasPayedScore"));
        orderMap.put("onlineAmount", request.getParameter("onlineAmount"));
        orderMap.put("hongbaoAmount", request.getParameter("hongbaoAmount"));
        orderMap.put("payTime", request.getParameter("payTime"));
        orderMap.put("virtualRecMobile", request.getParameter("virtualRecMobile"));

        signMap.putAll(orderMap);
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            //ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(orderMap);
            MallOrderBean orderBean = new MallOrderBean();
            orderBean.setPayStatus(orderMap.get("payStatus"));
            orderBean.setDeliveryStatus(orderMap.get("shipStatus"));
            orderBean.setSendingType(orderMap.get("deliverMethod"));
            orderBean.setTotalWeight(Double.parseDouble(orderMap.get("weight")));
            orderBean.setItemNum(Integer.parseInt(orderMap.get("itemNum")));
            orderBean.setFinishTime(new Date(Long.parseLong(orderMap.get("actTime"))));
            orderBean.setBuyerName(orderMap.get("shipName"));
            orderBean.setAddress(orderMap.get("shipAddr"));
            orderBean.setPost(orderMap.get("shipZip"));
            orderBean.setPhone(orderMap.get("shipTel"));
            orderBean.setEmail(orderMap.get("shipEmail"));
            orderBean.setJdDeliveryTime(new Date(Long.parseLong(orderMap.get("shipTime"))));
            orderBean.setReceiverMobile(orderMap.get("shipMobile"));
            orderBean.setProTotalFee(Double.parseDouble(orderMap.get("costItem")));
            orderBean.setInvoiceTitle(orderMap.get("taxCompany"));
            orderBean.setOtherFee(orderMap.get("costPayment"));
            orderBean.setDiscount(Double.parseDouble(orderMap.get("discount")));
            orderBean.setOrderTotalFee(Double.parseDouble(orderMap.get("totalAmount")));
            orderBean.setFileTime(new Date(Long.parseLong(orderMap.get("lastChangeTime"))));
            orderBean.setIsCod(Integer.parseInt(orderMap.get("cashOnDly")));
            orderBean.setPayTime(new Date(Long.parseLong(orderMap.get("payTime"))));
            MallOrderBean result = mallOrderService.save(orderBean);

            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }

            if (result == null) {
               return new BaseMonitor<>(new EventResult(0,
                       "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
          }

          return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
        }
    }

    @Override
    public Monitor<EventResult> obtainOrderInfo(HttpServletRequest request) throws IOException {
        String sign = (String) request.getAttribute("sign");
        String secret = (String) request.getAttribute("secret");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));

        //订单参数
        Map<String, String> orderMap = new TreeMap<>();
        orderMap.put("orderId", request.getParameter("orderId"));
        signMap.putAll(orderMap);
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            //ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(orderMap);

            // String result = HttpUtil.getInstance().doPost(null, null);
            //根据条件查询订单信息

            MallOrderBean result = mallOrderService.findByOrderId(orderMap.get("orderId"));
            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            XmlMapper xmlMapper = new XmlMapper();
            String xmlResult = xmlMapper.writeValueAsString(result);
            //获取伙伴商城的订单信息
            return new BaseMonitor<>(new EventResult(1, xmlResult));
        }
    }
}
