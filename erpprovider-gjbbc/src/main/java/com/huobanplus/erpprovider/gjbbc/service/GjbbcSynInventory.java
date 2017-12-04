package com.huobanplus.erpprovider.gjbbc.service;

import com.alibaba.fastjson.JSON;
import com.huobanplus.erpprovider.gjbbc.common.GjbbcSysData;
import com.huobanplus.erpprovider.gjbbc.handler.GJBBCProductHandler;
import com.huobanplus.erpprovider.gjbbc.response.GjbbcInventorySearchListResponse;
import com.huobanplus.erpprovider.gjbbc.search.GjbbcInventorySearch;
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

import javax.json.Json;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hot on 2017/11/30.
 */
@Service
public class GjbbcSynInventory {
    private static final Log log = LogFactory.getLog(GjbbcSynInventory.class);

    @Autowired
    private ERPDetailConfigService detailConfigService;
    @Autowired
    private InventorySyncLogService inventorySyncLogService;

    @Autowired
    private GJBBCProductHandler productHandler;

    @Autowired
    private HBGoodHandler hbGoodHandler;

    @Autowired
    private ERPRegister erpRegister;

    /**
     * 对库存进行一次性同步
     */
    @Scheduled(cron = "0 */40 * * * ?")
    @Transactional
    public void SynInventoryFromDB() {
        Date now = new Date();
        log.info("gjbbc inventory sync start");
        List<ERPDetailConfigEntity> detailConfigs = detailConfigService.findByErpTypeAndDefault(ERPTypeEnum.ProviderType.GJBBC);
        for (ERPDetailConfigEntity detailConfig : detailConfigs) {
            doSync(detailConfig, now);
        }
        log.info("gjbbc inventory sync end");
    }

    private List<ProInventoryInfo> toProInventoryInfo(List<GjbbcInventorySearchListResponse> proStockInfoList) {
        List<ProInventoryInfo> list = new ArrayList<>();
        proStockInfoList.forEach(proStockInfo -> {
            ProInventoryInfo proInventoryInfo = new ProInventoryInfo();
            proInventoryInfo.setProductBn(proStockInfo.getGoods_barcode());
            proInventoryInfo.setSalableInventory(Integer.parseInt(proStockInfo.getOrder_inventory()));
            list.add(proInventoryInfo);
        });
        return list;

    }

    public void doSync(ERPDetailConfigEntity detailConfig, Date now) {

        try {
            ERPUserInfo erpUserInfo = new ERPUserInfo(detailConfig.getErpUserType(), detailConfig.getCustomerId());

            ERPInfo erpInfo = new ERPInfo(detailConfig.getErpType(), detailConfig.getErpSysData());

            GjbbcSysData sysData = JSON.parseObject(detailConfig.getErpSysData(), GjbbcSysData.class);
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

                        log.info("start do " + totalCount);

                        String[] barcodes = new String[totalCount];
                        int n = 0;
                        for (SkusInfo skusInfo : skus) {
                            barcodes[n] = skusInfo.getBn();
                            n = n + 1;
                        }

                        GjbbcInventorySearch gjbbcInventorySearch = new GjbbcInventorySearch();
                        gjbbcInventorySearch.setGoods_barcode(barcodes);
                        EventResult nextEventResult = productHandler.getProductInventoryInfo(sysData, gjbbcInventorySearch);

                        if (nextEventResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
                            List<GjbbcInventorySearchListResponse> gjbbcInventorySearchListResponses = (List<GjbbcInventorySearchListResponse>) nextEventResult.getData();

//                            log.info("to syn info:" + JSON.toJSONString(gjbbcInventorySearchListResponses));

                            List<ProInventoryInfo> nextResult = toProInventoryInfo(gjbbcInventorySearchListResponses);
//                            log.info("111");
                            syncInventoryEvent.setInventoryInfoList(nextResult);

//                            log.info("112");

                            EventResult nextSyncResult = erpUserHandler.handleEvent(syncInventoryEvent);
//                            log.info("113");
                            if (nextSyncResult.getResultCode() == EventResultEnum.SUCCESS.getResultCode()) {
//                                log.info("gjbbc inventory syn do success");
                                failedList.addAll((List<ProInventoryInfo>) nextSyncResult.getData());
                            } else {
                                log.info("库存同步失败--" + nextEventResult.getResultMsg());
                                return;
                            }
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
        } catch (Exception e) {
            log.info(detailConfig.getErpUserType().getName() + detailConfig.getCustomerId() + "库存同步发生错误--" + e.getMessage());
        }
    }
}
