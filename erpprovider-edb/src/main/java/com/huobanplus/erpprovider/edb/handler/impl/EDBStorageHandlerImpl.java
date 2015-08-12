package com.huobanplus.erpprovider.edb.handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.edb.bean.EDBOutStoreInfo;
import com.huobanplus.erpprovider.edb.bean.EDBOutStoreWriteBack;
import com.huobanplus.erpprovider.edb.bean.EDBProductOut;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBStorageHandler;
import com.huobanplus.erpprovider.edb.net.HttpUtil;
import com.huobanplus.erpprovider.edb.support.SimpleMonitor;
import com.huobanplus.erpprovider.edb.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by allan on 2015/8/7.
 */
@Component
public class EDBStorageHandlerImpl extends BaseHandler implements EDBStorageHandler {
    @Override
    public Monitor<EventResult> outStorageAdd(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException {
        EDBOutStoreInfo edbOutStoreInfo = new EDBOutStoreInfo();
        List<EDBProductOut> edbProductOuts = new ArrayList<>();
        for (MallProductOutBean productOut : outStoreBean.getMallProductOutBeans()) {
            EDBProductOut edbProductOut = new EDBProductOut();
            edbProductOut.setOutStorageNo(outStoreBean.getOutStorageNo());
            edbProductOut.setProductItemNo(productOut.getProductItemNo());
            edbProductOut.setLocationNo(productOut.getLocationNo());
            edbProductOut.setStorageNo(productOut.getStorageNo());
            edbProductOut.setOutStorageNum(String.valueOf(productOut.getOutStorageNum()));
            edbProductOut.setOutStoragePrice(productOut.getOutStoragePrice());
            edbProductOut.setBatch(productOut.getBatch());
            edbProductOut.setFreightAvg(productOut.getFreightAvg());
            edbProductOut.setOutStorageRemark(productOut.getOutStorageRemark());
            edbProductOut.setBarCode(productOut.getBarCode());
            edbProductOuts.add(edbProductOut);
        }
        edbOutStoreInfo.setOutStorageNo(outStoreBean.getOutStorageNo());
        edbOutStoreInfo.setOutStorageType(outStoreBean.getOutStorageType());
        edbOutStoreInfo.setOutStorageTime(StringUtil.DateFormat(outStoreBean.getOutStorageTime(), StringUtil.TIME_PATTERN));
        edbOutStoreInfo.setStorageNo(outStoreBean.getStorageNo());
        edbOutStoreInfo.setSupplierNo(outStoreBean.getSupplierNo());
        edbOutStoreInfo.setFreightAvgWay(outStoreBean.getFreightAvgWay());
        edbOutStoreInfo.setFreight(outStoreBean.getFreight());
        edbOutStoreInfo.setImportSign(outStoreBean.getImportSign());
        edbOutStoreInfo.setRelateOrderNo(outStoreBean.getRelateOrderNo());
        edbOutStoreInfo.setYSInStorageNo(outStoreBean.getYSInStorageNo());
        edbOutStoreInfo.setOutStorageRemark(outStoreBean.getOutStorageRemark());
        edbOutStoreInfo.setProductOuts(edbProductOuts);

        String xmlResult = new XmlMapper().writeValueAsString(edbOutStoreInfo);
        int firstIndex = xmlResult.indexOf("<product_item>");
        int lastIndex = xmlResult.lastIndexOf("</product_item>");
        String firstPanel = xmlResult.substring(0, firstIndex);
        String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
        String xmlValues = "<info>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></info>";

        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
        Map<String, String> requestData = getSysRequestData("edbOutStoreAdd", edbSysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlValues", xmlValues);
        requestData.put("sign", getSign(signMap, edbSysData));

        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);

        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> outStoreConfirm(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException {
        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
        Map<String, String> requestData = getSysRequestData("edbOutStoreConfirm", edbSysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("outStorage_no", URLEncoder.encode(outStoreBean.getOutStorageNo(), "utf-8"));
        requestData.put("freight", URLEncoder.encode(outStoreBean.getFreight(), "utf-8"));
        requestData.put("freight_avgway", URLEncoder.encode(outStoreBean.getFreightAvgWay(), "utf-8"));
        signMap.put("outStorage_no", outStoreBean.getOutStorageNo());
        signMap.put("freight", outStoreBean.getFreight());
        signMap.put("freight_avgway", outStoreBean.getFreightAvgWay());
        requestData.put("sign", getSign(signMap, edbSysData));
        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }

    @Override
    public Monitor<EventResult> outStoreWriteback(MallProductOutBean productOutBean, ERPInfo erpInfo) throws IOException {
        EDBOutStoreWriteBack writeBack = new EDBOutStoreWriteBack();
        writeBack.setBarCode(productOutBean.getBarCode());
        writeBack.setOutStorageNo(productOutBean.getOutStoreBean().getOutStorageNo());
        writeBack.setOutStorageNum(productOutBean.getOutStorageNum());

        String resultXml = new XmlMapper().writeValueAsString(writeBack);
        String xmlValues = "<order>" + resultXml + "</order>";

        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
        Map<String, String> requestData = getSysRequestData("edbOutStoreWriteback", edbSysData);
        Map<String, String> signMap = new TreeMap<>(requestData);
        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
        signMap.put("xmlValues", xmlValues);
        requestData.put("sign", getSign(signMap, edbSysData));
        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);
        if (responseData == null) {
            return new SimpleMonitor<>(new EventResult(0, responseData));
        }
        return new SimpleMonitor<>(new EventResult(1, responseData));
    }
}
