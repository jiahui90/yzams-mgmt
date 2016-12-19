/*
 * ReportAttendancesModel.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-15 14:35:40
 */
package com.yz.ams.model.wrap.mgmt;

import com.yz.ams.model.DateMode;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 * 考勤报表包装类
 */
public class ReportAttendancesModel implements Serializable{

    
    private static final long serialVersionUID = -5460899074035069999L;
    /**
     * caohuiying
     * 包装类，存储考勤报表
     */
    private String userName;
    private List<DailyAttendance> attendance;
    private List<DateMode> dateList;

    public List<DateMode> getDateList() {
        return dateList;
    }

    public void setDateList(List<DateMode> dateList) {
        this.dateList = dateList;
    }
    
    //private Map<User,Map<Date,MorningNoon>> reports;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<DailyAttendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<DailyAttendance> attendance) {
        this.attendance = attendance;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.userName);
        hash = 23 * hash + Objects.hashCode(this.attendance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportAttendancesModel other = (ReportAttendancesModel) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.attendance, other.attendance)) {
            return false;
        }
        return true;
    }

    
    
}
