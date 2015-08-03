package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.handler.OrderHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpprovider.netshop.util.SignStrategy;
import com.huobanplus.erpservice.event.model.AuthBean;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 订单信息处理实现类
 */
public class OrderHandlerImpl implements OrderHandler {
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
            ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(orderMap);
            //todo 将获取的信息推送给伙伴商城

            String result = HttpUtil.getInstance().doPost(null, null);

            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }

            return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
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
            ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(orderMap);
            //todo 将获取的信息推送给伙伴商城

            String result = HttpUtil.getInstance().doPost(null, null);

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
            ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(orderMap);
            //todo 将获取的信息推送给伙伴商城

            String result = HttpUtil.getInstance().doPost(null, null);

            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }

            //获取伙伴商城的订单信息
            OrderInfo orderInfo = new OrderInfo();
            return new BaseMonitor<>(orderInfo);
        }
    }
}