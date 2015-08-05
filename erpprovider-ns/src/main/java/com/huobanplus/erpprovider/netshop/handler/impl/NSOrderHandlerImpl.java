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
            ///ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(orderMap);
            //String result = HttpUtil.getInstance().doPost(null, null);
            //将订单信息保存到数据库
            MallOrderBean orderBean = new MallOrderBean();
            orderBean.setOrderCode(orderMap.get("orderId"));
            orderBean.setNumId(orderMap.get("memberId"));
            orderBean.setSyncStatus(Integer.parseInt(orderMap.get("syncStatus")));
            orderBean.setOrderStatus(orderMap.get("orderStatus"));
            orderBean.setPayStatus(orderMap.get("payStatus"));
            orderBean.setDeliveryStatus(orderMap.get("shipStatus"));
            orderBean.setCustomerStatus(orderMap.get("memberStatus"));
            orderBean.setIsDelivery(Integer.parseInt(orderMap.get("isDelivery")));
            orderBean.setSendingTypeId(orderMap.get("deliverMethodId"));
            orderBean.setSendingType(orderMap.get("deliverMethod"));
            orderBean.setSendingArea(orderMap.get("deliverArea"));
            orderBean.setTotalWeight(Double.parseDouble(orderMap.get("weight")));
            orderBean.setOrderName(orderMap.get("orderName"));
            orderBean.setItemNum(Integer.parseInt(orderMap.get("itemNum")));
            orderBean.setBeginTime(new Date(Long.parseLong(orderMap.get("createTime"))));
            orderBean.setFinishTime(new Date(Long.parseLong(orderMap.get("actTime"))));
            orderBean.setCreateIP(orderMap.get("createIP"));
            orderBean.setBuyerName(orderMap.get("shipName"));
            orderBean.setAddress(orderMap.get("shipAddr"));
            orderBean.setPost(orderMap.get("shipZip"));
            orderBean.setPhone(orderMap.get("shipTel"));
            orderBean.setEmail(orderMap.get("shipEmail"));
            orderBean.setJdDeliveryTime(new Date(Long.parseLong(orderMap.get("shipTime"))));
            orderBean.setReceiverMobile(orderMap.get("shipMobile"));
            orderBean.setProTotalFee(Double.parseDouble(orderMap.get("costItem")));
            orderBean.setInvoiceIsopen(Integer.parseInt(orderMap.get("isTax")));
            orderBean.setCostTax(Double.parseDouble(orderMap.get("costTax")));
            orderBean.setInvoiceTitle(orderMap.get("taxCompany"));
            orderBean.setCostFreight(Double.parseDouble(orderMap.get("costFreight")));
            orderBean.setIsProtect(orderMap.get("isProtect"));
            orderBean.setCostProtect(Double.parseDouble(orderMap.get("costProtect")));
            orderBean.setOtherFee(orderMap.get("costPayment"));
            orderBean.setScoreU(Float.parseFloat(orderMap.get("scoreU")));
            orderBean.setDiscount(Double.parseDouble(orderMap.get("discount")));
            orderBean.setUsePmt(orderMap.get("usePmt"));
            orderBean.setOrderTotalFee(Double.parseDouble(orderMap.get("totalAmount")));
            orderBean.setFinalAmount(Double.parseDouble(orderMap.get("finalAmount")));
            orderBean.setPayed(Double.parseDouble(orderMap.get("pmtAmount")));
            orderBean.setMemo(orderMap.get("memo"));
            orderBean.setFileTime(new Date(Long.parseLong(orderMap.get("lastChangeTime"))));
            orderBean.setIsCod(Integer.parseInt(orderMap.get("cashOnDly")));
            orderBean.setOnlinePayType(orderMap.get("onlinePayType"));
            orderBean.setScoreUAmount(Double.parseDouble(orderMap.get("scoreUAmount")));
            orderBean.setPayAgentId(orderMap.get("payAgentId"));
            orderBean.setPayAgentScore(Double.parseDouble(orderMap.get("payAgentScore")));
            orderBean.setPayAgentScoreAmount(Double.parseDouble(orderMap.get("payAgentScoreAmount")));
            orderBean.setPayAgentPayed(Double.parseDouble(orderMap.get("payAgentPayed")));
            orderBean.setHasPayed(Double.parseDouble(orderMap.get("hasPayed")));
            orderBean.setHasPayedScore(Double.parseDouble(orderMap.get("hasPayedScore")));
            orderBean.setOnlineAmount(Double.parseDouble(orderMap.get("onlineAmount")));
            orderBean.setHongbaoAmount(Double.parseDouble(orderMap.get("hongbaoAmount")));
            orderBean.setPayTime(new Date(Long.parseLong(orderMap.get("payTime"))));
            orderBean.setVirtualRecMobile(orderMap.get("virtualRecMobile"));
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
            orderBean.setOrderCode(orderMap.get("orderId"));
            orderBean.setNumId(orderMap.get("memberId"));
            orderBean.setSyncStatus(Integer.parseInt(orderMap.get("syncStatus")));
            orderBean.setOrderStatus(orderMap.get("orderStatus"));
            orderBean.setPayStatus(orderMap.get("payStatus"));
            orderBean.setDeliveryStatus(orderMap.get("shipStatus"));
            orderBean.setCustomerStatus(orderMap.get("memberStatus"));
            orderBean.setIsDelivery(Integer.parseInt(orderMap.get("isDelivery")));
            orderBean.setSendingTypeId(orderMap.get("deliverMethodId"));
            orderBean.setSendingType(orderMap.get("deliverMethod"));
            orderBean.setSendingArea(orderMap.get("deliverArea"));
            orderBean.setTotalWeight(Double.parseDouble(orderMap.get("weight")));
            orderBean.setOrderName(orderMap.get("orderName"));
            orderBean.setItemNum(Integer.parseInt(orderMap.get("itemNum")));
            orderBean.setBeginTime(new Date(Long.parseLong(orderMap.get("createTime"))));
            orderBean.setFinishTime(new Date(Long.parseLong(orderMap.get("actTime"))));
            orderBean.setCreateIP(orderMap.get("createIP"));
            orderBean.setBuyerName(orderMap.get("shipName"));
            orderBean.setAddress(orderMap.get("shipAddr"));
            orderBean.setPost(orderMap.get("shipZip"));
            orderBean.setPhone(orderMap.get("shipTel"));
            orderBean.setEmail(orderMap.get("shipEmail"));
            orderBean.setJdDeliveryTime(new Date(Long.parseLong(orderMap.get("shipTime"))));
            orderBean.setReceiverMobile(orderMap.get("shipMobile"));
            orderBean.setProTotalFee(Double.parseDouble(orderMap.get("costItem")));
            orderBean.setInvoiceIsopen(Integer.parseInt(orderMap.get("isTax")));
            orderBean.setCostTax(Double.parseDouble(orderMap.get("costTax")));
            orderBean.setInvoiceTitle(orderMap.get("taxCompany"));
            orderBean.setCostFreight(Double.parseDouble(orderMap.get("costFreight")));
            orderBean.setIsProtect(orderMap.get("isProtect"));
            orderBean.setCostProtect(Double.parseDouble(orderMap.get("costProtect")));
            orderBean.setOtherFee(orderMap.get("costPayment"));
            orderBean.setScoreU(Float.parseFloat(orderMap.get("scoreU")));
            orderBean.setDiscount(Double.parseDouble(orderMap.get("discount")));
            orderBean.setUsePmt(orderMap.get("usePmt"));
            orderBean.setOrderTotalFee(Double.parseDouble(orderMap.get("totalAmount")));
            orderBean.setFinalAmount(Double.parseDouble(orderMap.get("finalAmount")));
            orderBean.setPayed(Double.parseDouble(orderMap.get("pmtAmount")));
            orderBean.setMemo(orderMap.get("memo"));
            orderBean.setFileTime(new Date(Long.parseLong(orderMap.get("lastChangeTime"))));
            orderBean.setIsCod(Integer.parseInt(orderMap.get("cashOnDly")));
            orderBean.setOnlinePayType(orderMap.get("onlinePayType"));
            orderBean.setScoreUAmount(Double.parseDouble(orderMap.get("scoreUAmount")));
            orderBean.setPayAgentId(orderMap.get("payAgentId"));
            orderBean.setPayAgentScore(Double.parseDouble(orderMap.get("payAgentScore")));
            orderBean.setPayAgentScoreAmount(Double.parseDouble(orderMap.get("payAgentScoreAmount")));
            orderBean.setPayAgentPayed(Double.parseDouble(orderMap.get("payAgentPayed")));
            orderBean.setHasPayed(Double.parseDouble(orderMap.get("hasPayed")));
            orderBean.setHasPayedScore(Double.parseDouble(orderMap.get("hasPayedScore")));
            orderBean.setOnlineAmount(Double.parseDouble(orderMap.get("onlineAmount")));
            orderBean.setHongbaoAmount(Double.parseDouble(orderMap.get("hongbaoAmount")));
            orderBean.setPayTime(new Date(Long.parseLong(orderMap.get("payTime"))));
            orderBean.setVirtualRecMobile(orderMap.get("virtualRecMobile"));
            MallOrderBean result = mallOrderService.save(orderBean);

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
