/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sursung.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sursung.SurSungTestBase;
import com.huobanplus.erpprovider.sursung.common.SurSungSysData;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungHandlerTest extends SurSungTestBase {

    private String orderInfoJson = "{\"orderId\":\"2016082966169443\",\"memberId\":17423,\"userLoginName\":\"15868807873\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":3500.000,\"orderName\":\"??????2(??,42?)(7)(?7)\",\"itemNum\":7,\"lastUpdateTime\":\"2016-08-29 17:09:49\",\"createTime\":\"2016-08-29 17:09:49\",\"shipName\":\"???\",\"shipArea\":\"???/???/???\",\"province\":\"???\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"zhe\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15868807873\",\"costItem\":0.700,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":0.700,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-08-29 17:09:49\",\"unionOrderId\":\"2016082976823811\",\"receiveStatus\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"orderItems\":[{\"itemId\":14701,\"orderId\":\"2016082966169442\",\"unionOrderId\":\"2016082976823811\",\"productBn\":\"CSXJ0005\",\"name\":\"??????2(??,42?)(7)\",\"cost\":5.000,\"price\":0.100,\"amount\":0.700,\"num\":7,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"0402210000\",\"standard\":\"??,42?\",\"brief\":null,\"shipStatus\":0}]}";

    protected ERPInfo mockErpInfo;

    protected ERPUserInfo mockErpUserInfo;

    private SurSungSysData surSungSysData;

    @Before
    public void mockData() {

        surSungSysData = new SurSungSysData();
        surSungSysData.setRequestUrl("http://b.sursung.com/api/open/query.aspx");
        surSungSysData.setPartnerId("ywv5jGT8ge6Pvlq3FZSPol345asd");
        surSungSysData.setPartnerKey("ywv5jGT8ge6Pvlq3FZSPol2323");
        surSungSysData.setVersion("1.0");
        surSungSysData.setToken("181ee8952a88f5a57db52587472c3798");
        surSungSysData.setShopId(14585);

        mockErpInfo = new ERPInfo();
        mockErpInfo = new ERPInfo();
        mockErpInfo.setErpType(ERPTypeEnum.ProviderType.SURSUNG);
        mockErpInfo.setSysDataJson(JSON.toJSONString(surSungSysData));

        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(296);
    }

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    @Test
    public void testPushOrder() {
        Order order = JSON.parseObject(orderInfoJson, Order.class);
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(orderInfoJson);
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);
        EventResult eventResult = surSungOrderHandler.pushOrder(pushNewOrderEvent);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
    }

    @Test
    public void testLogisticSearch() {
        SurSungLogisticSearch surSungLogisticSearch = new SurSungLogisticSearch();
        surSungSysData.setShopId(surSungSysData.getShopId());
        EventResult eventResult = surSungOrderHandler.logisticSearch(surSungLogisticSearch, surSungSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
    }

    @Test
    public void test() throws ParseException {
        System.out.println("\n" + new Date().getTime() / 1000);
        System.out.println("\n" + new Date().getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-mm-dd HH:mm:ss");
        Date date = simpleDateFormat.parse("2016-08-30 16:58:33");


    }


}
