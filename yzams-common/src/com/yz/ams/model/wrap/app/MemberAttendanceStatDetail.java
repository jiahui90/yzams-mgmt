/*
 * MemberAttendanceStatDetail.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:09:12
 */
package com.yz.ams.model.wrap.app;

import java.io.Serializable;

/**
 * 组员出勤统计详细
 * @author luoyongchang
 */
public class MemberAttendanceStatDetail implements Serializable {
    private static final long serialVersionUID = -8207752524092450247L;
    /**
     * 组员工姓名
     */
    private String memberName;
     /**
     * 组员工出勤天数
     */
    private double attendanceDays;
     /**
     * 组员工请假天数
     */
    private double vacationDays;
     /**
     * 组员工迟到次数
     */
    private int delayTimes;
     /**
     * 组员工旷工次数
     */
    private int absentTimes;
     /**
     * 组员工早退次数
     */
    private int leaveEarlyTimes;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public double getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(double attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public double getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(double vacationDays) {
        this.vacationDays = vacationDays;
    }

    public int getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(int delayTimes) {
        this.delayTimes = delayTimes;
    }

    public int getAbsentTimes() {
        return absentTimes;
    }

    public void setAbsentTimes(int absentTimes) {
        this.absentTimes = absentTimes;
    }

    public int getLeaveEarlyTimes() {
        return leaveEarlyTimes;
    }

    public void setLeaveEarlyTimes(int leaveEarlyTimes) {
        this.leaveEarlyTimes = leaveEarlyTimes;
    }

    public MemberAttendanceStatDetail(String memberName, double attendanceDays,
            double vacationDays, int delayTimes, int absentTimes,
            int leaveEarlyTimes) {
        this.memberName = memberName;
        this.attendanceDays = attendanceDays;
        this.vacationDays = vacationDays;
        this.delayTimes = delayTimes;
        this.absentTimes = absentTimes;
        this.leaveEarlyTimes = leaveEarlyTimes;
    }
    
}
