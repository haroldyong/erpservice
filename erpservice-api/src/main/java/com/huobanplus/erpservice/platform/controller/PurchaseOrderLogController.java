/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.platform.controller;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.dtw.common.DtwSysData;
import com.huobanplus.erpservice.common.SysConstant;
import com.huobanplus.erpservice.common.ienum.EnumHelper;
import com.huobanplus.erpservice.common.ienum.OrderSyncStatus;
import com.huobanplus.erpservice.common.util.SerialNo;
import com.huobanplus.erpservice.commons.annotation.RequestAttribute;
import com.huobanplus.erpservice.commons.bean.ApiResult;
import com.huobanplus.erpservice.commons.bean.ResultCode;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.logs.PurchaseOrderSyncLog;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrder;
import com.huobanplus.erpservice.datacenter.model.PurchaseOrderItem;
import com.huobanplus.erpservice.datacenter.repository.logs.PurchaseOrderSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.PurchaseOrderSyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.erpevent.push.PushPurchaseOrderEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpservice.proxy.utils.OrderProxyService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxiongliu on 2016-12-12.
 */
@Controller
@RequestMapping("/erpService/platform")
public class PurchaseOrderLogController {

    @Autowired
    private OrderProxyService orderProxyService;
    @Autowired
    private ERPDetailConfigService erpDetailConfigService;
    @Autowired
    private PurchaseOrderSyncLogRepository purchaseOrderSyncLogRepository;
    @Autowired
    private PurchaseOrderSyncLogService purchaseOrderSyncLogService;
    @Autowired
    private ERPRegister erpRegister;

    private static final Map<String, Integer> colMap = new HashMap<>();

    private static final Map<String, String> unitMap = new HashMap<>();

    static {
        colMap.put("productBn", 0);
        colMap.put("productName", 1);
        colMap.put("standard", 2);
        colMap.put("goodsBn", 3);
        colMap.put("qty", 4);
        colMap.put("unit", 5);
        colMap.put("price", 6);
        colMap.put("amount", 7);

        unitMap.put("罐", "122");
        unitMap.put("件", "011");
        unitMap.put("千克", "035");
        unitMap.put("克", "036");
        unitMap.put("毫升", "096");
        unitMap.put("盒", "140");
        unitMap.put("瓶", "142");
        unitMap.put("支", "012");
        unitMap.put("包", "125");
        unitMap.put("片", "020");
        unitMap.put("个", "007");


    }

    @RequestMapping(value = "/toPurchaseOrder")
    public String toPurchaseOrder(@RequestParam(required = false, defaultValue = "1") int pageIndex,
                                  @RequestAttribute int customerId,
                                  int erpUserType,
                                  String receiveNo,
                                  String blno,
                                  Model model) throws Exception {

        Page<PurchaseOrderSyncLog> page = purchaseOrderSyncLogService.findAll(pageIndex, SysConstant.DEFALUT_PAGE_SIZE,
                customerId, receiveNo, blno);
        model.addAttribute("purchaseOrderPage", page);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("pageSize", SysConstant.DEFALUT_PAGE_SIZE);
        model.addAttribute("erpUserType", erpUserType);
        model.addAttribute("erpUserType", erpUserType);
        return "logs/purchase_order_sync_log";
    }

    @RequestMapping(value = "/purchaseItem")
    public String showPurchaseOrderItem(Long id, int erpUserType, Model model) {
        PurchaseOrderSyncLog purchaseOrderSyncLog = purchaseOrderSyncLogRepository.findOne(id);
        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        if (purchaseOrderSyncLog != null) {
            PurchaseOrder purchaseOrder = JSON.parseObject(purchaseOrderSyncLog.getPurchaseOrderJson(), PurchaseOrder.class);
            purchaseOrderItems = purchaseOrder.getItems();
        }
        model.addAttribute("purchaseOrderItems", purchaseOrderItems);
        model.addAttribute("erpUserType", erpUserType);
        return "logs/purchase_order_items";
    }


    @RequestMapping(value = "/rePushPurchaseOrder")
    @ResponseBody
    public ApiResult rePushPurchaseOrder(long id) throws Exception {
        PurchaseOrderSyncLog purchaseOrderSyncLog = purchaseOrderSyncLogRepository.findOne(id);
        ERPInfo erpInfo = new ERPInfo(purchaseOrderSyncLog.getProviderType(), purchaseOrderSyncLog.getErpSysData());
        ERPUserInfo erpUserInfo = new ERPUserInfo(purchaseOrderSyncLog.getUserType(), purchaseOrderSyncLog.getCustomerId());
        PushPurchaseOrderEvent pushPurchaseOrderEvent = new PushPurchaseOrderEvent();
        pushPurchaseOrderEvent.setErpInfo(erpInfo);
        pushPurchaseOrderEvent.setErpUserInfo(erpUserInfo);
        pushPurchaseOrderEvent.setPurchaseOrderJson(purchaseOrderSyncLog.getPurchaseOrderJson());
        ERPUserHandler userHandler = erpRegister.getERPUserHandler(erpUserInfo);
        if (userHandler == null) {
            return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
        }

        return orderProxyService.handleEvent(pushPurchaseOrderEvent);
    }

