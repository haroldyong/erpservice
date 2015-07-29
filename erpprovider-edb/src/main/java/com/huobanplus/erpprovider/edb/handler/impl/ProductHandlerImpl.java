package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.handler.ProductHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpprovider.edb.util.XmlUtil;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by allan on 2015/7/28.
 */
@Component
public class ProductHandlerImpl implements ProductHandler {
    @Override
    public Monitor<EventResult> getProInventoryInfo() throws IOException, DocumentException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
        requestData.put("dbhost", Constant.DB_HOST);
        requestData.put("appkey", Constant.APP_KEY);
        requestData.put("method", Constant.GET_PRO_INFO);
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", Constant.IP);
        requestData.put("fields", URLEncoder.encode(Constant.GET_PRO_INFO_FIELD, "utf-8"));
        Map<String, String> signMap = new TreeMap<>(requestData);
        signMap.put("appscret", Constant.APP_SECRET);
        signMap.put("token", Constant.TOKEN);
        String sign = SignBuilder.buildSign(signMap, Constant.APP_KEY, "");
        requestData.put("sign", sign);

        String responseData = HttpUtil.getInstance().doGet(Constant.REQUEST_URI, requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        int firstRowIndex = responseData.indexOf("<Rows>");
        int lastRowIndex = responseData.lastIndexOf("</Rows>");
        String first = responseData.substring(0, firstRowIndex);
        String middle = responseData.substring(firstRowIndex, lastRowIndex + 7);
        String last = responseData.substring(lastRowIndex + 7, responseData.length());
        String resultXml = first + "<RowRoot>" + middle + "</RowRoot>" + last;
        String resultJson = XmlUtil.xml2Json(resultXml);
        return new SimpleMonitor<>(new EventResult(1, resultJson));
    }
}
