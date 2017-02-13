/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpprovider.baison.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.baison.BaisonTestBase;
import com.huobanplus.erpprovider.baison.formatdata.BaisonQueryOrder;
import com.huobanplus.erpprovider.baison.formatdata.BaisonQueryOrderItem;
import com.huobanplus.erpprovider.baison.search.BaisonOrderSearch;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-11-10.
 */
public class BaisonOrderHandlerTest extends BaisonTestBase {

    @Autowired
    private BaisonOrderHandler baisonOrderHandler;

    @Test
    public void testPushOrder() {

        String orderJson = "{\"orderId\":\"20170213181872570356\",\"memberId\":256494,\"userLoginName\":\"15067134475\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"百胜测试商品(蓝色,38码)(4)(×4)\",\"itemNum\":4,\"lastUpdateTime\":\"2017-02-13 14:50:17\",\"createTime\":\"2017-02-13 14:50:17\",\"shipName\":\"wxl\",\"shipArea\":\"北京/北京市/东城区\",\"province\":\"北京\",\"city\":\"北京市\",\"district\":\"东城区\",\"shipAddr\":\"北京北京市东城区四环五环六环\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15601235456\",\"costItem\":4.000,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":4.000,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"预付款\",\"payType\":700,\"customerId\":296,\"supplierId\":17415,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2017-02-13 14:50:17\",\"unionOrderId\":\"20170213018236760855\",\"receiveStatus\":0,\"sourceShop\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"\",\"buyerName\":\"wxl\",\"payNumber\":null,\"taxAmount\":0.000,\"orderItems\":[{\"itemId\":177484,\"orderId\":\"20170213181872570356\",\"unionOrderId\":\"20170213018236760855\",\"productBn\":\"SKU-000-5\",\"name\":\"百胜测试商品(蓝色,38码)(4)\",\"cost\":1.000,\"price\":1.000,\"amount\":4.000,\"num\":4,\"sendNum\":0,\"refundNum\":0,\"supplierId\":17415,\"customerId\":296,\"goodBn\":\"HS-0000001\",\"standard\":\"蓝色,38码\",\"brief\":null,\"shipStatus\":0,\"weight\":0.0,\"unit\":null}],\"errorMessage\":null}";
        mockOrder.setOrderId("1111111111111111111");
        PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
        pushNewOrderEvent.setOrderInfoJson(orderJson);
        pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        pushNewOrderEvent.setErpInfo(mockErpInfo);
        EventResult eventResult = baisonOrderHandler.pushOrder(pushNewOrderEvent);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testOrderQuery() {
        BaisonOrderSearch baisonOrderSearch = new BaisonOrderSearch();
        baisonOrderSearch.setSdCode("07");
//        baisonOrderSearch.setOrderSn("1111111111111111111");
//        baisonOrderSearch.setStartModified("2015-12-30 08:00:00");
//        baisonOrderSearch.setEndModified(StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN));
        baisonOrderSearch.setAllowFields("all");
        baisonOrderSearch.setOrderSn("20170213004544470150");

        baisonOrderSearch.setPageNo(1);
        baisonOrderSearch.setPageSize(1000);

        System.out.println("\n******************");
        System.out.println(JSON.toJSONString(baisonOrderSearch));
        System.out.println();
        System.out.println("\n******************");

        EventResult eventResult = baisonOrderHandler.orderQuery(baisonOrderSearch, mockBaisonSysData);
        JSONObject jsonObject = (JSONObject) eventResult.getData();
        System.out.println(jsonObject);
        JSONArray orderJsonArray = jsonObject.getJSONArray("orderListGets");
        System.out.println(convert2OrderDeliveryInfo(orderJsonArray));
    }

    private List<OrderDeliveryInfo> convert2OrderDeliveryInfo(JSONArray baisonOrderArray) {

        List<OrderDeliveryInfo> orderDeliveryInfoList = new ArrayList<>();

        for (Object o : baisonOrderArray) {
            JSONObject obj = JSON.parseObject(o.toString());
            String shipStatus = obj.getJSONObject("orderListGet").getString("shipping_status");
            if (shipStatus.equals("7")) {// 已发货
                BaisonQueryOrder baisonQueryOrder = obj.getObject("orderListGet", BaisonQueryOrder.class);
                OrderDeliveryInfo orderDeliveryInfo = new OrderDeliveryInfo();
                orderDeliveryInfo.setOrderId(baisonQueryOrder.getOrderSn());
                orderDeliveryInfo.setLogiName(baisonQueryOrder.getShippingName());
                orderDeliveryInfo.setLogiNo(baisonQueryOrder.getShippingSn());
                orderDeliveryInfo.setLogiCode(baisonQueryOrder.getShippingCode());
                orderDeliveryInfo.setFreight(baisonQueryOrder.getShippingFee());

                StringBuilder deliverItemStr = new StringBuilder();

                for (JSONObject jsonObject : baisonQueryOrder.getOrderDetails()) {
                    BaisonQueryOrderItem baisonQueryOrderItem = jsonObject.getObject("orderDetailGet", BaisonQueryOrderItem.class);
                    deliverItemStr.append(baisonQueryOrderItem.getGoods_sn())
                            .append(",")
                            .append(baisonQueryOrderItem.getGoods_number())
                            .append("|");
                }
                orderDeliveryInfo.setDeliverItemsStr(deliverItemStr.toString());
                orderDeliveryInfoList.add(orderDeliveryInfo);

            }
        }
        return orderDeliveryInfoList;
    }
}
