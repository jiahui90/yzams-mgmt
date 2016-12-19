/*
 * TeamAttendanceStat.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:06:15
 */
package com.yz.ams.model.wrap.app;

import java.io.Serializable;
import java.util.List;

/**
 * 团队出勤统计
 * @author luoyongchang
 */
public class TeamAttendanceStat implements Serializable {
    private static final long serialVersionUID = -6055230509839883403L;
    
    private double legalAttendanceDays; //法定出勤天数
    
    private List<TeamAttendanceStatDetail> teamStatDetails; //团队出勤详细

    
    
    public TeamAttendanceStat(double legalAttendanceDays,
            List<TeamAttendanceStatDetail> teamStatDetails) {
        this.legalAttendanceDays = legalAttendanceDays;
        this.teamStatDetails = teamStatDetails;
    }

    public TeamAttendanceStat() {
    }

    
    public double getLegalAttendanceDays() {
        return legalAttendanceDays;
    }

    public void setLegalAttendanceDays(double legalAttendanceDays) {
        this.legalAttendanceDays = legalAttendanceDays;
    }

    public List<TeamAttendanceStatDetail> getTeamStatDetails() {
        return teamStatDetails;
    }

    public void setTeamStatDetails(
            List<TeamAttendanceStatDetail> teamStatDetails) {
        this.teamStatDetails = teamStatDetails;
    }
    
    
}
