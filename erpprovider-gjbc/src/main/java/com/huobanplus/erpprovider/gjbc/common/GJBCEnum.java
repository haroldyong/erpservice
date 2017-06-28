package com.huobanplus.erpprovider.gjbc.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by hxh on 2017-06-27.
 */
public interface GJBCEnum {
    enum ExpressEnterpriseCode implements ICommonEnum {
        SF(1, "顺风"),
        ST(2, "申通"),
        BSHT(3, "百世汇通"),
        YZXB(4, "邮政小包"),
        YT(5, "圆通"),
        QF(7, "全峰"),
        TT(8, "天天");

        private int code;
        private String name;

        ExpressEnterpriseCode(int code, String name) {
            this.code = code;
            this.name = name;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    enum CountryEnum {
        ASIA("100", "亚洲"),
        CHINA("142", "中国"),
        SKOREA("133", "韩国"),
        Germany("304", "德国");
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
        KG("035", "千克"),
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
}
