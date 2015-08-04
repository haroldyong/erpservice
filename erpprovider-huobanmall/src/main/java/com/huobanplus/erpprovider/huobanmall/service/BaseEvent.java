package com.huobanplus.erpprovider.huobanmall.service;

import com.huobanplus.erpprovider.huobanmall.common.FailedBean;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.Monitor;

/**
 * 类描述：公共基础事件
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version v1.0
 */
public interface BaseEvent {

    /**
     * 方法描述：事件失败提醒接口
     * @param failedBean 失败实体，包括{失败原因， 失败编码, 操作人， 操作事件，操作时间}
     * @return 返回处理结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> notifyFailedEvent(FailedBean failedBean);

    /**
     * 方法描述：将失败的事件加入事件队列，并等待重新处理
     * @param event 处理失败的事件
     * @since 2015年7月25日 下午4:30:43
     */
    public void addEventQueue(BaseEvent event);
}
