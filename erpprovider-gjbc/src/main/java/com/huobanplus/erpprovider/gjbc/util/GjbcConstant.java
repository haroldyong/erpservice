package com.huobanplus.erpprovider.gjbc.util;

/**
 * 常用工具类
 *
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
}
