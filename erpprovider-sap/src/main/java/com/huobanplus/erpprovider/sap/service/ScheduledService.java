/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.edb.handler.EDBOrderHandler;
import com.huobanplus.erpprovider.sap.common.SAPEnum;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.LogiInfo;
import com.huobanplus.erpprovider.sap.search.SAPOrderSearch;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.OrderScheduledLog;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.OrderScheduledLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushOrderListInfoEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by allan on 12/24/15.
 */
@Service
public class ScheduledService {
    private static final Log log = LogFactory.getLog(ScheduledService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private OrderScheduledLogService scheduledLogService;
    @Autowired
    private ERPRegister erpRegister;
    @Autowired
    private EDBOrderHandler edbOrderHandler;

    /*
     * 同步订单发货状态轮训服务
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void syncOrderShip() {
        Date now = new Date();
        log.info("SAP 获取物流信息:" + StringUtil.DateFormat(now, StringUtil.TIME_PATTERN));
        //得到所有配置过edb信息的商家,准备获取数据
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.SAP);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "开始获取订单数据进行同步");
            int currentPageIndex = 1;
            SAPSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), SAPSysData.class);
            SAPOrderSearch sapOrderSearch = new SAPOrderSearch();
            sapOrderSearch.setEndTime(now);
            sapOrderSearch.setShipStatus(SAPEnum.ShipStatusEnum.ALL_DELIVER);
            sapOrderSearch.setPlatformStatus(SAPEnum.PlatformStatus.PAYED);
            sapOrderSearch.setProceStatus(SAPEnum.OrderStatusEnum.ACTIVE);

            //获取订单信息
            JCoFunction jCoFunction = null;
            JCoTable jCoTable = null;
            List<LogiInfo> results = new ArrayList<LogiInfo>();
            ERPUserInfo erpUserInfo = new ERPUserInfo();
            erpUserInfo.setCustomerId(detailConfig.getCustomerId());
            try {
                JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);
                jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_OUTPUT");
                if (jCoFunction == null) {
                    log.error("SAP中没有ZWS_DATA_IMPORT方法");
                }
                jCoFunction.execute(jCoDestination);
                jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");
                LogiInfo logiInfo = new LogiInfo();

                for (int i = 0; i < jCoTable.getNumRows(); i++) {
                    jCoTable.setRow(i);
                    logiInfo.setZVBELN(jCoTable.getString("ZVBELN"));
                    logiInfo.setYVBELN(jCoTable.getString("YVBELN"));
                    logiInfo.setZOrder(jCoTable.getString("ZORDER"));
                    logiInfo.setZType(jCoTable.getString("ZTYPE"));
                    logiInfo.setZWMOrder(jCoTable.getString("ZWMORDER"));
                    results.add(logiInfo);
                }
                String resultMsg = jCoFunction.getExportParameterList().getString("MESS");
            } catch (JCoException e) {
                log.error(e.toString());
            } catch (IOException e) {
                log.error(e.toString());
            }
            log.info("本次获取" + results.size() + "条订单数据");


            //推送物流信息
            if (results.size() > 0) {
                int totalResult = results.size();//本次获取的总数据量
                int successCount = 0;//成功走完流程的数量
                //推送给相应的erp使用商户
                List<Order> orders = getLogiInfo(results);
                erpUserInfo.setErpUserType(detailConfig.getErpUserType());
                //得到相应使用者处理器
                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                PushOrderListInfoEvent pushOrderListInfoEvent = new PushOrderListInfoEvent(JSON.toJSONString(orders));
                //处理事件,此处为推送订单列表信息到使用者
                EventResult firstPushResult = erpUserHandler.handleEvent(pushOrderListInfoEvent);
                if (firstPushResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    List<Order> successList = JSON.parseArray(String.valueOf(firstPushResult.getData()), Order.class);
                }
                //存入轮训记录表
                OrderScheduledLog orderScheduledLog = new OrderScheduledLog();
                orderScheduledLog.setCustomerId(detailConfig.getCustomerId());
                orderScheduledLog.setNum(totalResult);
                orderScheduledLog.setSuccessNum(successCount);
                orderScheduledLog.setCreateTime(now);
                scheduledLogService.save(orderScheduledLog);
            }
        }
    }


    /**
     * 用于发货
     *
     * @param resultOrders
     * @return
    */
    private List<Order> getLogiInfo(List<LogiInfo> resultOrders) {
        //推送给erp使用商户
        List<Order> orders = new ArrayList<>();
        for (LogiInfo loginInfo : resultOrders) {
            Order order = new Order();
            order.setOrderId(loginInfo.getZVBELN());
            order.setLogiNo(loginInfo.getZOrder());

            /*List<OrderItem> orderItems = new ArrayList<>();
            JSONArray jsonOrderItem = jo.getJSONArray("tid_item");
            for (Object ob : jsonOrderItem) {
                JSONObject joItem = (JSONObject) ob;
                OrderItem orderItem = new OrderItem();
                orderItem.setProductBn(joItem.getString("barcode"));
                String sendNum = joItem.getString("send_num");
                if (!StringUtils.isEmpty(sendNum)) {
                    orderItem.setSendNum(Integer.parseInt(sendNum));
                }
                orderItem.setSendNum(1);
                String refundNum = joItem.getString("refund_num");
                if (!StringUtils.isEmpty(refundNum)) {
                    orderItem.setRefundNum(Integer.parseInt(refundNum));
                }
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);*/
            orders.add(order);
        }
        return orders;
    }
}
