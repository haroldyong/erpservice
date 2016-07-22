package com.huobanplus.erpprovider.dtw.handler.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwOrder;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPersonalDelcareInfo;
import com.huobanplus.erpprovider.dtw.handler.DtwOrderHandler;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016/5/23.
 */
@Component
public class DtwOrderHandlerImpl implements DtwOrderHandler {

    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
//        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
//
//        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();
//        DtwSysData dtwSysData = JSON.parseObject(erpInfo.getSysDataJson(), DtwSysData.class);
//        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        Map<String,Object> requestMap = new HashMap<String,Object>();
        DtwOrder dtwOrder = new DtwOrder();
        dtwOrder.setPassKey("6442305C-5A31-43BB-B36D-C73FB1EE14EC");
        dtwOrder.setMsgid("1");

        requestMap.put("data",JSON.toJSONString(dtwOrder));


        HttpResult httpResult = HttpClientUtil.getInstance().post("http://logistics.dtw.com.cn:8080/QBT/api/QBIntegratedOrder",requestMap);


        return null;

    }

    @Override
    public EventResult personalInfoDeclare(DtwPersonalDelcareInfo dtwPersonalDelcareInfo, DtwSysData dtwSysData) {
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("data", JSON.toJSONString(dtwPersonalDelcareInfo));
        HttpResult httpResult = HttpClientUtil.getInstance().post(dtwSysData.getRequestUrl()+"/QBPresonal",requestMap);
        if(httpResult.getHttpStatus() == HttpStatus.SC_OK){
            System.out.println(httpResult);
        }
        return null;
    }
}
