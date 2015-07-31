package com.huobanplus.erpservice.htcomponent.controller;

import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpservice.event.OrderBaseEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.*;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <b>类描述：<b/>对接网店管家的控制类
 */
@Controller
public class NetShopController {

    @Resource
    private ERPRegister erpRegister;

    @ResponseBody
    @RequestMapping(value = "/obtainOrderDetail", method = RequestMethod.GET)
    public Monitor<EventResult> obtainOrderDetail(String sign,
                                                String uCode,
                                                String secret,
                                                String timeStamp,
                                                String mType,
                                                @RequestParam(value = "signType", required = false, defaultValue = "MD5") String signType,
                                                String erpName,
                                                String orderId)
    {
        //获取erp构建器
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        OrderBaseEvent orderBaseEvent = new OrderBaseEvent();
        AuthBean authBean = new AuthBean();
        authBean.setSign(sign);
        authBean.setTimeStamp(timeStamp);
        authBean.setuCode(uCode);
        authBean.setSecret(secret);
        authBean.setmType(mType);
        authBean.setSignType(signType);
        orderBaseEvent.setAuthBean(authBean);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderCode(orderId);
        if(erpHandler.eventSupported(orderBaseEvent))
        {
            //处理该事件
            try {
                return erpHandler.handleEvent(orderBaseEvent);
            } catch (IOException e) {

                EventResult result = new EventResult(0,"IO处理异常。");
                return new SimpleMonitor<EventResult>(result);
            } catch (IllegalAccessException e) {
                EventResult result = new EventResult(0,"IllegalAccessException处理异常。");
                return new SimpleMonitor<EventResult>(result);
            } catch (DocumentException e) {
                EventResult result = new EventResult(0,"DocumentException处理异常。");
                return new SimpleMonitor<EventResult>(result);
            }
        }
    else
    {
        EventResult result = new EventResult(0,"处理器不支持该方法处理机制。");
        return new SimpleMonitor<EventResult>(result);
    }
    }


}
