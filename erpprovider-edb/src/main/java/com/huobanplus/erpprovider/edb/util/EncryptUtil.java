package com.huobanplus.erpprovider.edb.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * 类描述：采用 Apache codec 组件实现MD5加密
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
public class EncryptUtil {

    private static class Holder
    {
        private static final EncryptUtil instance = new EncryptUtil();
    }

    private EncryptUtil()
    {

    }

    public static final EncryptUtil getInstance()
    {
        return Holder.instance;
    }

    public String encryptMd532(String source)
    {
        if (null == source || "".equals(source.trim()))
        {
            return null;
        } else
        {
            return DigestUtils.md5Hex(source);
        }
    }
}
