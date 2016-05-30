package com.huobanplus.erpprovider.kjyg.formatkjyg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * Created by wuxiongliu on 2016/5/23.
 */

@Data
public class KjygCreateOrderInfo {

    /**
     * 销售商代码
     */
    @JSONField(name = "clientcode")
    private String clientcode;

    /**
     * 收件人姓名
     */
    @JSONField(name = "recievername")
    private String shipName;

    /**
     * 收件人电话
     */
    @JSONField(name = "recievertel")
    private String shipMobile;

    /**
     * 购物网站买家账号
     */
    @JSONField(name = "webaccountno")
    private String webAccountNo;

    /**
     * 购物网站订单号
     */
    @JSONField(name = "webtradeno")
    private String webTradeNo;

    /**
     * 购物网站支付单号
     */
    @JSONField(name = "webpayno")
    private String webPayNo;

    /**
     * 支付方式
     */
    @JSONField(name = "payway")
    private String payWay;

    /**
     * 买家身份证
     */
    @JSONField(name = "buyerpid")
    private String buyerPid;

    /**
     * 买家真实姓名
     */
    @JSONField(name = "buyername")
    private String buyerName;

    /**
     * 买家手机号
     */
    @JSONField(name = "buyertel")
    private String buyerTel;

    /**
     * 买家实付金额
     */
    @JSONField(name = "payment")
    private String payment;

    /**
     * 购物网站
     */
    @JSONField(name = "website")
    private String website;

    /**
     * 省
     */
    @JSONField(name = "province")
    private String province;

    /**
     *  城市
     */
    @JSONField(name = "city")
    private String city;

    /**
     *  区
     */
    @JSONField(name = "area")
    private String area;

    /**
     * 邮编
     */
    @JSONField(name = "postcode")
    private String postCode;

    /**
     *  收货地址
     */
    @JSONField(name = "recieveaddr")
    private String shipAddr;

    /**
     *  订单备注
     */
    @JSONField(name = "remark")
    private String remark;

    /**
     * 发货地区（可选：美西、美东、韩国首尔、日本、澳洲悉尼、德国汉堡、荷兰阿姆、意大利米兰）
     */
    @JSONField(name = "fharea")
    private String fharea;

    /**
     *  订单号
     */
    @JSONField(name = "orderno")
    private String orderNo;

    @JSONField(name = "list")
    private List<KjygOrderItem> orderItems;

}
