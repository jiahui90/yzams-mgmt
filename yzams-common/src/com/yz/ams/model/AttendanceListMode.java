/*
 * AttendanceListMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-17 14:20:54
 */
package com.yz.ams.model;

import com.yz.ams.model.wrap.mgmt.DailyAttendance;
import java.io.Serializable;
import java.util.List;

/**
 *考勤子报表listMode类
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class AttendanceListMode implements Serializable{
    private static final long serialVersionUID = -2902230379314918761L;
    private List<DailyAttendance> dailyAttendancelis;
    
    /**
     * 一个人的时间段内的出勤
     * @return 
     */
    public List<DailyAttendance> getDailyAttendancelis() {
        return dailyAttendancelis;
    }

    public void setDailyAttendancelis(List<DailyAttendance> dailyAttendancelis) {
        this.dailyAttendancelis = dailyAttendancelis;
    }
    
}
