package com.huobanplus.erpprovicer.huobanmall.config;

import com.huobanplus.erpprovicer.huobanmall.controller.InventoryController;
import com.huobanplus.erpprovicer.huobanmall.controller.OrderInfoController;
import com.huobanplus.erpservice.event.erpevent.*;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.FailedBean;
import com.huobanplus.erpservice.event.model.Monitor;
import org.dom4j.DocumentException;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 伙伴Mall功能具体操作类
 * 该处貌似和Contorller有冲突，该类待定
 */

public class HuobanMallHandlerBuilder implements ERPHandlerBuilder {

    @Resource
    private InventoryController inventoryController;
    @Resource
    private OrderInfoController orderInfoController;

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if(!"huobanMall".equals(info.getName()))
        {
            //不是伙伴商城
            return null;
        }
        return new ERPHandler() {


            @Override
            public boolean eventSupported(Class<? extends ERPBaseEvent> baseEventClass) {
                if (baseEventClass == CreateOrderEvent.class) {
                    return true;
                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                    return true;
                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }

            @Override
            public Monitor<EventResult> handleEvent(Class<? extends ERPBaseEvent> baseEventClass, Object data) throws IOException, IllegalAccessException, DataAccessException {
                HttpServletRequest request = (HttpServletRequest) data;
                if (baseEventClass == CreateOrderEvent.class) {
                    //接收参数
                    String orderId = (String) request.getAttribute("orderId");
                    int syncStatus = (int) request.getAttribute("syncStatus");
                    String memberId = (String) request.getAttribute("memberId");
                    String orderStatus = (String) request.getAttribute("orderStatus");
                    String payStatus = (String) request.getAttribute("payStatus");
                    String shipStatus = (String) request.getAttribute("shipStatus");
                    String memberStatus = (String) request.getAttribute("memberStatus");
                    int isDelivery = (int) request.getAttribute("isDelivery");
                    String deliverMethodId = (String) request.getAttribute("deliverMethodId");
                    String deliverMethod = (String) request.getAttribute("deliverMethod");
                    String deliverArea = (String) request.getAttribute("deliverArea");
                    float weight = (float) request.getAttribute("weight");
                    String orderName = (String) request.getAttribute("orderName");
                    int itemNum = (int) request.getAttribute("itemNum");
                    long actTime = (long) request.getAttribute("actTime");
                    long createTime = (long) request.getAttribute("createTime");
                    String createIP = (String) request.getAttribute("createIP");
                    String shipName = (String) request.getAttribute("shipName");
                    String shipArea = (String) request.getAttribute("shipArea");
                    String shipAddr = (String) request.getAttribute("shipAddr");
                    String shipZip = (String) request.getAttribute("shipZip");
                    String shipTel = (String) request.getAttribute("shipTel");
                    String shipEmail = (String) request.getAttribute("shipEmail");
                    long shipTime = (long) request.getAttribute("shipTime");
                    String shipMobile = (String) request.getAttribute("shipMobile");
                    double costItem = (double) request.getAttribute("costItem");
                    int isTax = (int) request.getAttribute("isTax");
                    float costTax = (float) request.getAttribute("costTax");
                    String taxCompany = (String) request.getAttribute("taxCompany");
                    float costFreight = (float) request.getAttribute("costFreight");
                    String isProtect = (String) request.getAttribute("isProtect");
                    float costProtect = (float) request.getAttribute("costProtect");
                    double costPayment = (double) request.getAttribute("costPayment");
                    double scoreU = (double) request.getAttribute("scoreU");
                    float discount = (float) request.getAttribute("discount");
                    String usePmt = (String) request.getAttribute("usePmt");
                    float totalAmount = (float) request.getAttribute("totalAmount");
                    float finalAmount = (float) request.getAttribute("finalAmount");
                    float pmtAmount = (float) request.getAttribute("pmtAmount");
                    float payed = (float) request.getAttribute("payed");
                    String memo = (String) request.getAttribute("memo");
                    long lastChangeTime = (long) request.getAttribute("lastChangeTime");
                    String customerId = (String) request.getAttribute("customerId");
                    int cashOnDly = (int) request.getAttribute("cashOnDly");
                    String onlinePayType = (String) request.getAttribute("onlinePayType");
                    double scoreUAmount = (double) request.getAttribute("scoreUAmount");
                    String payAgentId = (String) request.getAttribute("payAgentId");
                    double payAgentScore = (double) request.getAttribute("payAgentScore");
                    double payAgentScoreAmount = (double) request.getAttribute("payAgentScoreAmount");
                    double payAgentPayed = (double) request.getAttribute("payAgentPayed");
                    double hasPayed = (double) request.getAttribute("hasPayed");
                    double hasPayedScore = (double) request.getAttribute("hasPayedScore");
                    double onlineAmount = (double) request.getAttribute("onlineAmount");
                    double hongbaoAmount = (double) request.getAttribute("hongbaoAmount");
                    long payTime = (long) request.getAttribute("payTime");
                    String virtualRecMobile = (String) request.getAttribute("virtualRecMobile");
                    String sign = (String) request.getAttribute("sign");
                    String uCode = (String) request.getAttribute("uCode");
                    String secret = (String) request.getAttribute("secret");
                    String timeStamp = (String) request.getAttribute("timeStamp");
                    String mType = (String) request.getAttribute("mType");
                    String signType = (String) request.getAttribute("signType");

                    return  orderInfoController.commitOrder(orderId, syncStatus,
                     memberId,
                     orderStatus,
                     payStatus,
                     shipStatus,
                     memberStatus,
                     isDelivery,
                     deliverMethodId,
                     deliverMethod,
                     deliverArea,
                     weight,
                     orderName,
                     itemNum,
                     actTime,
                     createTime,
                     createIP,
                     shipName,
                     shipArea,
                     shipAddr,
                     shipZip,
                     shipTel,
                     shipEmail,
                     shipTime,
                     shipMobile,
                     costItem,
                     isTax,
                     costTax,
                     taxCompany,
                     costFreight,
                     isProtect,
                     costProtect,
                     costPayment,
                     scoreU,
                     discount,
                     usePmt,
                     totalAmount,
                     finalAmount,
                     pmtAmount,
                     payed,
                     memo,
                     lastChangeTime,
                     customerId,
                     cashOnDly,
                     onlinePayType,
                     scoreUAmount,
                     payAgentId,
                     payAgentScore,
                     payAgentScoreAmount,
                     payAgentPayed,
                     hasPayed,
                     hasPayedScore,
                     onlineAmount,
                     hongbaoAmount,
                     payTime,
                     virtualRecMobile,
                     sign,
                     uCode,
                     secret,
                     timeStamp,
                     mType,
                     signType);

                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {
                    String sign = (String) request.getAttribute("sign");
                    String uCode = (String) request.getAttribute("uCode");
                    String secret = (String) request.getAttribute("secret");
                    String timeStamp = (String) request.getAttribute("timeStamp");
                    String mType = (String) request.getAttribute("mType");
                    String signType  = (String) request.getAttribute("signType");
                    String orderIds  = (String) request.getAttribute("orderIds");

                    return orderInfoController.obtainOrder(sign, uCode,
                             secret,
                             timeStamp,
                             mType,
                             signType,
                             orderIds);

                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {
                }
                else if(baseEventClass == InventoryEvent.class)
                {
                    /**
                     * 处理提交库存信息，分为入库单和出库单
                     */
                    //接收参数
                    //入库参数
                    String sign = (String) request.getAttribute("sign");
                    String uCode = (String) request.getAttribute("uCode");
                    String secret = (String) request.getAttribute("secret");
                    String timeStamp = (String) request.getAttribute("timeStamp");
                    String mType = (String) request.getAttribute("mType");
                    String signType = (String) request.getAttribute("signType");
                    String inStorageNo = (String) request.getAttribute("inStorageNo");
                    double freight = (double) request.getAttribute("freight");
                    String freightAvgway = (String) request.getAttribute("freightAvgway");
                    String barCode = (String) request.getAttribute("barCode");
                    int instorageNum = (int) request.getAttribute("instorageNum");
                    int type = (int) request.getAttribute("type");
                    String dateType = (String) request.getAttribute("dateType");
                    long beginTime = (long) request.getAttribute("beginTime");
                    long endTime = (long) request.getAttribute("endTime");
                    String storageName = (String) request.getAttribute("sign");
                    int instorageStatus = (int) request.getAttribute("instorageStatus");
                    String importMark = (String) request.getAttribute("importMark");
                    String typeNo = (String) request.getAttribute("typeNo");
                    String provider = (String) request.getAttribute("provider");
                    String storage = (String) request.getAttribute("storage");
                    String creater = (String) request.getAttribute("creater");
                    long createTime = (long) request.getAttribute("createTime");
                    long inTime = (long) request.getAttribute("inTime");
                    String qualityInspctor = (String) request.getAttribute("qualityInspctor");
                    long inspctTime = (long) request.getAttribute("inspctTime");
                    String inspctResult  = (String) request.getAttribute("inspctResult");
                    String examiner = (String) request.getAttribute("examiner");
                    long examineTime = (long) request.getAttribute("examineTime");
                    String inReason  = (String) request.getAttribute("inReason");
                    double cost = (double) request.getAttribute("cost");
                    String SourceTid  = (String) request.getAttribute("SourceTid");
                    double purchaseFee  = (double) request.getAttribute("purchaseFee");
                    double contractMoney  = (double) request.getAttribute("contractMoney");
                    String relevantTid = (String) request.getAttribute("relevantTid");
                    double rate = (double) request.getAttribute("rate");
                    String currency  = (String) request.getAttribute("currency");
                    String outContractTid = (String) request.getAttribute("outContractTid");
                    String logistics = (String) request.getAttribute("logistics");
                    String expressTid = (String) request.getAttribute("expressTid");
                    String freightPayer = (String) request.getAttribute("freightPayer");
                    String remarks  = (String) request.getAttribute("remarks");
                    String freightMode = (String) request.getAttribute("freightMode");
                    String storageNo = (String) request.getAttribute("storageNo");
                    String listSource = (String) request.getAttribute("listSource");
                    double otherCost = (double) request.getAttribute("otherCost");
                    String outPactNo = (String) request.getAttribute("outPactNo");
                    String productItemNo = (String) request.getAttribute("productItemNo");
                    String locationNo = (String) request.getAttribute("locationNo");
                    String batch = (String) request.getAttribute("batch");
                    long expireTime = (long) request.getAttribute("expireTime");
                    String supplieNo = (String) request.getAttribute("supplieNo");
                    String YSInstorageNo = (String) request.getAttribute("YSInstorageNo");
                    double freightAvg = (double) request.getAttribute("freightAvg");

                    //出库的部分参数
                    String outStorageNo = (String) request.getAttribute("outStorageNo");
                    int outstorageNum = (int) request.getAttribute("outstorageNum");
                    String outstorageType = (String) request.getAttribute("outstorageType");
                    long outstorageTime = (long) request.getAttribute("outstorageTime");
                    String outStorageRemark = (String) request.getAttribute("outStorageRemark");
                    double outstoragePrice = (double) request.getAttribute("outstoragePrice");
                    String outstorageStatus = (String) request.getAttribute("outstorageStatus");
                    String outStoreTypeName = (String) request.getAttribute("outStoreTypeName");
                    if(!StringUtils.isEmpty(inStorageNo))
                    {
                        //入库订单
                        return inventoryController.commitInStorage( sign,
                         uCode,
                         secret,
                         timeStamp,
                         mType,
                         signType,
                         inStorageNo,
                         freight,
                         freightAvgway,
                         barCode,
                         instorageNum,
                         type,
                         dateType,
                         beginTime,
                         endTime,
                         storageName,
                         instorageStatus,
                         importMark,
                         typeNo,
                         provider,
                         storage,
                         creater,
                         createTime,
                         inTime,
                         qualityInspctor,
                         inspctTime,
                         inspctResult,
                         examiner,
                         examineTime,
                         inReason,
                         cost,
                         SourceTid,
                         purchaseFee,
                         contractMoney,
                         relevantTid,
                         rate,
                         currency,
                         outContractTid,
                         logistics,
                         expressTid,
                         freightPayer,
                         remarks,
                         freightMode,
                         storageNo,
                         listSource,
                         otherCost,
                         outPactNo,
                         productItemNo,
                         locationNo,
                         batch,
                         expireTime,
                         supplieNo,
                         YSInstorageNo,
                         freightAvg);
                    }
                    else
                    {
                        //出库订单
                        return inventoryController.commitOutStorage(sign,
                         uCode,
                         secret,
                         timeStamp,
                         mType,
                         signType,
                         outStorageNo,
                         freight,
                         freightAvgway,
                         barCode,
                         outstorageNum,
                         dateType,
                         beginTime,
                         endTime,
                         storageName,
                         importMark,
                         provider,
                         storage,
                         creater,
                         createTime,
                         qualityInspctor,
                         inspctTime,
                         inspctResult,
                         examiner,
                         examineTime,
                         cost,
                         SourceTid,
                         purchaseFee,
                         contractMoney,
                         relevantTid,
                         rate,
                         currency,
                         outContractTid,
                         logistics,
                         expressTid,
                         freightPayer,
                         freightMode,
                         storageNo,
                         listSource,
                         otherCost,
                         outPactNo,
                         productItemNo,
                         locationNo,
                         batch,
                         expireTime,
                         supplieNo,
                         freightAvg,
                         outstorageType,
                         outstorageTime,
                         outStorageRemark,
                         outstoragePrice,
                         outstorageStatus,
                         outStoreTypeName);
                    }

                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {
                    //接收参数
                    String orderId = (String) request.getAttribute("orderId");
                    int syncStatus = (int) request.getAttribute("syncStatus");
                    String memberId = (String) request.getAttribute("memberId");
                    String orderStatus = (String) request.getAttribute("orderStatus");
                    String payStatus = (String) request.getAttribute("payStatus");
                    String shipStatus = (String) request.getAttribute("shipStatus");
                    String memberStatus = (String) request.getAttribute("memberStatus");
                    int isDelivery = (int) request.getAttribute("isDelivery");
                    String deliverMethodId = (String) request.getAttribute("deliverMethodId");
                    String deliverMethod = (String) request.getAttribute("deliverMethod");
                    String deliverArea = (String) request.getAttribute("deliverArea");
                    float weight = (float) request.getAttribute("weight");
                    String orderName = (String) request.getAttribute("orderName");
                    int itemNum = (int) request.getAttribute("itemNum");
                    long actTime = (long) request.getAttribute("actTime");
                    long createTime = (long) request.getAttribute("createTime");
                    String createIP = (String) request.getAttribute("createIP");
                    String shipName = (String) request.getAttribute("shipName");
                    String shipArea = (String) request.getAttribute("shipArea");
                    String shipAddr = (String) request.getAttribute("shipAddr");
                    String shipZip = (String) request.getAttribute("shipZip");
                    String shipTel = (String) request.getAttribute("shipTel");
                    String shipEmail = (String) request.getAttribute("shipEmail");
                    long shipTime = (long) request.getAttribute("shipTime");
                    String shipMobile = (String) request.getAttribute("shipMobile");
                    double costItem = (double) request.getAttribute("costItem");
                    int isTax = (int) request.getAttribute("isTax");
                    float costTax = (float) request.getAttribute("costTax");
                    String taxCompany = (String) request.getAttribute("taxCompany");
                    float costFreight = (float) request.getAttribute("costFreight");
                    String isProtect = (String) request.getAttribute("isProtect");
                    float costProtect = (float) request.getAttribute("costProtect");
                    double costPayment = (double) request.getAttribute("costPayment");
                    double scoreU = (double) request.getAttribute("scoreU");
                    float discount = (float) request.getAttribute("discount");
                    String usePmt = (String) request.getAttribute("usePmt");
                    float totalAmount = (float) request.getAttribute("totalAmount");
                    float finalAmount = (float) request.getAttribute("finalAmount");
                    float pmtAmount = (float) request.getAttribute("pmtAmount");
                    float payed = (float) request.getAttribute("payed");
                    String memo = (String) request.getAttribute("memo");
                    long lastChangeTime = (long) request.getAttribute("lastChangeTime");
                    String customerId = (String) request.getAttribute("customerId");
                    int cashOnDly = (int) request.getAttribute("cashOnDly");
                    String onlinePayType = (String) request.getAttribute("onlinePayType");
                    double scoreUAmount = (double) request.getAttribute("scoreUAmount");
                    String payAgentId = (String) request.getAttribute("payAgentId");
                    double payAgentScore = (double) request.getAttribute("payAgentScore");
                    double payAgentScoreAmount = (double) request.getAttribute("payAgentScoreAmount");
                    double payAgentPayed = (double) request.getAttribute("payAgentPayed");
                    double hasPayed = (double) request.getAttribute("hasPayed");
                    double hasPayedScore = (double) request.getAttribute("hasPayedScore");
                    double onlineAmount = (double) request.getAttribute("onlineAmount");
                    double hongbaoAmount = (double) request.getAttribute("hongbaoAmount");
                    long payTime = (long) request.getAttribute("payTime");
                    String virtualRecMobile = (String) request.getAttribute("virtualRecMobile");
                    String sign = (String) request.getAttribute("sign");
                    String uCode = (String) request.getAttribute("uCode");
                    String secret = (String) request.getAttribute("secret");
                    String timeStamp = (String) request.getAttribute("timeStamp");
                    String mType = (String) request.getAttribute("mType");
                    String signType = (String) request.getAttribute("signType");

                    return orderInfoController.modifyOrder(orderId, syncStatus,
                            memberId,
                            orderStatus,
                            payStatus,
                            shipStatus,
                            memberStatus,
                            isDelivery,
                            deliverMethodId,
                            deliverMethod,
                            deliverArea,
                            weight,
                            orderName,
                            itemNum,
                            actTime,
                            createTime,
                            createIP,
                            shipName,
                            shipArea,
                            shipAddr,
                            shipZip,
                            shipTel,
                            shipEmail,
                            shipTime,
                            shipMobile,
                            costItem,
                            isTax,
                            costTax,
                            taxCompany,
                            costFreight,
                            isProtect,
                            costProtect,
                            costPayment,
                            scoreU,
                            discount,
                            usePmt,
                            totalAmount,
                            finalAmount,
                            pmtAmount,
                            payed,
                            memo,
                            lastChangeTime,
                            customerId,
                            cashOnDly,
                            onlinePayType,
                            scoreUAmount,
                            payAgentId,
                            payAgentScore,
                            payAgentScoreAmount,
                            payAgentPayed,
                            hasPayed,
                            hasPayedScore,
                            onlineAmount,
                            hongbaoAmount,
                            payTime,
                            virtualRecMobile,
                            sign,
                            uCode,
                            secret,
                            timeStamp,
                            mType,
                            signType);
                }
                else if(baseEventClass == ProductInfoEvent.class)
                {
                }
                else
                {
                    return null;
                }
                return null;
            }

            @Override
            public Monitor<EventResult> handleException(Class<? extends ERPBaseEvent> baseEventClass, FailedBean failedBean) {

                if (baseEventClass == CreateOrderEvent.class) {


                }
                else if(baseEventClass == ObtainOrderEvent.class)
                {

                }
                else if(baseEventClass == DeliveryInfoEvent.class)
                {

                }
                else if(baseEventClass == InventoryEvent.class)
                {

                }
                else if(baseEventClass == OrderStatusInfoEvent.class)
                {

                }
                else if(baseEventClass == ProductInfoEvent.class)
                {

                }
                else
                {
                    return null;
                }
                return null;
            }
        };
    }
}
