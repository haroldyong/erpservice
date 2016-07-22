package com.huobanplus.erpprovider.dtw.handler;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.DtwTestBase;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpprovider.dtw.formatdtw.DtwPersonalDelcareInfo;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/16.
 */
public class DtwOrderHandlerTest extends DtwTestBase {

    private DtwSysData mockDtwSysData;

    @Autowired
    private DtwOrderHandler dtwOrderHandler;

    private Order mockOrder;

    private List<OrderItem> mockOrderItems;

    private ERPInfo mockErpInfo;

    private ERPUserInfo mockErpUserInfo;

    private PushNewOrderEvent mockPushNewOrderEvent;

    @Before
    public void setUp(){

        mockDtwSysData = new DtwSysData();
        mockDtwSysData.setPassKey("tesaa");
        mockDtwSysData.setECommerceName("testhot");
        mockDtwSysData.setECommerceCode("100");
        mockDtwSysData.setRequestUrl("http://logistics.dtw.com.cn:8080/QBT/api");//http://thirdpart.kaola.com/api,http://223.252.220.85/api

        mockOrderItems = new ArrayList<>();

        OrderItem mockOrderItem = new OrderItem();
        mockOrderItem.setNum(5);
        mockOrderItem.setOrderId("8070");
        mockOrderItem.setProductBn("3872824-ecc4090b639c47f89b453980923afb8e");


        mockOrderItems.add(mockOrderItem);


        mockOrder = new Order();
        mockOrder.setOrderId("000000001222");
        mockOrder.setMemberId(1761390);
        mockOrder.setShipName("吴雄琉");
        mockOrder.setShipMobile("18705153967");
        mockOrder.setShipEmail("");
        mockOrder.setProvince("浙江省");
        mockOrder.setCity("杭州市");
        mockOrder.setDistrict("滨江区");
        mockOrder.setShipAddr("浙江省杭州市滨江区阡陌路智慧E谷B幢4楼火图科技");
        mockOrder.setBuyerPid("330682199006015217");
        mockOrder.setBuyerName("刘渠成");

        mockOrder.setPayTime(StringUtil.DateFormat(new Date(),StringUtil.TIME_PATTERN));
        mockOrder.setOrderItems(mockOrderItems);

        mockErpInfo = new ERPInfo();
        mockErpInfo.setSysDataJson(JSON.toJSONString(mockDtwSysData));


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setErpUserType(ERPTypeEnum.UserType.HUOBAN_MALL);
        mockErpUserInfo.setCustomerId(23347);

    }


    @Test
    public void testPushOrder(){

        mockPushNewOrderEvent = new PushNewOrderEvent();
        mockPushNewOrderEvent.setOrderInfoJson(JSON.toJSONString(mockOrder));
        mockPushNewOrderEvent.setErpInfo(mockErpInfo);
        mockPushNewOrderEvent.setErpUserInfo(mockErpUserInfo);
        dtwOrderHandler.pushOrder(mockPushNewOrderEvent);
    }

    @Test
    public void testPersonalInfoDeclare(){
        DtwPersonalDelcareInfo dtwPersonalDelcareInfo = new DtwPersonalDelcareInfo();
        dtwOrderHandler.personalInfoDeclare(dtwPersonalDelcareInfo,mockDtwSysData);
    }
}
