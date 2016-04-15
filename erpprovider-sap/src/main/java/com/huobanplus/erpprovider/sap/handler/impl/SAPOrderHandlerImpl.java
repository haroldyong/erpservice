/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.handler.impl;

import com.huobanplus.erpprovider.sap.common.SAPSysData;
import com.huobanplus.erpprovider.sap.handler.SAPOrderHandler;
import com.huobanplus.erpservice.datacenter.jsonmodel.Order;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import org.springframework.stereotype.Component;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.sap.conn.jco.*;

/**
 * Created by liuzheng on 2016/4/14.
 */
@Component
public class SAPOrderHandlerImpl implements SAPOrderHandler {


    /**
     * 推送订单
     *
     * @param orderInfo 订单信息实体
     * @param sysData   SAP的系统数据
     * @return EventResult
     */
    @Override
    public EventResult pushOrder(Order orderInfo, SAPSysData sysData, ERPUserInfo erpUserInfo) {

        SAPSaleOrderInfo sapSaleOrderInfo = new SAPSaleOrderInfo();
        if (orderInfo.getPayStatus() == 1) {//正常订单
            sapSaleOrderInfo.setOrderType("ZWOR");
            sapSaleOrderInfo.setProvederFactory("8000");
        } else {// 退货单
            sapSaleOrderInfo.setOrderType("ZWRE");
            sapSaleOrderInfo.setProvederFactory("1000");
        }
        sapSaleOrderInfo.setOrderSaleFrom("售达方");
        sapSaleOrderInfo.setNumId("订单号");
        sapSaleOrderInfo.setCustomName(orderInfo.getShipName());
        sapSaleOrderInfo.setCustomTel(orderInfo.getShipMobile());
        sapSaleOrderInfo.setCity(orderInfo.getCity());
        sapSaleOrderInfo.setShipZip(orderInfo.getShipZip());
        sapSaleOrderInfo.setShipAddr(orderInfo.getShipAddr());
        sapSaleOrderInfo.setGoodsInfo("产品组");
        sapSaleOrderInfo.setMaterialCode("物料编码");
        sapSaleOrderInfo.setOrderNum("订单数量");
        sapSaleOrderInfo.setOrganization("单位");
        sapSaleOrderInfo.setDiscount("折扣金额");
        sapSaleOrderInfo.setInvoiceIsopen(false);
        sapSaleOrderInfo.setInvoiceTitle("发票抬头");
        sapSaleOrderInfo.setSapSallId("销售订单号");
        sapSaleOrderInfo.setLogiNo("物流单号");
        sapSaleOrderInfo.setGoodsOrg("产品组");
        return this.orderPush(sysData, erpUserInfo, sapSaleOrderInfo);

    }

    private EventResult orderPush(SAPSysData sysData, ERPUserInfo erpUserInfo, SAPSaleOrderInfo sapSaleOrderInfo) {

        JCoDestination jCoDestination = ConnectHelper.connect(sysData, erpUserInfo);

        JCoFunction jCoFunction = null;
        JCoTable jCoTable = null;
        try {

            jCoFunction = jCoDestination.getRepository().getFunction("ZWS_DATA_IMPORT");
            if (jCoFunction == null) {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }
            jCoTable = jCoFunction.getTableParameterList().getTable("ZTABLE");
            if (jCoTable == null) {
                return EventResult.resultWith(EventResultEnum.ERROR, "");
            }

            jCoTable.appendRow();
            jCoTable.setValue("ZTYPE", sapSaleOrderInfo.getOrderType());
//            jCoTable.setValue("KUNNR","客户编号");
//            jCoTable.setValue("ZORDER","微商城订单");
            jCoTable.setValue("NAME", sapSaleOrderInfo.getCustomName());
            jCoTable.setValue("TELF", sapSaleOrderInfo.getCustomTel());
            jCoTable.setValue("ORT01", sapSaleOrderInfo.getCity());
            jCoTable.setValue("PSTLZ", sapSaleOrderInfo.getShipZip());
            jCoTable.setValue("STRAS", sapSaleOrderInfo.getShipAddr());
            jCoTable.setValue("VKORG", sapSaleOrderInfo.getSellOrg());
            jCoTable.setValue("VTWEG", sapSaleOrderInfo.getDistributWay());
            jCoTable.setValue("SPART", sapSaleOrderInfo.getGoodsOrg());
            jCoTable.setValue("MATNR", sapSaleOrderInfo.getMaterialCode());
            jCoTable.setValue("KWMENG", sapSaleOrderInfo.getOrderNum());
            jCoTable.setValue("VRKME", sapSaleOrderInfo.getOrganization());
            jCoTable.setValue("WERKS", sapSaleOrderInfo.getProvederFactory());
            jCoTable.setValue("LGORT", sapSaleOrderInfo.getGoodsAddr());
            jCoTable.setValue("NETPR", sapSaleOrderInfo.getDiscount());
            jCoTable.setValue("ZFP", sapSaleOrderInfo.isInvoiceIsopen());
            jCoTable.setValue("ZTITLE", sapSaleOrderInfo.getInvoiceTitle());
            jCoTable.setValue("ZWMORDER", sapSaleOrderInfo.getLogiNo());

            jCoFunction.execute(jCoDestination);
            String resultMsg = jCoFunction.getExportParameterList().getString("MESS");
            if (resultMsg.equals("")) {
                return EventResult.resultWith(EventResultEnum.SUCCESS);
            } else {
                return EventResult.resultWith(EventResultEnum.ERROR);
            }

        } catch (JCoException ex) {
            return EventResult.resultWith(EventResultEnum.ERROR);
        }

    }

    public static void main(String[] args) {
        SAPOrderHandlerImpl obj = new SAPOrderHandlerImpl();
        obj.orderPush(null, null, null);
    }

}
