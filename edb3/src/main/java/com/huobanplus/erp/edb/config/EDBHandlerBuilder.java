package com.huobanplus.erp.edb.config;

import event.ERPEvent;
import event.OrderEvent;
import handler.ERPHandler;
import handler.ERPHandlerBuilder;
import model.ERPInfo;
import model.EventResult;

import java.io.IOException;

/**
 * edb 具体事件处理实现类
 * Created by allan on 2015/7/13.
 */
public class EDBHandlerBuilder implements ERPHandlerBuilder {

    /**
     * 根据erp信息判断是否由该erp-provider处理
     *
     * @param erpInfo
     * @return 无法处理返回空，可以处理返回该erp事件处理器
     */
    public ERPHandler buildHandler(ERPInfo erpInfo) {
        if (erpInfo.toString().equals("")) {
            return null;
        }
        return new ERPHandler() {
            public boolean eventSupported(ERPEvent erpEvent) {
                //todo 判断事件是否可以处理
                if (erpEvent instanceof OrderEvent) {
                    return true;
                }
                return false;
            }

            public EventResult handleEvent(ERPEvent erpEvent) throws IOException, IllegalAccessException {
                //todo 处理事件并返回结果
                if (erpEvent instanceof OrderEvent) {

                }
                return null;
            }
        };
    }
}
