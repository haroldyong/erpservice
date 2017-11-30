package com.huobanplus.test.gjbbc.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbbc.handler.GjbbcOrderHandler;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hxh on 2017-08-15.
 */
public class TestGjbbcHandler extends TestGjbbcBase {
    @Autowired
    private GjbbcOrderHandler gjbbcOrderHandler;
    private PushNewOrderEvent mockPushNewOrderEvent;

    /**
     * 高捷订单推送测试
     */
    @Test
    public void testPushPlatformOrder() {
        EventResult eventResult = gjbbcOrderHandler.pushPlatformOrder(mockOrder, mockGjbbcSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushOrderAliPay() {
        mockOrder.setOrderId("20161215608041164709");
        mockOrder.setPayNumber("2016121521001004570220367991");
        String orderJson = "{\"orderId\":\"20161215608041164709\",\"memberId\":256362,\"userLoginName\":\"S8AZBYFMDGZ\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"??app????app??(???,XXS)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-12-15 10:24:46\",\"createTime\":\"2016-12-15 10:22:20\",\"shipName\":\"???\",\"shipArea\":\"??/???/???\",\"province\":\"??\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"???????????\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15067134478\",\"costItem\":0.100,\"onlinePayAmount\":0.100,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.100,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":1,\"customerId\":3447,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-12-15 10:24:47\",\"unionOrderId\":\"20161215733517365701\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"payNumber\":\"20161215394589117200\",\"orderItems\":[{\"itemId\":176873,\"orderId\":\"20161215608041164709\",\"unionOrderId\":\"20161215733517365701\",\"productBn\":\"CSXJ0001\",\"name\":\"??app????app??(???,XXS)(1)\",\"cost\":0.100,\"price\":0.100,\"amount\":0.100,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":3447,\"goodBn\":\"1901101000\",\"standard\":\"???,XXS\",\"brief\":null,\"shipStatus\":0}],\"errorMessage\":null}";
        Order order = JSON.parseObject(orderJson, Order.class);
        order.setPayNumber("2016121521001004570220367991");
        EventResult eventResult = gjbbcOrderHandler.PushOrderAliPay(order, mockGjbbcSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testPushOrder() {

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        for (int i = 0; i < 10; i++) {
            EventResult result = gjbbcOrderHandler.pushOrder(mockPushNewOrderEvent);
            System.out.println(result.getData());
            System.out.println(result.getResultMsg());
            System.out.println(result.getResultCode());
        }
    }
}
