/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbc.common;

import com.huobanplus.erpservice.common.ienum.ICommonEnum;

/**
 * Created by hxh on 2017-06-27.
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

    enum CountryEnum {
        ASIA("100", "亚洲"),
        CHINA("142", "中国"),
        SKOREA("133", "韩国"),
        Germany("304", "德国"),
        HONGKANG("110", "香港");
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

    enum CurrencyEnum implements ICommonEnum {
        HKD(110, "港币"),
        JPY(116, "日本元"),
        MOP(121, "澳门元"),
        PHP(129, "菲律宾比索"),
        SGD(132, "新加坡元"),
        KRW(133, "韩国元"),
        THB(136, "泰国铢"),
        CNY(142, "人民币"),
        EUR(300, "欧元"),
        DKK(302, "丹麦克朗"),
        GBP(303, "英镑"),
        DEM(304, "德国马克"),
        FRF(305, "法国法郎"),
        ITL(307, "意大利朗"),
        ESP(312, "西班牙比塞塔"),
        ATS(315, "奥地利先令"),
        FIM(318, "芬兰马克"),
        NOK(326, "挪威克朗"),
        SEK(330, "瑞士法郎"),
        CAD(501, "加拿大元"),
        USD(502, "美元"),
        AUD(601, "澳大利亚元"),
        NZD(609, "新西兰元");
        private int code;
        private String name;

        CurrencyEnum(int code, String name) {
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
