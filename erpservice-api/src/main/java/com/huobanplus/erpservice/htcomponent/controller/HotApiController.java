package com.huobanplus.erpservice.htcomponent.controller;

import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPRegister;
import com.huobanplus.erpservice.event.model.ERPInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allan on 2015/7/31.
 */
@RequestMapping("/hotErpApi")
public interface HotApiController {

    /**
     * 得到商品库存信息
     *
     * @param info
     * @param request
     */
    @RequestMapping("/createOrder/{erpInfo}")
    void createOrder(@PathVariable("erpInfo") String info, HttpServletRequest request);

    /**
     * 得到商品库存信息
     *
     * @param erpInfo
     * @param request
     */
    @RequestMapping("/getProInventory")
    void getProInventory(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 得到订单信息
     *
     * @param erpInfo
     * @param request
     */
    @RequestMapping("/getOrderInfo")
    void getOrderInfo(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);
}
