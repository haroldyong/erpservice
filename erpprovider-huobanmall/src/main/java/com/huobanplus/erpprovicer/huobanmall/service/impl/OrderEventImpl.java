package com.huobanplus.erpprovicer.huobanmall.service.impl;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;
import com.huobanplus.erpprovicer.huobanmall.common.FailedBean;
import com.huobanplus.erpprovicer.huobanmall.service.BaseEvent;
import com.huobanplus.erpprovicer.huobanmall.service.OrderEventService;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.Monitor;
import com.huobanplus.erpservice.event.model.OrderInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 类描述：订单事件实现类
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
@Service
public class OrderEventImpl implements OrderEventService {

    @Override
    public Monitor<BaseResult> commitOrder(AuthBean authBean, OrderInfo orderBean) {
        return null;

    }

    @Override
    public Monitor<BaseResult> commitOrder(AuthBean authBean, List<OrderInfo> orderBeans) {
        return null;
    }

    @Override
    public Monitor<OrderInfo> obtainOrder(AuthBean authBean, String orderIds) {
        return null;
    }

    @Override
    public Monitor<OrderInfo> obtainOrders(AuthBean authBean) {
        return null;
    }

    @Override
    public Monitor<BaseResult> modifyOrderInfo(AuthBean authBean, OrderInfo orderBean) {
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
