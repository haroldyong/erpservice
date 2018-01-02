package com.huobanplus.erpservice.service.impl;

import com.huobanplus.erpprovider.dtw.util.DtwUtil;
import com.huobanplus.erpprovider.gjbc.common.GjbcEnum;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.handler.impl.GjbcOrderHandlerImpl;
import com.huobanplus.erpprovider.gjbc.util.GjbcConstant;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.model.AlipayOrder;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.hotapi.common.HotApiConstant;
import com.huobanplus.erpservice.service.AlipayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author hxh
 * @Date 2018/1/2 11:06
 */
@Service
public class AlipayServiceImpl implements AlipayService{
    private final static Log log = LogFactory.getLog(AlipayServiceImpl.class);
    @Override
    public EventResult PushOrderAliPay(AlipayOrder order) {
        try {
//            log.info(JSON.toJSONString(gjbcSysData));
            log.info("orderNo:" + order.getOrderId());

            Map<String, Object> requestMap = new TreeMap<>();
            requestMap.put("service", "alipay.acquire.customs");
            requestMap.put("_input_charset", "utf-8");
            requestMap.put("sign_type", "MD5");

            requestMap.put("out_request_no", order.getOrderId());
            requestMap.put("trade_no", order.getPayNumber());
            requestMap.put("amount", order.getOnlinePayAmount());
            requestMap.put("partner", order.getAliPartner());
            requestMap.put("merchant_customs_code", order.getECommerceCode());
            requestMap.put("merchant_customs_name", order.getECommerceName());
            requestMap.put("customs_place", HotApiConstant.customer_value);
            requestMap.put("is_split", "T");
            requestMap.put("sub_out_biz_no", order.getOrderId());


            String sign = DtwUtil.aliBuildSign(requestMap, order.getAliKey());
            requestMap.put("sign", sign);

            HttpResult httpResult = HttpClientUtil.getInstance().get(HotApiConstant.ALI_PAY_URL, requestMap);
            if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
                String XmlData = httpResult.getHttpContent();
                Element rootElement = DocumentHelper.parseText(XmlData).getRootElement();
                Element isSuccess = rootElement.element("is_success");
                if (isSuccess.getText().equals("T")) {
                    Element aliPay_Element = rootElement.element("response").element("alipay");
                    Element result_code = aliPay_Element.element("result_code");
                    if (result_code.getText().equals("SUCCESS")) {
                        log.info("ali pay order push success");
                        return EventResult.resultWith(EventResultEnum.SUCCESS);
                    } else {
                        Element detailErrorDesdes = aliPay_Element.element("detail_error_des");
                        log.info("ali pay order push failed:" + detailErrorDesdes.getText());
                        return EventResult.resultWith(EventResultEnum.ERROR, detailErrorDesdes.getText(), null);
                    }
                } else {
                    Element error_Elem = rootElement.element("error");
                    log.info("ali pay order push failed:" + error_Elem.getText());
                    return EventResult.resultWith(EventResultEnum.ERROR, error_Elem.getText(), null);
                }
            } else {
                log.error("Server Request Failed:" + httpResult.getHttpContent());
                return EventResult.resultWith(EventResultEnum.ERROR, httpResult.getHttpContent(), null);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, e.getMessage(), null);
        }
    }
}
