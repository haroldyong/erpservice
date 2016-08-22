/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huobanplus.erpprovider.dtw.common;

/**
 * Created by wuxiongliu on 2016-07-12.
 */
public class DtwConstant {

    public static final int PAGE_SIZE = 50;

    public static final String ALI_PAY_URL = "https://mapi.alipay.com/gateway.do";

    public static final String WEIXIN_PAY_URL = "https://api.mch.weixin.qq.com/cgi-bin/mch/customs/customdeclareorder";

    public static final String WEIXIN_PAY_CUSTOM_CODE = "ZF14120401";

    public static final String CUSTOM_WEBSERVICE_URL = "http://122.224.230.4:18003/newyorkWS/ws/ReceiveEncryptDeclare?wsdl";

    public static final String CUSTOM_TARGET_NAMESPACE = "http://ws.newyork.zjport.gov.cn/";

    public static final String USER_PROCOTOL = "本人承诺所购买商品系个人合理自用，" +
            "现委托商家代理申报、代缴税款等通关事宜，" +
            "本人保证遵守《海关法》和国家相关法律法规，保证所提供的身份信息和收货信息真实完整，" +
            "无侵犯他人权益的行为，以上委托关系系如实填写，本人愿意接受海关、" +
            "检验检疫机构及其他监管部门的监管，并承担相应法律责任.";

    public static final String RSA_PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtchVwQgz8c2gLeqixidktya+4QQxFSnYYk8ITU5Xekxdk9Nsv8g4DJVI3gnE0RoGi/MfbNC3KAM8ukqX8nUpxQIDAQABAkB+eWTYibmCSdthNveLQEs9dnKlocH4hIWtWGlhR9lrF1qvovhOSl2rRl3kgRJVlb8m+/NcrR/N1DcQ5bYHqTchAiEA8F8dMLgHzi/UsJKJUFnQKKhnb2i0QbJY0tOkNxV8Fm0CIQDBmggFFKTlxjRljAuQXxnqZMWWQ885cSEOz+9u9AepuQIhAIItsNxivm9vPgwGwHEQwwhR8/rFROJTmYFMd6IRc6udAiBMRLwIFFvp0S1vG76qf2ycNFrKmagXPwQA69WtZiFmsQIgFUJkExILctNg4e4K3ttPHezd3lJs7LtLLZGHFGwGmS0=";

    public static final String RSA_PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALXIVcEIM/HNoC3qosYnZLcmvuEEMRUp2GJPCE1OV3pMXZPTbL/IOAyVSN4JxNEaBovzH2zQtygDPLpKl/J1KcUCAwEAAQ==";

    public static final String CUSTOM_DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCffOeIVYut9jW5w1L5uKX4aDvd837a8JhaWm5S8YqNQfgEmfD9T+rDknXLqMT+DXeQAqGo4hBmcbej1aoMzn6hIJHk3/TfTAToNN8fgwDotHewsTCBbVkQWtDTby3GouWToVsRi1i/A0Vfb0+xM8MnF46DdhhrnZrycERBSbyrcwIDAQAB";

    public static final String AES_KEY = "kI0/zRegCAZXytz0XV/G/w==";
}
