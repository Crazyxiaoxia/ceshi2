package com.haili.site.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * 日期时间相关
 * 
 * @author 王晓东 2012-9-7 下午1:58:46
 */
public abstract class ToolDateTime {

    private static Logger log = Logger.getLogger(ToolDateTime.class);

    public static final String pattern_ymd = "yyyy-MM-dd"; // pattern_ymd
    public static final String pattern_ymd_hms = "yyyy-MM-dd HH:mm:ss"; // pattern_ymdtime
    public static final String pattern_ymd_hms_s = "yyyy-MM-dd HH:mm:ss:SSS"; // pattern_ymdtimeMillisecond
    public static final String pattern_yyyymmdd = "yyyyMMdd"; // pattern_ymd

    /**
     * 主要是给jfinal使用，数据库只认java.sql.*
     * 
     * @param date
     * @return
     */
    public static Timestamp getSqlTimestamp() {
        return getSqlTimestamp(new Date().getTime());
    }

    /**
     * 主要是给jfinal使用，数据库只认java.sql.*
     * 
     * @param date
     * @return
     */
    public static Timestamp getSqlTimestamp(Date date) {
        if (null == date) {
            return getSqlTimestamp();
        }
        return getSqlTimestamp(date.getTime());
    }

    /**
     * 主要是给jfinal使用，数据库只认java.sql.*
     * 
     * @param time
     * @return
     */
    public static Timestamp getSqlTimestamp(long time) {
        return new java.sql.Timestamp(time);
    }

    /**
     * 主要是给jfinal使用，数据库只认java.sql.*
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Timestamp getSqlTimestamp(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return new java.sql.Timestamp(format.parse(date).getTime());
        } catch (ParseException e) {
            log.error("ToolDateTime.parse异常：date值" + date + "，pattern值" + pattern);
            return null;
        }
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取指定日期
     * 
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     * @return
     */
    public static Date getDate(int date, int hour, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的时间戳
     * 
     * @return
     */
    public static long getDateByTime() {
        return new Date().getTime();
    }

    /**
     * 格式化
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        DateFormat format;
        if (ToolString.isEmpty(pattern)) {
            format = new SimpleDateFormat(pattern_ymd_hms);
        } else {
            format = new SimpleDateFormat(pattern);
        }

        return format.format(date);
    }

    /**
     * 格式化
     * @param date
     * @param pattern
     * @param timeZone
     * @return
     */
    public static String format(Date date, String pattern, TimeZone timeZone) {
        DateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(timeZone);
        return format.format(date);
    }

    /**
     * 格式化
     * @param date
     * @param parsePattern
     * @param returnPattern
     * @return
     */
    public static String format(String date, String parsePattern, String returnPattern) {
        return format(parse(date, parsePattern), returnPattern);
    }

    /**
     * 格式化
     * @param date
     * @param parsePattern
     * @param returnPattern
     * @param timeZone
     * @return
     */
    public static String format(String date, String parsePattern, String returnPattern, TimeZone timeZone) {
        return format(parse(date, parsePattern), returnPattern, timeZone);
    }

    /**
     * 解析
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            log.error("ToolDateTime.parse异常：date值" + date + "，pattern值" + pattern);
            return null;
        }
    }

    /**
     * 解析
     * 
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
        Date date = null;
        try {
            date = DateFormat.getDateTimeInstance().parse(dateStr);
        } catch (ParseException e) {
            log.error("ToolDateTime.parse异常：date值" + date);
            return null;
        }
        return date;
    }

    /**
     * 两个日期的时间差，返回"X天X小时X分X秒"
     * 
     * @param start
     * @param end
     * @return
     */
    public static String getBetween(Date start, Date end) {
        long between = (end.getTime() - start.getTime()) / 1000;// 除以1000是为了转换成秒
        long day = between / (24 * 3600);
        long hour = between % (24 * 3600) / 3600;
        long minute = between % 3600 / 60;
        long second = between % 60 / 60;

        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("小时");
        sb.append(minute);
        sb.append("分");
        sb.append(second);
        sb.append("秒");

        return sb.toString();
    }

    /**
     * 返回两个日期之间隔了多少分钟
     * 
     * @param start
     * @param end
     * @return
     */
    public static int getDateMinuteSpace(Date start, Date end) {
        int hour = (int) ((end.getTime() - start.getTime()) / (60 * 1000));
        return hour;
    }

    /**
     * 返回两个日期之间隔了多少小时
     * 
     * @param start
     * @param end
     * @return
     */
    public static int getDateHourSpace(Date start, Date end) {
        int hour = (int) ((end.getTime() - start.getTime()) / (60 * 60 * 1000));
        return hour;
    }

    /**
     * 返回两个日期之间隔了多少天
     * 
     * @param start
     * @param end
     * @return
     */
    public static int getDateDaySpace(Date start, Date end) {
        int day = (int) ((end.getTime() - start.getTime()) / (60 * 60 * 24 * 1000));
        return day;
    }

    /**
     * 得到某一天是星期几
     * 
     * @param strDate
     *            日期字符串
     * @return String 星期几
     */
    @SuppressWarnings("static-access")
    public static String getDateInWeek(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayIndex = calendar.get(calendar.DAY_OF_WEEK) - calendar.SUNDAY;
        if (dayIndex < 0) {
            dayIndex = 0;
        }
        return weekDays[dayIndex];
    }

    /**
     * 日期减去多少个小时
     * 
     * @param date
     * @param hourCount
     *            多少个小时
     * @return
     */
    public static Date getDateReduceHour(Date date, long hourCount) {
        long time = date.getTime() - 3600 * 1000 * hourCount;
        Date dateTemp = new Date();
        dateTemp.setTime(time);
        return dateTemp;
    }

    /**
     * 日期区间分割
     * 
     * @param start
     * @param end
     * @param splitCount
     * @return
     */
    public static List<Date> getDateSplit(Date start, Date end, long splitCount) {
        long startTime = start.getTime();
        long endTime = end.getTime();
        long between = endTime - startTime;

        long count = splitCount - 1l;
        long section = between / count;

        List<Date> list = new ArrayList<Date>();
        list.add(start);

        for (long i = 1l; i < count; i++) {
            long time = startTime + section * i;
            Date date = new Date();
            date.setTime(time);
            list.add(date);
        }

        list.add(end);

        return list;
    }

    /**
     * 返回两个日期之间隔了多少天，包含开始、结束时间
     * 
     * @param start
     * @param end
     * @return
     */
    public static List<String> getDaySpaceDate(Date start, Date end) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        List<String> dateList = new LinkedList<String>();

        long dayCount = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
        if (dayCount < 0) {
            return dateList;
        }

        dateList.add(format(fromCalendar.getTime(), pattern_ymd));

        for (int i = 0; i < dayCount; i++) {
            fromCalendar.add(Calendar.DATE, 1);// 增加一天
            dateList.add(format(fromCalendar.getTime(), pattern_ymd));
        }

        return dateList;
    }

