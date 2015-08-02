package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.bean.AuthBean;
import com.huobanplus.erpprovider.netshop.handler.DeliverHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignStrategy;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 2015/7/31.
 */
@Component
public class DeliverHandlerImpl implements DeliverHandler {
    @Override
    public Monitor<EventResult> deliverInform(HttpServletRequest request) throws IOException {
        AuthBean authBean = new AuthBean();
        authBean.setuCode(request.getParameter(Constant.SIGN_U_CODE));
        authBean.setmType(request.getParameter(Constant.SIGN_M_TYPE));
        authBean.setTimeStamp(request.getParameter(Constant.SIGN_TIME_STAMP));
        authBean.setSecret(request.getParameter(Constant.SIGN_SECRET));
        authBean.setSignType(Constant.SIGN_TYPE);
        //authBean = SignStrategy.getInstance().buildSign(authBean);
        if (!authBean.getSign().toUpperCase().equals(request.getParameter("Sign"))) {
            //throw new IllegalArgumentException("签名不正确");
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("orderNo", request.getParameter("OrderNO"));
        responseMap.put("sndStyle", request.getParameter("SndStyle"));
        responseMap.put("billId", request.getParameter("BillID"));

        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(responseMap);
        //todo 将获取的信息推送给伙伴商城

        String result = HttpUtil.getInstance().doPost(null, null);

        if (result == null) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
        }

        return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
    }
}
