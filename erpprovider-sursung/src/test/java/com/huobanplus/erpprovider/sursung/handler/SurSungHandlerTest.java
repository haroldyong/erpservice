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
import com.huobanplus.erpprovider.sursung.formatdata.SurSungInventory;
import com.huobanplus.erpprovider.sursung.formatdata.SurSungLogistic;
import com.huobanplus.erpprovider.sursung.search.SurSungLogisticSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearch;
import com.huobanplus.erpprovider.sursung.search.SurSungOrderSearchResult;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil2;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.model.ProReturnInfo;
import com.huobanplus.erpservice.datacenter.model.ReturnInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncChannelOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-08-30.
 */
public class SurSungHandlerTest extends SurSungTestBase {

    private String orderInfoJson = "{\"payNumber\":\"123456\",\"orderId\":\"2016090928627540\",\"memberId\":17423,\"userLoginName\":\"15868807873\",\"confirm\":1,\"orderStatus\":0,\"payStatus\":1,\"shipStatus\":0,\"weight\":0.000,\"orderName\":\"???????(??,42?)(1)(?1)\",\"itemNum\":1,\"lastUpdateTime\":\"2016-09-09 15:15:55\",\"createTime\":\"2016-09-09 15:15:55\",\"shipName\":\"???\",\"shipArea\":\"???/???/???\",\"province\":\"???\",\"city\":\"???\",\"district\":\"???\",\"shipAddr\":\"????????????????????e?\",\"shipZip\":\"\",\"shipTel\":\"\",\"shipEmail\":\"\",\"shipMobile\":\"15868807873\",\"costItem\":125.000,\"onlinePayAmount\":0.00,\"costFreight\":0.000,\"currency\":\"CNY\",\"finalAmount\":125.000,\"pmtAmount\":0.000,\"memo\":\"\",\"remark\":\"\",\"printStatus\":0,\"paymentName\":\"???\",\"payType\":700,\"customerId\":296,\"supplierId\":0,\"logiName\":null,\"logiNo\":null,\"logiCode\":null,\"payTime\":\"2016-09-09 15:15:55\",\"unionOrderId\":\"2016090966611538\",\"receiveStatus\":0,\"isTax\":0,\"taxCompany\":\"\",\"buyerPid\":\"362322199411050053\",\"buyerName\":\"???\",\"orderItems\":[{\"itemId\":14720,\"orderId\":\"2016090928627549\",\"unionOrderId\":\"2016090966611538\",\"productBn\":\"296pfsNHNko-1\",\"name\":\"???????(??,42?)(1)\",\"cost\":100.000,\"price\":125.000,\"amount\":125.000,\"num\":1,\"sendNum\":0,\"refundNum\":0,\"supplierId\":0,\"customerId\":296,\"goodBn\":\"123456goodsbn\",\"standard\":\"??,42?\",\"brief\":null,\"shipStatus\":0}]}";

    @Autowired
    private SurSungOrderHandler surSungOrderHandler;

    @Test
    public void testPushOrder() throws IOException {
//        Order order = JSON.parseObject(orderInfoJson, Order.class);
//        HttpClientUtil2.getInstance().initHttpClient();
        for (int i = 0; i < 1; i++) {
            String orderNo = SerialNo.create();
            mockOrder.setOrderId(orderNo);
            System.out.println("\norderNo:" + orderNo);
            for (OrderItem orderItem : mockOrder.getOrderItems()) {
                orderItem.setProductBn("BN-" + i);
            }

            String orderInfoJson = JSON.toJSONString(mockOrder);
            PushNewOrderEvent pushNewOrderEvent = new PushNewOrderEvent();
            pushNewOrderEvent.setOrderInfoJson(orderInfoJson);
            pushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
            pushNewOrderEvent.setErpInfo(mockErpInfo);
            EventResult eventResult = surSungOrderHandler.pushOrder(pushNewOrderEvent);
            System.out.println(eventResult.getResultCode());
            System.out.println(eventResult.getData());
            System.out.println(eventResult.getResultMsg());
        }
//        HttpClientUtil2.getInstance().close();
    }

