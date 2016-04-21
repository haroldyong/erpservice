/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.SAPOrderItem;
import com.huobanplus.erpprovider.sap.formatsap.SAPSaleOrderInfo;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.OrderOperatorLog;
import com.huobanplus.erpservice.datacenter.entity.OrderSync;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.datacenter.jsonmodel.OrderItem;
import com.huobanplus.erpservice.datacenter.service.OrderOperatorService;
import com.huobanplus.erpservice.datacenter.service.OrderSyncService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuzheng on 2016/4/14.
 */
@Component
public class SAPOrderHandlerImpl implements SAPOrderHandler {

    private static final Logger logger = LoggerFactory.getLogger(SAPOrderHandlerImpl.class);

    @Autowired
    private OrderSyncService orderSyncService;
    @Autowired
    private OrderOperatorService orderOperatorService;

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent 订单信息实体
     * @return EventResult
     */
    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {

        SAPSysData sysData = JSON.parseObject(pushNewOrderEvent.getErpInfo().getSysDataJson(), SAPSysData.class);
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        List<OrderItem> orderItemList = orderInfo.getOrderItems();
        List<SAPOrderItem> sapOrderItemList = new ArrayList<SAPOrderItem>();

        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();

        orderItemList.forEach(orderItem -> {
            SAPOrderItem sapOrderItem = new SAPOrderItem();
            sapOrderItem.setName(orderItem.getName());
            sapOrderItem.setNum(orderItem.getNum());
            sapOrderItemList.add(sapOrderItem);
        });

        SAPSaleOrderInfo sapSaleOrderInfo = new SAPSaleOrderInfo();
        if (orderInfo.getPayStatus() == 1) {//正常订单
            sapSaleOrderInfo.setOrderType("ZWOR");
            sapSaleOrderInfo.setProvederFactory("8000");
        } else {// 退货单
            sapSaleOrderInfo.setOrderType("ZWRE");
            sapSaleOrderInfo.setProvederFactory("1000");
        }
        sapSaleOrderInfo.setOrderSaleFrom("售达方");
        sapSaleOrderInfo.setNumId(orderInfo.getOrderId());
        sapSaleOrderInfo.setCustomName(orderInfo.getShipName());
        sapSaleOrderInfo.setCustomTel(orderInfo.getShipMobile());
        sapSaleOrderInfo.setCity(orderInfo.getCity());
        sapSaleOrderInfo.setShipZip(orderInfo.getShipZip());
        sapSaleOrderInfo.setShipAddr(orderInfo.getShipAddr());
        //sapSaleOrderInfo.setGoodsInfo("产品组");
        sapSaleOrderInfo.setMaterialCode("物料编码");
        sapSaleOrderInfo.setOrderNum(orderInfo.getItemNum());
        sapSaleOrderInfo.setOrganization("PC");
        sapSaleOrderInfo.setDiscount("20");
        sapSaleOrderInfo.setInvoiceIsopen(false);
        sapSaleOrderInfo.setInvoiceTitle("火图科技股份有限公司");
        //sapSaleOrderInfo.setSapSallId("销售订单号");
        sapSaleOrderInfo.setLogiNo(orderInfo.getLogiNo());
        //sapSaleOrderInfo.setGoodsOrg("产品组");
        sapSaleOrderInfo.setSapOrderItems(sapOrderItemList);




        Date now = new Date();
        //订单同步日志
        OrderOperatorLog orderOperatorLog = new OrderOperatorLog();
        orderOperatorLog.setProviderType(erpInfo.getErpType());
        orderOperatorLog.setUserType(erpUserInfo.getErpUserType());
        orderOperatorLog.setCustomerId(erpUserInfo.getCustomerId());
        orderOperatorLog.setCreateTime(now);
        orderOperatorLog.setOrderId(orderInfo.getOrderId());
        orderOperatorLog.setEventInfo(JSON.toJSONString(pushNewOrderEvent));

        //订单同步记录
        OrderSync orderSync = orderSyncService.getOrderSync(orderInfo.getOrderId(), erpUserInfo.getCustomerId());
        orderSync.setOrderStatus(EnumHelper.getEnumType(OrderEnum.OrderStatus.class, orderInfo.getOrderStatus()));
        orderSync.setPayStatus(EnumHelper.getEnumType(OrderEnum.PayStatus.class, orderInfo.getPayStatus()));
        orderSync.setShipStatus(EnumHelper.getEnumType(OrderEnum.ShipStatus.class, orderInfo.getShipStatus()));
        orderSync.setProviderType(ERPTypeEnum.ProviderType.SAP);
        orderSync.setUserType(erpUserInfo.getErpUserType());
        orderSync.setRemark(orderOperatorLog.getRemark());

        EventResult eventResult = null;
        eventResult = this.orderPush(sysData, erpUserInfo, sapSaleOrderInfo);

        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            orderOperatorLog.setResultStatus(true);
            orderOperatorLog.setRemark(pushNewOrderEvent.getEventType().getName() + "成功");
            switch (pushNewOrderEvent.getEventType()) {
                case PUSH_NEW_ORDER:
                    orderSync.setOutPayStatus("已付款");
                    orderSync.setOutShipStatus("未发货");
                    orderSync.setOrderSyncStatus(OrderSyncStatus.PUSHING_SUCCESS);
                    break;
            }
        } else {
            orderOperatorLog.setResultStatus(false);
            switch (pushNewOrderEvent.getEventType()) {
                case PUSH_NEW_ORDER:
                    orderSync.setOrderSyncStatus(OrderSyncStatus.WAITING_FOR_PUSHING);
                    break;
            }
            orderOperatorLog.setRemark(pushNewOrderEvent.getEventType().getName() + "失败");
        }
        orderSync.setResultStatus(orderOperatorLog.isResultStatus());
        orderSync.setRemark(orderOperatorLog.getRemark());