    /**
     * 获取开始时间
     * 
     * @param start
     * @param end
     * @return
     */
    public static Date startDateByDay(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, end);// 明天1，昨天-1
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取结束时间
     * 
     * @param start
     * @param end
     * @return
     */
    public static Date endDateByDay(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取开始时间
     * 
     * @param start
     * @param end
     * @return
     */
    public static Date startDateByHour(Date start, int end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.set(Calendar.MINUTE, end);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取结束时间
     * 
     * @param start
     * @param end
     * @return
     */
    public static Date endDateByHour(Date end) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 根据年份和周得到周的开始和结束日期
     * 
     * @param year
     * @param week
     * @return
     */
    public static Map<String, Date> getStartEndDateByWeek(int year, int week) {
        Calendar weekCalendar = new GregorianCalendar();
        weekCalendar.set(Calendar.YEAR, year);
        weekCalendar.set(Calendar.WEEK_OF_YEAR, week);
        weekCalendar.set(Calendar.DAY_OF_WEEK, weekCalendar.getFirstDayOfWeek());

        Date startDate = weekCalendar.getTime(); // 得到周的开始日期

        weekCalendar.roll(Calendar.DAY_OF_WEEK, 6);
        Date endDate = weekCalendar.getTime(); // 得到周的结束日期

        // 开始日期往前推一天
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        startCalendar.add(Calendar.DATE, 1);// 明天1，昨天-1
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        startDate = startCalendar.getTime();

        // 结束日期往前推一天
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DATE, 1);// 明天1，昨天-1
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        endDate = endCalendar.getTime();

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("start", startDate);
        map.put("end", endDate);
        return map;
    }

    /**
     * 根据日期月份，获取月份的开始和结束日期
     * 
     * @param date
     * @return
     */
    public static Map<String, Date> getMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 得到前一个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date start = calendar.getTime();

        // 得到前一个月的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = calendar.getTime();

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("start", start);
        map.put("end", end);
        return map;
    }

