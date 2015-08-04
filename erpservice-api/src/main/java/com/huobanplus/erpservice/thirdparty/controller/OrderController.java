package com.huobanplus.erpservice.thirdparty.controller;

import com.huobanplus.erpservice.event.model.BaseResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by allan on 2015/8/3.
 */
public interface OrderController {
    Map createOrder(String orderId, @RequestParam(value = "syncStatus",
            required = false, defaultValue = "0") int syncStatus,
                    int memberId,
                    int orderStatus,
                    int payStatus,
                    int shipStatus,
                    int memberStatus,
                    int isDelivery,
                    @RequestParam(value = "deliverMethodId", required = false, defaultValue = "0") int deliverMethodId,
                    String deliverMethod,
                    String deliverArea,
                    float weight,
                    String orderName,
                    int itemNum,
                    @RequestParam(value = "actTime", required = false, defaultValue = "0") long actTime,
                    @RequestParam(value = "createTime", required = false, defaultValue = "0") long createTime,
                    @RequestParam(value = "createIP", required = false, defaultValue = "") String createIP,
                    String shipName,
                    String shipArea,
                    String shipAddr,
                    @RequestParam(value = "shipZip", required = false, defaultValue = "") String shipZip,
                    String shipTel,
                    @RequestParam(value = "shipEmail", required = false, defaultValue = "") String shipEmail,
                    @RequestParam(value = "shipTime", required = false, defaultValue = "") String shipTime,
                    String shipMobile,
                    float costItem,
                    @RequestParam(value = "isTax", required = false, defaultValue = "0") int isTax,
                    @RequestParam(value = "costTax", required = false, defaultValue = "0") float costTax,
                    @RequestParam(value = "taxCompany", required = false, defaultValue = "") String taxCompany,
                    @RequestParam(value = "costFreight", required = false, defaultValue = "0") float costFreight,
                    @RequestParam(value = "isProtect", required = false, defaultValue = "0") int isProtect,
                    @RequestParam(value = "costProtect", required = false, defaultValue = "0") float costProtect,
                    float costPayment,
                    @RequestParam(value = "scoreU", required = false, defaultValue = "0") float scoreU,
                    @RequestParam(value = "discount", required = false, defaultValue = "0") float discount,
                    @RequestParam(value = "usePmt", required = false, defaultValue = "") String usePmt,
                    float totalAmount,
                    float finalAmount,
                    @RequestParam(value = "pmtAmount", required = false, defaultValue = "0") float pmtAmount,
                    @RequestParam(value = "payed", required = false, defaultValue = "0") float payed,
                    @RequestParam(value = "memo", required = false, defaultValue = "") String memo,
                    @RequestParam(value = "lastChangeTime", required = false, defaultValue = "0") long lastChangeTime,
                    int customerId,
                    @RequestParam(value = "cashOnDly", required = false, defaultValue = "0") int cashOnDly,
                    int onlinePayType,
                    @RequestParam(value = "scoreUAmount", required = false, defaultValue = "0") float scoreUAmount,
                    @RequestParam(value = "payAgentId", required = false, defaultValue = "0") int payAgentId,
                    @RequestParam(value = "payAgentScore", required = false, defaultValue = "0") float payAgentScore,
                    @RequestParam(value = "payAgentScoreAmount", required = false, defaultValue = "0") float payAgentScoreAmount,
                    @RequestParam(value = "payAgentPayed", required = false, defaultValue = "0") float payAgentPayed,
                    float hasPayed,
                    @RequestParam(value = "hasPayedScore", required = false, defaultValue = "0") float hasPayedScore,
                    float onlineAmount,
                    @RequestParam(value = "hongbaoAmount", required = false, defaultValue = "0") float hongbaoAmount,
                    @RequestParam(value = "payTime", required = false, defaultValue = "0") long payTime,
                    @RequestParam(value = "virtualRecMobile", required = false, defaultValue = "") String virtualRecMobile,
                    String sign,
                    String appKey,
                    String operation,
                    String capCode,
                    String timeStamp,
                    String erpName);

        BaseResult obtainOrder(String erpName);
}
