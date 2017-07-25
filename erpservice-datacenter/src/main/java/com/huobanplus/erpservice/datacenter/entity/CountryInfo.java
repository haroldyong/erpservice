package com.huobanplus.erpservice.datacenter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 国家代码
 * Created by montage on 2017/7/4.
 */

@Entity
@Setter
@Getter
@Table(name = "ERP_CountryInfo")
public class CountryInfo {
    /**
     * 国别地区代码
     */
    @Id
    private String countryCode;
    /**
     * 中文名
     */
    private String ChinaName;
    /**
     * 英文名
     */
    private String EnglishName;
}
