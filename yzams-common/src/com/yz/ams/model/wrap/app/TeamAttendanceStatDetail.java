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

/**
 * 团队出勤统计详细
 * @author luoyongchang
 */
public class TeamAttendanceStatDetail implements Serializable {
    private static final long serialVersionUID = -6055230509839883403L;
    /**
     * 团队名称
     */
    private String teamName;  
    /**
     * 团队人员总数
     */
    private String teamMemberCount;
    /**
     * 平均出勤天数
     */
    private double averageAttendanceDays;
    /**
     * 请假总天数
     */
    private double totalVocationDays;
    /**
     * 团队迟到总次数
     */
    private int totalDelayTimes;
    /**
     * 团队早退总次数
     */
    private int totalAbsentTimes;
    /**
     * 团队旷工总次数
     */
    private int totalLeaveEarlyTimes;

    public TeamAttendanceStatDetail(String teamName, String teamMemberCount,
            double averageAttendanceDays, double totalVocationDays,
            int totalDelayTimes, int totalAbsentTimes, int totalLeaveEarlyTimes) {
        this.teamName = teamName;
        this.teamMemberCount = teamMemberCount;
        this.averageAttendanceDays = averageAttendanceDays;
        this.totalVocationDays = totalVocationDays;
        this.totalDelayTimes = totalDelayTimes;
        this.totalAbsentTimes = totalAbsentTimes;
        this.totalLeaveEarlyTimes = totalLeaveEarlyTimes;
    }

    public TeamAttendanceStatDetail() {
    }
    

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamMemberCount() {
        return teamMemberCount;
    }

    public void setTeamMemberCount(String teamMemberCount) {
        this.teamMemberCount = teamMemberCount;
    }

    public double getAverageAttendanceDays() {
        return averageAttendanceDays;
    }

    public void setAverageAttendanceDays(double averageAttendanceDays) {
        this.averageAttendanceDays = averageAttendanceDays;
    }

    public double getTotalVocationDays() {
        return totalVocationDays;
    }

    public void setTotalVocationDays(double totalVocationDays) {
        this.totalVocationDays = totalVocationDays;
    }

    public int getTotalDelayTimes() {
        return totalDelayTimes;
    }

    public void setTotalDelayTimes(int totalDelayTimes) {
        this.totalDelayTimes = totalDelayTimes;
    }

    public int getTotalAbsentTimes() {
        return totalAbsentTimes;
    }

    public void setTotalAbsentTimes(int totalAbsentTimes) {
        this.totalAbsentTimes = totalAbsentTimes;
    }

    public int getTotalLeaveEarlyTimes() {
        return totalLeaveEarlyTimes;
    }

    public void setTotalLeaveEarlyTimes(int totalLeaveEarlyTimes) {
        this.totalLeaveEarlyTimes = totalLeaveEarlyTimes;
    }
    
}
