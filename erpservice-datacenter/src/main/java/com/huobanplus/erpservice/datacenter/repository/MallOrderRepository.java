package com.huobanplus.erpservice.datacenter.repository;

        import com.huobanplus.erpservice.datacenter.bean.MallOrderBean;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

/**
 * Created by allan on 2015/7/10.
 */
public interface MallOrderRepository extends JpaRepository<MallOrderBean, String> {
}
