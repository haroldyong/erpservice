package com.huobanplus.erpprovider.kaola.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huobanplus.erpprovider.kaola.KaoLaTestBase;
import com.huobanplus.erpprovider.kaola.common.KaoLaSysData;
import com.huobanplus.erpprovider.kaola.service.KaolaScheduledService;
import com.huobanplus.erpservice.common.httputil.HttpClientUtil;
import com.huobanplus.erpservice.common.httputil.HttpResult;
import com.huobanplus.erpservice.common.util.SignBuilder;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderDeliveryInfo;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.OrderStatusInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.OrderInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by wuxiongliu on 2016/5/10.
 */
public class KaoLaOrderInfoHandlerTest extends KaoLaTestBase{

    private KaoLaSysData mockKaoLaSysData;

    private OrderStatusInfoEvent mockOrderStatusInfoEvent;

    private PushNewOrderEvent mockPushNewOrderEvent;

    private OrderInfo mockOrderInfo;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    @Autowired
    private KaoLaOrderInfoHandler kaoLaOrderInfoHandler;

    @Autowired
    private KaolaScheduledService kaolaScheduledService;

    /**
     *  appkey: bb0b3ad64c9e5eb06c2fb6f163bf179e79051bd5c9b652fc45dc68a2b5dd23c6
     *  appkey zs : ff438be07823f990fc6eef7f1a3171d05512031e704803949b36fc13fc05b493
     *  appkey: 0dd1a2b29d6e4bfebce479450889b4b2
     *  secretKey: 4ed8b056c32939b9fd66987470b3e9fb720bdded02197e678e516bdcdf810833
     *  secretKey: 3cf1a3ed8556444bbd1fbd8b9381c8bb
     *  secretKey zs: ea2358dda586ed69e51812d0e6e107af5f3c741d5cde7c14e97f265eba9ebcdc
     */

