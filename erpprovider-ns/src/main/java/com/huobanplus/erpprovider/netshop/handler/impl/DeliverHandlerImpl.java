package com.huobanplus.erpprovider.netshop.handler.impl;

import com.huobanplus.erpprovider.netshop.handler.DeliverHandler;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignStrategy;
import com.huobanplus.erpservice.event.model.AuthBean;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allan on 2015/7/31.
 */
@Component
public class DeliverHandlerImpl implements DeliverHandler {
    @Override
    public Monitor<EventResult> deliverInform(HttpServletRequest request) throws IllegalArgumentException {
        AuthBean authBean = new AuthBean();
        authBean.setuCode(request.getParameter(Constant.SIGN_U_CODE));
        authBean.setmType(request.getParameter(Constant.SIGN_M_TYPE));
        authBean.setTimeStamp(request.getParameter(Constant.SIGN_TIME_STAMP));
        authBean.setSecret(request.getParameter(Constant.SIGN_SECRET));
        authBean.setSignType(Constant.SIGN_TYPE);
        authBean = SignStrategy.getInstance().buildSign(authBean);
        if (!authBean.getSign().toUpperCase().equals(request.getParameter("Sign"))) {
            throw new IllegalArgumentException("签名不正确");
        }

        return null;
    }
}
