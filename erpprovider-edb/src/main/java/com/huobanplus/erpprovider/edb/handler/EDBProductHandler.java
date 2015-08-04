package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.event.model.ERPInfo;
import com.huobanplus.erpservice.event.model.EventResult;
import com.huobanplus.erpservice.event.model.Monitor;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * 货品相关
 * Created by allan on 2015/7/28.
 */
public interface EDBProductHandler {
    /**
     * 得到货品库存信息
     *
     * @return
     * @throws IOException
     */
    Monitor<EventResult> getProInventoryInfo(ERPInfo info) throws IOException;
}
