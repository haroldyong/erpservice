package com.huobanplus.erpprovider.lz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lz.handler.LzOrderHandler;
import com.huobanplus.erpprovider.lz.util.RSA;
import com.huobanplus.erpservice.datacenter.model.GetTrackingInfo;
import com.huobanplus.erpservice.datacenter.model.OrderRefundStatusInfo;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.GetTrackingInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestLzHandler extends TestLzBase {
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
    public void getRASPublicKey() {
        String[] pair = RSA.genRSAKeyPair();
        System.out.println("私钥:" + pair[0]);
        System.out.println("公钥:" + pair[1]);

    }

    @Test
    public void t() {
        String x = "{\"success\":true,\"error_msg\":\"\",\"error_code\":\"200\",\"data\":{\"mailNo\":\"\",\"logisticsId\":\"77120197944947\",\"traces\":[{\"Status\":\"1\",\"SiteName\":null,\"PreOrNextStation\":null,\"OptMan\":\"郑全爽\",\"OptReason\":\"网点收件扫描\",\"OptSiteName\":\"唐山唐海\",\"OptDate\":\"2019-04-10 14:21:55\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":\"18633998719\",\"Country\":\"中国\",\"OptReasonEn\":\"Parcel scanned by site\",\"OptManPhone\":\"0315-8714288\"},{\"Status\":\"2\",\"SiteName\":null,\"PreOrNextStation\":\"唐山中心\",\"OptMan\":null,\"OptReason\":\"网点发件扫描\",\"OptSiteName\":\"唐山唐海\",\"OptDate\":\"2019-04-10 14:21:55\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":null,\"Country\":\"中国\",\"OptReasonEn\":\"Departure from inward office of exchange.\",\"OptManPhone\":\"0315-8714288\"},{\"Status\":\"3\",\"SiteName\":null,\"PreOrNextStation\":\"唐山唐海\",\"OptMan\":null,\"OptReason\":\"网点到件扫描\",\"OptSiteName\":\"唐山中心\",\"OptDate\":\"2019-04-11 05:30:26\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":null,\"Country\":\"中国\",\"OptReasonEn\":\"Arrival at inward office of exchange.\",\"OptManPhone\":\"0315-5273921、0315-5273922\"},{\"Status\":\"2\",\"SiteName\":null,\"PreOrNextStation\":\"唐山高新开发区\",\"OptMan\":null,\"OptReason\":\"网点发件扫描\",\"OptSiteName\":\"唐山中心\",\"OptDate\":\"2019-04-11 05:43:06\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":null,\"Country\":\"中国\",\"OptReasonEn\":\"Departure from inward office of exchange.\",\"OptManPhone\":\"0315-5273921、0315-5273922\"},{\"Status\":\"3\",\"SiteName\":null,\"PreOrNextStation\":\"唐山中心\",\"OptMan\":null,\"OptReason\":\"网点到件扫描\",\"OptSiteName\":\"唐山高新开发区\",\"OptDate\":\"2019-04-11 07:28:40\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":null,\"Country\":\"中国\",\"OptReasonEn\":\"Arrival at inward office of exchange.\",\"OptManPhone\":\"0315-6810288、0315-8998931\"},{\"Status\":\"4\",\"SiteName\":null,\"PreOrNextStation\":null,\"OptMan\":\"甄庆保\",\"OptReason\":\"网点派件扫描\",\"OptSiteName\":\"唐山高新开发区\",\"OptDate\":\"2019-04-11 11:29:21\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":\"15531504179\",\"Country\":\"中国\",\"OptReasonEn\":\"Physical delivery scheduled.\",\"OptManPhone\":\"0315-6810288、0315-8998931\"},{\"Status\":\"5\",\"SiteName\":null,\"PreOrNextStation\":null,\"OptMan\":null,\"OptReason\":\"订单已被客户签收\",\"OptSiteName\":\"唐山高新开发区\",\"OptDate\":\"2019-04-11 13:49:44\",\"OptCity\":\"唐山市\",\"Dest_Country\":\"中国\",\"Extend\":\"ZTO\",\"Emp_Phone\":null,\"Country\":\"中国\",\"OptReasonEn\":\"Delivered \",\"OptManPhone\":\"0315-6810288、0315-8998931\"}]}}";
        JSONObject jsonObject = JSON.parseObject(x);
        if ("true".equalsIgnoreCase(jsonObject.getString("success"))) {
            log.info(JSON.toJSONString(EventResult.resultWith(EventResultEnum.SUCCESS, "", jsonObject.getString("data"))));
        }
        else{

        }
    }

}
