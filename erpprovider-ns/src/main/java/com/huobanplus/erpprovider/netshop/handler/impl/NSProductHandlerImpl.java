package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.datacenter.repository.MallProductOutRepository;
import com.huobanplus.erpservice.datacenter.service.MallProductOutService;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.ProductInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.metal.MetalLabelUI;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 商品事件实现类
 */
@Component
public class NSProductHandlerImpl implements NSProductHandler {

    @Resource
    private MallProductOutService mallProductOutService;

    @Override
    public Monitor<EventResult> obtainGood(HttpServletRequest request) throws IOException {

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
            /*ObjectMapper objectMapper = new ObjectMapper();
            String resultJson = objectMapper.writeValueAsString(productMap);

            String result = HttpUtil.getInstance().doPost(null, null);

            if (result == null) {
                return new BaseMonitor<>(new EventResult(0,
                        "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>客户端请求失败</Cause></Rsp>"));
            }
            //获取伙伴商城的产品信息
            ProductInfo productInfo = new ProductInfo();
            return new BaseMonitor<>(productInfo);*/
            ObjectMapper mapper = new ObjectMapper();
            try {
                MallProductOutBean productOutBean = mapper.readValue(signMap.get("datas"), MallProductOutBean.class);

                MallProductOutBean result = productOutBean = mallProductOutService.findById(productOutBean.getProductOutId());
                XmlMapper xmlMapper = new XmlMapper();
                String xmlResult = xmlMapper.writeValueAsString(result);
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
