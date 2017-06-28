package com.huobanplus.erpprovider.gjbc.common;

import lombok.Data;

/**
 * Created by hxh on 2017-06-26.
 */
@Data
public class GJBCSysData {
    /**
     * 高捷接口请求地址
     */
    private String requestUrl;
    /**
     * 海关请求地址
     */
    private String requestCustomsUrl;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 验证码
     */
    private String key;
    /**
     * 业务类型
     */
    private String mark;
    /**
     * 下单状态
     */
    private String confirm;
    /**
     * 请求数据
     */
    private String order;
    /**
     * 是否BC订单
     */
    private int isBc;
    /**
     * 发货人和收件人信息 逗号分隔的字符串;发件人姓名,发件人城市,发件人地址,发件人电话,发件人国别,收件人身份证号
     */
    private String senderAndAddresseeInfo;
    /**
     * 保价费
     */
    private double customsInsured;
    /**
     * 支付企业名称
     */
    private String pName;
    /**
     * 订单网址
     */
    private String pWeb;
    /**
     * 网址名称
     */
    private String webName;
    /**
     * 商家备案信息 逗号分隔的字符串；商家广州备案号，商家全国备案号，商家备案名称
     */
    private String reInfo;

}
