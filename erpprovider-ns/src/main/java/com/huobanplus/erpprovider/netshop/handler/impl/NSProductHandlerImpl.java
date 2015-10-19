package com.huobanplus.erpprovider.netshop.handler.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.huobanplus.erpprovider.netshop.bean.NSGoodDetailResult;
import com.huobanplus.erpprovider.netshop.bean.NSGoodItemResult;
import com.huobanplus.erpprovider.netshop.bean.NSGoodResult;
import com.huobanplus.erpprovider.netshop.handler.NSProductHandler;
import com.huobanplus.erpprovider.netshop.support.BaseMonitor;
import com.huobanplus.erpprovider.netshop.util.Constant;
import com.huobanplus.erpprovider.netshop.util.SignBuilder;
import com.huobanplus.erpservice.common.util.HttpUtil;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.bean.MallGoodBean;
import com.huobanplus.erpservice.datacenter.service.MallGoodService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 商品事件实现类
 */
@Component
public class NSProductHandlerImpl implements NSProductHandler {

    @Autowired
    private MallGoodService goodService;

    @Override
    public EventResult obtainGoods(HttpServletRequest request) {
        try {
            String sign = request.getParameter(Constant.SIGN_PARAM);
            String uCode = request.getParameter(Constant.SIGN_U_CODE);
            String goodsType = request.getParameter("GoodsType");
            String goodId = request.getParameter("OuterID");
            String goodName = request.getParameter("GoodsName");
            String pageSize = request.getParameter("PageSize");
            String page = request.getParameter("Page");

            Map<String, String> signMap = new TreeMap<>();
            signMap.put(Constant.SIGN_U_CODE, uCode);
            signMap.put(Constant.SIGN_M_TYPE, request.getParameter(Constant.SIGN_M_TYPE));
            signMap.put(Constant.SIGN_TIMESTAMP, request.getParameter(Constant.SIGN_TIMESTAMP));
            signMap.put("GoodsType", goodsType);
            signMap.put("OuterID", goodId);
            signMap.put("GoodsName", goodName);
            signMap.put("PageSize", pageSize);
            signMap.put("Page", page);

            String signStr = SignBuilder.buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

            if (null == signStr && !signStr.equals(sign)) {
                return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>签名不正确</Cause></Rsp>");
            } else {
                Page<MallGoodBean> pageInfo = goodService.findAll(goodName, goodId, uCode, Integer.parseInt(page), Integer.parseInt(pageSize));
                NSGoodResult goodResult = new NSGoodResult();
                goodResult.setResult(1);
                goodResult.setTotalCount((int) pageInfo.getTotalElements());
                List<NSGoodDetailResult> detailResults = new ArrayList<>();
                pageInfo.getContent().forEach(good -> {
                    NSGoodDetailResult goodDetailResult = new NSGoodDetailResult();
                    goodDetailResult.setItemID(good.getBn());
                    goodDetailResult.setItemName(good.getGoodName());
                    goodDetailResult.setOuterID(good.getBn());
                    goodDetailResult.setNum(good.getNum());
                    goodDetailResult.setPrice(good.getPrice());
                    goodDetailResult.setIsSku(good.getIsSku());
                    List<NSGoodItemResult> itemResults = new ArrayList<>();
                    good.getProductBeans().forEach(pro -> {
                        NSGoodItemResult goodItemResult = new NSGoodItemResult();
                        goodItemResult.setUnit(pro.getSkuName());
                        goodItemResult.setSkuID(pro.getSkuId());
                        goodItemResult.setNum(pro.getNum());
                        goodItemResult.setSkuOuterID(pro.getSkuId());
                        itemResults.add(goodItemResult);
                    });
                    goodDetailResult.setItemResults(itemResults);
                    detailResults.add(goodDetailResult);
                });
                goodResult.setDetailResults(detailResults);
                String xmlResult = new XmlMapper().writeValueAsString(goodResult);
                xmlResult = xmlResult.replaceFirst("<Ware>", "");
                int wareIndex = xmlResult.lastIndexOf("</Ware>");
                String a = xmlResult.substring(0, wareIndex);
                String b = xmlResult.substring(wareIndex + 7);
                xmlResult = a + b;
                //fomat item
                xmlResult = xmlResult.replaceFirst("<Item>", "<Items>");
                int itemIndex = xmlResult.lastIndexOf("</Item>");
                String a1 = xmlResult.substring(0, itemIndex);
                String b1 = xmlResult.substring(itemIndex, xmlResult.length());
                xmlResult = a1 + b1.replaceFirst("</Item>", "</Items>");
                return EventResult.resultWith(EventResultEnum.SUCCESS, xmlResult);
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><Cause>服务器请求失败--" + e.getMessage() + "</Cause></Rsp>");
        }
    }

    @Override
    public EventResult syncInventory(HttpServletRequest request) {
        try {
            String sign = request.getParameter(Constant.SIGN_PARAM);
            String uCode = request.getParameter(Constant.SIGN_U_CODE);
            String itemId = request.getParameter("ItemID");
            String skuId = request.getParameter("SkuID");
            String quantity = request.getParameter("Quantity");

            Map<String, String> signMap = new TreeMap<>();
            signMap.put(Constant.SIGN_U_CODE, uCode);
            signMap.put(Constant.SIGN_M_TYPE, request.getParameter(Constant.SIGN_M_TYPE));
            signMap.put(Constant.SIGN_TIMESTAMP, request.getParameter(Constant.SIGN_TIMESTAMP));
            signMap.put("ItemID", itemId);
            signMap.put("SkuID", skuId);
            signMap.put("Quantity", quantity);

            String signStr = SignBuilder.buildSign(signMap, StringUtil.NETSHOP_SECRET, StringUtil.NETSHOP_SECRET);

            if (null == signStr && !signStr.equals(sign)) {
                return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><GoodsType></GoodsType><Cause>签名不正确</Cause></Rsp>");
            } else {
                //todo 推送给伙伴商城
                Map<String, String> requestData = new HashMap<>();
                requestData.put("itemId", itemId);
                requestData.put("skuId", skuId);
                requestData.put("quantity", quantity);
                String response = HttpUtil.getInstance().doPost("", requestData);
                if (response == null) {
                    return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><GoodsType></GoodsType><Cause>服务器请求失败</Cause></Rsp>");
                } else {
                    return EventResult.resultWith(EventResultEnum.SUCCESS, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>1</Result><GoodsType>OnSale</GoodsType><Cause></Cause></Rsp>");
                }
            }
        } catch (Exception e) {
            return EventResult.resultWith(EventResultEnum.ERROR, "<?xml version='1.0' encoding='utf-8'?><Rsp><Result>0</Result><GoodsType></GoodsType><Cause>服务器请求失败</Cause></Rsp>");
        }
    }
}
