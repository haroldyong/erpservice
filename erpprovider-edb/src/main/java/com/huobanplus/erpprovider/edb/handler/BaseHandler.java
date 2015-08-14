package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpprovider.edb.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/8/7.
 */
public class BaseHandler {
    /**
     * 得到包含公用系统参数的requestData
     *
     * @return
     */
    protected Map<String, String> getSysRequestData(String method, EDBSysData sysData) throws IOException {
        Map<String, String> requestData = new HashMap<>();
        String timestamp = StringUtil.DateFormat(new Date(), Constant.TIMESTAMP_PATTERN);
//        requestData.put("dbhost", Constant.DB_HOST);
//        requestData.put("appkey", Constant.APP_KEY);
//        requestData.put("format", Constant.FORMAT);
//        requestData.put("timestamp", timestamp);
//        requestData.put("v", Constant.V);
//        requestData.put("slencry", Constant.SLENCRY);
//        requestData.put("ip", Constant.IP);
        requestData.put("dbhost", sysData.getDbHost());
        requestData.put("appkey", sysData.getAppKey());
        requestData.put("format", Constant.FORMAT);
        requestData.put("timestamp", timestamp);
        requestData.put("v", Constant.V);
        requestData.put("slencry", Constant.SLENCRY);
        requestData.put("ip", sysData.getIp());
        requestData.put("method", method);
        return requestData;
    }

    /**
     * 得到sign签名
     *
     * @param signMap
     * @return
     */
    protected String getSign(Map signMap, EDBSysData sysData) {
//        signMap.put("appscret", Constant.APP_SECRET);
//        signMap.put("token", Constant.TOKEN);
        signMap.put("appscret", sysData.getAppSecret());
        signMap.put("token", sysData.getToken());
        return SignBuilder.buildSign(signMap, sysData.getAppKey(), "");
    }
}
