package com.huobanplus.erpprovider.edb.handler.impl;

import com.huobanplus.erpprovider.edb.handler.ProductHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/7/28.
 */
@Component
public class ProductHandlerImpl implements ProductHandler {
    @Override
    public Monitor<EventResult> getProductInfo() throws IOException {
        Map<String, String> signMap = new TreeMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        signMap.put("dbhost", Constant.DB_HOST);
        signMap.put("appkey", Constant.APP_KEY);
        signMap.put("method", Constant.GET_PRO_INFO);
        signMap.put("format", Constant.FORMAT);
        signMap.put("timestamp", timestamp);
        signMap.put("v", Constant.V);
        signMap.put("slencry", Constant.SLENCRY);
        signMap.put("ip", Constant.IP);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        signMap.put("fields", Constant.GET_PRO_INFO_FIELD);

        String sign = SignBuilder.buildSign(signMap, Constant.APP_KEY, "");

        String requestUri = Constant.REQUEST_URI + String.format("?dbhost=%s&appkey=%s&method=%s&format=%s&timestamp=%s&v=%s&slencry=%s&ip%s&sign=%s&fields=%s",
                Constant.DB_HOST, Constant.APP_KEY, Constant.GET_PRO_INFO, Constant.FORMAT, timestamp, Constant.V, Constant.SLENCRY, Constant.IP, sign, URLEncoder.encode(Constant.GET_PRO_INFO_FIELD, "utf-8"));

        HttpUtil httpUtil = HttpUtil.getInstance();
        String result = httpUtil.doGet(requestUri);
        return null;
    }
}
