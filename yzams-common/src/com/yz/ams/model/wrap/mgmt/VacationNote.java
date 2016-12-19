/*
 * HolidayNote.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-07-05 16:45:11
 */
package com.yz.ams.model.wrap.mgmt;

import java.io.Serializable;

/**
 *
 * @author 曹慧英 <caohuiying@yzhtech.com>
 */
public class VacationNote implements Serializable{
    private static final long serialVersionUID = -7690560099513827144L;
    
    private String appliantName;
    private String vacationType;
    private String auditorName1;
    private String auditorName2;
    private double vacationDays;
    private String memo;
    private String startTime;
    private String endTime;
    private String createTime;

    public String getAppliantName() {
        return appliantName;
    }

    public void setAppliantName(String appliantName) {
        this.appliantName = appliantName;
    }

    public String getVacationType() {
        return vacationType;
    }

    public void setVacationType(String vacationType) {
        this.vacationType = vacationType;
    }

    public String getAuditorName1() {
        return auditorName1;
    }

    public void setAuditorName1(String auditorName1) {
        this.auditorName1 = auditorName1;
    }

    public String getAuditorName2() {
        return auditorName2;
    }

    public void setAuditorName2(String auditorName2) {
        this.auditorName2 = auditorName2;
    }

    public double getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(double vacationDays) {
        this.vacationDays = vacationDays;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
}
