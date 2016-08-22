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
 * Created by liual on 2015-11-03.
 */
public interface ERPTypeEnum {
    enum ProviderType implements ICommonEnum {
        EDB(0, "E店宝"),
        NETSHOP(1, "网店管家（尽请期待）"),
        SAP(2, "SAP系统"),
        ISCS(3, "网仓"),
        LGJ(4, "礼管家（尽请期待）"),
        KAOLA(5, "考拉"),
        KJYG(6, "跨境易购"),
        GY(7, "管易云ERP"),
        DTW(8, "大田跨境");

        private int code;
        private String name;

        ProviderType(int code, String name) {
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

    enum UserType implements ICommonEnum {
        HUOBAN_MALL(0, "伙伴商城"),
        HUOBAN_SUPPLIER(1, "供应商");


        private int code;
        private String name;

        UserType(int code, String name) {
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
