package com.huobanplus.erpprovicer.huobanmall.util;

import com.huobanplus.erpprovicer.huobanmall.common.AuthBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 类描述：签名生成策略
 * 调用API 时需要对请求参数进行签名验证， 根据参数名称按照字母先后顺序排序:key + value .... key + value
 * 需要排列的参数包括uCode，mType，Secret,TimeStamp。其中Secret是密钥，会在esAPI里面填写，商家自己这边需要设置，不会进行参数传值
 * 例如：将uCode=1, mType =2, Secret =ABCD，TimeStamp=123456789参数名和参数值链接后，得到拼装字符串
 * mType2TimeStamp123456789uCode1，最后将Secret拼接到头尾，ABCDmType2TimeStamp123456789uCode1ABCD。
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
public class SignStrategy {

    /**
     * 单例模式的衍生形式
     */
    private static class Holder {
        private static final SignStrategy instance = new SignStrategy();
    }

    public static final SignStrategy getInstance() {
        return Holder.instance;
    }

    private SignStrategy()
    {

    }

    /**
     * 生成签名信息
     * @param authBean 签名参数实体
     * @return 返回签名参数
     */
    public AuthBean buildSign(AuthBean authBean)
    {
        Map<String, String> paramsMap;
        String sign = authBean.getSign();
        String signType = authBean.getSignType();
        if(Constant.SIGN_TYPE.equals(signType))
        {
            paramsMap = new HashMap<String, String>();
            //签名秘钥
            String secret = authBean.getSecret();
            //paramsMap.put(Constant.SIGN_SECRET, secret);
            //接入码
            String uCode = authBean.getuCode();
            paramsMap.put(Constant.SIGN_U_CODE, uCode);
            //时间戳
            String timeStamp = authBean.getTimeStamp();
            paramsMap.put(Constant.SIGN_TIME_STAMP, timeStamp);
            //请求方法
            String mType = authBean.getmType();
            paramsMap.put(Constant.SIGN_M_TYPE, mType);
            //根据信息生成签名信息
            sign = this.obtainSign(paramsMap, secret);
        }
        else
        {
            //非MD5签名方式
            sign = null;
            authBean.setSign(sign);
        }

        return authBean;
    }

    /**
     * 生成签名
     * 1、uCode、timeStamp、mType 按字母排序
     * 2、头尾加上secret
     * 3、MD5加密
     * 4、返回sign
     * @param paramsMap 签名参数的容器
     * @param secret 签名秘钥
     * @return 返回签名字符
     */
    private String obtainSign(Map<String, String> paramsMap, String secret)
    {
        String sign = null;
        StringBuilder builder = new StringBuilder();
        //1、按字母排序
        Map<String, String> resultMap = new TreeMap<String, String>();
        resultMap.putAll(paramsMap);
        //2、拼接字符串
        builder.append(secret);
        Iterator<Map.Entry<String, String>> it = resultMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, String> entry = it.next();
            builder.append(entry.getKey());
            builder.append(entry.getValue());
        }
        builder.append(secret);
        //3、MD5加密
        sign = EncryptUtil.getInstance().encryptMd532(builder.toString());
        return sign;
    }
}
