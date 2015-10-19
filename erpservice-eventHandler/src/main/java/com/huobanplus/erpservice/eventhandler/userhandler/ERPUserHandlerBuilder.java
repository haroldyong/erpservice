package com.huobanplus.erpservice.eventhandler.userhandler;

import com.huobanplus.erpservice.eventhandler.handler.ERPHandler;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.ERPUserInfo;

/**
 * ERP使用者处理器生成器，由erp-user具体实现
 * Created by liual on 2015-10-15.
 */
public interface ERPUserHandlerBuilder {
    /**
     * 生成具体的ERP使用者处理器
     *
     * @param info 相关信息
     * @return 如果为空表示该Builder无法处理这个info
     */
    ERPUserHandler buildHandler(ERPUserInfo info);
}
