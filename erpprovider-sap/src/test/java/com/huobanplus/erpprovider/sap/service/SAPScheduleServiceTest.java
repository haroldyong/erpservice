package com.huobanplus.erpprovider.sap.service;

import com.huobanplus.erpprovider.sap.SAPTestBase;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.LogiInfo;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/4/20.
 */
public class SAPScheduleServiceTest extends SAPTestBase {
//    @Autowired
//    private SAPOrderHandler sapOrderHandler;

    @Autowired
    private ScheduledService scheduledService;


    private Order mockOrder;
    private ERPUserInfo mockErpUserInfo;
    private SAPSysData mockSysData;

    @Before
    public void setUp() throws Exception {

        mockOrder = new Order();
        mockOrder.setOrderId("100");
        mockOrder.setShipName("小李");
        mockOrder.setShipMobile("15623235656");
        mockOrder.setCity("杭州");
        mockOrder.setShipZip("254565");
        mockOrder.setShipAddr("杭州市滨江区明月江南三栋1号");
        List<OrderItem> items = new ArrayList<OrderItem>();
        OrderItem item1 = new OrderItem();
        item1.setItemId(1);
        OrderItem item2 = new OrderItem();
        item2.setItemId(2);
        mockOrder.setOrderItems(items);
        mockOrder.setItemNum(2);
        mockOrder.setLogiNo("125463");


        mockSysData = new SAPSysData();
        mockSysData.setHost("193.168.9.15");
        mockSysData.setSysNo("00");
        mockSysData.setClient("500");
        mockSysData.setJcoUser("DEV3");
        mockSysData.setJcoPass("800sap");
        mockSysData.setSapRouter("/H/202.107.243.45/H/");


        mockErpUserInfo = new ERPUserInfo();
        mockErpUserInfo.setCustomerId(12);

    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(scheduledService);
    }

    @Test
    public void testSyncOrderShip() {
        scheduledService.syncOrderShip();
    }


    @Test
    public void testScheduledService() {
        //获取订单信息
        JCoFunction jCoFunction = null;
        JCoTable jCoTable = null;
        JCoDestination jCoDestination = null;
        JCoFunction jCoFunctionIn = null;
        List<LogiInfo> results = new ArrayList<LogiInfo>();
        try {
            jCoDestination = ConnectHelper.connect(mockSysData, mockErpUserInfo);
            jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT");
            jCoFunctionIn = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT_IN");
            if (jCoFunction == null) {
                //log.error("SAP中没有ZWS_DATA_IMPORT方法");
                //return EventResult.resultWith(EventResultEnum.ERROR);
            }
            jCoFunction.execute(jCoDestination);
            jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");

//                for(JCoField field :jCoTable ){
//                    logiInfo.setZVBELN(field.get);
//                }
            for (int i = 0; i < jCoTable.getNumRows(); i++) {
                jCoTable.setRow(i);
                LogiInfo logiInfo = new LogiInfo();
                logiInfo.setZVBELN(jCoTable.getString("ZVBELN"));
                logiInfo.setYVBELN(jCoTable.getString("YVBELN"));
                logiInfo.setZOrder(jCoTable.getString("ZORDER"));
                logiInfo.setZType(jCoTable.getString("ZTYPE"));
                logiInfo.setZWMOrder(jCoTable.getString("ZWMORDER"));
                System.out.println("******");
                results.add(logiInfo);
            }
            String resultMsg = jCoFunction.getExportParameterList().getString("MESS");


//            JCoTable ztable = jCoFunctionIn.getTableParameterList().getTable("ZTABLE");
//            for (LogiInfo logiInfo1 : results) {
//                ztable.appendRow();
//                ztable.setValue("ZVBELN", logiInfo1.getZVBELN());
//                ztable.setValue("YVBELN", logiInfo1.getYVBELN());
//                ztable.setValue("ZORDER", logiInfo1.getZOrder());
//                ztable.setValue("ZTYPE", "X");
//                ztable.setValue("ZWMORDER", logiInfo1.getZWMOrder());
//            }
//            jCoFunctionIn.execute(jCoDestination);
 //           resultMsg = jCoFunctionIn.getExportParameterList().getString("MESS");
        } catch (JCoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