    @Before
    public void setUp(){
        String appKey = "0dd1a2b29d6e4bfebce479450889b4b2";
        String secretKey = "3cf1a3ed8556444bbd1fbd8b9381c8bb";

        mockKaoLaSysData = new KaoLaSysData();
        mockKaoLaSysData.setAppKey(appKey);
        mockKaoLaSysData.setAppSecret(secretKey);
        mockKaoLaSysData.setRequestUrl("http://223.252.220.85/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api
        mockKaoLaSysData.setV("1.0");
        mockKaoLaSysData.setChannelId(1200L);


        mockOrderInfo = new OrderInfo();
        mockOrderInfo.setOrderCode("25874125852656s565d22");
        mockOrderInfo.setOrderChannel("1200");
        mockOrderInfo.setPayTime(new Date());


        mockOrderItems = new ArrayList<>();

        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setNum(5);
        mockOrderItem.setOrderId("8070");
        mockOrderItem.setProductBn("10405-68a3e5516d7a7dc21fbe0e7ee13bfc1c");

        OrderItem mockOrderItem2 = new OrderItem();
        mockOrderItem2.setNum(5);
        mockOrderItem2.setOrderId("8071");
        mockOrderItem2.setProductBn("10407-68a3e5516d7a7dc21fbe0e7ee13bfc1c");



        mockOrderItems.add(mockOrderItem);
        mockOrderItems.add(mockOrderItem2);


        mockOrder = new Order();
        mockOrder.setOrderId("0003");
        mockOrder.setMemberId(1);
        mockOrder.setShipName("吴雄琉");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setShipEmail("xiong328160186@qq.com");
        mockOrder.setProvince("zhejiang");
        mockOrder.setCity("hangzhou");
        mockOrder.setDistrict("binjiang");
        mockOrder.setShipAddr("zhihuiegu");
        mockOrder.setBuyerPid("3623221994110580053");

        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);


        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockKaoLaSysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);


    }

    @Test
    public void testQueryOrderInfo(){

        List<Order> orderList = new ArrayList<>();
        orderList.add(mockOrder);

        mockOrderStatusInfoEvent = new OrderStatusInfoEvent();
        mockOrderStatusInfoEvent.setOrderInfo(mockOrderInfo);
        mockOrderStatusInfoEvent.setErpInfo(mockErpInfo);
        EventResult eventResult = kaoLaOrderInfoHandler.queryOrderStatusInfo(orderList,mockKaoLaSysData);
        List<OrderDeliveryInfo> deliveryInfoList = (List<OrderDeliveryInfo>) eventResult.getData();
        deliveryInfoList.forEach(deliveryInfo -> {
            System.out.println(deliveryInfo);
        });
        System.out.println(eventResult.getResultMsg());
        System.out.println(eventResult.getResultCode());
    }

    @Test
    public void testPushOrder(){

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);

        EventResult eventResult = kaoLaOrderInfoHandler.pushOrder(mockPushNewOrderEvent);
        System.out.println(eventResult.getResultMsg());
    }

    @Test
    public void testSync(){
        kaolaScheduledService.syncOrderShip();
    }


    private JSONArray testSkuIds(KaoLaSysData kaoLaSysData) throws UnsupportedEncodingException {
        String timestamp = StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN);
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("channelId", kaoLaSysData.getChannelId());
        requestData.put("timestamp", timestamp);
        requestData.put("v", "1.0");
        requestData.put("sign_method", "md5");
        requestData.put("app_key", kaoLaSysData.getAppKey());
        requestData.put("sign", SignBuilder.buildSign(requestData, kaoLaSysData.getAppSecret(), kaoLaSysData.getAppSecret()));
        HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryAllGoodsId", requestData);
        JSONObject jsonObject = JSONObject.parseObject(httpResult.getHttpContent());
        return jsonObject.getJSONArray("goodsInfo");

    }

    private JSONObject testGoodsIds(String skuId,KaoLaSysData kaoLaSysData) throws UnsupportedEncodingException {
        String timestamp = StringUtil.DateFormat(new Date(), StringUtil.TIME_PATTERN);
        Map<String, Object> requestData = new TreeMap<>();
        requestData.put("channelId", kaoLaSysData.getChannelId());
        requestData.put("timestamp", timestamp);
        requestData.put("v", "1.0");
        requestData.put("sign_method", "md5");
        requestData.put("app_key", kaoLaSysData.getAppKey());
        requestData.put("skuId", skuId);
        requestData.put("queryType", 1);
        requestData.put("sign", SignBuilder.buildSign(requestData, kaoLaSysData.getAppSecret(), kaoLaSysData.getAppSecret()));
        HttpResult httpResult = HttpClientUtil.getInstance().post(kaoLaSysData.getRequestUrl()+"/queryGoodsInfoById", requestData);
        JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
        return jsonObject.getJSONObject("goodsInfo");
    }

//    @Test
//    public void createGoodsIdAndSkuIdTable() throws IOException {
//        JSONArray jsonArray = testSkuIds(mockKaoLaSysData);
//        HSSFWorkbook workbook= new HSSFWorkbook();
//        HSSFSheet sheet= workbook.createSheet("skuId&goodsId");
//        FileOutputStream out = null;
//        try {
//
//            int count = 0;
//            out = new FileOutputStream("d:/newKaola.xls");
//
//            HSSFRow row = sheet.createRow(0);
//            row.createCell(0).setCellValue("skuId");
//            row.createCell(1).setCellValue("goodsId");
//
//            for(Object item:jsonArray){
//                row = sheet.createRow(count);
//                String skuId =item.toString();
//                if(testGoodsIds(skuId,mockKaoLaSysData)!=null){
//                    row.createCell(0).setCellValue(skuId);
//                    row.createCell(1).setCellValue(testGoodsIds(skuId,mockKaoLaSysData).getString("goodsId"));
//                }
//                count++;
//                System.out.println("add....");
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally{
//            workbook.write(out);
//            workbook.close();
//            out.close();
//        }
//    }

    @Test
    public void testQueryGoodsId() throws UnsupportedEncodingException {
        String skuId = "10405-68a3e5516d7a7dc21fbe0e7ee13bfc1c";
        String goodsId = kaoLaOrderInfoHandler.queryGoodsId(skuId,mockKaoLaSysData);
        System.out.println(goodsId);
    }

}
