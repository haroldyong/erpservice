/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.wangdianv2.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.wangdianv2.WangDianV2TestBase;
import com.huobanplus.erpprovider.wangdianv2.search.WangDianV2OrderSearch;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by wuxiongliu on 2016-11-02.
 */
public class WangDianV2OrderHandlerTest extends WangDianV2TestBase {

    @Autowired
    private WangDianV2OrderHandler wangDianV2OrderHandler;

    @Test
    public void test() {
        Assert.assertNotNull(wangDianV2OrderHandler);
    }

    @Test
    public void testPushOrder() {
        String orderJson = "{\"orderId\":\"20170302601442111410\",\"memberId\":256494,\"userLoginName\":\"15067134475\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"百胜测试商品(1)(×1)\",\"itemNum\":1,\"lastUpdateTime\":\"2017-03-02 14:26:00\",\"createTime\":\"2017-03-02 14:26:00\",\"shipName\":\"wxl\",\"shipArea\":\"北京/北京市/东城区\",\"province\":\"北京\",\"city\":\"北京市\",\"district\":\"东城区\",\"shipAddr\":\"北京北京市东城区四环五环六环\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15601235456\",\"costItem\":1.000,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":1.200,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"预付款\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2017-03-02 14:26:00\",\"unionOrderId\":\"20170302431327772814\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"wxl\",\"payNumber\":\"20170302648350598467\",\"taxAmount\":0.000,\"orderItems\":[{\"itemId\":177569,\"orderId\":\"20170302601442111418\",\"unionOrderId\":\"20170302431327772814\",\"productBn\":\"6920981442726\",\"name\":\"百胜测试商品(1)\",\"cost\":1.000,\"price\":1.000,\"amount\":1.000,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"6920981442726\",\"standard\":\"无规格\",\"brief\":null,\"shipStatus\":0,\"weight\":0.0,\"unit\":null}],\"errorMessage\":null}";

        orderJson = JSON.toJSONString(mockOrder);

        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        pushNewOrderEvent.setOrderInfoJson(orderJson);
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);

        EventResult eventResult = wangDianV2OrderHandler.pushOrder(pushNewOrderEvent);

        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }

    @Test
    public void testQueryOrder() {
        WangDianV2OrderSearch wangDianV2OrderSearch = new WangDianV2OrderSearch();
        Date beginTime = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusHours(3));
        Date endTime = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(LocalDateTime.now().minusMinutes(1));
        wangDianV2OrderSearch.setStartTime(StringUtil.DateFormat(beginTime, StringUtil.TIME_PATTERN));
        wangDianV2OrderSearch.setEndTime(StringUtil.DateFormat(endTime, StringUtil.TIME_PATTERN));
        wangDianV2OrderSearch.setPageNo(0);
//        wangDianV2OrderSearch.setWarehouseNo("api_test");
        wangDianV2OrderSearch.setPageSize(10);
//        wangDianV2OrderSearch.setTradeNo("JY201702270005");
        wangDianV2OrderSearch.setStatus(95);
        EventResult eventResult = wangDianV2OrderHandler.queryOrder(wangDianV2OrderSearch, mockWangDianV2SysData);
        System.out.println("\n**************request result*************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getData());
        System.out.println("\n**************request result*************");
    }

    @Test
    public void testDecode() throws UnsupportedEncodingException {
        String str = " \u672a\u4ed8\u6b3e\u8ba2\u5355\u4e0d\u53ef\u53d1\u8d27 ";
        String decodeStr = URLDecoder.decode(str, "utf-8");
        System.out.println(decodeStr);
    }

    @Test
    public void testLogisticsSyncQuery() throws Exception {
        wangDianV2OrderHandler.logisticsSyncQuery(mockWangDianV2SysData);
    }
}
