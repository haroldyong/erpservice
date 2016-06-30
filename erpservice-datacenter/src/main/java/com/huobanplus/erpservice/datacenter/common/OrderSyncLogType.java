/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpservice.datacenter.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by allan on 6/30/16.
 */
public interface OrderSyncLogType {
    enum OrderDetailSyncType implements ICommonEnum {
        ORDER_PUSH(0, "订单推送"),
        ORDER_REFUND_TAG(1, "订单售后标记"),
        ORDER_REMARK(2, "订单备注");

        private int code;
        private String name;

        OrderDetailSyncType(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
