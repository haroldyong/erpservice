package com.huobanplus.erpservice.thirdparty.controller.impl;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.huobanplus.erpservice.commons.utils.ResponseCode;
import com.huobanplus.erpservice.commons.utils.SecurityUtils;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.erpevent.CreateOrderEvent;
import com.huobanplus.erpservice.event.erpevent.ObtainOrderEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.thirdparty.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by allan on 2015/8/3.
 */
@Controller
public class OrderControllerImpl implements OrderController {
    @Autowired
    private MallOrderService orderService;
    @Autowired
    private ERPRegister erpRegister;

    @Override
    @ResponseBody
    @RequestMapping(value = "/commitOrderInfo", method = RequestMethod.POST)
    public Map createOrder(String orderId, @RequestParam(value = "syncStatus",
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
                           String erpName) {
        Map<String, String> resultMap;
        MallOrderBean order;
        if (!SecurityUtils.getInstance().validateSign(sign, appKey, operation, capCode, timeStamp)) {
            resultMap = new HashMap<String, String>();
            resultMap.put("resut_message", "sign验证失败");
            resultMap.put("resut_code", ResponseCode.SING_ERROR);
            return resultMap;
        } else {
            order = new MallOrderBean();
            order.setOrderId(orderId);
            order.setSyncStatus(syncStatus);
            order.setMemberId(memberId);
            order.setOrderStatus(orderStatus);
            order.setPayStatus(payStatus);
            order.setShipStatus(shipStatus);
            order.setMemberStatus(memberStatus);
            order.setIsDelivery(isDelivery);
            order.setDeliverMethodId(deliverMethodId);
            order.setDeliverMethod(deliverMethod);
            order.setDeliverArea(deliverArea);
            order.setWeight(weight);
            order.setOrderName(orderName);
            order.setItemNum(itemNum);
            order.setActTime(new Date(actTime));
            order.setCreateTime(new Date(createTime));
            order.setCreateIP(createIP);
            order.setShipName(shipName);
            order.setShipArea(shipArea);
            order.setShipAddr(shipAddr);
            order.setShipZip(shipZip);
            order.setShipTel(shipTel);
            order.setShipEmail(shipEmail);
            order.setShipTime(shipTime);
            order.setShipMobile(shipMobile);
            order.setCostItem(costItem);
            order.setIsTax(isTax);
            order.setCostTax(costTax);
            order.setTaxCompany(taxCompany);
            order.setCostFreight(costFreight);
            order.setIsProtect(isProtect);
            order.setCostProtect(costProtect);
            order.setCostPayment(costPayment);
            order.setScoreU(scoreU);
            order.setDiscount(discount);
            order.setUsePmt(usePmt);
            order.setTotalAmount(totalAmount);
            order.setFinalAmount(finalAmount);
            order.setPmtAmount(pmtAmount);
            order.setPayed(payed);
            order.setMemo(memo);
            order.setLastChangeTime(new Date(lastChangeTime));
            order.setCustomerId(customerId);
            order.setCashOnDly(cashOnDly);
            order.setOnlinePayType(onlinePayType);
            order.setScoreUAmount(scoreUAmount);
            order.setPayAgentId(payAgentId);
            order.setPayAgentScore(payAgentScore);
            order.setPayAgentScoreAmount(payAgentScoreAmount);
            order.setPayAgentPayed(payAgentPayed);
            order.setHasPayed(hasPayed);
            order.setHasPayedScore(hasPayedScore);
            order.setOnlineAmount(onlineAmount);
            order.setHongbaoAmount(hongbaoAmount);
            order.setPayTime(new Date(payTime));
            order.setVirtualRecMobile(virtualRecMobile);
            order = orderService.save(order);
            if (null == order) {

                //插入数据失败
                resultMap = new HashMap<String, String>();
                resultMap.put("resut_message", "订单数据保存失败");
                resultMap.put("resut_code", ResponseCode.DATABASE_ERROR);
                return resultMap;
            } else {
                if (null == erpName || !"".equals(erpName.trim())) {
                    //erp信息错误
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "ERP信息出错");
                    resultMap.put("resut_code", ResponseCode.ERP_INFO_ERROR);
                    return resultMap;
                } else {
                    ERPInfo info = new ERPInfo();
                    info.setName(erpName);
                    //处理edb模块
                    ERPHandler edbHandler = erpRegister.getERPHandler(info);
                    if (null != edbHandler) {
                        if (edbHandler.eventSupported(CreateOrderEvent.class)) {
//                            try {
//                                edbHandler.handleEvent(CreateOrderEvent.class,new Orde);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (IllegalAccessException e) {
//                                e.printStackTrace();
//                            } catch (DocumentException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }

                    //返回操作成功结果
                    resultMap = new HashMap<String, String>();
                    resultMap.put("resut_message", "订单接收成功");
                    resultMap.put("resut_code", ResponseCode.SUCCESS);
                    return resultMap;
                }
            }

        }
    }

    @Override
    @RequestMapping(value = "/obtainOrder", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult obtainOrder(String erpName) {
        BaseResult baseResult = new BaseResult();
        ERPInfo erpInfo = new ERPInfo();
        erpInfo.setName(erpName);
        ERPHandler erpHandler = erpRegister.getERPHandler(erpInfo);
        if (erpHandler != null) {
            if (erpHandler.eventSupported(ObtainOrderEvent.class)) {
                try {
                    Monitor<EventResult> resultMonitor = erpHandler.handleEvent(ObtainOrderEvent.class, null);
                    EventResult eventResult = resultMonitor.get();
                    baseResult.setResultMsg(eventResult.getSystemResult());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return baseResult;
    }

}
