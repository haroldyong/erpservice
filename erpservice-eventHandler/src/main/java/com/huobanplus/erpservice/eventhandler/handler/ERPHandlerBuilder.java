package com.huobanplus.erpservice.eventhandler.handler;

import com.huobanplus.erpservice.eventhandler.model.ERPInfo;

/**
 * ERP处理器生成器，由erp-provider具体实现
 * Created by allan on 2015/7/13.
 */
public interface ERPHandlerBuilder {
    /**
     * 生成具体的ERP处理器
     *
     * @param info 相关信息
     * @return 如果为空表示该Builder无法处理这个info
     */
    ERPHandler buildHandler(ERPInfo info);
}
