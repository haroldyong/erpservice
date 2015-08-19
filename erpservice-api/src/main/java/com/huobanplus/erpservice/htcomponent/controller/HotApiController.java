package com.huobanplus.erpservice.htcomponent.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * api处理方法接口
 */
@RequestMapping("/hotErpApi")
public interface HotApiController {

    /**
     * 写入订单信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/createOrder/{erpInfo}")
    void createOrder(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 获取商品库存信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/getProInventory/{erpInfo}")
    void getProInventory(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 获取订单信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/getOrderInfo/{erpInfo}")
    void getOrderInfo(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 修改订单信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/modifyOrderInfo/{erpInfo}")
    void modifyOrderInfo(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 获取商品信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/obtainProductInfo/{erpInfo}")
    void obtainProductInfo(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);

    /**
     * 修改物流信息
     * @param erpInfo erp信息
     * @param request 请求实体
     */
    @RequestMapping("/modifyDeliveryInfo/{erpInfo}")
    void modifyDeliveryInfo(@PathVariable("erpInfo") String erpInfo, HttpServletRequest request);
}