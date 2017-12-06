package com.huobanplus.erpprovider.gjbc.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbc.common.GjbcSysData;
import com.huobanplus.erpprovider.gjbc.handler.GJBCProductHandler;
import com.huobanplus.erpprovider.gjbc.response.GjbcInventorySearchListResponse;
import com.huobanplus.erpprovider.gjbc.search.GjbcInventorySearch;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.model.ProInventoryInfo;
import com.huobanplus.erpservice.datacenter.model.SkusInfo;
import com.huobanplus.erpservice.datacenter.service.ERPDetailConfigService;
import com.huobanplus.erpservice.datacenter.service.logs.InventorySyncLogService;
import com.huobanplus.erpservice.eventhandler.ERPRegister;
import com.huobanplus.erpservice.eventhandler.common.EventResultEnum;
import com.huobanplus.erpservice.eventhandler.erpevent.sync.SyncInventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.userhandler.ERPUserHandler;
import com.huobanplus.erpuser.huobanmall.handler.HBGoodHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hot on 2017/11/25.
 */
@Service
public class GjbcSynInventory {
    private static final Log log = LogFactory.getLog(GjbcSynInventory.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;

    @Autowired
    private GJBCProductHandler productHandler;

    @Autowired
    private HBGoodHandler hbGoodHandler;

    @Autowired
    private ERPRegister erpRegister;

    /**
     * 对库存进行一次性同步
     */
    @Scheduled(cron = "0 */35 * * * ?")
    @Transactional
    public void SynInventoryFromDB() {
        Date now = new Date();
        log.info("gjbc inventory sync start");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GJBC);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            doSync(detailConfig, now);
        }
        log.info("gjbc inventory sync end");
    }

    private List<ProInventoryInfo> toProInventoryInfo(List<GjbcInventorySearchListResponse> proStockInfoList) {
        List<ProInventoryInfo> proInventoryInfoList = new ArrayList<>();
        proStockInfoList.forEach(proStockInfo -> {
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setProductBn(proStockInfo.getGoods_barcode());
            proInventoryInfo.setSalableInventory(Integer.parseInt(proStockInfo.getOrder_inventory()));
            proInventoryInfoList.add(proInventoryInfo);
        });
        return proInventoryInfoList;

    }

    public void doSync(ERPDetailConfigEntity detailConfig, Date now) {
        try {
            ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());
            ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());

            GjbcSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), GjbcSysData.class);
            if (sysData.getStatus() != null && sysData.getStatus() == 1) {


                List<ProInventoryInfo> failedList = new ArrayList<>(); //失败的列表

                SyncInventoryEvent syncInventoryEvent = new SyncInventoryEvent();
                syncInventoryEvent.setErpInfo(erpInfo);
                syncInventoryEvent.setErpUserInfo(erpUserInfo);


                ERPUserHandler erpUserHandler = erpRegister.getERPUserHandler(erpUserInfo);

                int totalCount = 0;
                //从平台获取商品列表
                EventResult productListEventResult = hbGoodHandler.obtainAllProductList(erpUserInfo);
                if (productListEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                    if (productListEventResult.getData() != null) {
                        List<SkusInfo> skus = (List<SkusInfo>) productListEventResult.getData();
                        if (skus != null && skus.size() > 0) {
                            totalCount = skus.size();

                            List<String> listBarcode = new ArrayList<>();
                            for (SkusInfo skusInfo : skus) {
                                listBarcode.add(skusInfo.getBn());
                            }


                            int pageSize = 20;
                            int pageCount = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

                            List<String> listSubCode;
                            for (int i = 0; i < pageCount; i++) {
                                if (i == pageCount - 1) {
                                    listSubCode = listBarcode.subList(pageSize * i, totalCount);
                                } else {
                                    listSubCode = listBarcode.subList(pageSize * i, pageSize * (i + 1));
                                }

                                synPage(sysData, failedList, syncInventoryEvent, erpUserHandler, listSubCode.toArray(new String[listSubCode.size()]));
                            }

                        }
                    }
                } else {

                    log.info("code:" + productListEventResult.getResultCode());
                }

                if (failedList.size() > 0) {
                    log.info("failedCount--->" + failedList.size());
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
        } catch (Exception e) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "库存同步发生错误--" + e.getMessage());
        }

    }

    private boolean synPage(GjbcSysData sysData, List<ProInventoryInfo> failedList, SyncInventoryEvent syncInventoryEvent
            , ERPUserHandler erpUserHandler, String[] barcodes) throws UnsupportedEncodingException {

        GjbcInventorySearch gjbcInventorySearch = new GjbcInventorySearch();
        gjbcInventorySearch.setGood_barcode(barcodes);
        EventResult nextEventResult = productHandler.getProductInventoryInfo(sysData, gjbcInventorySearch);

        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
            List<GjbcInventorySearchListResponse> gjbcInventorySearchListResponses = (List<GjbcInventorySearchListResponse>) nextEventResult.getData();


            List<ProInventoryInfo> nextResult = toProInventoryInfo(gjbcInventorySearchListResponses);
            syncInventoryEvent.setInventoryInfoList(nextResult);

            EventResult nextSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);

            if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                failedList.addAll((List<ProInventoryInfo>) nextSyncResult.getData());
            } else {
                log.info("库存同步失败--" + nextEventResult.getResultMsg());
                return true;
            }
        }
        return false;
    }

}
