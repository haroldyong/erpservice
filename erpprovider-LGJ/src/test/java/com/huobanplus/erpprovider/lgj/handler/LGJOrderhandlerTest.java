/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.lgj.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.lgj.LGJTestBase;
import com.huobanplus.erpprovider.lgj.common.LGJSysData;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/4/29.
 */
public class LGJOrderhandlerTest extends LGJTestBase {

    @Autowired
    private LGJOrderHandler lgjOrderHandler;


    private Order mockOrder;
    private ERPUserInfo mockErpUserInfo;
    private ERPInfo mockERP;
    private LGJSysData mockSysData;
    private PushNewOrderEvent mockPushNewOrderEvent;
    private List<OrderItem> mockOrderItemList;


    @Before
    public void setUp(){
        mockSysData = new LGJSysData();
        mockSysData.setHost("http://www.liguanjia.com/index.php/api");
        mockSysData.setUsername("ligoukeji");
        mockSysData.setPassword("111111");
        mockSysData.setApiName("ligoukeji");
        mockSysData.setApiSecret("111111");

        mockERP = new ERPInfo();
//        mockERP.setErpType(ERPTypeEnum.ProviderType.LGJ);
        mockERP.setSysDataJson(JSON.toJSONString(mockSysData));


        mockOrder = new Order();
        mockOrder.setOrderId("1019");
        mockOrder.setShipName("小李");
        mockOrder.setShipMobile("15623235656");
        mockOrder.setCity("杭州");
        mockOrder.setShipZip("254565");
        mockOrder.setShipArea("15/854/2430");
        mockOrder.setShipAddr("杭州市滨江区明月江南三栋1号");

        mockOrderItemList = new ArrayList<OrderItem>();

        for (int i = 0; i < 2; i++) {
            OrderItem mockOrderItem = new OrderItem();
            mockOrderItem.setItemId(i);
            mockOrderItem.setNum(i + 5);
            mockOrderItemList.add(mockOrderItem);
        }
        mockOrder.setOrderItems(mockOrderItemList);

        mockErpUserInfo = new ERPUserInfo();

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setErpInfo(mockERP);
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
    }

    @Test
    public void TestpushOrder(){
        EventResult eventResult = lgjOrderHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(eventResult);
        Assert.assertTrue(eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode());
    }



    @Test
    public void TestGetStr(){


//        Map<String, Object> requestData = new HashMap<>();
//        requestData.put("param","{\"func\":\"GetTokenSafeCode\",\"username\":\"ligoukeji\",\"password\":\"111111\",\"api_name\":\"ligoukeji\",\"api_secret\":\"111111\"}");
//  //      HttpResult httpResult = HttpClientUtil.getInstance().post("http://www.liguanjia.com/index.php/api", requestData);
//
//        System.out.println("-----------------"+String.valueOf(requestData.get("param")));

        Map<String, Object> requestData2 = new HashMap<>();
        Map<String, Object> b = new HashMap<>();
        requestData2.put("func","GetTokenSafeCode");
        requestData2.put("username","ligoukeji");
        requestData2.put("password","111111");
        requestData2.put("api_name","ligoukeji");
        requestData2.put("api_secret","111111");
        String str = JSON.toJSONString(requestData2);
        b.put("param",str);
        System.out.println("-----------------"+String.valueOf(b.get("param")));
        HttpResult httpResult = HttpClientUtil.getInstance().post("http://www.liguanjia.com/index.php/api", b);

        if (httpResult.getHttpStatus() == HttpStatus.SC_OK) {
            JSONObject result = JSON.parseObject(httpResult.getHttpContent());
           String s= result.getString("safecode");
            System.out.println(s);

        }
  //      System.out.println(httpResult);

    }
}
