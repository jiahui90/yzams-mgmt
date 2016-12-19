/*
 * DateUtil.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-02-17 10:21:24
 */
package com.yz.ams.client.util;

import com.nazca.util.NazcaFormater;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Your Name <Song Haixiang >
 */
public class DateUtil {
    
    public static String catStartTime(Date d, boolean m) {
        StringBuilder sb = new StringBuilder(NazcaFormater.getSimpleDateString(d));
        if (m) {
            sb.append(" 上午");
        } else {
            sb.append(" 下午");
        }
        return sb.toString();
    }
    
    public static String catEndTime(Date d, boolean m, double days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        //TODO算法有问题，再考虑
//        long count = Math.round(days);
//        c.add(Calendar.DATE, (int) count);
//        Date end = c.getTime();
//        StringBuilder sb = new StringBuilder(NazcaFormater.getSimpleDateString(end));
//        if (m) {
//            sb.append(" 上午");
//        } else {
//            sb.append(" 下午");
//        }
        StringBuilder sb = null;
        if (days % 1 == 0) {
            c.add(Calendar.DATE, (int) days);
            Date end = c.getTime();
            sb = new StringBuilder(NazcaFormater.getSimpleDateString(end));
            if (m) {
                sb.append(" 上午");
            } else {
                sb.append(" 下午");
            }
        }else{
            c.add(Calendar.DATE, (int) days + 1);
            Date end = c.getTime();
            sb = new StringBuilder(NazcaFormater.getSimpleDateString(end));
            if (m) {
                sb.append(" 下午");
            } else {
                sb.append(" 上午");
            }
        }
        return sb.toString();
    }
    
