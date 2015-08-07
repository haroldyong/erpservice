package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBProductHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpprovider.edb.util.XmlUtil;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by allan on 2015/7/28.
 */
@Component
public class EDBProductHandlerImpl extends BaseHandler implements EDBProductHandler {
    @Override
    public Monitor<EventResult> getProInventoryInfo(ERPInfo erpInfo) throws IOException {
        EDBSysData sysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
        Map<String, String> requestData = getSysRequestData(Constant.GET_PRO_INFO, sysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("sign", getSign(signMap, sysData));

        String responseData = HttpUtil.getInstance().doGet(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }
}
