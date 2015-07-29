package com.huobanplus.erpprovicer.huobanmall.service;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;
import com.huobanplus.erpservice.event.model.BaseResult;
import com.huobanplus.erpservice.event.model.InventoryInfo;
import com.huobanplus.erpservice.event.model.Monitor;

/**
 *
 * 类描述：库存处理事件
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
public interface InventoryEvent extends BaseEvent {

    /**
     * 方法描述：同步当前库存信息
     *（待定，当前标记为过期方法。）
     * 提示错误信息，并开启重新同步机制
     * @param authBean 安全签名参数集合
     * @param storageIds 库存编号集合{1、当只包含1个库存时，同步该库存信息 2、当包含多个库存时，批量同步库存信息}
     * @return 返回同步结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    @Deprecated
    public Monitor<BaseResult> syncInventory(AuthBean authBean, String storageIds);

    /**
     * （新增）提交入库单信息
     * 失败处理 提示错误信息，并开启重新提交入库单信息机制
     * @param authBean 安全签名参数集合
     * @param inventoryBean 入库单信息参数集合
     * @return 返回新增入库单结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> commitInStorage(AuthBean authBean, InventoryInfo inventoryBean);

    /**
     * 方法描述：按条件（批量）获取入库单信息
     * 失败处理 提示错误信息，并开启重新查询入库单信息机制
     * @param authBean 安全签名参数集合
     * @param params 入库单编号参数集合
     * @return 返回符合条件的入库订单信息
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<InventoryInfo> obtainInStorage(AuthBean authBean, String... params);

    /**
     * （新增）提交出库单信息
     *  失败处理 提示错误信息，并开启重新提交出库单信息机制
     * @param authBean 安全签名参数集合
     * @param inventoryBean 出库单信息参数集合
     * @return 返回新增出库单结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> commitOutStorage(AuthBean authBean, InventoryInfo inventoryBean);

    /**
     * 方法描述：按条件（批量）获取出库单信息
     * 失败处理 提示错误信息，并开启重新查询出库单信息机制
     * @param authBean 安全签名参数集合
     * @param params 出库单编号参数集合
     * @return 返回符合条件的出库订单信息
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<InventoryInfo> obtainOutStorage(AuthBean authBean, String... params);

    /**
     * 方法描述：（批量）确认入库单信息
     * 失败处理 提示错误信息，并开启重新确认机制
     * @param authBean 安全签名参数集合
     * @param params 需要确认的参数集合(入库单IDs)
     * @return 返回确认结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> confirmInStorage(AuthBean authBean, String... params);

    /**
     * 方法描述：（批量）确认出库单信息
     * 失败处理 提示错误信息，并开启重新确认机制
     * @param authBean 安全签名参数集合
     * @param params 需要确认的参数集合(出库单IDs)
     * @return 返回确认结果（成功、失败）
     * @since 2015年7月25日 下午4:30:43
     */
    public Monitor<BaseResult> confirmOutStorage(AuthBean authBean, String... params);
}
