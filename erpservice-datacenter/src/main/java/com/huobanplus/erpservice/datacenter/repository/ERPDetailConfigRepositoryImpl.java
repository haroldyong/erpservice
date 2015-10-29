/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.repository;

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
    public void test() {
        String sql = "SELECT ID FROM ERP_SysDataInfo";
        List<Integer> list = jdbcTemplate.query(sql, ((rs, rowNum) -> rs.getInt("ID")));
        System.out.println(list);
    }

    @Override
    public ERPDetailConfigEntity findBySysData(List<ERPSysDataInfo> sysDataInfos) {
        StringBuilder sqlWhere = new StringBuilder();
        for (ERPSysDataInfo sysDataInfo : sysDataInfos) {
            sqlWhere.append("and").append(sysDataInfo.getColumnName() + "=" + sysDataInfo.getParamName()).append("");
        }
        String sql = "SELECT * FROM ERP_DetailConfig WHERE 1=1" + sqlWhere.toString();
        return null;
    }
}
