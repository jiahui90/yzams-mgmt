/*
 * PaidVacation.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:21:04
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Date;

/**
 * 年假管理
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "paid_vacation")
public class PaidVacation implements Serializable {

    private static final long serialVersionUID = 3580274163468159556L;
    /**
     * 纪录ID
     */
    @DatabaseField(id = true, columnName = "pv_id", width = 64)
    private String pvId;
    /**
     * 年度
     */
    @DatabaseField(columnName = "pv_year")
    private int pvYear;
    /**
     * 用户ID
     */
    @DatabaseField(columnName = "user_id", width = 64)
    private String userId;
    /**
     * 法定年假数
     */
    @DatabaseField(columnName = "official_days", columnDefinition = "DECIMAL(10,1)")
    private Double officialDays;
    /**
     * 内部年假数
     */
    @DatabaseField(columnName = "inner_days", columnDefinition = "DECIMAL(10,1)")
    private Double innerDays;
    /**
     * 修改者ID
     */
    @DatabaseField(columnName = "modifier_id", width = 64)
    private String modifierId;
    /**
     * 修改者姓名
     */
    @DatabaseField(columnName = "modifier_name", width = 64)
    private String modifierName;
    /**
     * 修改时间
     */
    @DatabaseField(columnName = "modify_time")
    private Date modifyTime;

    /**
     * 上年度剩余年假天数
     */
    @DatabaseField(columnName = "last_year_days")
    private double lastYearDays;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastYearDays(int lastYearDays) {
        this.lastYearDays = lastYearDays;
    }
    
    public String getPvId() {
        return pvId;
    }

    public void setPvId(String pvId) {
        this.pvId = pvId;
    }

    public int getPvYear() {
        return pvYear;
    }

    public void setPvYear(int pvYear) {
        this.pvYear = pvYear;
    }

    public String getUsreId() {
        return userId;
    }

    public void setUsreId(String usreId) {
        this.userId = usreId;
    }

    public Double getOfficialDays() {
        return officialDays;
    }

    public void setOfficialDays(Double officialDays) {
        this.officialDays = officialDays;
    }

    public Double getInnerDays() {
        return innerDays;
    }

    public void setInnerDays(Double innerDays) {
        this.innerDays = innerDays;
    }


    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public double getLastYearDays() {
        return lastYearDays;
    }

    public void setLastYearDays(double lastYearDays) {
        this.lastYearDays = lastYearDays;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        // hash = 19 * hash + this.pvId;
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
        final PaidVacation other = (PaidVacation) obj;
        if (this.pvId != other.pvId) {
            return false;
        }
        return true;
    }

}