    @Test
    public void testLogisticSearch() {
        SurSungLogisticSearch surSungLogisticSearch = new SurSungLogisticSearch();
        EventResult eventResult = surSungOrderHandler.logisticSearch(surSungLogisticSearch, mockSurSungSysData);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testReturnRefund() {

//        List<SurSungReturnRefundItem> surSungReturnRefundItems = new ArrayList<>();
//        SurSungReturnRefundItem surSungReturnRefundItem = new SurSungReturnRefundItem();
//        surSungReturnRefundItem.setOuterOiId("0987654321");
//        surSungReturnRefundItem.setSkuId("123");
//        surSungReturnRefundItem.setQty(2);
//        surSungReturnRefundItem.setAmount(50);
//        surSungReturnRefundItem.setType("退货");
//        surSungReturnRefundItem.setName("奶粉");
//        surSungReturnRefundItem.setPropertiesValue("红色");
//        surSungReturnRefundItems.add(surSungReturnRefundItem);
//
//        SurSungReturnRefund surSungReturnRefund = new SurSungReturnRefund();
//        surSungReturnRefund.setShopId(14670);
//        surSungReturnRefund.setOuterAsId("1234567890");
//        surSungReturnRefund.setSoId("20160908145050387283");
//        surSungReturnRefund.setType("普通退货");
//        surSungReturnRefund.setLogiCompany("顺丰快递");
//        surSungReturnRefund.setLogiNo("12345logino");
//        surSungReturnRefund.setShopStatus("WAIT_SELLER_AGREE");
//        surSungReturnRefund.setRemark("不喜欢");
//        surSungReturnRefund.setGoodStatus("BUYER_RETURNED_GOODS");
//        surSungReturnRefund.setQuestionType("测试");
//        surSungReturnRefund.setTotalAmount(100);
//        surSungReturnRefund.setRefund(100);
//        surSungReturnRefund.setPayment(0);
//        surSungReturnRefund.setItems(surSungReturnRefundItems);

        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setOrderId("20161110194809568103");
        returnInfo.setReason("test");
        returnInfo.setLogiName("顺丰快递");
        returnInfo.setLogiNo("12345logino");
        returnInfo.setReturnAddr("浙江省杭州市滨江区");
        returnInfo.setReturnMobile("15061745623");
        returnInfo.setReturnName("wuxiongliu");
        returnInfo.setReturnZip("334600");
        returnInfo.setFreight(20);
        returnInfo.setRemark("测试");
        returnInfo.setReturnItemStr("fasfas|fasfas|sfas");
        returnInfo.setUserLoginName("wuxionglou");

        List<ProReturnInfo> proReturnInfos = new ArrayList<>();
        ProReturnInfo proReturnInfo = new ProReturnInfo();
        proReturnInfo.setGoodBn("goodsBn");
        proReturnInfo.setProductBn("123");
        proReturnInfo.setReturnNum(2);
        proReturnInfo.setPrice(102);
        proReturnInfos.add(proReturnInfo);

        returnInfo.setProReturnInfoList(proReturnInfos);

        System.out.println("\n*********");
        System.out.println(JSON.toJSONString(returnInfo));
        System.out.println("\n*********");


//        PushReturnInfoEvent pushReturnInfoEvent = new PushReturnInfoEvent();
//        pushReturnInfoEvent.setErpInfo(mockErpInfo);
//        pushReturnInfoEvent.setErpUserInfo(mockErpUserInfo);
//        pushReturnInfoEvent.setReturnInfo(returnInfo);
//
//        EventResult eventResult = surSungOrderHandler.returnRefundUpload(pushReturnInfoEvent);
//        System.out.println(eventResult.getResultCode());
//        System.out.println(eventResult.getData());
//        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testLogisticsUpload() {
        String postBody = "{\"o_id\":\"307477\",\"l_id\":\"52301250\",\"so_id\":\"2016090928627540\",\"logistics_company\":\"????\",\"send_date\":\"2016-09-10 09:33:15\",\"items\":[{\"sku_id\":\"296pfsNHNko-1\",\"qty\":\"1\",\"name\":\"???????(??,42?)(1)\",\"so_id\":\"2016090928627540\"}]}";
        SurSungLogistic surSungLogistic = JSON.parseObject(postBody, SurSungLogistic.class);
        EventResult eventResult = surSungOrderHandler.logisticUpload(surSungLogistic, mockErpUserInfo, mockErpInfo);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testInventoryUpload() {
        String postBody = "[{\"id\":0,\"shop_id\":0,\"sku_id\":\"123456\",\"qty\":1100,\"shop_sku_id\":\"KE-YRC16012012\",\"i_id\":null,\"shop_i_id\":null},{\"id\":0,\"shop_id\":0,\"sku_id\":\"KE-YRC16015012\",\"qty\":2100,\"shop_sku_id\":\"KE-YRC16015012\",\"i_id\":null,\"shop_i_id\":null}]";
        List<SurSungInventory> surSungInventoryList = JSON.parseArray(postBody, SurSungInventory.class);
        EventResult eventResult = surSungOrderHandler.inventoryUpload(surSungInventoryList, mockErpUserInfo, mockErpInfo);
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testQueryOrder() throws IOException {
        HttpClientUtil2.getInstance().initHttpClient();
        SurSungOrderSearch surSungOrderSearch = new SurSungOrderSearch();
        surSungOrderSearch.setPageIndex(1);
        surSungOrderSearch.setPageSize(10);
//        surSungOrderSearch.setShopId(14670);
        surSungOrderSearch.setModifiedBegin("2016-10-05");
        surSungOrderSearch.setModifiedEnd("2016-10-11");
        surSungOrderSearch.setFlds("*");
        EventResult eventResult = surSungOrderHandler.queryChannelOrder(surSungOrderSearch, mockSurSungSysData);

        System.out.println("*********************Data*********************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        SurSungOrderSearchResult resultData = (SurSungOrderSearchResult) eventResult.getData();
        System.out.println(resultData.getDataCount());
        System.out.println(resultData.getOrders());
        System.out.println("*********************Data*********************");

        HttpClientUtil2.getInstance().close();

    }

    @Test
    public void testCancelOrder() {
        CancelOrderEvent cancelOrderEvent = new CancelOrderEvent();
        cancelOrderEvent.setOrderId("20161110194809568103");
        cancelOrderEvent.setErpInfo(mockErpInfo);
        cancelOrderEvent.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = surSungOrderHandler.cancelOrder(cancelOrderEvent);

        System.out.println("*********************Data*********************");
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getResultMsg());
        System.out.println("*********************Data*********************");
    }

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private ERPRegister erpRegister;

    @Test
    public void testSync() {
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SURSUNG);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            try {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                SurSungSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SurSungSysData.class);


                SyncChannelOrderEvent syncChannelOrderEvent = new SyncChannelOrderEvent();
                syncChannelOrderEvent.setErpInfo(erpInfo);
                syncChannelOrderEvent.setErpUserInfo(erpUserInfo);
                List<Order> orderList = new ArrayList<>();
                orderList.add(mockOrder);
                syncChannelOrderEvent.setOrderList(orderList);
                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                // 推送至平台
                EventResult firstSyncEvent = erpUserHandler.handleEvent(syncChannelOrderEvent);
                System.out.println(firstSyncEvent.getResultCode());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
