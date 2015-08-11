package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.handler.NSDeliverHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/7/31.
 */
@Component
public class NSDeliverHandlerImpl implements NSDeliverHandler {
    @Override
    public Monitor<EventResult> deliverInform(HttpServletRequest request) throws IOException {
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("OrderNO", request.getParameter("OrderNO"));
        signMap.put("SndStyle", request.getParameter("SndStyle"));
        signMap.put("BillID", request.getParameter("BillID"));
        String sign = SignBuilder.buildSign(signMap, Constant.SECRET, Constant.SECRET);
        if (!sign.toUpperCase().equals(request.getParameter("Sign"))) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("orderNo", request.getParameter("OrderNO"));
        responseMap.put("sndStyle", request.getParameter("SndStyle"));
        responseMap.put("billId", request.getParameter("BillID"));

        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(responseMap);

        String result = HttpUtil.getInstance().doPost(null, null);

        if (result == null) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
        }

        return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
    }
}
