package com.huobanplus.erpservice.transit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huobanplus.erpprovider.edb.bean.EDBSysData;
import com.huobanplus.erpprovider.edb.util.Constant;
import com.huobanplus.erpservice.SpringWebTest;
import com.huobanplus.erpservice.commons.config.ApplicationConfig;
import com.huobanplus.erpservice.commons.config.WebConfig;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import com.huobanplus.erpservice.datacenter.service.MallOrderService;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.transit.utils.DxDESCipher;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by allan on 2015/8/4.
 */
@ActiveProfiles("test")
@ContextConfiguration(classes = {ApplicationConfig.class, WebConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HotOrderControllerTest extends SpringWebTest {

    private ERPInfo mockERP;

    private MallOrderBean mockOrder;
    @Autowired
    private MallOrderService orderService;

    @Before
    public void setUp() throws Exception {
        mockERP = new ERPInfo();
        mockERP.setName("edb");
        mockERP.setType("mockType");
        mockERP.setValidation("mockValidation");
        EDBSysData sysData = new EDBSysData();
        sysData.setRequestUrl(Constant.REQUEST_URI);
        sysData.setDbHost(Constant.DB_HOST);
        sysData.setAppKey(Constant.APP_KEY);
        sysData.setAppSecret(Constant.APP_SECRET);
        sysData.setToken(Constant.TOKEN);
        sysData.setFormat(Constant.FORMAT);
        sysData.setV(Constant.V);
        sysData.setSlencry(Constant.SLENCRY);
        sysData.setIp(Constant.IP);
        ObjectMapper objectMapper = new ObjectMapper();

        mockERP.setSysDataJson(objectMapper.writeValueAsString(sysData));

        mockOrder = new MallOrderBean();
        mockOrder.setOutTid("123212322");
        mockOrder.setShopId("12");
        mockOrder.setStorageId("1");
        mockOrder.setExpress("dddd");
        mockOrder.setTidTime(new Date());
        mockOrder.setOrderId("123212322");
        mockOrder.setTid("123212322");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("123123");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("123212322");
        orderItem.setProNum(1);
        mockOrder.setOrderItems(Arrays.asList(orderItem));

        mockOrder = orderService.save(mockOrder);
    }

    @Test
    public void testCreateOrder() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOutTid("1232222132");
        orderInfo.setShopId("12");
        orderInfo.setStorageId("1");
        orderInfo.setExpress("dddd");
        orderInfo.setTidTime(new Date());
        orderInfo.setOrderId("1232222132");

        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setBarcode("22222");
        orderItem.setProName("方便面");
        orderItem.setSpecification("大碗");
        orderItem.setOutTid("1232222132");
        orderItem.setProNum(1);
        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());

        String sign = buildSign(signMap, signKey, null);
//        mockMvc.perform(post("/hotClientOrderApi/createOrder")
//                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
//                .param("name", DxDESCipher.encrypt(mockERP.getName()))
//                .param("type", DxDESCipher.encrypt(mockERP.getType()))
//                .param("validation", DxDESCipher.encrypt(mockERP.getValidation()))
//                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
//                .param("sign", sign))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("1"));
        mockMvc.perform(post("/hotClientOrderApi/createOrder")
                .param("name", "504256CF51D96457")
                .param("sysDataJson", "01B4951D2B7001A5602DEA08671BF0B2B305B78D12D18286C8D292965B0FAD248BF3605BCA06A68E6712DD995F550B322DA7AB6E7559DE97365CBFBD067B503D851CB305520FD285A58F3E1FC5C00B76B6089B6B786BF9B97E27A003D7BF53EDF0E614D0429838866A447D7873B76FB7DFED5E5EA3D4CAE6D49B08BA102CEAD729B34D232BA1E4A78CA69D43170B6B124A4B5AE6673C7DBE9C6072FD73626AAEBFF0784D8C986951B7C77CAE98714377062283231EB2017C")
                .param("sign", "affddab00bb58320066eaa9335050d82".toUpperCase())
                .param("type", "504256CF51D96457")
                .param("timestamp","1439286915")
                .param("validation", "2FABCB4AAF7BEA383CD23EDBAED3F164")
                .param("orderInfoJson", "%7b%22orderId%22%3anull%2c%22storageId%22%3anull%2c%22tid%22%3anull%2c%22transactionId%22%3anull%2c%22customerId%22%3anull%2c%22distributorId%22%3anull%2c%22shopName%22%3anull%2c%22outTid%22%3anull%2c%22orderType%22%3anull%2c%22outPayTid%22%3anull%2c%22voucherId%22%3anull%2c%22shopId%22%3anull%2c%22serialNum%22%3anull%2c%22orderChannel%22%3anull%2c%22orderFrom%22%3anull%2c%22buyerId%22%3anull%2c%22buyerName%22%3anull%2c%22type%22%3anull%2c%22status%22%3anull%2c%22abnormalStatus%22%3anull%2c%22mergeStatus%22%3anull%2c%22receiverName%22%3anull%2c%22alipayTransactionNo%22%3anull%2c%22receiverMobile%22%3anull%2c%22phone%22%3anull%2c%22province%22%3anull%2c%22city%22%3anull%2c%22district%22%3anull%2c%22address%22%3anull%2c%22post%22%3anull%2c%22email%22%3anull%2c%22isBill%22%3a0%2c%22invoiceName%22%3anull%2c%22invoiceSituation%22%3a0%2c%22invoiceTitle%22%3anull%2c%22invoiceType%22%3anull%2c%22invoiceContent%22%3anull%2c%22invoiceMoney%22%3a0.0%2c%22proTotalFee%22%3a0.0%2c%22orderTotalFee%22%3a0.0%2c%22referencePricePaid%22%3a0.0%2c%22invoiceFee%22%3a0.0%2c%22codFee%22%3anull%2c%22otherFee%22%3anull%2c%22refundTotalFee%22%3anull%2c%22discountFee%22%3a0.0%2c%22discount%22%3a0.0%2c%22channelDisfee%22%3anull%2c%22merchantDisFee%22%3anull%2c%22orderDisfee%22%3a0.0%2c%22commissionFee%22%3a0.0%2c%22isCod%22%3a0%2c%22pointPay%22%3a0%2c%22costPoint%22%3a0%2c%22point%22%3a0%2c%22superiorPoint%22%3a0%2c%22royaltyFee%22%3a0.0%2c%22externalPoint%22%3a0%2c%22expressNo%22%3anull%2c%22tradeGifadd%22%3anull%2c%22express%22%3anull%2c%22expressCoding%22%3anull%2c%22onlineExpress%22%3anull%2c%22sendingType%22%3anull%2c%22realIncomefreight%22%3a0.0%2c%22realPayFreight%22%3a0.0%2c%22grossWeight%22%3anull%2c%22grossWeightFreight%22%3a0.0%2c%22netWeightWreight%22%3anull%2c%22freightExplain%22%3anull%2c%22totalWeight%22%3a0.0%2c%22tidNetWeight%22%3a0.0%2c%22tidTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22payTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22getTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22orderCreater%22%3anull%2c%22businessMan%22%3anull%2c%22paymentReceivedOperator%22%3anull%2c%22paymentReceivedTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22reviewOrdersOperator%22%3anull%2c%22reviewOrdersTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22financeReviewOperator%22%3anull%2c%22financeReviewTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22advancePrinter%22%3anull%2c%22printer%22%3anull%2c%22printTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22isPrint%22%3anull%2c%22advDistributer%22%3anull%2c%22advDistributTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22distributer%22%3anull%2c%22distributTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22inspecter%22%3anull%2c%22inspectTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22cancelOperator%22%3anull%2c%22cancelTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22revokeCanceler%22%3anull%2c%22revokeCancelTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22packager%22%3anull%2c%22packTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22weighOperator%22%3anull%2c%22weighTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22bookDeliveryTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22deliveryOperator%22%3anull%2c%22deliveryTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22locker%22%3anull%2c%22lockTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22bookFileTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22fileOperator%22%3anull%2c%22fileTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22finishTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22modityTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22isPromotion%22%3anull%2c%22promotionPlan%22%3anull%2c%22outPromotionDetail%22%3anull%2c%22goodReceiveTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22receiveTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22verificatyTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22enableInteStoTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22enableInteDeliveryTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22alipayId%22%3anull%2c%22alipayStatus%22%3anull%2c%22payStatus%22%3anull%2c%22payMothed%22%3anull%2c%22platformStatus%22%3anull%2c%22rate%22%3anull%2c%22currency%22%3anull%2c%22deliveryStatus%22%3anull%2c%22buyerMessage%22%3anull%2c%22serviceRemarks%22%3anull%2c%22innerLable%22%3anull%2c%22distributorMark%22%3anull%2c%22systemRemarks%22%3anull%2c%22otherRemarks%22%3anull%2c%22message%22%3anull%2c%22messageTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22isStock%22%3anull%2c%22relatedOrders%22%3anull%2c%22relatedOrdersType%22%3anull%2c%22importMark%22%3anull%2c%22deliveryName%22%3anull%2c%22isNewCustomer%22%3anull%2c%22distributorLevel%22%3anull%2c%22codServiceFee%22%3a0.0%2c%22expressColFee%22%3anull%2c%22productNum%22%3anull%2c%22sku%22%3anull%2c%22itemNum%22%3a0%2c%22singleNum%22%3anull%2c%22flagColor%22%3anull%2c%22isFlag%22%3anull%2c%22taobaoDeliveryOrderStatus%22%3anull%2c%22taobaoDeliveryStatus%22%3anull%2c%22taobaoDeliveryMethod%22%3anull%2c%22orderProcessTime%22%3a0%2c%22isBreak%22%3anull%2c%22breaker%22%3anull%2c%22breakTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22breakExplain%22%3anull%2c%22platSendStatus%22%3anull%2c%22platType%22%3anull%2c%22isAdvSale%22%3a0%2c%22provincCode%22%3anull%2c%22cityCode%22%3anull%2c%22areaCode%22%3anull%2c%22lastReturnedTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22lastRefundTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22deliverCentre%22%3anull%2c%22deliverStation%22%3anull%2c%22isPreDeliveryNotice%22%3anull%2c%22jdDeliveryTime%22%3a%220001-01-01T00%3a00%3a00%22%2c%22sortingCode%22%3anull%2c%22codSettlementVouchernumber%22%3anull%7d"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testObtainOrder() throws Exception {
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/obtainOrder")
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign)
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderDeliver() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
        orderInfo.setDeliveryTime(new Date());
        orderInfo.setExpressNo("12323");
        orderInfo.setExpress("mockExpress");
        orderInfo.setGrossWeight("dd");
        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);

        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("type", mockERP.getType());
        signMap.put("validation", mockERP.getValidation());

        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderDeliver")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("sign", sign)
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testOrderUpdate() throws Exception {
        MallOrderBean orderInfo = new MallOrderBean();
        orderInfo.setOrderId(mockOrder.getOrderId());
        orderInfo.setTid(mockOrder.getOrderId());
        orderInfo.setDeliveryTime(new Date());
        orderInfo.setDistributTime(new Date());
        orderInfo.setPrintTime(new Date());
        orderInfo.setInspectTime(new Date());
        MallOrderItem orderItem = new MallOrderItem();
        orderItem.setId(mockOrder.getOrderItems().get(0).getId());
        orderItem.setTid(orderInfo.getOrderId());
        orderItem.setBarcode("1123123213");
        orderItem.setInspectionNum(1);
        orderInfo.setOrderItems(Arrays.asList(orderItem));

        String orderInfoJson = new ObjectMapper().writeValueAsString(orderInfo);
        Map<String, String> signMap = new TreeMap<>();
        signMap.put("orderInfoJson", orderInfoJson);
        signMap.put("type", mockERP.getType());
        signMap.put("name", mockERP.getName());
        signMap.put("sysDataJson", mockERP.getSysDataJson());
        signMap.put("validation", mockERP.getValidation());
        String sign = buildSign(signMap, signKey, null);

        mockMvc.perform(post("/hotClientOrderApi/orderUpdate")
                .param("orderInfoJson", URLEncoder.encode(orderInfoJson, "utf-8"))
                .param("type", DxDESCipher.encrypt(mockERP.getType()))
                .param("name", DxDESCipher.encrypt(mockERP.getName()))
                .param("sysDataJson", DxDESCipher.encrypt(mockERP.getSysDataJson()))
                .param("validation", DxDESCipher.encrypt(mockERP.getValidation()))
                .param("sign", sign))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testOrderStatusUpdate() throws Exception {

    }
}