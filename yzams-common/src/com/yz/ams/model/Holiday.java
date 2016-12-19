/*
 * Holiday.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:20:15
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yz.ams.consts.HolidayTypeEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 节假日
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "holiday")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 8782212668302051650L;
    /**
     * 成员ID
     */
    @DatabaseField(id = true, columnName = "holiday_id", width = 64)
    private String holidayId;
    /**
     * 日期
     */
    @DatabaseField(columnName = "holiday_date" ,dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date holidayDate;
    /**
     * 类型
     */
    @DatabaseField(columnName = "holiday_type")
    private HolidayTypeEnum holidayType;
    /**
     * 名称
     */
    @DatabaseField(columnName = "holiday_name")
    private String holidayName;
    /**
     * 是否删除
     */
    @DatabaseField(columnName = "deleted")
    private boolean deleted;
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
    @DatabaseField(columnName = "modify_time" ,dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public String getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(String holidayId) {
        this.holidayId = holidayId;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public HolidayTypeEnum getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(HolidayTypeEnum holidayType) {
        this.holidayType = holidayType;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.holidayId);
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
        final Holiday other = (Holiday) obj;
        if (!Objects.equals(this.holidayId, other.holidayId)) {
            return false;
        }
        return true;
    }

}
