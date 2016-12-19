/*
 * reportMode.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-14 11:59:27
 */
package com.yz.ams.model;

import com.yz.ams.model.Team;
import com.yz.ams.model.wrap.mgmt.ReportAttendancesModel;
import java.io.Serializable;
import java.util.List;

/**
 *考勤报表listMode类
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */

public class reportListMode implements Serializable{
    private static final long serialVersionUID = -2902230379314918761L;
    /**
     * 个人出勤list
     * @return 
     */
    private List<ReportAttendancesModel> attendancelis;
    /**
     * 报表标题，如"2016年6月份考勤报表"
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public List<ReportAttendancesModel> getAttendancelis() {
        return attendancelis;
    }

    public void setAttendancelis(List<ReportAttendancesModel> attendancelis) {
        this.attendancelis = attendancelis;
    }

    
    
}
