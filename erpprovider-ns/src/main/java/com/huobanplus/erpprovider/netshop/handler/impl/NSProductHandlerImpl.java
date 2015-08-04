package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.ProductInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 商品事件实现类
 */
@Component
public class NSProductHandlerImpl implements NSProductHandler {
    @Override
    public Monitor<EventResult> obtainGood(HttpServletRequest request) throws IOException {

        String sign = (String) request.getAttribute("sign");
        String secret = (String) request.getAttribute("secret");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));

        //订单参数
        Map<String, String> productMap = new TreeMap<>();
        productMap.put("productNo", request.getParameter("productNo"));
        signMap.putAll(productMap);
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(productMap);
            //todo 将获取的信息推送给伙伴商城

            String result = HttpUtil.getInstance().doPost(null, null);

            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            //获取伙伴商城的产品信息
            ProductInfo productInfo = new ProductInfo();
            return new BaseMonitor<>(productInfo);
        }
    }
}
