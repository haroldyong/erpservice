package com.huobanplus.erpservice.transit.controller;

import handler.ERPRegister;
import model.EventResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/7/13.
 */
@Controller
@RequestMapping("/app")
public class ObtainDataController {

    private ERPRegister erpRegister;

    @ResponseBody
    @RequestMapping(value = "/commitOrderInfo", method = RequestMethod.POST)
    public EventResult obtainOrderInfo(String orderId, @RequestParam(value = "syncStatus",
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
                                       @RequestParam(value = "shipZip", required = false, defaultValue = "")  String shipZip,
                                       String shipTel,
                                       @RequestParam(value = "shipEmail", required = false, defaultValue = "")  String shipEmail,
                                       @RequestParam(value = "shipTime", required = false, defaultValue = "") String shipTime,
                                       String shipMobile,
                                       BigDecimal costItem,
                                       @RequestParam(value = "isTax", required = false, defaultValue = "0") int isTax,
                                       @RequestParam(value = "costTax", required = false, defaultValue = "0") BigDecimal costTax,
                                       @RequestParam(value = "taxCompany", required = false, defaultValue = "") String taxCompany,
                                       @RequestParam(value = "costFreight", required = false, defaultValue = "0") BigDecimal costFreight,
                                       @RequestParam(value = "isProtect", required = false, defaultValue = "0") int isProtect,
                                       @RequestParam(value = "costProtect", required = false, defaultValue = "0") BigDecimal costProtect,
                                       ) {

        //保存成功
        //

        return null;
    }


}
