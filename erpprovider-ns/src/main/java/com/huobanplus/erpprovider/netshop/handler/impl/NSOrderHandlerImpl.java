package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
        signMap.put("datas", request.getParameter("datas"));

        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {
            ///ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(orderMap);
            //String result = HttpUtil.getInstance().doPost(null, null);
            //将订单信息保存到数据库
            //json转成orderBean
            ObjectMapper mapper = new ObjectMapper();
            try {
                MallOrderBean orderBean = mapper.readValue(signMap.get("datas"), MallOrderBean.class);
                MallOrderBean result = mallOrderService.save(orderBean);
                return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
            } catch (JsonParseException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (JsonMappingException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (IOException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            catch (Exception e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
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
        signMap.put("datas", request.getParameter("datas"));

        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {

            //json转成orderBean
            ObjectMapper mapper = new ObjectMapper();
            try {
                MallOrderBean orderBean = mapper.readValue(signMap.get("datas"), MallOrderBean.class);
                MallOrderBean result = mallOrderService.save(orderBean);
                return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result></Rsp>"));
            } catch (JsonParseException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (JsonMappingException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (IOException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            catch (Exception e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
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
        signMap.put("datas", request.getParameter("datas"));
        //订单参数
        String signStr = SignBuilder.buildSign(signMap, secret, secret);

        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>"));
        } else {

            ObjectMapper mapper = new ObjectMapper();
            try {
                MallOrderBean orderBean = mapper.readValue(signMap.get("datas"), MallOrderBean.class);
                MallOrderBean result = mallOrderService.findByOrderId(orderBean.getOrderId());
                XmlMapper xmlMapper = new XmlMapper();
                String xmlResult = xmlMapper.writeValueAsString(result);
                //获取伙伴商城的订单信息
                return new BaseMonitor<>(new EventResult(1, xmlResult));
            } catch (JsonParseException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (JsonMappingException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>数据解析失败</Cause></Rsp>"));
            }
            catch (IOException e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            catch (Exception e)
            {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }

        }
    }
}
