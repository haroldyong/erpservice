/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.handler.impl;

import com.huobanplus.erpprovider.edb.handler.BaseHandler;
import com.huobanplus.erpprovider.edb.handler.EDBStorageHandler;
import org.springframework.stereotype.Component;

/**
 * Created by allan on 2015/8/7.
 */
@Component
public class EDBStorageHandlerImpl extends BaseHandler implements EDBStorageHandler {
//    @Override
//    public EventResult outStorageAdd(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException {
//        EDBOutStoreInfo edbOutStoreInfo = new EDBOutStoreInfo();
//        List<EDBProductOut> edbProductOuts = new ArrayList<>();
//        for (MallProductOutBean productOut : outStoreBean.getMallProductOutBeans()) {
//            EDBProductOut edbProductOut = new EDBProductOut();
//            edbProductOut.setOutStorageNo(outStoreBean.getOutStorageNo());
//            edbProductOut.setProductItemNo(productOut.getProductItemNo());
//            edbProductOut.setLocationNo(productOut.getLocationNo());
//            edbProductOut.setStorageNo(productOut.getStorageNo());
//            edbProductOut.setOutStorageNum(String.valueOf(productOut.getOutStorageNum()));
//            edbProductOut.setOutStoragePrice(productOut.getOutStoragePrice());
//            edbProductOut.setBatch(productOut.getBatch());
//            edbProductOut.setFreightAvg(productOut.getFreightAvg());
//            edbProductOut.setOutStorageRemark(productOut.getOutStorageRemark());
//            edbProductOut.setBarCode(productOut.getBarCode());
//            edbProductOuts.add(edbProductOut);
//        }
//        edbOutStoreInfo.setOutStorageNo(outStoreBean.getOutStorageNo());
//        edbOutStoreInfo.setOutStorageType(outStoreBean.getOutStorageType());
//        edbOutStoreInfo.setOutStorageTime(StringUtil.DateFormat(outStoreBean.getOutStorageTime(), StringUtil.TIME_PATTERN));
//        edbOutStoreInfo.setStorageNo(outStoreBean.getStorageNo());
//        edbOutStoreInfo.setSupplierNo(outStoreBean.getSupplierNo());
//        edbOutStoreInfo.setFreightAvgWay(outStoreBean.getFreightAvgWay());
//        edbOutStoreInfo.setFreight(outStoreBean.getFreight());
//        edbOutStoreInfo.setImportSign(outStoreBean.getImportSign());
//        edbOutStoreInfo.setRelateOrderNo(outStoreBean.getRelateOrderNo());
//        edbOutStoreInfo.setYSInStorageNo(outStoreBean.getYSInStorageNo());
//        edbOutStoreInfo.setOutStorageRemark(outStoreBean.getOutStorageRemark());
//        edbOutStoreInfo.setProductOuts(edbProductOuts);
//
//        String xmlResult = new XmlMapper().writeValueAsString(edbOutStoreInfo);
//        int firstIndex = xmlResult.indexOf("<product_item>");
//        int lastIndex = xmlResult.lastIndexOf("</product_item>");
//        String firstPanel = xmlResult.substring(0, firstIndex);
//        String productPanel = xmlResult.substring(firstIndex + 14, lastIndex);
//        String xmlValues = "<info>" + firstPanel + "<product_info>" + productPanel + "</product_info></orderInfo></info>";
//
//        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
//        Map<String, Object> requestData = getSysRequestData("edbOutStoreAdd", edbSysData);
//        Map<String, Object> signMap = new TreeMap<>(requestData);
//        requestData.put("xmlvalues", URLEncoder.encode(xmlValues, "utf-8"));
//        signMap.put("xmlvalues", xmlValues);
//        requestData.put("sign", getSign(signMap, edbSysData));
//
//        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);
//
//        if (responseData == null) {
//            return EventResult.resultWith(EventResultEnum.ERROR, responseData);
//        }
//        return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
//    }
//
//    @Override
//    public EventResult outStoreConfirm(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException {
//        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
//        Map<String, Object> requestData = getSysRequestData("edbOutStoreConfirm", edbSysData);
//        Map<String, Object> signMap = new TreeMap<>(requestData);
//        requestData.put("outStorage_no", URLEncoder.encode(outStoreBean.getOutStorageNo(), "utf-8"));
//        requestData.put("freight", URLEncoder.encode(outStoreBean.getFreight(), "utf-8"));
//        requestData.put("freight_avgway", URLEncoder.encode(outStoreBean.getFreightAvgWay(), "utf-8"));
//        signMap.put("outStorage_no", outStoreBean.getOutStorageNo());
//        signMap.put("freight", outStoreBean.getFreight());
//        signMap.put("freight_avgway", outStoreBean.getFreightAvgWay());
//        requestData.put("sign", getSign(signMap, edbSysData));
//        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);
//        if (responseData == null) {
//            return EventResult.resultWith(EventResultEnum.ERROR, responseData);
//        }
//        return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
//    }
//
//    @Override
//    public EventResult outStoreWriteback(MallProductOutBean productOutBean, ERPInfo erpInfo) throws IOException {
//        EDBOutStoreWriteBack writeBack = new EDBOutStoreWriteBack();
//        writeBack.setBarCode(productOutBean.getBarCode());
//        writeBack.setOutStorageNo(productOutBean.getOutStoreBean().getOutStorageNo());
//        writeBack.setOutStorageNum(productOutBean.getOutStorageNum());
//
//        String resultXml = new XmlMapper().writeValueAsString(writeBack);
//        String xmlValues = "<order>" + resultXml + "</order>";
//
//        EDBSysData edbSysData = new ObjectMapper().readValue(erpInfo.getSysDataJson(), EDBSysData.class);
//        Map<String, Object> requestData = getSysRequestData("edbOutStoreWriteback", edbSysData);
//        Map<String, Object> signMap = new TreeMap<>(requestData);
//        requestData.put("xmlValues", URLEncoder.encode(xmlValues, "utf-8"));
//        signMap.put("xmlValues", xmlValues);
//        requestData.put("sign", getSign(signMap, edbSysData));
//        String responseData = HttpUtil.getInstance().doPost(edbSysData.getRequestUrl(), requestData);
//        if (responseData == null) {
//            return EventResult.resultWith(EventResultEnum.ERROR);
//        }
//        return EventResult.resultWith(EventResultEnum.SUCCESS, responseData);
//    }
}
