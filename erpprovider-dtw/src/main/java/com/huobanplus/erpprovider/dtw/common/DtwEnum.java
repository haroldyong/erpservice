/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by wuxiongliu on 2016-07-13.
 */
public interface DtwEnum {

    enum CountryEnum {
        ASIA("100", "亚洲"),
        CHINA("142", "中国"),
        SKOREA("133", "韩国");
        private String code;
        private String name;

        CountryEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum CurrencyEnum {
        RMB("142", "人民币");

        private String code;
        private String name;

        CurrencyEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum UnitEnum {
        JIAN("011", "件"),
        GUAN("122", "罐");

        private String code;
        private String name;

        UnitEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum PaytypeEnum implements ICommonEnum {
        BankCard(1, "银行卡支付"),
        Balance(2, "余额支付"),
        Other(3, "其他");

        private int code;
        private String name;

        PaytypeEnum(int code, String name) {
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

    enum ImportTypeEnum {
        YIBAN("1", "一般进口"),
        BAOSHUI("2", "保税进口");

        private String code;
        private String name;

        ImportTypeEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum CustomerEnum {
        HANGZHOU,
        ZHENGZHOU,
        GUANGZHOU,
        CHONGQING,
        NINGBO,
        SHENZHEN,
        HENAN,
        SHANGHAI,
        XIAN,
        FUJIAN,
        TIANJIN,
        NANSHAGJ
    }

}