        orderSyncService.save(orderSync);
        orderOperatorService.save(orderOperatorLog);
        return eventResult;
    }

    private EventResult orderPush(SAPSysData sysData, ERPUserInfo erpUserInfo, SAPSaleOrderInfo sapSaleOrderInfo)  {
        JCoFunction jCoFunction = null;
        JCoTable jCoTable = null;
        try {

            JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);
            jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_IMPORT");
            if (jCoFunction == null) {
                logger.error("SAP中没有ZWS_DATA_IMPORT方法");
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");
            List<SAPOrderItem> sapOrderItemList = sapSaleOrderInfo.getSapOrderItems();
            for (SAPOrderItem sapOrderItem : sapOrderItemList) {
                jCoTable.appendRow();
                jCoTable.setValue("ZKONDM", "01");
                jCoTable.setValue("ZTYPE", sapSaleOrderInfo.getOrderType());
                //jCoTable.setValue("KUNNR",sapSaleOrderInfo.getOrderSaleFrom());
                jCoTable.setValue("ZORDER", sapSaleOrderInfo.getNumId());
                //jCoTable.setValue("VBELN","");
                jCoTable.setValue("NAME", sapSaleOrderInfo.getCustomName());
                jCoTable.setValue("TELF", sapSaleOrderInfo.getCustomTel());
                jCoTable.setValue("ORT01", sapSaleOrderInfo.getCity());
                jCoTable.setValue("PSTLZ", sapSaleOrderInfo.getShipZip());
                jCoTable.setValue("STRAS", sapSaleOrderInfo.getShipAddr());
                //jCoTable.setValue("VKORG", sapSaleOrderInfo.getSellOrg());
                //jCoTable.setValue("VTWEG", sapSaleOrderInfo.getDistributWay());
                //jCoTable.setValue("SPART", sapSaleOrderInfo.getGoodsOrg());
                //jCoTable.setValue("MATNR", "000000000010000668");
                jCoTable.setValue("MATNR", sapOrderItem.getName());
                jCoTable.setValue("KWMENG", sapOrderItem.getNum());
                jCoTable.setValue("VRKME", sapSaleOrderInfo.getOrganization());
                //jCoTable.setValue("WERKS", sapSaleOrderInfo.getProvederFactory());
                //jCoTable.setValue("LGORT", sapSaleOrderInfo.getGoodsAddr());
                jCoTable.setValue("NETPR", sapSaleOrderInfo.getDiscount());

                //到时order 中需传递发票相关信息
                jCoTable.setValue("ZFP", sapSaleOrderInfo.isInvoiceIsopen() ? "X" : null);

                jCoTable.setValue("ZTITLE", sapSaleOrderInfo.getInvoiceTitle());
                //jCoTable.setValue("ZWMORDER", sapSaleOrderInfo.getLogiNo());
            }

            jCoFunction.execute(jCoDestination);
            String resultMsg = jCoFunction.getExportParameterList().getString("MESS");
            logger.info(resultMsg);

            return EventResult.resultWith(EventResultEnum.SUCCESS);

        } catch (JCoException ex) {
            logger.error(ex.toString());
            return EventResult.resultWith(EventResultEnum.ERROR);
        } catch (JCoRuntimeException ex) {
            logger.error("JCO运行时异常",ex.toString());
            return EventResult.resultWith(EventResultEnum.ERROR);
        } catch (IOException ex){
            logger.error("IO 异常",ex.toString());
            return EventResult.resultWith(EventResultEnum.ERROR);
        }
    }

}
