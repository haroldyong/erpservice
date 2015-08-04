package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallInventoryBean;
import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * <p>类描述：<p/>
 * 库存信息数据库处理接口
 */
public interface MallInventoryRepository extends JpaRepository<MallInventoryBean, String>, JpaSpecificationExecutor {

    @Query("select orderBean from MallOrderBean orderBean where orderBean.orderStatus=?1 and orderBean.payStatus=?2 and orderBean.orderCode like %?3%")
    Page<MallInventoryBean> findAll(Integer storageStatus, String inventoryNo, Pageable pageable);
}
