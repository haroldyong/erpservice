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

package com.huobanplus.erpprovider.baison.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.baison.common.BaisonConstant;
import com.huobanplus.erpprovider.baison.common.BaisonSysData;
import com.huobanplus.erpprovider.baison.formatdata.BaisonStockResp;
import com.huobanplus.erpprovider.baison.formatdata.BaisonStockSearch;
import com.huobanplus.erpprovider.baison.handler.BaisonGoodsHandler;
import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.model.ProductInfo;
import com.huobanplus.erpservice.datacenter.model.ProductListInfo;
import com.huobanplus.erpservice.datacenter.model.ProductSearchInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.pull.GetProductInfoEvent;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016-12-26.
 */
@Service
public class BaisonInventoryScheduleService {

    private static final Log log = LogFactory.getLog(BaisonInventoryScheduleService.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;
    @Autowired
    private BaisonGoodsHandler baisonGoodsHandler;
    @Autowired
    private ERPRegister erpRegister;

    //    @Scheduled(cron = "0 0 */1 * * ?")
    @Transactional
    public void syncInventory() {

        Date now = new Date();
        String nowStr = StringUtil.DateFormat(now, StringUtil.TIME_PATTERN);
        log.info("inventory sync for BaisonE3 start!");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.BAISONE3);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            if (detailConfig.getErpBaseConfig().getIsSyncInventory() == 1) {
                ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
                ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());
                BaisonSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), BaisonSysData.class);

                List<ProInventoryInfo> failedList = new ArrayList<>(); //失败的列表

                int pageIndex = 1;
                int totalCount = 0;
                ProductSearchInfo productSearchInfo = new ProductSearchInfo();
                productSearchInfo.setPageIndex(pageIndex);
                productSearchInfo.setPageSize(BaisonConstant.PAGE_SIZE);

                GetProductInfoEvent getProductInfoEvent = new GetProductInfoEvent();
                getProductInfoEvent.setErpInfo(erpInfo);
                getProductInfoEvent.setErpUserInfo(erpUserInfo);
                getProductInfoEvent.setProductSearchInfo(productSearchInfo);

                List<BaisonStockResp> baisonStcokRespList = new ArrayList<>();
                List<ProInventoryInfo> proInventoryInfoList = new ArrayList<>();
                SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
                syncInventoryEvent.setErpInfo(erpInfo);
                syncInventoryEvent.setErpUserInfo(erpUserInfo);

                ProductListInfo productListInfo = null;
                List<BaisonStockSearch> baisonStockSearchList = new ArrayList<>();

                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
                EventResult firstObtainEvent = erpUserHandler.handleEvent(getProductInfoEvent);
                if (firstObtainEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    productListInfo = (ProductListInfo) firstObtainEvent.getData();
                    totalCount = productListInfo.getRecordCount();

                    baisonStockSearchList = convert2SearchList(productListInfo.getProducts());
                    EventResult firstQueryEvent = baisonGoodsHandler.queryGoodsStock(baisonStockSearchList, sysData);
                    if (firstQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        baisonStcokRespList = (List<BaisonStockResp>) firstQueryEvent.getData();// 接口返回的数据确认
                        proInventoryInfoList = convert2InventoryInfo(baisonStcokRespList);
                        syncInventoryEvent.setInventoryInfoList(proInventoryInfoList);

                        // 第一次同步库存到平台
                        EventResult firstSyncEvent = erpUserHandler.handleEvent(syncInventoryEvent);
                        if (firstSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {// 同步成功
                            failedList.addAll((List<ProInventoryInfo>) firstSyncEvent.getData());
                        }
                    }
                }
                // 后续几次
                while (productListInfo != null && productListInfo.isHasNext()) {

                    productSearchInfo.setPageIndex(++pageIndex);
                    getProductInfoEvent.setProductSearchInfo(productSearchInfo);
                    EventResult nextObtainEvent = erpUserHandler.handleEvent(getProductInfoEvent);

                    if (nextObtainEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                        productListInfo = (ProductListInfo) nextObtainEvent.getData();
                        baisonStockSearchList = convert2SearchList(productListInfo.getProducts());
                        EventResult nextQueryEvent = baisonGoodsHandler.queryGoodsStock(baisonStockSearchList, sysData);
                        if (nextQueryEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            baisonStcokRespList = (List<BaisonStockResp>) nextQueryEvent.getData();// 接口返回的数据确认
                            proInventoryInfoList = convert2InventoryInfo(baisonStcokRespList);
                            syncInventoryEvent.setInventoryInfoList(proInventoryInfoList);
                            EventResult nextSyncEvent = erpUserHandler.handleEvent(syncInventoryEvent);
                            if (nextSyncEvent.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                                failedList.addAll((List<ProInventoryInfo>) nextSyncEvent.getData());
                            }
                        }
                    }
                }
                if (totalCount > 0) {
                    inventorySyncLogService.saveLogAndDetail(
                            erpUserInfo.getErpUserType(),
                            erpInfo.getErpType(),
                            erpUserInfo.getCustomerId(),
                            totalCount,
                            failedList,
                            now
                    );
                }
            }
        }
    }

    /**
     * 将从平台获取的商品列表转为商品库存查询条件列表
     *
     * @param productInfoList
     * @return
     */
    private List<BaisonStockSearch> convert2SearchList(List<ProductInfo> productInfoList) {
        List<BaisonStockSearch> baisonStockSearchList = new ArrayList<>();
        productInfoList.forEach(item -> {
            BaisonStockSearch baisonStockSearch = new BaisonStockSearch();
            baisonStockSearch.setWarehouseCode(item.getWarehouseCode());
            baisonStockSearch.setProductBn(item.getSkuId());
            baisonStockSearchList.add(baisonStockSearch);
        });
        return baisonStockSearchList;
    }


    public EventResult captureMallProducts(ERPUserInfo erpUserInfo) {

        GetProductInfoEvent getProductInfoEvent = new GetProductInfoEvent();
        getProductInfoEvent.setErpInfo(null);
        getProductInfoEvent.setErpUserInfo(erpUserInfo);
        getProductInfoEvent.setProductSearchInfo(new ProductSearchInfo());

        ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);
        return erpUserHandler.handleEvent(getProductInfoEvent);
    }

    /**
     * 将百胜接口返回的商品库存列表转为平台的库存列表
     *
     * @param baisonStockRespList
     * @return
     */
    private List<ProInventoryInfo> convert2InventoryInfo(List<BaisonStockResp> baisonStockRespList) {

        List<ProInventoryInfo> inventoryInfoList = new ArrayList<>();
        baisonStockRespList.forEach(item -> {
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setProductBn(item.getProductBn());
            proInventoryInfo.setInventory(item.getNumber());
            proInventoryInfo.setSalableInventory(item.getNumber());
            inventoryInfoList.add(proInventoryInfo);
        });
        return inventoryInfoList;
    }
}
