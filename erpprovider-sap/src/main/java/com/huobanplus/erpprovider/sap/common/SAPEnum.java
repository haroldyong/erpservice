/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.sap.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by liual on 2015-10-23.
 */
public interface SAPEnum {
    enum OrderStatusEnum implements ICommonEnum {
        DEAD(-1, "已作废"),
        ACTIVE(0, "已确认"),
        FINISH(1, "已归档");
        private int code;
        private String name;

        OrderStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

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
    }

    enum PayStatusEnum implements ICommonEnum {
        UNPAYED(0, "未付款"),
        ALL_PAYED(1, "已付款"),
        PARTY_PAYED(3, "部分付款"),
        PARTY_REFUND(4, "已退款部分退款"),
        ALL_REFUND(5, "已退款全部退款"),
        AFTER_SALE(6, "售后退款中"),
        ALL_DELIVER(7, "已发货");

        private int code;
        private String name;

        PayStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

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
    }

    enum ShipStatusEnum implements ICommonEnum {
        UNDELIVER(0, "未发货"),
        ALL_DELIVER(1, "已发货"),
        PARTY_DELIVER(2, "部分发货"),
        PARTY_RETURN(3, "退货到货部分退货"),
        ALL_RETURN(4, "退货到货全部退货"),
        WAIT_FOR_PARTY_RETURN(5, "待退货部分退货"),
        WAIT_FOR_ALL_RETURN(6, "待退货全部退货");

        private int code;
        private String name;

        ShipStatusEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

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
    }

    enum OrderDateType implements ICommonEnum {
        CREATE_TIME(0, "订货日期"),
        PAY_TIME(1, "付款日期"),
        DELIVERY_TIME(2, "发货日期"),
        FILE_TIME(3, "归档日期"),
        PRE_FILE_TIME(4, "预计归档日期"),
        RECEIVE_TIME(5, "到货日期"),
        UPDATE_TIME(6, "订单修改日期"),
        CANCEL_TIME(7, "取消时间"),
        FINISH_TIME(8, "完成时间");

        private int code;
        private String name;

        OrderDateType(int code, String name) {
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

    enum ObtainType implements ICommonEnum {
        ORDER_DETAIL_LIST(0, "订单列表"),
        PRODUCT_DETAIL_LIST(1, "商品列表");
        private int code;
        private String name;

        ObtainType(int code, String name) {
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

    /**
     * 平台订单状态
     */
    enum PlatformStatus implements ICommonEnum {
        PARTY_DELIVER(0, "部分发货"),
        WAIT_FOR_PARTY_REFUND(0, "待退款部分退款"),
        WAIT_FOR_ALL_REFUND(1, "待退款全部退款"),
        PAY_ON_DELIVERY(2, "货到付款"),
        TRADE_SUCCESS(3, "交易成功"),
        TRADE_CANCLED(4, "交易关闭"),
        DELIVERY(5, "已发货"),
        PAYED(6, "已付款");

        private int code;
        private String name;

        PlatformStatus(int code, String name) {
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
