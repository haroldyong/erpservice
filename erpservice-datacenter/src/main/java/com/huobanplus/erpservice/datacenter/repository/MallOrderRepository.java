package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * Created by allan on 2015/7/10.
 */
public interface MallOrderRepository extends JpaRepository<MallOrderBean, String>, JpaSpecificationExecutor {
    @Query("select orderBean from MallOrderBean orderBean where orderBean.orderStatus=?1 and orderBean.payStatus=?2 and orderBean.orderCode like %?3%")
    Page<MallOrderBean> findAll(int orderStatus, int payStatus, String orderId, Pageable pageable);
}
