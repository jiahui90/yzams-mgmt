/*
 * AttendanceStat.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-15 18:17:02
 */
package com.yz.ams.model.wrap.app;

import com.j256.ormlite.dao.GenericRawResults;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 出勤统计
 *
 * @author luoyongchang
 */
public class AttendanceStat implements Serializable {

    private static final long serialVersionUID = 996781907414368650L;
    /**
     * 本周迟到次数
     */
    private int delayThisWeek;
    /**
     * 本周早退次数
     */
    private int leaveEarlyThisWeek;
    /**
     * 本周旷工次数
     */
    private int absentThisWeek;
    /**
     * 本月病假天数
     */
    private double sickVocationThisMonth;
    /**
     * 本月事假天数
     */
    private double personalVocationThisMonth;
    /**
     * 本月年假天数
     */
    private double paidVocationThisMonth;
    /**
     * 本月其他假天数
     */
    private double otherVocationThisMonth;
     /**
     * 今年病假天数
     */
    private double sickVocationThisYear;
    /**
     * 今年事假天数
     */
    private double personalVocationThisYear;
    /**
     * 今年年假天数
     */
    private double paidVocationThisYear;
    /**
     * 今年其他假天数
     */
    private double otherVocationThisYear;
    /**
     * 剩余年假天数
     */
    private double availablePaidVacationDays;

    public int getDelayThisWeek() {
        return delayThisWeek;
    }

    public void setDelayThisWeek(int delayThisWeek) {
        this.delayThisWeek = delayThisWeek;
    }

    public int getLeaveEarlyThisWeek() {
        return leaveEarlyThisWeek;
    }

    public void setLeaveEarlyThisWeek(int leaveEarlyThisWeek) {
        this.leaveEarlyThisWeek = leaveEarlyThisWeek;
    }

    public int getAbsentThisWeek() {
        return absentThisWeek;
    }

    public void setAbsentThisWeek(int absentThisWeek) {
        this.absentThisWeek = absentThisWeek;
    }

    public double getSickVocationThisMonth() {
        return sickVocationThisMonth;
    }

    public void setSickVocationThisMonth(double sickVocationThisMonth) {
        this.sickVocationThisMonth = sickVocationThisMonth;
    }

    public double getPersonalVocationThisMonth() {
        return personalVocationThisMonth;
    }

    public void setPersonalVocationThisMonth(double personalVocationThisMonth) {
        this.personalVocationThisMonth = personalVocationThisMonth;
    }

    public double getPaidVocationThisMonth() {
        return paidVocationThisMonth;
    }

    public void setPaidVocationThisMonth(double paidVocationThisMonth) {
        this.paidVocationThisMonth = paidVocationThisMonth;
    }

    public double getOtherVocationThisMonth() {
        return otherVocationThisMonth;
    }

    public void setOtherVocationThisMonth(double otherVocationThisMonth) {
        this.otherVocationThisMonth = otherVocationThisMonth;
    }

    public double getSickVocationThisYear() {
        return sickVocationThisYear;
    }

    public void setSickVocationThisYear(double sickVocationThisYear) {
        this.sickVocationThisYear = sickVocationThisYear;
    }

    public double getPersonalVocationThisYear() {
        return personalVocationThisYear;
    }

    public void setPersonalVocationThisYear(double personalVocationThisYear) {
        this.personalVocationThisYear = personalVocationThisYear;
    }

    public double getPaidVocationThisYear() {
        return paidVocationThisYear;
    }

    public void setPaidVocationThisYear(double paidVocationThisYear) {
        this.paidVocationThisYear = paidVocationThisYear;
    }

    public double getOtherVocationThisYear() {
        return otherVocationThisYear;
    }

    public void setOtherVocationThisYear(double otherVocationThisYear) {
        this.otherVocationThisYear = otherVocationThisYear;
    }

    public double getAvailablePaidVacationDays() {
        return availablePaidVacationDays;
    }

    public void setAvailablePaidVacationDays(double availablePaidVacationDays) {
        this.availablePaidVacationDays = availablePaidVacationDays;
    }

}
