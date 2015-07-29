package com.huobanplus.erpprovicer.huobanmall.service.impl;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;
import com.huobanplus.erpprovicer.huobanmall.common.FailedBean;
import com.huobanplus.erpprovicer.huobanmall.service.BaseEvent;
import com.huobanplus.erpprovicer.huobanmall.service.InventoryEvent;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.InventoryInfo;
import com.huobanplus.erpservice.event.model.Monitor;
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
    public Monitor<BaseResult> commitInStorage(AuthBean authBean, InventoryInfo inventoryBean) {
        return null;
    }

    @Override
    public Monitor<InventoryInfo> obtainInStorage(AuthBean authBean, String... params) {
        return null;
    }

    @Override
    public Monitor<BaseResult> commitOutStorage(AuthBean authBean, InventoryInfo inventoryBean) {
        return null;
    }

    @Override
    public Monitor<InventoryInfo> obtainOutStorage(AuthBean authBean, String... params) {
        return null;
    }

    @Override
    public Monitor<BaseResult> confirmInStorage(AuthBean authBean, String... params) {
        return null;
    }

    @Override
    public Monitor<BaseResult> confirmOutStorage(AuthBean authBean, String... params) {
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
