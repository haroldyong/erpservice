package com.huobanplus.erpservice.datacenter.service.logs.impl;

import com.huobanplus.erpservice.common.util.StringUtil;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.logs.GoodsInfoSyncLog;
import com.huobanplus.erpservice.datacenter.repository.logs.GoodsInfoSyncLogRepository;
import com.huobanplus.erpservice.datacenter.service.logs.GoodsInfoSyncLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuxiongliu on 2016/6/14.
 */
@Service
public class GoodsInfoSyncLogServiceImpl implements GoodsInfoSyncLogService {

    @Autowired
    private GoodsInfoSyncLogRepository goodsInfoSyncLogRepository;

    @Override
    public GoodsInfoSyncLog save(GoodsInfoSyncLog goodsInfoSyncLog) {
        return goodsInfoSyncLogRepository.save(goodsInfoSyncLog);
    }

    @Override
    public GoodsInfoSyncLog findTop(int customerId, ERPTypeEnum.ProviderType providerType) {
        return goodsInfoSyncLogRepository.findTopByCustomerIdAndProviderTypeOrderByIdDesc(customerId,providerType);
    }

    @Override
    public Page<GoodsInfoSyncLog> findAll(int pageIndex, int pageSize, String beginTime, String endTime, int customerId) {
        Specification<GoodsInfoSyncLog> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customerId").as(Integer.class), customerId));
            if (!StringUtils.isEmpty(beginTime)) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(beginTime, StringUtil.DATE_PATTERN)));
            }
            if (!StringUtils.isEmpty(endTime)) {
                predicates.add(cb.lessThanOrEqualTo(root.get("syncTime").as(Date.class),
                        StringUtil.DateFormat(endTime, StringUtil.DATE_PATTERN)));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return goodsInfoSyncLogRepository.findAll(specification,
                new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "syncTime")));
    }
}
