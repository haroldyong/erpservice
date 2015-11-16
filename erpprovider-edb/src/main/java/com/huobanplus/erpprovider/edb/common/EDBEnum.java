/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.edb.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by liual on 2015-10-23.
 */
public interface EDBEnum {
    enum OrderStatusEnum implements ICommonEnum {
        DEAD(-1, "已作废"),
        ACTIVE(0, "已确认"),
        FINISH(1, "已归档");
        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        OrderStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    enum PayStatusEnum implements ICommonEnum {
        UNPAYED(0, "未付款"),
        ALL_PAYED(1, "已付款"),
        PARTY_PAYED(3, "部分付款"),
        PARTY_REFUND(4, "已退款部分退款"),
        ALL_REFUND(5, "已退款全部退款");

        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        PayStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    enum ShipStatusEnum implements ICommonEnum {
        UNDELIVER(0, "未发货"),
        ALL_DELIVER(1, "已发货"),
        PARTY_DELIVER(2, "部分发货"),
        PARTY_RETURN(3, "退货到货部分退货"),
        ALL_RETURN(4, "退货到货全部退货");

        private int code;
        private String name;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        ShipStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
