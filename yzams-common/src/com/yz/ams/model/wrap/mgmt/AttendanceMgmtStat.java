 /*
 * AttendanceMgmtStat.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 12:34:57
 */
package com.yz.ams.model.wrap.mgmt;

import com.j256.ormlite.field.DatabaseField;
import java.io.Serializable;
import java.util.Objects;

/**
 *考勤管理统计
 * @author Zhang Chun Nan
 */

public class AttendanceMgmtStat implements Serializable {
    private static final long serialVersionUID = -5460899074035069990L;
    
    /**
     * 员工姓名
     */
    private String userName;
    
    /**
     * 员工工号
     */
    @DatabaseField(columnName = "job_number", width = 64)
    private String jobNumber;
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
    private double annualLeaveDays;
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
    private double paidLegaDays;
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

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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

    public double getPaidLegaDays() {
        return paidLegaDays;
    }

    public void setPaidLegaDays(double paidLegaDays) {
        this.paidLegaDays = paidLegaDays;
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

    public int getPunishmentLightLateTime() {
        return punishmentLightLateTime;
    }

    public void setPunishmentLightLateTime(int punishmentLightLateTime) {
        this.punishmentLightLateTime = punishmentLightLateTime;
    }

    public int getSeriousLateTime() {
        return seriousLateTime;
    }

    public void setSeriousLateTime(int seriousLateTime) {
        this.seriousLateTime = seriousLateTime;
    }

    public int getLateTime() {
        return lateTime;
    }

    public void setLateTime(int lateTime) {
        this.lateTime = lateTime;
    }

    public int getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(int earlyTime) {
        this.earlyTime = earlyTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.jobNumber);
        return hash;
    }

    public double getBusinessDays() {
        return businessDays;
    }

    public void setBusinessDays(double businessDays) {
        this.businessDays = businessDays;
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
        final AttendanceMgmtStat other = (AttendanceMgmtStat) obj;
        if (!Objects.equals(this.jobNumber, other.jobNumber)) {
            return false;
        }
        return true;
    }


}
