/*
 * *
 *  * @author guomw
 *  *
 *
 */

package com.huobanplus.erpprovider.lz.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * LocalDateTime工具
 *
 * @author hot
 * @date 2017/5/31
 */
public class LocalDateTimeUtils {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    /**
     * 获取第二天
     *
     * @param date
     * @return
     */
    public static String getNextDay(String date) {
        LocalDate now = stringToLocalDate(date);
        return now.plusDays(1).format(dateFormatter);
    }

    /**
     * 获取第二天
     *
     * @return
     */
    public static LocalDateTime getNext(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(localDateTime.plusDays(1).format(formatter)).atStartOfDay();
    }


    public static String getNext(String date) {
        LocalDateTime now = convertToDate(date);
        LocalDateTime next = getNext(now);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return next.format(dateTimeFormatter);
    }

    /**
     * {@link String} 转 {@link LocalDate},格式默认为 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    public static LocalDateTime convert(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return localDate.atStartOfDay();
    }

    public static LocalDateTime convert(String date, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return localDate.atStartOfDay();
    }


    public static String convert(LocalDateTime date) {
        if (date != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return date.format(dateTimeFormatter);
        }
        return "";
    }

    public static String convert(LocalDateTime date, String format) {
        if (date != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            return date.format(dateTimeFormatter);
        }
        return "";
    }

    public static LocalDateTime convertToDate(String date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return localDate.atStartOfDay();
    }

    public static String convertTODate(LocalDateTime date) {
        if (date != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return date.format(dateTimeFormatter);
        }
        return "";
    }

    /**
     * {@link LocalDateTime} 转 {@link String},格式默认为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String localDateTimeToString(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(dateTimeFormatter);
    }

    public static String localDateTimeToString(LocalDateTime date, String format) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return date.format(dateTimeFormatter);
    }

    /**
     * 获取今天的凌晨时间
     *
     * @return
     */
    public static LocalDateTime getStartDate() {
        return LocalDate.now().atStartOfDay();
    }


    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }


    /**
     * {@link String} 转 {@link LocalDateTime},格式默认为 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @param format
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

    /**
     * 时间转时间戳 秒
     *
     * @param time
     * @return
     */
    public static long dateToTimestamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime() / 1000;
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }


    public static long dateToLong(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        try {
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            return ts;
        } catch (ParseException e) {
            return 0;
        }
    }

    public  static String longToDate(Long time){
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
        return simpleDateFormat.format(date);
    }
    /**
     * LocalDateTime 日期时间转时间戳
     *
     * @param now
     * @return
     */
    public static long LocalDateTimeToTimestamp(LocalDateTime now) {
        return now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 周几
     *
     * @param date
     * @return
     */
    public static String getWeekName(String date) {
        DayOfWeek dayOfWeek = stringToLocalDate(date).getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return "周一";
            case TUESDAY:
                return "周二";
            case WEDNESDAY:
                return "周三";
            case THURSDAY:
                return "周四";
            case FRIDAY:
                return "周五";
            case SATURDAY:
                return "周六";
            case SUNDAY:
                return "周日";
            default:
                return "";
        }
    }
}
