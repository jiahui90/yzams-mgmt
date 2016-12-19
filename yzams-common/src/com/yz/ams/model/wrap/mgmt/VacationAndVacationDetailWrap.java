/*
 * VacationAndVacationDetailWrap.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-06-15 18:04:41
 */
package com.yz.ams.model.wrap.mgmt;

import com.yz.ams.consts.VacationTypeEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author 赵洪坤 <zhaohongkun@yzhtech.com>
 */
public class VacationAndVacationDetailWrap implements Serializable {
    
    private static final long serialVersionUID = -7690560099513827144L;
    private String appliantId;
    private boolean morning;
    private VacationTypeEnum vacationType;
    private float days;
    private Date startTime;
    private Date endTime;

    public String getAppliantId() {
        return appliantId;
    }

    public void setAppliantId(String appliantId) {
        this.appliantId = appliantId;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public VacationTypeEnum getVacationType() {
        return vacationType;
    }

    public void setVacationType(VacationTypeEnum vacationType) {
        this.vacationType = vacationType;
    }

    public float getDays() {
        return days;
    }

    public void setDays(float days) {
        this.days = days;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.appliantId);
        hash = 37 * hash + Objects.hashCode(this.startTime);
        hash = 37 * hash + Objects.hashCode(this.endTime);
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
        final VacationAndVacationDetailWrap other = (VacationAndVacationDetailWrap) obj;
        if (!Objects.equals(this.appliantId, other.appliantId)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }
    
}