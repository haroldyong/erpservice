/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huobanplus.erpprovider.gjbc.util;

/**
 * 常用工具类0
 * <p>
 * Created by montage on 2017/6/27.
 */
public class GjbcConstant {
    /**
     * 日期格式
     */
    public final static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 订单下单
     */
    public final static String SEND_ORDER = "SendOrder";
    /**
     * 请求地址
     */
    public final static String REQUEST_URL = "http://test.goldjet.com.cn/api/index.php?act=order_bc&op=order";

    public  final  static  String TEST_REQUEST_URL ="http://oms.goldjet.com.cn/api/index.php?act=order_bc&op=order";

    public static final String ALI_PAY_URL = "https://mapi.alipay.com/gateway.do";

    public static final String WEIXIN_PAY_URL = "https://api.mch.weixin.qq.com/cgi-bin/mch/customs/customdeclareorder";

    public static final String WEIXIN_PAY_CUSTOM_CODE = "ZF14120401";

    public static final String ALI_PAY_CUSTOM_CODE = "ZF14021901";

    public static final String USER_PROCOTOL = "本人承诺所购买商品系个人合理自用，" +
            "现委托商家代理申报、代缴税款等通关事宜，" +
            "本人保证遵守《海关法》和国家相关法律法规，保证所提供的身份信息和收货信息真实完整，" +
            "无侵犯他人权益的行为，以上委托关系系如实填写，本人愿意接受海关、" +
            "检验检疫机构及其他监管部门的监管，并承担相应法律责任.";

    public static final String LOGISTIC_CODE = "WL15041401";
    public static final String LOGISTIC_NAME = "百世物流科技（中国）有限公司";
    public static final String RECORD_NUMBER = "3301964K02";
    public static final String WEB_NAME = "伙伴商城";
    public static final String MARK_STATUS = "order";
    public static final String SEL_ADD_ORDER_MARK = "sel_add_order";
    public static final String CONFIRM_STATUS = "2";
    public static final String RECORD_NAME = "杭州伙聚网络技术有限公司";

}
