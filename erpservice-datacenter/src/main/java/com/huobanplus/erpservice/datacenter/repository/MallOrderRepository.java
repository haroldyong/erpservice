package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by allan on 2015/7/10.
 */
public interface MallOrderRepository extends JpaRepository<MallOrderBean, String>, JpaSpecificationExecutor {
    List<MallOrderBean> findByRotaryStatus(int rotaryStatus);
}
