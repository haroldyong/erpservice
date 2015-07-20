package com.huobanplus.erpservice.event.model;

import java.io.Serializable;

/**
 * 公共参数
 */
public class BaseBean implements Serializable {

    //接入码，用于验证请求的有效性。主要用于区分店铺。
    private String uCode;
    //方法名，不同接口分别传入不同的方法值。
    private String mType;
    //验证码，用于双方做安全验证。
    //调用API 时需要对请求参数进行签名验证， 根据参数名称按照字母先后顺序排序:key + value .... key + value
    //需要排列的参数包括uCode，mType，Secret,TimeStamp。其中Secret是密钥，会在esAPI里面填写，商家自己这边需要设置，不会进行参数传值
    //例如：将uCode=1, mType =2, Secret =ABCD，TimeStamp=123456789参数名和参数值链接后，得到拼装字符串
    //mType2TimeStamp123456789uCode1，最后将Secret拼接到头尾，ABCDmType2TimeStamp123456789uCode1ABCD。
    //加密方式:
    //md5：进行md5加密后，再转化成大写，格式是：md5(ABCDmType2TimeStamp123456789uCode1ABCD)，最后得到
    private String sign;
    //标准时间戳、商家自行调整误差范围，建议在十分钟外不允许调用
    private String timeStamp;
    //是否成功
    private String result;
    //失败原因
    private String cause;
    //
}
