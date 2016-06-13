/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.kaola.formatkaola;

import lombok.Data;

/**
 * Created by wuxiongliu on 2016/5/11.
 */
@Data
public class KaoLaUserInfo {

    /**
     * 用户id
     */
    private String accountId;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     *  省份代码
     */
//    private String provinceCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     *  城市代码
     */
//    private String cityCode;


    /**
     * 县(区)名称
     */
    private String districtName;

    /**
     *  县(区)代码
     */
//    private String districtCode;

    /**
     * 街道地址
     */
    private String address;

    /**
     *  邮编
     */
//    private String postCode;

    /**
     *  固定电话
     */
//    private String phoneNum;

    /**
     *  区号
     */
//    private String phoneAreaNum;

    /**
     *  分机号
     */
//    private String phoneExtNum;

    /**
     *  证件姓名
     */
//    private String identityName;

    /**
     * 证件号码
     */
    private String identityId;

    /**
     * 身份证正面
     */
//    private String identityPicFront;

    /**
     * 身份证反面
     */
//    private String identityPicBack;

}
