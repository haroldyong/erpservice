package com.huobanplus.erpservice.datacenter.service;

import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import org.springframework.data.domain.Page;

/**
 * <p>类描述：<p/>
 * 库存信息服务接口
 */
public interface MallInventoryService {

    /**
     * 保存库存信息
     * @param mallInventoryBean 库存信息实体
     * @return 返回保存结果
     */
    MallInventoryBean save(MallInventoryBean mallInventoryBean);

    /**
     * 根据库存编号获取库存详细信息
     * @param inventoryId 库存编号
     * @return 返回库存信息
     */
    MallInventoryBean findInventoryById(String inventoryId);

    /**
     * 条件分组查询库存
     * @param storageStatus 库存状态
     * @param inventoryNo     库存号
     * @param pageIndex   页码
     * @param pageSize    每页数量
     * @return 返回查询的结果
     */
    Page<MallInventoryBean> findAll(Integer storageStatus, String inventoryNo, int pageIndex, int pageSize);
}
