package com.huobanplus.erpprovider.huobanmall.service.impl;

import com.huobanplus.erpprovider.huobanmall.common.AuthBean;
import com.huobanplus.erpprovider.huobanmall.common.FailedBean;
import com.huobanplus.erpprovider.huobanmall.service.BaseEvent;
import com.huobanplus.erpprovider.huobanmall.service.InventoryEvent;
import com.huobanplus.erpservice.eventhandler.model.BaseResult;
import com.huobanplus.erpservice.eventhandler.model.InventoryInfo;
import com.huobanplus.erpservice.eventhandler.model.Monitor;
import org.springframework.stereotype.Service;

/**
 *
 * 类描述：库存事件实现类
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
@Service
public class InventoryEventImpl implements InventoryEvent {

    @Override
    public Monitor<BaseResult> syncInventory(AuthBean authBean, String storageIds) {
        return null;
    }

    @Override
    public Monitor<BaseResult> modifyInStorage(AuthBean authBean, InventoryInfo inventoryInfo) {
        return null;
    }

    @Override
    public Monitor<BaseResult> modifyOutStorage(AuthBean authBean, InventoryInfo inventoryInfo) {
        return null;
    }

    @Override
    public Monitor<BaseResult> commitInStorage(AuthBean authBean, InventoryInfo inventoryBean) {
        return null;
    }


    @Override
    public Monitor<BaseResult> commitOutStorage(AuthBean authBean, InventoryInfo inventoryBean) {
        return null;
    }

    @Override
    public Monitor<InventoryInfo> obtainInStorage(AuthBean authBean, String inStorageIds) {
        return null;
    }

    @Override
    public Monitor<InventoryInfo> obtainOutStorage(AuthBean authBean, String outStorageIds) {
        return null;
    }

    @Override
    public Monitor<BaseResult> confirmInStorage(AuthBean authBean, String inStorageIds) {
        return null;
    }

    @Override
    public Monitor<BaseResult> confirmOutStorage(AuthBean authBean, String outStorageIds) {
        return null;
    }

    @Override
    public Monitor<BaseResult> notifyFailedEvent(FailedBean failedBean) {
        return null;
    }

    @Override
    public void addEventQueue(BaseEvent event) {

    }
}