    @RequestMapping(value = "/rePushAllPurchaseOrder")
    @ResponseBody
    public ApiResult rePushAllPurchaseOrder(@RequestAttribute int customerId) throws Exception {
        List<PurchaseOrderSyncLog> purchaseOrderSyncLogs = purchaseOrderSyncLogRepository.findByCustomerIdAndDetailSyncStatus(customerId,
                OrderSyncStatus.DetailSyncStatus.SYNC_FAILURE);
        for (PurchaseOrderSyncLog purchaseOrderSyncLog : purchaseOrderSyncLogs) {
            ERPInfo erpInfo = new ERPInfo(purchaseOrderSyncLog.getProviderType(), purchaseOrderSyncLog.getErpSysData());
            ERPUserInfo erpUserInfo = new ERPUserInfo(purchaseOrderSyncLog.getUserType(), purchaseOrderSyncLog.getCustomerId());
            PushPurchaseOrderEvent pushPurchaseOrderEvent = new PushPurchaseOrderEvent();
            pushPurchaseOrderEvent.setErpInfo(erpInfo);
            pushPurchaseOrderEvent.setErpUserInfo(erpUserInfo);
            pushPurchaseOrderEvent.setPurchaseOrderJson(purchaseOrderSyncLog.getPurchaseOrderJson());
            ERPUserHandler userHandler = erpRegister.getERPUserHandler(erpUserInfo);
            if (userHandler == null) {
                return ApiResult.resultWith(ResultCode.NO_SUCH_ERPHANDLER);
            }
        }
        return ApiResult.resultWith(ResultCode.SUCCESS, "推送完成", null);
    }


    @RequestMapping(value = "/uploadPurchaseOrderFile")
    public String uploadPurchaseOrderFile(@RequestAttribute int customerId,
                                          @RequestParam CommonsMultipartFile file,
                                          int erpUserType,
                                          String blno,
                                          String supplierId,
                                          String passKey,
                                          String eCommerceName,
                                          String eCommerceCode,
                                          String requestUrl) throws Exception {

        // read file
        XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());

        XSSFSheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getLastRowNum();

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierId(supplierId);
        purchaseOrder.setReceiveNo(SerialNo.create());
        purchaseOrder.setBolNo(blno);

        DecimalFormat df = new DecimalFormat("0");


        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

        for (int i = 1; i <= rowNum; i++) {
            XSSFRow hssfRow = sheet.getRow(i);
            if (hssfRow.getCell(0) == null || hssfRow.getCell(0).getCellType() == 3) {
                continue;
            }
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setProductBn(hssfRow.getCell(colMap.get("productBn")).getStringCellValue());
            purchaseOrderItem.setProductName(hssfRow.getCell(colMap.get("productName")).getStringCellValue());
            purchaseOrderItem.setStandard(hssfRow.getCell(colMap.get("standard")).getStringCellValue());
            if (hssfRow.getCell(colMap.get("goodsBn")).getCellType() == 1) {// string
                purchaseOrderItem.setGoodsBn(hssfRow.getCell(colMap.get("goodsBn")).getRawValue());
            } else if (hssfRow.getCell(colMap.get("goodsBn")).getCellType() == 0) {

                purchaseOrderItem.setGoodsBn(df.format(hssfRow.getCell(colMap.get("goodsBn")).getNumericCellValue()));
            }

            purchaseOrderItem.setQty((int) hssfRow.getCell(colMap.get("qty")).getNumericCellValue());
            String unitName = hssfRow.getCell(colMap.get("unit")).getStringCellValue();
            purchaseOrderItem.setUnitName(unitName);
            purchaseOrderItem.setUnit(unitMap.get(unitName));
            purchaseOrderItem.setAmount(Double.parseDouble(df.format(hssfRow.getCell(colMap.get("amount")).getNumericCellValue())));

            purchaseOrderItems.add(purchaseOrderItem);

        }

        purchaseOrder.setItems(purchaseOrderItems);

        ERPTypeEnum.UserType erpUserTypeEnum = EnumHelper.getEnumType(ERPTypeEnum.UserType.class, erpUserType);
        ERPDetailConfigEntity detailConfigEntity = erpDetailConfigService.findByCustomerIdAndDefault(customerId, erpUserTypeEnum);
        ERPInfo erpInfo = new ERPInfo(detailConfigEntity.getErpType(), detailConfigEntity.getErpSysData());
        DtwSysData dtwSysData = new DtwSysData();
        dtwSysData.setRequestUrl(requestUrl);
        dtwSysData.setPassKey(passKey);
        dtwSysData.setECommerceCode(eCommerceCode);
        dtwSysData.setECommerceName(eCommerceName);

        ERPInfo erpInfo1 = new ERPInfo(ERPTypeEnum.ProviderType.DTW, JSON.toJSONString(dtwSysData));
        ERPUserInfo erpUserInfo = new ERPUserInfo(erpUserTypeEnum, customerId);
        PushPurchaseOrderEvent pushPurchaseOrderEvent = new PushPurchaseOrderEvent();
        pushPurchaseOrderEvent.setErpInfo(erpInfo1);
        pushPurchaseOrderEvent.setErpUserInfo(erpUserInfo);
        pushPurchaseOrderEvent.setPurchaseOrderJson(JSON.toJSONString(purchaseOrder));
        orderProxyService.handleEvent(pushPurchaseOrderEvent);

        return "redirect:/erpService/platform/toPurchaseOrder?erpUserType=" + erpUserType;
    }

}
