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
import com.huobanplus.erpservice.datacenter.model.*;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.erpevent.push.CancelOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushAfterSaleEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncChannelOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

    @Rollback(value = false)
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

        AfterSaleInfo afterSaleInfo = new AfterSaleInfo();

        ReturnInfo returnInfo = new ReturnInfo();
        afterSaleInfo.setOrderId("20161110194644997344");
        afterSaleInfo.setRemark("test");
        afterSaleInfo.setLogiCompany("顺丰快递");
        afterSaleInfo.setLogiNo("12345logino");
        afterSaleInfo.setAfterStatus(5);
        afterSaleInfo.setAfterSaleId("20161110194644997355");
        afterSaleInfo.setRefund(110);
        afterSaleInfo.setTotalAmount(120);
        afterSaleInfo.setPayment(10);

        List<AfterSaleItem> afterSaleItems = new ArrayList<>();
        AfterSaleItem afterSaleItem = new AfterSaleItem();
        afterSaleItem.setSkuId("BN-1234");
        afterSaleItem.setAmount(10);
        afterSaleItem.setReturnNum(2);
        afterSaleItem.setType("其他");
        afterSaleItem.setOrderId("20161110194644997344");
        afterSaleItems.add(afterSaleItem);

        afterSaleInfo.setItems(afterSaleItems);

        System.out.println("\n*********");
        System.out.println(JSON.toJSONString(afterSaleInfo));
        System.out.println("\n*********");

        mockErpUserInfo.setCustomerId(7297);

