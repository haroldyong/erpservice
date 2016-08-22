/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.formatdtw;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016-07-27.
 */
@Data
public class AliCustomer {

    /**
     * 报关流水号 不可空
     */
    private String outRequestNo;

    /**
     * 支付宝交易号 不可空
     */
    private String tradeNo;

    /**
     * 商户海关备案编号 不可空
     */
    private String merchantCustomsCode;

    /**
     * 报关金额 不可空
     */
    private String amount;

    /**
     * 海关编号 不可空
     */
    private String customsPlace;

    /**
     * 商户海关备案名称 不可空
     */
    private String merchantCustomsName;

    /**
     * 是否拆单 可空
     */
    private String isSplit;

    /**
     * 子订单号 可空
     */
    private String subOutBizNo;
}
