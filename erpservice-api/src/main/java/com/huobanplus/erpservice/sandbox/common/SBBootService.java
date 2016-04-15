/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.sandbox.common;

import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPBaseConfigEntity;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;

/**
 * Created by allan on 12/10/15.
 */
@Component
@Getter
public class SBBootService {
    private List<Order> orders;
    private ERPBaseConfigEntity baseConfig;

    @PostConstruct
    public void onApplicationStart() throws ParseException {
        //模拟沙箱数据

        //配置数据
        if (baseConfig == null) {
            baseConfig = new ERPBaseConfigEntity();
            baseConfig.setAppKey(SBConstant.APP_KEY);
            baseConfig.setToken(SBConstant.TOKEN);
            baseConfig.setSecretKey(SBConstant.SECRET_KEY);
            baseConfig.setIsOpen(1);
            baseConfig.setCustomerId(1);
            baseConfig.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        }

        //模拟订单数据
        String mockShipName = "测试收货人";
        String mockShipArea = "测试收货区域";
        String mockShipAddr = "测试收货地址";
        String mockShipMobile = "16688889999";

        //订单:2015102899437158
//        MallOrderBean mockOrder1 = orderService.findByOrderId("2015102899437158");
//        if (mockOrder1 == null) {
//            mockOrder1 = new MallOrderBean();
//            mockOrder1.setOrderId("2015102899437158");
//            mockOrder1.setMemberId(1);
//            mockOrder1.setConfirm(1);
//            mockOrder1.setOrderStatus(OrderEnum.OrderStatus.ACTIVE);
//            mockOrder1.setPayStatus(OrderEnum.PayStatus.PAYED);
//            mockOrder1.setShipStatus(OrderEnum.ShipStatus.NOT_DELIVER);
//            mockOrder1.setOrderName("测试订单1");
//            mockOrder1.setItemNum(1);
//            mockOrder1.setLastUpdateTime(StringUtil.DateFormat("2015-11-11", StringUtil.DATE_PATTERN));
//            mockOrder1.setCreateTime(StringUtil.DateFormat("2015-11-10", StringUtil.DATE_PATTERN));
//            mockOrder1.setShipName(mockShipName);
//            mockOrder1.setShipArea(mockShipArea);
//            mockOrder1.setShipAddr(mockShipAddr);
//            mockOrder1.setShipMobile(mockShipMobile);
//            mockOrder1.setCostItem(100);
//            mockOrder1.setCostFreight(10);
//            mockOrder1.setFinalAmount(110);
//            mockOrder1.setPaymentName("支付宝");
//            mockOrder1.setCustomerId(1);
//            mockOrder1.setSupplierId(2);
//            mockOrder1.setPayTime(StringUtil.DateFormat("2015-11-10 09:00:00", StringUtil.TIME_PATTERN));
//            orderService.save(mockOrder1);
//
//            MallOrderItemBean mockItem1 = new MallOrderItemBean();
//            mockItem1.setItemId(1);
//            mockItem1.setOrderBean(mockOrder1);
//            mockItem1.setBn("1000000");
//            mockItem1.setName("测试商品");
//            mockItem1.setCost(80);
//            mockItem1.setPrice(90);
//            mockItem1.setAmount(100);
//            mockItem1.setNum(1);
//            mockItem1.setSupplierId(mockOrder1.getSupplierId());
//            mockItem1.setCustomerId(mockOrder1.getCustomerId());
//            mockItem1.setGoodBn("2000000");
//            mockItem1.setStandard("测试规格");
//            mockItem1.setBrief("测试简介");
//            orderItemService.save(mockItem1);
//        }
//        MallOrderBean mockOrder2 = orderService.findByOrderId("2015102899437159");
//        if (mockOrder2 == null) {
//            mockOrder2 = new MallOrderBean();
//            mockOrder2.setOrderId("2015102899437159");
//            mockOrder2.setMemberId(1);
//            mockOrder2.setConfirm(1);
//            mockOrder2.setOrderStatus(OrderEnum.OrderStatus.ACTIVE);
//            mockOrder2.setPayStatus(OrderEnum.PayStatus.PAYED);
//            mockOrder2.setShipStatus(OrderEnum.ShipStatus.DELIVERED);
//            mockOrder2.setOrderName("测试订单1");
//            mockOrder2.setItemNum(2);
//            mockOrder2.setLastUpdateTime(StringUtil.DateFormat("2015-10-11", StringUtil.DATE_PATTERN));
//            mockOrder2.setCreateTime(StringUtil.DateFormat("2015-10-10", StringUtil.DATE_PATTERN));
//            mockOrder2.setShipName(mockShipName);
//            mockOrder2.setShipArea(mockShipArea);
//            mockOrder2.setShipAddr(mockShipAddr);
//            mockOrder2.setShipMobile(mockShipMobile);
//            mockOrder2.setCostItem(200);
//            mockOrder2.setCostFreight(10);
//            mockOrder2.setFinalAmount(210);
//            mockOrder2.setPaymentName("支付宝");
//            mockOrder2.setCustomerId(1);
//            mockOrder2.setSupplierId(2);
//            mockOrder2.setPayTime(StringUtil.DateFormat("2015-10-10 09:00:00", StringUtil.TIME_PATTERN));
//            orderService.save(mockOrder2);
//
//            MallOrderItemBean mockItem2 = new MallOrderItemBean();
//            mockItem2.setItemId(1);
//            mockItem2.setOrderBean(mockOrder2);
//            mockItem2.setBn("1000001");
//            mockItem2.setName("测试商品");
//            mockItem2.setCost(80);
//            mockItem2.setPrice(90);
//            mockItem2.setAmount(100);
//            mockItem2.setNum(1);
//            mockItem2.setSendNum(1);
//            mockItem2.setSupplierId(mockOrder2.getSupplierId());
//            mockItem2.setCustomerId(mockOrder2.getCustomerId());
//            mockItem2.setGoodBn("2000001");
//            mockItem2.setStandard("测试规格");
//            mockItem2.setBrief("测试简介");
//            orderItemService.save(mockItem2);
//
//            MallOrderItemBean mockItem3 = new MallOrderItemBean();
//            mockItem3.setItemId(1);
//            mockItem3.setOrderBean(mockOrder2);
//            mockItem3.setBn("1000002");
//            mockItem3.setName("测试商品");
//            mockItem3.setCost(80);
//            mockItem3.setPrice(90);
//            mockItem3.setAmount(100);
//            mockItem3.setNum(1);
//            mockItem3.setSendNum(1);
//            mockItem3.setSupplierId(mockOrder2.getSupplierId());
//            mockItem3.setCustomerId(mockOrder2.getCustomerId());
//            mockItem3.setGoodBn("2000002");
//            mockItem3.setStandard("测试规格");
//            mockItem3.setBrief("测试简介");
//            orderItemService.save(mockItem3);
//        }
    }


}
