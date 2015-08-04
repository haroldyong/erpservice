package com.huobanplus.erpprovider.huobanmall.handler;

import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>类描述：<p/>
 * 伙伴商城库存事件接口
 */
public interface InventoryHandler {

    /**
     * 提交库存信息
     * @param request 请求实体
     * @return 返回提交库存信息的结果（成功、失败）
     * @throws IOException
     */
    Monitor<EventResult> commitInventoryInfo(HttpServletRequest request) throws IOException;

}
