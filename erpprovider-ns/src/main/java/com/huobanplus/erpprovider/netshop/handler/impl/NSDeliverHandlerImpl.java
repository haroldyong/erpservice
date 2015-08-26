package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.handler.NSDeliverHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/7/31.
 */
@Service
public class NSDeliverHandlerImpl implements NSDeliverHandler {
    @Autowired
    private MallOrderService orderService;

    @Override
    public Monitor<EventResult> deliverInform(HttpServletRequest request) throws IOException {
        String orderNo = request.getParameter("OrderNO");
        String sndStyle = request.getParameter("SndStyle");
        String billId = request.getParameter("BillID");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put(Constant.SIGN_U_CODE, request.getParameter(Constant.SIGN_U_CODE));
        signMap.put(Constant.SIGN_M_TYPE, request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put(Constant.SIGN_TIME_STAMP, request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("OrderNO", orderNo);
        signMap.put("SndStyle", sndStyle);
        signMap.put("BillID", billId);

        String sign = SignBuilder.buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

        if (!sign.toUpperCase().equals(request.getParameter("Sign"))) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            try {
                MallOrderBean preOrder = orderService.findByOrderId(orderNo);
//                preOrder.setDeliveryStatus(1);
                preOrder.setExpress(sndStyle);
                preOrder.setExpressNo(billId);
                orderService.save(preOrder);

                Map<String, String> responseMap = new HashMap<>();
                responseMap.put("orderId", orderNo);
                responseMap.put("express", sndStyle);
                responseMap.put("expressNo", billId);
                //todo 推送给伙伴商城
                String result = HttpUtil.getInstance().doPost(null, null);

                return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
            } catch (JsonParseException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            } catch (JsonMappingException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            } catch (IOException e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            } catch (Exception e) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
        }

    }
}
