package com.dcstd.web.ecspserver.utils;

/**
 * @FileName DateUtil
 * @Description
 * @Author fazhu
 * @date 2024-08-14
 **/
import cn.hutool.core.date.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// cron 和 Date 相互转化
public class DateUtil {

    private static final String DATEFORMAT = "ss mm HH dd MM ? yyyy";

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * @param cron
     * @param dateFormat
     * @return
     * @throws ParseException
     */
    public static Date parseStringToDate(String cron, String dateFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        if (cron != null) {
            date = sdf.parse(cron);
        }
        return date;
    }

    /***
     * Date转化cron
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date date) {
        return formatDateByPattern(date, DATEFORMAT);
    }

    /***
     * cron转化Date
     * @param cron  : cron表达式 cron表达式仅限于周为*
     * @return
     */
    public static Date getDate(String cron) throws ParseException {
        return parseStringToDate(cron, DATEFORMAT);
    }

    /**
     * 获取本周周一时间
     * @return (Date)
     */
    public static Date getThisWeekMonday() {
        return getThisWeekMonday(DateTime.now());
    }

    /**
     * 获取指定周周一时间
     * @param date 现在时间
     * @return (Date)
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取当天零点时间
     * @return (Date)
     */
    public static Date getThisDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


}
