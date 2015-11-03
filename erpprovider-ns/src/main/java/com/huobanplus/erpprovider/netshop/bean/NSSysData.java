/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huobanplus.erpprovider.netshop.bean;

/**
 * Created by liual on 2015-08-26.
 */
public class NSSysData {
    /**
     * <p>接入码，用于验证请求的有效性。主要用于区分店铺</p>
     */
    private int uCode;
    /**
     * <p>Secret是密钥，会在esAPI里面填写，商家自己这边需要设置，不会进行参数传值</p>
     */
    private int secret;
    /**
     * 时间戳
     */
    private long timeStamp;
    /**
     * 方法名
     */
    private String mType;

    public int getuCode() {
        return uCode;
    }

    public void setuCode(int uCode) {
        this.uCode = uCode;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
