/*
 * VacationDetail.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:15:29
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yz.ams.consts.VacationTypeEnum;
import java.io.Serializable;
import java.util.Date;


/**
 * 请假详情表
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "vacation_detail")

public class VacationDetail implements Serializable {
    private static final long serialVersionUID = -6566446131230993861L;
    /**
     * 记录UUID
     */
    @DatabaseField(id = true, columnName = "detail_id", width = 64)
    private String detailId;
    /**
     * 请假记录ID
     */
    @DatabaseField(columnName = "vacation_id", width = 64)
    private String vacationId;
    /**
     * 类型
     */
    @DatabaseField(columnName = "vacation_type", width = 16)
    private VacationTypeEnum vacationType;
    /**
     * 请假开始日期
     */
    @DatabaseField(columnName = "start_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /**
     * 请假结束日期
     */
    @DatabaseField(columnName = "end_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /**
     * 天数
     */
    @DatabaseField(columnName = "vacation_days", columnDefinition = "DECIMAL(10,1)")
    private Double vacationDays;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public VacationTypeEnum getVacationType() {
        return vacationType;
    }

    public void setVacationType(VacationTypeEnum vacationType) {
        this.vacationType = vacationType;
    }

    public Double getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(Double vacationDays) {
        this.vacationDays = vacationDays;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash
                = 17 * hash +
                (this.detailId != null ? this.detailId.hashCode() : 0);
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
        final VacationDetail other = (VacationDetail) obj;
        if ((this.detailId == null) ? (other.detailId != null)
                : !this.detailId.equals(other.detailId)) {
            return false;
        }
        return true;
    }

}
