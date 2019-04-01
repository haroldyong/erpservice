package com.huobanplus.erpprovider.lz;

import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpprovider.lz.util.RSA;
import com.huobanplus.erpservice.datacenter.model.GetTrackingInfo;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.push.GetTrackingInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestLzHandler extends  TestLzBase {
    private Log log = LogFactory.getLog(TestLzHandler.class);

    @Autowired
    private LzOrderHandler lzOrderHandler;

    @Test
    public void testPushPlatformOrder() {
        EventResult eventResult = lzOrderHandler.pushPlatformOrder(mockOrder, mockLzSysData);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }



    @Test
    public void canncelOrder() {
        OrderRefundStatusUpdate orderRefundStatusUpdate = new OrderRefundStatusUpdate();
        OrderRefundStatusInfo orderRefundStatusInfo = new OrderRefundStatusInfo();
        orderRefundStatusInfo.setOrderId(mockOrder.getOrderId());
        orderRefundStatusUpdate.setOrderRefundStatusInfo(orderRefundStatusInfo);
        orderRefundStatusUpdate.setErpInfo(mockErpInfo);
        orderRefundStatusUpdate.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = lzOrderHandler.pushRefund(orderRefundStatusUpdate);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void tracking() {
        GetTrackingInfoEvent getTrackingInfoEvent = new GetTrackingInfoEvent();
        GetTrackingInfo getTrackingInfo = new GetTrackingInfo();
        getTrackingInfo.setOrderId(mockOrder.getOrderId());
        getTrackingInfoEvent.setGetTrackingInfo(getTrackingInfo);
        getTrackingInfoEvent.setErpInfo(mockErpInfo);
        getTrackingInfoEvent.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = lzOrderHandler.tracking(getTrackingInfoEvent);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }


    @Test
    public void getRASPublicKey(){
        String[] pair= RSA.genRSAKeyPair();
        System.out.println("私钥:"+pair[0]);
        System.out.println("公钥:"+pair[1]);

    }
}