    /**
     * 分割List
     * 
     * @param list
     *            待分割的list
     * @param pageSize
     *            每段list的大小
     * @return List<<List<T>>
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int listSize = list.size(); // list的大小
        int page = (listSize + (pageSize - 1)) / pageSize; // 页数

        List<List<T>> listArray = new ArrayList<List<T>>();// 创建list数组
                                                           // ,用来保存分割后的list

        for (int i = 0; i < page; i++) { // 按照数组大小遍历
            List<T> subList = new ArrayList<T>(); // 数组每一位放入一个分割后的list
            for (int j = 0; j < listSize; j++) { // 遍历待分割的list
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize; // 当前记录的页码(第几页)
                if (pageIndex == (i + 1)) { // 当前记录的页码等于要放入的页码时
                    subList.add(list.get(j)); // 放入list中的元素到分割后的list(subList)
                }

                if ((j + 1) == ((j + 1) * pageSize)) { // 当放满一页时退出当前循环
                    break;
                }
            }
            listArray.add(subList); // 将分割后的list放入对应的数组的位中
        }
        return listArray;
    }

    /**
     * 判断当前发薪日，是否在当前月的前后十天之内
     * @param date
     * @return
     */
    public static boolean timeDifference(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            Date preDate = calendar.getTime();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            Date nowDate2 = new Date();
            if (compareTime(preDate, nowDate2, backDate)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断当前发薪日，是否在当前月的前后十天之内
     * @param date
     * @return
     */
    public static Map<String, String> nowMonthRange(String date) {
        Map<String, String> map = new HashMap<String, String>();
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            Date preDate = calendar.getTime();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            //返回前十天 
            map.put("before", sd.format(preDate));
            //返回后十天
            map.put("after", sd.format(backDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
     * 判断当前发薪日，是否在上个月的前后十天之内
     * @param date
     * @return
     */
    public static boolean timePreDifference(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String now = sdf.format(calendar.getTime());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            Date preDate = calendar.getTime();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            Date nowDate2 = new Date();
            if (compareTime(preDate, nowDate2, backDate)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断当前发薪日，是否在下个月的前后十天之内
     * @param date
     * @return
     */
    public static boolean timeNextDifference(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        String now = sdf.format(calendar.getTime());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            Date preDate = calendar.getTime();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            Date nowDate2 = new Date();
            if (compareTime(preDate, nowDate2, backDate)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断当前发薪日，是否在上个月的前后十天之内
     * @param date
     * @return
     */
    public static Map<String, String> preMonthRanges(String date) {
        Map<String, String> map = new HashMap<String, String>();
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, -10);
            Date preDate = calendar.getTime();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            map.put("before", sd.format(preDate));
            map.put("after", sd.format(backDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    public static boolean compareTime(Date date1, Date date2, Date date3) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now;
        try {
            if (date2 == null) {
                now = sdFormat.format(new Date());
            } else {
                now = sdFormat.format(date2);
            }
            List<String> list = getDaySpaceDate(date1, date3);
            if (list.contains(now)) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /* if (date2.getTime() >= date1.getTime() && date2.getTime() <= date3.getTime()) {
             return true;
         } else {
             return false;
         }*/
        return false;
    }

    public static boolean compareTime(String date1, String date2, String date3) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now;
        try {
            if (date2 == null) {
                now = sdFormat.format(new Date());
            } else {
                now = date2;
            }
            Date date = sdFormat.parse(date1);
            Date date4 = sdFormat.parse(date3);
            List<String> list = getDaySpaceDate(date, date4);
            if (list.contains(now)) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /* if (date2.getTime() >= date1.getTime() && date2.getTime() <= date3.getTime()) {
             return true;
         } else {
             return false;
         }*/
        return false;
    }

    public static boolean isNow(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "-0" + date;
        } else {
            str = "-" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        String cureent = now + str;
        Date nowDate;
        try {
            nowDate = sd.parse(cureent);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date backDate = calendar.getTime();
            Date nowDate2 = new Date();
            if (nowDate2.equals(backDate)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //日期转当月
    public static String convert(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "0" + date;
        } else {
            str = date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String now = sdf.format(new Date());
        String cureent = now + str;
        return cureent.replace("-", "");

    }

    //日期转上个月
    public static String convertPre(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "0" + date;
        } else {
            str = date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String preDate = sdf.format(calendar.getTime());
        String dates = preDate.split("-")[0] + preDate.split("-")[1] + str;
        return dates;

    }

    //日期转下个月
    public static String convertNext(String date) {
        String str = "";
        if (Integer.valueOf(date) < 10) {
            str = "0" + date;
        } else {
            str = date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        String preDate = sdf.format(calendar.getTime());
        String dates = preDate.split("-")[0] + preDate.split("-")[1] + str;
        return dates;

    }

    //得到本月第一天
    public static String getCorrentMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String dateStr = null;
        try {
            dateStr = convertDateToString(calendar.getTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    //获取本月11号之前
    public static String getCorrentMonthTenDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 11);
        String dateStr = null;
        try {
            dateStr = convertDateToString(calendar.getTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getCorrentMonthAfterDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        String dateStr = null;
        try {
            dateStr = convertDateToString(calendar.getTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String getCorrentMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String dateStr = null;
        try {
            dateStr = convertDateToString(calendar.getTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static String convertDateToString(Date date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static boolean isPreDay(Date date) {
        boolean flag = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(getCorrentMonthFirstDay());
            Date date2 = sdf.parse(getCorrentMonthTenDay());
            if (compareTime(date1, date, date2)) {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean isAfterDay(Date date) {
        boolean flag = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(getCorrentMonthAfterDay());
            Date date2 = sdf.parse(getCorrentMonthLastDay());
            if (compareTime(date1, date, date2)) {
                return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean isBefore(String date) {
        boolean flag = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date2 = new Date();
            Map<String, String> map = getRange(date);
            String fString = sdf.format(date2).split("-")[0] + "-" + sdf.format(date2).split("-")[1];
            String date1 = fString + "-" + map.get("before");
            String date3 = fString + "-" + date;
            Date minDate = sdf.parse(date1);
            Date maxDate = sdf.parse(date3);
            if (compareTime(minDate, date2, maxDate)) {
                flag = true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static Map<String, String> getRange(String date) {
        String day = getCorrentMonthLastDay().split("-")[2];
        Map<String, String> map = new HashMap<String, String>();
        if (Integer.valueOf(date) <= 10) {
            map.put("after", String.valueOf(Integer.valueOf(date) + 10));
            map.put("before", String.valueOf(Integer.valueOf(day) - Integer.valueOf(date)));
        } else {
            String value;
            if ((Integer.valueOf(date) + 10) > Integer.valueOf(day)) {
                value = String.valueOf((Integer.valueOf(date) + 10) - Integer.valueOf(day));
            } else {
                value = String.valueOf(Integer.valueOf(day) - Integer.valueOf(date));
                if (value.equals("0")) {
                    value = String.valueOf(day);
                }
            }
            map.put("after", value);
            map.put("before", String.valueOf(Integer.valueOf(date) - 10));
        }
        return map;
    }

    public static int getPreMonthTotalDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return day;
    }

    public static int getNowMonthTotalDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return day;
    }

    public static int getNowDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    public static boolean isInRanges(String date) {
        boolean flag = false;
        if (Integer.valueOf(date) < 10) {
            date = "0" + date;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nowdate = new Date();
            String dString = sdf.format(nowdate);
            String fString = dString.split("-")[0] + "-" + dString.split("-")[1] + "-" + date;
            Date d = sdf.parse(fString);
            Date now = sdf.parse(dString);
            int i = d.compareTo(now);
            if (i >= 0) {
                flag = true;
            }
            //  flag = d.before(nowdate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean isInRange(String date) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nowdate = new Date();
            String dString = sdf.format(nowdate);
            String fString = dString.split("-")[0] + "-" + dString.split("-")[1] + "-" + date;
            Date d = sdf.parse(fString);
            flag = d.before(nowdate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

    public static Map<String, String> getTime(String date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> map = new HashMap<String, String>();
        try {
            Calendar now = Calendar.getInstance();
            Date date2 = sd.parse(date);
            now.setTime(date2);
            now.add(Calendar.DAY_OF_MONTH, 10);
            String after = sd.format(now.getTime());
            map.put("after", after);
            Calendar now1 = Calendar.getInstance();
            now1.setTime(date2);
            now1.add(Calendar.DAY_OF_MONTH, -10);
            String before = sd.format(now1.getTime());
            map.put("before", before);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isAfterOneday(String payDay) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar pre = Calendar.getInstance();
            Date date2 = sdf.parse(payDay);
            pre.setTime(date2);
            pre.add(Calendar.DATE, 1);
            Date preDates = pre.getTime();
            String preDate = sdf.format(preDates);
            Date date = new Date();
            String now = sdf.format(date);
            if (now.equals(preDate)) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return flag;

    }

    public static boolean isAfterNow(String payDay) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar pre = Calendar.getInstance();
            Date date2 = sdf.parse(payDay);
            pre.setTime(date2);
            pre.add(Calendar.DATE, 1);
            Date preDates = pre.getTime();
            Date date = new Date();
            if (date.after(preDates)) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return flag;

    }

    /**
     * 日期计算
     * @param diff 加减天数，正负
     * @param _date 日期
     * @return
     */
    public static String checkOption(int diff, String _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        if (diff < 0) {
            // 时间减一天  
            cl.add(Calendar.DAY_OF_MONTH, diff);

        } else if (diff > 0) {
            // 时间加一天  
            cl.add(Calendar.DAY_OF_YEAR, diff);
        }
        date = cl.getTime();
        return sdf.format(date);
    }

    /**
     * 获取上个月的最后一天
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String dateStr = null;
        try {
            dateStr = ToolDateTime.convertDateToString(calendar.getTime(), "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 判断上个月是否是2月份
     * @return
     */
    public static boolean isFeb() {
        boolean flag = false;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String day = getMonthLastDay().split("-")[1];
        if (day.equals("02")) {
            return true;
        }
        return flag;
    }

    /**
     * 获取当前月份
     * @param date
     * @return
     */
    public static String getNowMonth(String date) {
        if (Integer.valueOf(date) < 10) {
            date = "0" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(new Date());
        String date1 = dateString.split("-")[0];
        String date2 = dateString.split("-")[1];
        return date1 + date2 + date;
    }

    /**
     * 获取下个月
     * @param date
     * @return
     */
    public static String getNext(String date) {
        if (Integer.valueOf(date) < 10) {
            date = "0" + date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date dates = cal.getTime();
        String dateString = sdf.format(dates);
        String date1 = dateString.split("-")[0];
        String date2 = dateString.split("-")[1];
        return date1 + date2 + date;
    }

    /**
     * 获取前一个月
     * @param date
     * @return
     */
    public static String getPreMonth(String date) {
        if (Integer.valueOf(date) < 10) {
            date = "0" + date;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String date1 = getMonthLastDay().split("-")[0];
        String date2 = getMonthLastDay().split("-")[1];
        return date1 + date2 + date;
    }

    /**
     * 获取前一个月
     * @param date
     * @return
     */
    public static String getMonth(String date) {
        if (Integer.valueOf(date) < 10) {
            date = "0" + date;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String date1 = getMonthLastDay().split("-")[0];
        String date2 = getMonthLastDay().split("-")[1];
        return date1 + "-" + date2 + "-" + date;
    }

    /**
     * 获取1到10号之间的前后十天
     * @param date
     * @return
     */
    public static Map<String, String> getcurrentRange(String date) {
        int day = ToolDateTime.getNowMonthTotalDay();
        Map<String, String> map = new HashMap<String, String>();
        map.put("before", String.valueOf((day + Integer.valueOf(date) - 10)));
        map.put("after", String.valueOf((Integer.valueOf(date) + 10)));
        return map;
    }

    /**
     * 获取20号之后的前后十天
     * @param date
     * @return
     */
    public static Map<String, String> getRanges(String date) {
        int day = ToolDateTime.getPreMonthTotalDay();
        Map<String, String> map = new HashMap<String, String>();
        map.put("before", String.valueOf((Integer.valueOf(date) - 10)));
        map.put("after", String.valueOf((10 - (day - Integer.valueOf(date)))));
        return map;
    }

    public static void main(String[] args) {
        System.err.println(timePreDifference("5"));
    }
}
