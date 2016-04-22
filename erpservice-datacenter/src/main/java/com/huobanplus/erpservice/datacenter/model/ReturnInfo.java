/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.model;

import lombok.Data;

/**
 * Created by liual on 2015-10-19.
 */
@Data
public class ReturnInfo extends BaseInfo {
    private String orderId;
    private String reason;
    private String logiName;
    private String logiNo;
    private String returnAddr;
    private String returnMobile;
    private String returnName;
    private String returnZip;
    private double freight;
    private String remark;
    private String returnItemStr;
}
