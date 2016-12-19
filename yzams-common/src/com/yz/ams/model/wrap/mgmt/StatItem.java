/*
 * StatItem.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-17 11:45:23
 */
package com.yz.ams.model.wrap.mgmt;

import java.io.Serializable;

/**
 *
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class StatItem implements Serializable {
    private static final long serialVersionUID = -3629042242580756323L;
    /**
     * 法定工作日
     */
    private double legalAttendanceDays;
    /**
     * 正常出勤天数
     */
    private double normalAttendanceDays;
    /**
     * 年假天数
     */
    private double annualLeaveDays = 0.0;
    /**
     * 事假天数
     */
     private double personalDays;
    /**
     * 病假天数
     */
    private double sickDays;
    /**
     * 其它假天数
     */
    private double otherDays;
    /**
     * 出差天数
     */
    private double businessDays;
    /**
     * 调休天数
     */
    private double restDays;
    /**
     * 旷工天数
     */
      private double absentDays;
    /**
     * 轻微迟到次数
     */
    private int lightLateTime ;
    
    /**
     * 处罚轻微迟到次数
     */
    private int punishmentLightLateTime ;
    /**
     * 迟到次数
     */
    private int lateTime;
    /**
     * 严重迟到次数
     */
    private int seriousLateTime; 
    /**
     * 早退次数
     */
    private int earlyTime;

    public double getLegalAttendanceDays() {
        return legalAttendanceDays;
    }

    public void setLegalAttendanceDays(double legalAttendanceDays) {
        this.legalAttendanceDays = legalAttendanceDays;
    }

    public double getNormalAttendanceDays() {
        return normalAttendanceDays;
    }

    public void setNormalAttendanceDays(double normalAttendanceDays) {
        this.normalAttendanceDays = normalAttendanceDays;
    }

    public double getAnnualLeaveDays() {
        return annualLeaveDays;
    }

    public void setAnnualLeaveDays(double annualLeaveDays) {
        this.annualLeaveDays = annualLeaveDays;
    }

    public double getPersonalDays() {
        return personalDays;
    }

    public void setPersonalDays(double personalDays) {
        this.personalDays = personalDays;
    }

    public double getSickDays() {
        return sickDays;
    }

    public void setSickDays(double sickDays) {
        this.sickDays = sickDays;
    }

    public int getPunishmentLightLateTime() {
        return punishmentLightLateTime;
    }

    public void setPunishmentLightLateTime(int punishmentLightLateTime) {
        this.punishmentLightLateTime = punishmentLightLateTime;
    }

    public double getOtherDays() {
        return otherDays;
    }

    public void setOtherDays(double otherDays) {
        this.otherDays = otherDays;
    }

    public double getBusinessDays() {
        return businessDays;
    }

    public void setBusinessDays(double businessDays) {
        this.businessDays = businessDays;
    }

    public double getRestDays() {
        return restDays;
    }

    public void setRestDays(double restDays) {
        this.restDays = restDays;
    }

    public double getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(double absentDays) {
        this.absentDays = absentDays;
    }

    public int getLightLateTime() {
        return lightLateTime;
    }

    public void setLightLateTime(int lightLateTime) {
        this.lightLateTime = lightLateTime;
    }

    public int getLateTime() {
        return lateTime;
    }

    public void setLateTime(int lateTime) {
        this.lateTime = lateTime;
    }

    public int getSeriousLateTime() {
        return seriousLateTime;
    }

    public void setSeriousLateTime(int seriousLateTime) {
        this.seriousLateTime = seriousLateTime;
    }

    public int getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(int earlyTime) {
        this.earlyTime = earlyTime;
    }
    
}
