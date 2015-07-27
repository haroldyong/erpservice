package com.huobanplus.erpprovicer.huobanmall.common;

/**
 * 类描述：授权 签名字段
 * @author aaron
 * @since 2015年7月27日 上午10:26:48
 * @version V1.0
 */
public class AuthBean extends BaseResult {

    /**
     * 生成签名
     */
    private String sign;
    /**
     * 签名类型
     * 一般为MD5方式
     */
    private String signType;
    /**
     * 签名秘钥
     */
    private String secret;
    /**
     * 接入码
     * 主要区别不同的平台
     */
    private String uCode;
    /**
     * 当前时间戳
     */
    private String timeStamp;

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    /**
     * 请求方法名

     */
    private String mType;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode) {
        this.uCode = uCode;
    }
}
