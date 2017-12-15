/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler.impl;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.formatsap.SAPOrderItem;
import com.huobanplus.erpprovider.sap.formatsap.SAPSaleOrderInfo;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpprovider.sap.util.ConnectHelper;
import com.huobanplus.erpservice.common.ienum.OrderEnum;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.datacenter.entity.logs.OrderDetailSyncLog;
import com.huobanplus.erpservice.datacenter.model.Order;
import com.huobanplus.erpservice.datacenter.model.OrderItem;
import com.huobanplus.erpservice.datacenter.service.logs.OrderDetailSyncLogService;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.push.OrderRefundStatusUpdate;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushNewOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liuzheng on 2016/4/14.
 */
@Component
public class SAPOrderHandlerImpl implements SAPOrderHandler {
    private static final Log log = LogFactory.getLog(SAPOrderHandlerImpl.class);
    @Autowired
    private OrderDetailSyncLogService orderDetailSyncLogService;

    /**
     * 推送订单
     *
     * @param pushNewOrderEvent 订单信息实体
     * @return EventResult
     */
    @Override
    public EventResult pushOrder(PushNewOrderEvent pushNewOrderEvent) {
        Order orderInfo = JSON.parseObject(pushNewOrderEvent.getOrderInfoJson(), Order.class);
        if (orderInfo.getPayStatus() == OrderEnum.PayStatus.REFUNDING.getCode()) {
            return EventResult.resultWith(EventResultEnum.ERROR, "无效订单");
        }
        log.info("sap start to order, orderId=" + orderInfo.getOrderId());
        SAPSysData sysData = JSON.parseObject(pushNewOrderEvent.getErpInfo().getSysDataJson(), SAPSysData.class);

        List<OrderItem> orderItemList = orderInfo.getOrderItems();
        List<SAPOrderItem> sapOrderItemList = new ArrayList<SAPOrderItem>();

        ERPUserInfo erpUserInfo = pushNewOrderEvent.getErpUserInfo();
        ERPInfo erpInfo = pushNewOrderEvent.getErpInfo();

        orderItemList.forEach(orderItem -> {
            SAPOrderItem sapOrderItem = new SAPOrderItem();
            sapOrderItem.setName(orderItem.getName());
            sapOrderItem.setProductBn(orderItem.getProductBn());
            sapOrderItem.setNum(orderItem.getNum());
            sapOrderItem.setCost(orderItem.getCost());
            sapOrderItem.setPrice(orderItem.getPrice());
            sapOrderItem.setAmount(orderItem.getPrice());
            sapOrderItemList.add(sapOrderItem);
        });

        SAPSaleOrderInfo sapSaleOrderInfo = new SAPSaleOrderInfo();
        if (orderInfo.getPayStatus() == OrderEnum.PayStatus.PAYED.getCode()) {//正常订单
            sapSaleOrderInfo.setOrderType("ZWOR");
            sapSaleOrderInfo.setProvederFactory("8000");
        }
        //退款
        if (orderInfo.getPayStatus() == OrderEnum.PayStatus.REFUNDING.getCode()) {
            sapSaleOrderInfo.setOrderType("ZWRE");
            sapSaleOrderInfo.setProvederFactory("1000");
        }
        sapSaleOrderInfo.setShopName(sysData.getShopName());
        sapSaleOrderInfo.setOrderSaleFrom("售达方");
        sapSaleOrderInfo.setNumId(orderInfo.getOrderId());
        sapSaleOrderInfo.setCustomName(orderInfo.getShipName());
        sapSaleOrderInfo.setCustomTel(orderInfo.getShipMobile());
        sapSaleOrderInfo.setCity(orderInfo.getCity());
        sapSaleOrderInfo.setShipZip(orderInfo.getShipZip());
        sapSaleOrderInfo.setShipAddr(orderInfo.getShipAddr());
        sapSaleOrderInfo.setPmtAmount(orderInfo.getPmtAmount());
        sapSaleOrderInfo.setCostItem(orderInfo.getCostItem());
//        sapSaleOrderInfo.setFinalAmount(orderInfo.getFinalAmount());
        sapSaleOrderInfo.setFinalAmount(orderInfo.getFinalAmount() - orderInfo.getIntegralAmount());
        //sapSaleOrderInfo.setGoodsInfo("产品组");
//        sapSaleOrderInfo.setMaterialCode("物料编码");
//        sapSaleOrderInfo.setOrderNum(orderInfo.getItemNum());
        sapSaleOrderInfo.setOrganization("PC");
        //   sapSaleOrderInfo.setDiscount("20");
        sapSaleOrderInfo.setInvoiceIsopen(orderInfo.getIsTax() == 1);
        sapSaleOrderInfo.setInvoiceTitle(orderInfo.getTaxCompany());
        //sapSaleOrderInfo.setSapSallId("销售订单号");
        sapSaleOrderInfo.setLogiNo(orderInfo.getLogiNo());
        sapSaleOrderInfo.setFreight(orderInfo.getCostFreight());
        sapSaleOrderInfo.setUnionOrderId(orderInfo.getUnionOrderId());
        //sapSaleOrderInfo.setGoodsOrg("产品组");
        sapSaleOrderInfo.setSapOrderItems(sapOrderItemList);

        Date now = new Date();

        EventResult eventResult = this.orderPush(sysData, erpUserInfo, sapSaleOrderInfo);
        OrderDetailSyncLog orderDetailSyncLog = orderDetailSyncLogService.findByOrderId(orderInfo.getOrderId());

        if (orderDetailSyncLog == null) {
            orderDetailSyncLog = new OrderDetailSyncLog();
            orderDetailSyncLog.setCreateTime(now);
        }
        orderDetailSyncLog.setCustomerId(erpUserInfo.getCustomerId());
        orderDetailSyncLog.setProviderType(erpInfo.getErpType());
        orderDetailSyncLog.setUserType(erpUserInfo.getErpUserType());
        orderDetailSyncLog.setOrderId(orderInfo.getOrderId());
        orderDetailSyncLog.setOrderInfoJson(pushNewOrderEvent.getOrderInfoJson());
        orderDetailSyncLog.setErpSysData(erpInfo.getSysDataJson());
        orderDetailSyncLog.setSyncTime(now);

        if (eventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_SUCCESS);
        } else {
            orderDetailSyncLog.setDetailSyncStatus(OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
            orderDetailSyncLog.setErrorMsg(eventResult.getResultMsg());
        }

        orderDetailSyncLogService.save(orderDetailSyncLog);
        return eventResult;
    }

