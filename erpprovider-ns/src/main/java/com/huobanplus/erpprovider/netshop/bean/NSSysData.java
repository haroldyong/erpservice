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
}