    /**
     * 获取在某一时间上加上XX小时的0分0秒
     *
     * @param d
     * @param hour
     * @return
     */
    public static Date getLastHourStartTime(Date d, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取在某一时间上加上XX小时的59分59秒
     *
     * @param d
     * @param hour
     * @return
     */
    public static Date getLastHourEndTime(Date d, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 在某一时间上加XX小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour2Date(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();

    }

    /**
     * 在某一时间上加上XX分
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute2Date(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 在某一时间上加上XX天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDay2Date(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }
    
    /**
     * 在某一时间上加上XX年
     *
     * @param date
     * @param year
     * @return
     */
    public static Date addYear2Date(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 在某一月上加某月
     *
     * @param year
     * @param month
     * @param addMonth
     * @return
     */
    public static Date addMonthByYearAndMontn(int year, int month, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.add(Calendar.MONTH, addMonth);
        return calendar.getTime();
    }

    /**
     * 在某一时间上加上XX月
     *
     * @param curDate
     * @param month
     * @return
     */
    public static Date addMonthByDate(Date curDate, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 在某一时间上加上XX秒
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addSecond2Date(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();

    }

    /**
     * 根据时间获取该日期是一周中的周几
     *
     * @param d
     * @return
     */
    public static int getDayOfWeed(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据时间获取该天的最大时间
     *
     * @param d
     * @return
     */
    public static Date getMaxTimeOfDay(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }
    
     /**
     * 根据时间获取该天的00分00秒
     *
     * @param d
     * @return
     */
    public static Date getZeroTimeOfDay(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 根据时间获取该天的12点
     *
     * @param d
     * @return
     */
    public static Date getMiddleTimeOfDay(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 根据时间获取该天的最小时间
     *
     * @param d
     * @return
     */
    public static Date getMinTimeOfDay(Date d) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 设置某一天的时间为XX小时整
     *
     * @param d
     * @param hour
     * @return
     */
    public static Date setHour2Date(Date d, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 根据时间获取该周的最小天
     *
     * @param d
     * @return
     */
    public static Date getOneWeekMinTime(Date d) {
        int day = getDayOfWeed(d);
        Date std = getMinTimeOfDay(addDay2Date(d, -day - 6 + 1));
        return std;
    }

    /**
     * 根据时间获取该周的最大天
     *
     * @param d
     * @return
     */
    public static Date getOneWeekMaxTime(Date d) {
        int day = getDayOfWeed(d);
        Date std = getMaxTimeOfDay(addDay2Date(d, -day + 1));
        return std;
    }

    /**
     * 根据时间获取小时
     *
     * @param d
     * @return
     */
    public static int getHourOfDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 根据时间获取分钟
     *
     * @param d
     * @return
     */
    public static int getMinuteOfDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MINUTE);
    }

    public static int getSecondOfDate(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.SECOND);
    }

    /**
     * 根据时间获取该年的第几周，格式为 yyyyWW
     *
     * @param d
     * @return
     */
    public static int getWeekOfYear(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int y = cal.get(Calendar.YEAR);
        int w = cal.get(Calendar.WEEK_OF_YEAR);
        String str = y + "";
        if (w < 10) {
            str = str + "0" + w;
        } else {
            str = str + w;
        }
        return Integer.parseInt(str);
    }

    /**
     * 获取两个日期相差的分钟数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getMinuteDiffByDates(Date d1, Date d2) {
        int minute = 0;
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long diff = l1 - l2;
        minute = (int) diff / 1000 / 60;
        return minute;
    }

    /**
     * 获取一个月中的最小天
     *
     * @param d
     * @return
     */
    public static Date getMinDateByMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取一个月中的最大天
     *
     * @param d
     * @return
     */
    public static Date getMaxDateByMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 将long类型的时间转化为date
     *
     * @param time
     * @return
     */
    public static Date buildTimeLong2Date(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return cal.getTime();
    }

    /**
     * 获取年
     *
     * @param d
     * @return
     */
    public static int getYearByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取月
     *
     * @param d
     * @return
     */
    public static int getMonthByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    /**
     * 获取月里面的天
     *
     * @param d
     * @return
     */
    public static int getDayOfMonthByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    /**
     * 获取一年中的第几周
     *
     * @param d
     * @return
     */
    public static int getWeekByDate(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }

    /**
     * 拼接时间
     *
     * @param year
     * @param week
     * @return
     */
    public static Date buildDateByYearAndWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * 获取一周的工作日
     *
     * @return 返回工作日 日期
     */
    public static Date[] getWeekDay(Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        Date[] dates = new Date[5];
        for (int i = 0; i < 5; i++) {
            dates[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * 根据年和周获取每周的星期一
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getWeekDateByYearAndWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 根据年和周计算加一周后的年和周
     *
     * @param year
     * @param week
     * @return
     */
    public static String getYearAndWeekByYearAndWeek(int year, int week) {
        String str = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekDateByYearAndWeek(year, week));
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        int year1 = cal.get(Calendar.YEAR);
        int week1 = cal.get(Calendar.WEEK_OF_YEAR);
//        if(week1 == 1){
//            year1 += 1;
//        }
        str = year1 + "_" + week1;
        return str;
    }

    /**
     * 获取工作日的早高峰开始时间
     *
     * @param d
     * @return
     */
    public static Date getWorkDayAmPeakStartTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取工作日的早高峰结束时间
     *
     * @param d
     * @return
     */
    public static Date getWorkDayAmPeakEndTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取工作日的晚高峰开始时间
     *
     * @param d
     * @return
     */
    public static Date getWorkDayPmPeakStartTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取工作日的晚高峰结束时间
     *
     * @param d
     * @return
     */
    public static Date getWorkDayPmPeakEndTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 19);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周末和节假日的早高峰开始时间
     *
     * @param d
     * @return
     */
    public static Date getWeekendAmPeakStartTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周末和节假日的早高峰结束时间
     *
     * @param d
     * @return
     */
    public static Date getWeekendAmPeakEndTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周末和节假日的晚高峰开始时间
     *
     * @param d
     * @return
     */
    public static Date getWeekendPmPeakStartTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 16);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周末和节假日的晚高峰结束时间
     *
     * @param d
     * @return
     */
    public static Date getWeekendPmPeakEndTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 判断两天是否是同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSameDay(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH);
        int day1 = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(d2);
        int year2 = cal.get(Calendar.YEAR);
        int month2 = cal.get(Calendar.MONTH);
        int day2 = cal.get(Calendar.DAY_OF_MONTH);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取小时和分钟并转化为小时
     *
     * @param d
     * @return
     */
    public static double getHourAndMinute(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        double t = 0;
        int hour = getHourOfDate(d);
        double minute = (double) getMinuteOfDate(d) / 60;
        t = hour + minute;
        return t;
    }

    /**
     * 将bufferedimage根据转化为图片类型为imgtype的byte数组
     *
     * @param img
     * @param imgType
     * @return
     */
    public static byte[] imageToByte(BufferedImage img, String imgType) {
        if (img != null) {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            try {
                ImageIO.write(img, imgType, bo);
                return bo.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 将byte数组转化为buffeedimage
     *
     * @param b
     * @return
     */
    public static BufferedImage byteToImage(byte[] b) {
        if (b != null) {
            try {
                return ImageIO.read(new ByteArrayInputStream(b));
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 根据起始的年和月获取到目前上一个月之间的年和月
     *
     * @param year 起始的年
     * @param month 起始的月
     * @return
     */
    public static List<String> getMonths(int year, int month) {
        List<String> dates = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        while (cal.compareTo(c) < 0) {
            dates.add(new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
            cal.add(Calendar.MONTH, 1);
        }
        Collections.reverse(dates);
        return dates;
    }

    /**
     * 根据传入的日期判断是否是工作日
     *
     * @param date 需要判断的日期
     * @return true表示是工作日、false表示不是工作日
     */
    public static boolean isWorkingDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek >= 2 && dayOfWeek <= 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据开始时间和结束时间获取从开始时间到结束时间的全部日期（yyyy-MM-dd）
     *
     * @param endtDate 开始时间
     * @param endDate 结束时间
     * @return 返回时间集合
     */
    public static List<Date> getDays(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (cal.compareTo(c) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * 根据开始时间和结束时间获取从开始时间到结束时间的全部日期(yyyy-MM)
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 返回时间集合
     */
    public static List<Date> getMonths(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (cal.compareTo(c) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.MONTH, 1);
        }
        return dates;
    }

    /**
     * 格式化时间 yyyy-MM-dd HH:mm:ss
     *
     * @param date 需要格式化的时间
     * @return 格式化后的字符串时间
     */
    public static String getDateTimeFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date str2Date(String str) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(str);
    }
    public static void main(String[] args){
        try {
            System.out.println(str2Date("2016-07-07 15:07:28"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 计算指定年度共有多少个周。
     *
     * @param year 格式 yyyy ，必须大于1900年度 小于9999年
     * @return
     */
    public static int getWeekNumByYear(final int year) {
        if (year < 1900 || year > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        int result = 52;//每年至少有52个周 ，最多有53个周。 
        Date date = getYearWeekFirstDay(year, 53);
        if (NazcaFormater.getSimpleDateString(date).substring(0, 4).equals(year + "")) { //判断年度是否相符，如果相符说明有53个周。 
            result = 53;
        }
        return result;
    }

    /**
     * 计算某年某周的开始日期
     *
     * @param yearNum 格式 yyyy ，必须大于1900年度 小于9999年
     * @param weekNum 1到52或者53
     * @return 日期，格式为yyyy-MM-dd
     */
    public static Date getYearWeekFirstDay(int yearNum, int weekNum) {
        if (yearNum < 1900 || yearNum > 9999) {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一 
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//每周从周一开始 
        // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。 
        cal.setMinimalDaysInFirstWeek(7); //设置每周最少为7天 
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);

        //分别取得当前日期的年、月、日 
        return cal.getTime();
    }

    /**
     * 根据指定的日期返回从指定的日期到当前年
     *
     * @param year 其实年
     * @return
     */
    public static List<String> getYear(int year) {
        List<String> dates = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        while (cal.compareTo(c) < 0) {
            dates.add(getDateTimeFormat(cal.getTime()).substring(0, 4));
            cal.add(Calendar.YEAR, 1);
        }
        dates.add(getDateTimeFormat(c.getTime()).substring(0, 4));
        return dates;
    }

    /**
     * 根据年和月获取该月的第一天
     *
     * @param year 年
     * @param monday 月
     * @return
     */
    public static Date getMonthByYearAndMonth(int year, int monday) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monday);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 根据年和月计算该月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDayByYearAndMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取周
     *
     * @param curDate 需要计算的时间
     * @param week1 需要计算的星期
     * @param week2 获取星期几
     * @return
     */
    public static Date getlastWeekMon(Date curDate, int week1, int week2) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, week1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, week2 + 1);
        return cal.getTime();
    }

    public static List<Date> getDayByTime(Date curDate, int day) {
        List<Date> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(curDate);
        cal.add(Calendar.DATE, day);
        while (cal.compareTo(c) <= 0) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static Date str2Date(String dateStr, String dataFormat) {
        try {
            DateFormat df = new SimpleDateFormat(dataFormat);
            return df.parse(dateStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String date2Str(Date date, String dataFormat) {
        DateFormat df = new SimpleDateFormat(dataFormat);
        return df.format(date);
    }

    public static Date changeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date getMinTimeByDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
     /**
     * 鏍规嵁骞村拰鏈堣幏鍙栬鏈堢殑绗竴澶╃殑鏈�灏忔椂闂�
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getMinDateByYearAndMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * 鏍规嵁骞村拰鏈堣幏鍙栬鏈堟渶鍚庝竴澶╃殑鏈�澶ф椂闂�
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getMaxDateByYearAndMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    /**
     * 一个月的工作日数
     * @param date
     * @return 
     */
    public static int getMonthDays(Date d) {
        
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBegin=DateUtil.getMinDateByMonth(new Date()); 
        Calendar date = Calendar.getInstance();
        date.setTime(dateBegin);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        dateBegin = date.getTime();
        Date dateEnd=DateUtil.getMaxDateByMonth(new Date());             
        Calendar ca=Calendar.getInstance(); 
        int workDaysCount=0;
        while(dateBegin.compareTo(dateEnd)<0){
            ca.setTime(dateBegin);    
            ca.add(ca.DATE,1);//把dateBegin加上1天然后重新赋值给date1   
            dateBegin=ca.getTime();
            if(DateUtil.isWorkingDays(dateBegin)){   
                workDaysCount++;    
            }         
    }
          return workDaysCount;    
        
    }
    
    /**
     * 判断某一天是不是周六日
     * @param d
     * @return 
     */
    public static boolean getDayIsSaturdayorSundy(Date d) {
        
       int weekday = DateUtil.getDayOfWeed(d)-1;
       if(weekday == 6 || weekday == 7){
           return true;
       }else{
           return false;
           }
        
    }
     /**
     * zhaohongkun  获得两个日期差
     * @param 
     * @return 
     */
     public static long getDateDiffence(Date startDate,Date endDate) throws ParseException {
         Calendar cal = Calendar.getInstance();  
        cal.setTime(startDate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(endDate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
        long betwen_years= between_days/365;
         return betwen_years;
    }
     /**
      * zhaohongkun 根据年和月获得指定月的第一天的日期
      * @param year
      * @param month
      * @return 
      */
      public static Date getFirstDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //设置日历中月份的第1天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND, 0);
        //格式化日期
        return cal.getTime() ;
}
     /*  
      *zhaohongkun 根据年和月获得指定月份的最后一天的日期
     * @param year 
     *            int 年份 
     * @param month 
     *            int 月份 
     *  
     * @return int 某年某月的最后一天 
     */  
    public static  Date getLastDayOfMonth(int year, int month) {  
        
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //设置日历中月份的第1天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND, 59);
        // 某年某月的最后一天  
        return   cal.getTime();
    }     
    /**
     * zhaohognkun 根据年和周获取每周的星期一
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFistWeekDateByYearAndWeek(int year, int week) {
         Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, year);  
        c.set(Calendar.WEEK_OF_YEAR, week);  
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//设置周一  
        c.setFirstDayOfWeek(Calendar.MONDAY);  
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();  
    }
    /**  
     * zhaohongkun 得到某年某周的最后一天  
     *  
     * @param year  
     * @param week  
     * @return  
     */  
    public static Date getLastWeekDateByYearAndWeek(int year, int week) {  
        Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, year);  
        c.set(Calendar.WEEK_OF_YEAR, week);  
        c.setFirstDayOfWeek(Calendar.MONDAY);  
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday   
        return c.getTime();  
    }  
}