//        PushReturnInfoEvent pushReturnInfoEvent = new PushReturnInfoEvent();
//        pushReturnInfoEvent.setErpInfo(mockErpInfo);
//        pushReturnInfoEvent.setErpUserInfo(mockErpUserInfo);
//        pushReturnInfoEvent.setReturnInfo(returnInfo);

        PushAfterSaleEvent pushAfterSaleEvent = new PushAfterSaleEvent();
        pushAfterSaleEvent.setErpInfo(mockErpInfo);
        pushAfterSaleEvent.setErpUserInfo(mockErpUserInfo);
        pushAfterSaleEvent.setAfterSaleInfo(JSON.toJSONString(afterSaleInfo));

        EventResult eventResult = surSungOrderHandler.returnRefundUpload(pushAfterSaleEvent);
        System.out.println(eventResult.getResultCode());
        System.out.println(eventResult.getData());
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testLogisticsUpload() {
        mockErpUserInfo.setCustomerId(7297);
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

                String orderJson = "{\"city\":\"常德市\",\"confirm\":1,\"costFreight\":0,\"costItem\":168,\"createTime\":\"2016-11-11 08:43:14\",\"customerId\":0,\"district\":\"武陵区\",\"finalAmount\":168,\"isTax\":0,\"itemNum\":3,\"memberId\":0,\"onlinePayAmount\":0,\"orderId\":\"2755982872419815\",\"orderItems\":[{\"amount\":178,\"cost\":0,\"customerId\":0,\"goodBn\":\"Y24003\",\"name\":\"【双11全球狂欢节】艺福堂安吉白茶 特级 茶叶 绿茶 安吉白茶2016新茶 珍稀白茶100g\",\"num\":2,\"orderId\":\"2755982872419815\",\"price\":216,\"productBn\":\"Y24003\",\"refundNum\":0,\"sendNum\":0,\"shipStatus\":0,\"standard\":\"100g/罐\",\"supplierId\":0},{\"amount\":0,\"cost\":0,\"customerId\":0,\"goodBn\":\"Y11025\",\"name\":\"艺福堂茶点绿茶方块酥200g/盒\",\"num\":1,\"orderId\":\"2755982872419815\",\"price\":0,\"productBn\":\"Y11025\",\"refundNum\":0,\"sendNum\":0,\"shipStatus\":0,\"standard\":\"200g/盒\",\"supplierId\":0},{\"amount\":0,\"cost\":0,\"customerId\":0,\"goodBn\":\"YZ01029\",\"name\":\"赠品--艺福堂体验装（福袋C袋）\",\"num\":1,\"orderId\":\"2755982872419815\",\"price\":0,\"productBn\":\"YZ01029\",\"refundNum\":0,\"sendNum\":0,\"shipStatus\":0,\"standard\":\"C袋\",\"supplierId\":0}],\"orderStatus\":0,\"payNumber\":\"2016111121001001820295951591\",\"payStatus\":1,\"payTime\":\"2016-11-11 08:43:28\",\"payType\":9,\"pmtAmount\":0,\"printStatus\":0,\"province\":\"湖南省\",\"receiveStatus\":0,\"shipAddr\":\"永安街道武陵区东宛小区（武陵区党校对面）\",\"shipArea\":\"湖南省/常德市/武陵区\",\"shipMobile\":\"13975618638\",\"shipName\":\"吴平安\",\"shipStatus\":0,\"sourceShop\":0,\"supplierId\":0,\"userLoginName\":\"wuqin166\",\"weight\":0}";
                Order order = JSON.parseObject(orderJson, Order.class);

                orderList.add(order);
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

    @Test
    public void testUrlDecode() throws UnsupportedEncodingException {
        String str = "%7B%22city%22%3A%22%E5%B8%B8%E5%BE%B7%E5%B8%82%22%2C%22confirm%22%3A1%2C%22costFreight%22%3A0%2C%22costItem%22%3A168%2C%22createTime%22%3A%222016-11-11+08%3A43%3A14%22%2C%22customerId%22%3A0%2C%22district%22%3A%22%E6%AD%A6%E9%99%B5%E5%8C%BA%22%2C%22finalAmount%22%3A168%2C%22isTax%22%3A0%2C%22itemNum%22%3A3%2C%22memberId%22%3A0%2C%22onlinePayAmount%22%3A0%2C%22orderId%22%3A%222755982872419815%22%2C%22orderItems%22%3A%5B%7B%22amount%22%3A178%2C%22cost%22%3A0%2C%22customerId%22%3A0%2C%22goodBn%22%3A%22Y24003%22%2C%22name%22%3A%22%E3%80%90%E5%8F%8C11%E5%85%A8%E7%90%83%E7%8B%82%E6%AC%A2%E8%8A%82%E3%80%91%E8%89%BA%E7%A6%8F%E5%A0%82%E5%AE%89%E5%90%89%E7%99%BD%E8%8C%B6+%E7%89%B9%E7%BA%A7+%E8%8C%B6%E5%8F%B6+%E7%BB%BF%E8%8C%B6+%E5%AE%89%E5%90%89%E7%99%BD%E8%8C%B62016%E6%96%B0%E8%8C%B6+%E7%8F%8D%E7%A8%80%E7%99%BD%E8%8C%B6100g%22%2C%22num%22%3A2%2C%22orderId%22%3A%222755982872419815%22%2C%22price%22%3A216%2C%22productBn%22%3A%22Y24003%22%2C%22refundNum%22%3A0%2C%22sendNum%22%3A0%2C%22shipStatus%22%3A0%2C%22standard%22%3A%22100g%2F%E7%BD%90%22%2C%22supplierId%22%3A0%7D%2C%7B%22amount%22%3A0%2C%22cost%22%3A0%2C%22customerId%22%3A0%2C%22goodBn%22%3A%22Y11025%22%2C%22name%22%3A%22%E8%89%BA%E7%A6%8F%E5%A0%82%E8%8C%B6%E7%82%B9%E7%BB%BF%E8%8C%B6%E6%96%B9%E5%9D%97%E9%85%A5200g%2F%E7%9B%92%22%2C%22num%22%3A1%2C%22orderId%22%3A%222755982872419815%22%2C%22price%22%3A0%2C%22productBn%22%3A%22Y11025%22%2C%22refundNum%22%3A0%2C%22sendNum%22%3A0%2C%22shipStatus%22%3A0%2C%22standard%22%3A%22200g%2F%E7%9B%92%22%2C%22supplierId%22%3A0%7D%2C%7B%22amount%22%3A0%2C%22cost%22%3A0%2C%22customerId%22%3A0%2C%22goodBn%22%3A%22YZ01029%22%2C%22name%22%3A%22%E8%B5%A0%E5%93%81--%E8%89%BA%E7%A6%8F%E5%A0%82%E4%BD%93%E9%AA%8C%E8%A3%85%EF%BC%88%E7%A6%8F%E8%A2%8BC%E8%A2%8B%EF%BC%89%22%2C%22num%22%3A1%2C%22orderId%22%3A%222755982872419815%22%2C%22price%22%3A0%2C%22productBn%22%3A%22YZ01029%22%2C%22refundNum%22%3A0%2C%22sendNum%22%3A0%2C%22shipStatus%22%3A0%2C%22standard%22%3A%22C%E8%A2%8B%22%2C%22supplierId%22%3A0%7D%5D%2C%22orderStatus%22%3A0%2C%22payNumber%22%3A%222016111121001001820295951591%22%2C%22payStatus%22%3A1%2C%22payTime%22%3A%222016-11-11+08%3A43%3A28%22%2C%22payType%22%3A9%2C%22pmtAmount%22%3A0%2C%22printStatus%22%3A0%2C%22province%22%3A%22%E6%B9%96%E5%8D%97%E7%9C%81%22%2C%22receiveStatus%22%3A0%2C%22shipAddr%22%3A%22%E6%B0%B8%E5%AE%89%E8%A1%97%E9%81%93%E6%AD%A6%E9%99%B5%E5%8C%BA%E4%B8%9C%E5%AE%9B%E5%B0%8F%E5%8C%BA%EF%BC%88%E6%AD%A6%E9%99%B5%E5%8C%BA%E5%85%9A%E6%A0%A1%E5%AF%B9%E9%9D%A2%EF%BC%89%22%2C%22shipArea%22%3A%22%E6%B9%96%E5%8D%97%E7%9C%81%2F%E5%B8%B8%E5%BE%B7%E5%B8%82%2F%E6%AD%A6%E9%99%B5%E5%8C%BA%22%2C%22shipMobile%22%3A%2213975618638%22%2C%22shipName%22%3A%22%E5%90%B4%E5%B9%B3%E5%AE%89%22%2C%22shipStatus%22%3A0%2C%22sourceShop%22%3A0%2C%22supplierId%22%3A0%2C%22userLoginName%22%3A%22wuqin166%22%2C%22weight%22%3A0%7D";
        String json = URLDecoder.decode(str, "utf-8");
        System.out.println("\n*************");
        System.out.println(json);
        System.out.println("\n*************");
    }

}
