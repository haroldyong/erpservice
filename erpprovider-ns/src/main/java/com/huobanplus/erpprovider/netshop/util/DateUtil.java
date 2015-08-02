package com.huobanplus.erpprovider.netshop.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 类描述：时间相关操作类
 * @author aaron
 * @since 2015年7月25日 下午4:30:43
 * @version V1.0
 */
public class DateUtil {

    /**
     * 时间long型转String型
     * @param currentTime 待转型时间
     * @param formatText 时间格式
     * @return 返回格式化后的时间
     */
    public static String formatDate(Long currentTime, String formatText)
    {
        DateFormat format = null;
        try
        {
            format = new SimpleDateFormat(formatText);
            Date date = new Date(currentTime);
            return format.format(date);
        } catch(Exception e)
        {
            return format.format(new Date());
        }
    }
}
