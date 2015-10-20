/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpservice.eventhandler.model;

import java.io.Serializable;

/**
 * 类描述：公共参数
 * @author aaron
 * @since  2015年7月27日 上午10:24:35
 * @version V1.0
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

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
