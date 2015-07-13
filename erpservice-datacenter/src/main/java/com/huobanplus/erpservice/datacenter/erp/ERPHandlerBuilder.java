package com.huobanplus.erpservice.datacenter.erp;

import com.huobanplus.erpservice.datacenter.model.ERPInfo;

/**
 * 由ERP-Provider提供实现
 * @author CJ
 */
public interface ERPHandlerBuilder {

    /**
     * 生成具体的ERP处理器
     * @param info 相关信息
     * @return 如果为空表示该Builder无法处理这个info
     */
    ERPHandler buildHandler(ERPInfo info);

}
