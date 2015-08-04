package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.handler.NSInventoryHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>类描述：<p/>
 * 网店管家库存事件处理类。
 */
@Component
public class NSInventoryHandlerImpl implements NSInventoryHandler {

    @Resource
    private
    @Override
    public Monitor<EventResult> synsInventory(HttpServletRequest request) throws IOException {
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("ItemID", request.getParameter("ItemID"));
        signMap.put("SkuID", request.getParameter("SkuID"));
        signMap.put("Quantity", request.getParameter("Quantity"));
        String sign = SignBuilder.buildSign(signMap, Constant.SECRET, Constant.SECRET);
        if (!sign.toUpperCase().equals(request.getParameter("Sign"))) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>签名不正确</Cause></Rsp>"));
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("itemId", signMap.get("ItemID"));
        responseMap.put("skuID", signMap.get("SkuID"));
        responseMap.put("quantity", signMap.get("Quantity"));

        ObjectMapper objectMapper = new ObjectMapper();
        String resultJson = objectMapper.writeValueAsString(responseMap);
        //todo 将获取的信息推送给伙伴商城

        String result = HttpUtil.getInstance().doPost(null, null);

        if (result == null) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>客户端请求失败</Cause></Rsp>"));
        }


        Map map = objectMapper.readValue(result, Map.class);

        return new BaseMonitor<>(new EventResult(1, new XmlMapper().writeValueAsString(map)));
    }
}
