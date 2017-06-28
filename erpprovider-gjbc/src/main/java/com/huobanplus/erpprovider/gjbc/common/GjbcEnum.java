package com.huobanplus.erpprovider.gjbc.common;

/**
 * Created by montage on 2017/6/27.
 */
public interface GjbcEnum {

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