    @Override
    public EventResult pushRefund(OrderRefundStatusUpdate orderRefundStatusUpdate) {
        log.info("sap start to push refund");

        JCoFunction jCoFunction;
        try {
            SAPSysData sysData = JSON.parseObject(orderRefundStatusUpdate.getErpInfo().getSysDataJson(), SAPSysData.class);
            JCoDestination jCoDestination = ConnectHelper.connect(sysData, orderRefundStatusUpdate.getErpUserInfo());
            jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DN_UPDATE");
            if (jCoFunction == null) {
                log.info("SAP中没有ZWS_DN_UPDATE方法");
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            jCoFunction.getImportParameterList().setValue("INPUT_ZORDER", orderRefundStatusUpdate.getOrderRefundStatusInfo().getOrderId());
            jCoFunction.execute(jCoDestination);
            String responseMsg = jCoFunction.getExportParameterList().getString("OUTPUT_MESSAGE");
            log.info("sap push refund response---->" + responseMsg);
            return EventResult.resultWith(EventResultEnum.SUCCESS);
        } catch (Exception ex) {
            log.info("JCO异常:" + ex.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }
    }

    private EventResult orderPush(SAPSysData sysData, ERPUserInfo erpUserInfo, SAPSaleOrderInfo sapSaleOrderInfo) {
        JCoFunction jCoFunction;
        JCoTable jCoTable;
        try {
            JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);
            jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_IMPORT");
            if (jCoFunction == null) {
                log.info("SAP中没有ZWS_DATA_IMPORT方法");
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");
            double totalPmtAmount = 0; //已分配的优惠金额
            int index = 0;
            List<SAPOrderItem> sapOrderItemList = sapSaleOrderInfo.getSapOrderItems();
            for (SAPOrderItem sapOrderItem : sapOrderItemList) {
                double percent = sapOrderItem.getAmount() / sapSaleOrderInfo.getCostItem();
                double subPmtAmount = index == sapOrderItemList.size() - 1 ?
                        sapSaleOrderInfo.getPmtAmount() - totalPmtAmount :
                        sapSaleOrderInfo.getPmtAmount() * percent;
                double netPrice = sapOrderItem.getPrice() - sapOrderItem.getPrice() - subPmtAmount; //净价 市场价-销售价-优惠金额
                jCoTable.appendRow();
                jCoTable.setValue("ZKONDM", sysData.getShopName());
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
//                jCoTable.setValue("MATNR", sapOrderItem.getName());
                jCoTable.setValue("MATNR", sapOrderItem.getProductBn());
                jCoTable.setValue("KWMENG", sapOrderItem.getNum());
                jCoTable.setValue("VRKME", sapSaleOrderInfo.getOrganization());
                //jCoTable.setValue("WERKS", sapSaleOrderInfo.getProvederFactory());
                //jCoTable.setValue("LGORT", sapSaleOrderInfo.getGoodsAddr());
                if (index == 0) {
//                    jCoTable.setValue("NETPR", BigDecimal.valueOf(netPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    jCoTable.setValue("NETPR", sapSaleOrderInfo.getFinalAmount() - sapSaleOrderInfo.getFreight());
                }

                //到时order 中需传递发票相关信息
                jCoTable.setValue("ZFP", sapSaleOrderInfo.isInvoiceIsopen() ? "X" : null);

                jCoTable.setValue("ZTITLE", sapSaleOrderInfo.getInvoiceTitle());

                //联合订单和运费,新增20160909
                jCoTable.setValue("ZORDER1", sapSaleOrderInfo.getUnionOrderId());
                jCoTable.setValue("ZNETPR", sapSaleOrderInfo.getFreight());
                //jCoTable.setValue("ZWMORDER", sapSaleOrderInfo.getLogiNo());
                index++;
                totalPmtAmount += subPmtAmount;
            }

            jCoFunction.execute(jCoDestination);
            String resultMsg = jCoFunction.getExportParameterList().getString("MESS");
            log.info("SAP订单推送--" + resultMsg);

            return EventResult.resultWith(EventResultEnum.SUCCESS);

        } catch (Exception ex) {
            log.info("JCO异常:" + ex.getMessage());
            return EventResult.resultWith(EventResultEnum.ERROR, ex.getMessage(), null);
        }
    }

}
