package com.huobanplus.erpprovicer.huobanmall.config;

import com.huobanplus.erpservice.event.erpevent.ERPBaseEvent;
import com.huobanplus.erpservice.event.handler.ERPHandler;
import com.huobanplus.erpservice.event.handler.ERPHandlerBuilder;
import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.dom4j.DocumentException;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

/**
 * 伙伴Mall功能具体操作类
 * 该处貌似和Contorller有冲突，该类待定
 */

public class HuobanMallHandlerBuilder implements ERPHandlerBuilder {

    @Override
    public ERPHandler buildHandler(ERPInfo info) {
        if(!"huobanMall".equals(info.getName()))
        {
            //不是网店管家
            return null;
        }
        return new ERPHandler() {

            @Override
            public boolean eventSupported(ERPBaseEvent event) {
                return false;
            }

            @Override
            public Monitor<EventResult> handleEvent(ERPBaseEvent event) throws IOException, IllegalAccessException, DataAccessException, DocumentException {
                return null;
            }
        };
    }
}
