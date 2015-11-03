/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

import com.huobanplus.erpservice.common.util.EnumHelper;
import com.huobanplus.erpservice.datacenter.common.ERPTypeEnum;
import com.huobanplus.erpservice.datacenter.entity.ERPDetailConfigEntity;
import com.huobanplus.erpservice.datacenter.entity.ERPSysDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by liual on 2015-10-29.
 */
public class ERPDetailConfigRepositoryImpl implements ERPDetailConfigRepositoryCustom {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ERPDetailConfigEntity findBySysData(List<ERPSysDataInfo> sysDataInfos) {
        StringBuilder sqlWhere = new StringBuilder();
        StringBuilder sqlCustomerIn = new StringBuilder();
        int index = 0;
        for (ERPSysDataInfo sysDataInfo : sysDataInfos) {
            sqlWhere.append("and").append(sysDataInfo.getColumnName() + "=" + sysDataInfo.getParamName()).append(" ");
            if (index == sysDataInfos.size() - 1) {
                sqlCustomerIn.append(sysDataInfo.getCustomerId());
            } else {
                sqlCustomerIn.append(sysDataInfo.getCustomerId()).append(",");
            }
            index++;
        }
        String sql = "SELECT * FROM ERP_DetailConfig WHERE ISDEFAULT=1 " + sqlWhere.toString() + " AND CUSTOMERID IN (" + sqlCustomerIn.toString() + ")";
        ERPDetailConfigEntity result = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            ERPDetailConfigEntity detailConfigEntity = new ERPDetailConfigEntity();
            detailConfigEntity.setId(rs.getInt("ID"));
            detailConfigEntity.setErpType(EnumHelper.getEnumType(ERPTypeEnum.class, rs.getInt("ERPTYPE")));
            detailConfigEntity.setErpSysData(rs.getString("ERPSYSDATA"));
            detailConfigEntity.setCustomerId(rs.getInt("CUSTOMERID"));

            return detailConfigEntity;
        });
        return result;
    }
}
