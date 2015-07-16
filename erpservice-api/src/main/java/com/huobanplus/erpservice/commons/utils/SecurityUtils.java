package com.huobanplus.erpservice.commons.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/7/14.
 */
public class SecurityUtils {

    private static class Holder {
        private static final SecurityUtils instance = new SecurityUtils();
    }
    private SecurityUtils() {
    }
    public static final SecurityUtils getInstance() {
        return Holder.instance;
    }

    public static boolean validateSign(String sign, String appKey, String operation, String capCode, String timeStamp)
    {
        if(null == sign || "".equals(sign.trim()))
        {
            return  false;
        }
        else if (!sign.equals(obtainLocalSign(appKey, operation, capCode, timeStamp)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private static String obtainLocalSign(String appKey, String operation, String capCode, String timeStamp)
    {
        //将appKey operation capCode timeStamp 封装处理后获取一个sign Copy
        Map<String, String> resultMap = new TreeMap<String, String>();
        resultMap.put("appKey",appKey );
        resultMap.put("operation",operation );
        resultMap.put("capCode",capCode );
        resultMap.put("timeStamp", timeStamp);

        StringBuilder strB = new StringBuilder();
        resultMap.keySet().stream().filter(key -> !"sign".equals(key)).forEach(key -> strB.append(resultMap.get(key)));
        try {
            return DigestUtils.md5DigestAsHex(strB.toString().getBytes("UTF-8")).toLowerCase();
        } catch (UnsupportedEncodingException e)
        {
            return  null;
        }
    }
}
