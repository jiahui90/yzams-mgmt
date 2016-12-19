/*
 * DateTool.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-03-31 11:07:41
 */
package com.yz.ams.server.util;

import com.yz.ams.consts.HolidayTypeEnum;
import com.yz.ams.model.Holiday;
import com.yz.ams.rpc.mgmt.AttendanceMgmtService;
import com.yz.ams.server.dao.HolidayDAO;
import com.yz.ams.server.rpcimpl.app.AttendanceServiceImpl;
import com.yz.ams.util.DateUtil;
import static com.yz.ams.util.DateUtil.getMonthByDate;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.kohsuke.rngom.digested.Main;

/**
 *
 * @author fred
 */
public final class DateTool {
    private DateTool(){}
    
    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();
        return today;
    }
    
    /**
     * 获取两个日期相差的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDayDiffByDates(Date d1, Date d2) {
        int day = 0;
        long l1 = d1.getTime();
        long l2 = d2.getTime();
        long diff = l1 - l2;
        day = (int) diff / 1000 / 60 / 60 /24;
        return day;
    }
     public static Date getFirstDateOfThisYear(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_YEAR, 1);//设置为1号,当前日期既为本年的第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    
     
     //本月开始时间
       public static Date getFirstDateOfThisMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
      
    //本月结束时间eee
     public static Date getLastDateOfThisMonth(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.getTime().getMonth() + 1 );
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.SECOND, -1);
        return c.getTime();
    }
     
      
     //上个月开始时间eee
       public static Date getLastMonthDateStart(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.getTime().getMonth()-1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
       }
       
     public static Date getLastDateOfThisYear(){
        Calendar  cal = Calendar.getInstance();
//        c.set(Calendar.YEAR, c.YEAR );
//        c.set(Calendar.DAY_OF_YEAR, 1);       
//        c.add(Calendar.DAY_OF_YEAR, -1);
//        c.set(Calendar.HOUR_OF_DAY, 23);
//        c.set(Calendar.MINUTE, 59);
//        c.set(Calendar.SECOND, 59);
        
        
         cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_YEAR, 1);//设置为1号,当前日期既为本年的第一天
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
//            Date yearStart = cal.getTime();
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.SECOND, -1);
            Date yearEnd = cal.getTime();
        return yearEnd;
    }
      
    //上个月结束时间
     public static Date getLastMonthDateEnd(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.MONTH+1);
         c.set(Calendar.DAY_OF_MONTH, 1);       
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
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
    
    public static void main(String[] args) {
        System.out.println(getYearByDate(new Date()));
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
}
