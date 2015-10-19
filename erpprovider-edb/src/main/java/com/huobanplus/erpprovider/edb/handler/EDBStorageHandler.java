package com.huobanplus.erpprovider.edb.handler;

import com.huobanplus.erpservice.datacenter.bean.MallOutStoreBean;
import com.huobanplus.erpservice.datacenter.bean.MallProductOutBean;
import com.huobanplus.erpservice.eventhandler.model.ERPInfo;
import com.huobanplus.erpservice.eventhandler.model.EventResult;
import com.huobanplus.erpservice.eventhandler.model.Monitor;

import java.io.IOException;

/**
 * 出入库相关
 * Created by allan on 2015/8/7.
 */
public interface EDBStorageHandler {
    /**
     * 增加出库单信息
     *
     * @param outStoreBean
     * @param erpInfo
     * @return
     * @throws IOException
     */
    EventResult outStorageAdd(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException;

    /**
     * 根据出库单号，对出库单进行确认。出库单确认后系统库存会减少
     *
     * @param outStoreBean 需要传入outStorage_no,freight,freight_avgway
     * @param erpInfo
     * @return
     * @throws IOException
     */
    EventResult outStoreConfirm(MallOutStoreBean outStoreBean, ERPInfo erpInfo) throws IOException;

    /**
     * 在出库确认前对出库单修改出库数量
     *
     * @param productOutBean 需要传入bar_code，outstorage_no，outstorage_num
     * @param erpInfo
     * @return
     * @throws IOException
     */
    EventResult outStoreWriteback(MallProductOutBean productOutBean, ERPInfo erpInfo) throws IOException;
}
