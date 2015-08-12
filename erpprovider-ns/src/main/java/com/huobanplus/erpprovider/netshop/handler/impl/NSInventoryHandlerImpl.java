package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.handler.NSInventoryHandler;
import com.huobanplus.erpprovider.netshop.net.HttpUtil;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.repository.MallInventoryRepository;
import com.huobanplus.erpservice.datacenter.service.MallInventoryService;
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
 *  <p>类描述:   网店管家库存事件处理类。</p>
 */
@Component
public class NSInventoryHandlerImpl implements NSInventoryHandler {

    @Resource
    private MallInventoryService mallInventoryService;
    @Override
    public Monitor<EventResult> synsInventory(HttpServletRequest request) throws IOException {

        String secret = (String) request.getAttribute("secret");
        String sign = (String) request.getAttribute("sign");
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("uCode", request.getParameter(Constant.SIGN_U_CODE));
        signMap.put("mType", request.getParameter(Constant.SIGN_M_TYPE));
        signMap.put("TimeStamp", request.getParameter(Constant.SIGN_TIME_STAMP));
        signMap.put("datas", request.getParameter("datas"));

        String signStr = SignBuilder.buildSign(signMap, secret, secret);
        if (null != signStr && signStr.equals(sign)) {
            return new BaseMonitor<>(new EventResult(0,
                    "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</result><GoodsType></GoodsType><Cause>签名不正确</Cause></Rsp>"));
        }
        else
        {
            // ObjectMapper objectMapper = new ObjectMapper();
            //String resultJson = objectMapper.writeValueAsString(responseMap);
            //记录需要更新的库存信息
            ObjectMapper mapper = new ObjectMapper();
            try {
                MallInventoryBean inventoryBean = mapper.readValue(signMap.get("datas"), MallInventoryBean.class);
                MallInventoryBean result = mallInventoryService.save(inventoryBean);
                return new BaseMonitor<>(new EventResult(1, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</result><GoodsType></GoodsType><Cause>需要更新的数据已经数据已经保存</Cause></Rsp>"));

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
