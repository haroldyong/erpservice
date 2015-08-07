package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface MallProductRepository extends JpaRepository<MallOrderItem, Long> {


}
