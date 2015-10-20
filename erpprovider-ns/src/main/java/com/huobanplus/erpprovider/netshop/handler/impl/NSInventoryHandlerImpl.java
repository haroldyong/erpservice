/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.netshop.handler.NSInventoryHandler;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.service.MallInventoryService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>类描述:   网店管家库存事件处理类。</p>
 */
@Component
public class NSInventoryHandlerImpl implements NSInventoryHandler {

    @Resource
    private MallInventoryService mallInventoryService;

    @Override
    public EventResult synsInventory(HttpServletRequest request) {
        try {
            String secret = (String) request.getAttribute("secret");
            String sign = (String) request.getAttribute("sign");
            Map<String, String> signMap = new TreeMap<>();
            signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
            signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
            signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIMESTAMP));
            signMap.put("datas", request.getParameter("datas"));

            String signStr = SignBuilder.buildSign(signMap, secret, secret);
            if (null != signStr && signStr.equals(sign)) {
                return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>签名不正确</Cause></Rsp>");
            } else {
                // ObjectMapper objectMapper = new ObjectMapper();
                //String resultJson = objectMapper.writeValueAsString(responseMap);
                //记录需要更新的库存信息
                ObjectMapper mapper = new ObjectMapper();
                MallInventoryBean inventoryBean = mapper.readValue(signMap.get("datas"), MallInventoryBean.class);
                MallInventoryBean result = mallInventoryService.save(inventoryBean);
                return EventResult.resultWith(EventResultEnum.SUCCESS, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</result><GoodsType></GoodsType><Cause>请求成功</Cause></Rsp>");
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>服务器请求失败--" + e.getMessage() + "</Cause></Rsp>");
        }
    }
}
