/*
 *
 *  * 版权所有:杭州火图科技有限公司
 *  * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *  *
 *  * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *  * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  * 2013-2016. All rights reserved.
 *
 */

package com.huobanplus.erpservice.datacenter.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by wuxiongliu on 2016-11-11.
 */
public interface AfterSaleEnum {

    enum AfterStatus implements ICommonEnum {
        AFTER_SALE_APPLYING(0, "售后申请中"),
        REFUNDING(1, "退款中"),
        REFUSE_REFUND(2, "拒绝售后"),
        REFUND_SUCCESS(3, "退款成功"),
        AFTER_SALE_CANCEL(4, "售后已取消"),
        WAIT_BUYER_RETURN(5, "等待买家退货"),
        BUYER_RETURN_WAIT_CONFIRM(6, "买家退货等待确认");


        private int code;
        private String name;

        AfterStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
