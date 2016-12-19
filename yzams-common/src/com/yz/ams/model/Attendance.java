/*
 * Attendance.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-15 17:26:34
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yz.ams.consts.AttendanceOutTypeEnum;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.DelayTypeEnum;
import java.io.Serializable;
import java.util.Date;

/**
 * 考勤记录
 *
 * @author song hai xiang
 */
@DatabaseTable(tableName = "attendance")
public class Attendance implements Serializable {

    private static final long serialVersionUID = -7423070893673435336L;
    /**
     * 记录UUID
     */
    @DatabaseField(id = true, columnName = "attendance_id", width = 64)
    private String attendanceId;
    /**
     * 记录日期
     */
    @DatabaseField(columnName = "attendance_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date attendanceDate;
    /**
     * 用户ID
     */
    @DatabaseField(columnName = "user_id", width = 64)
    private String userId;
    /**
     * 用户姓名
     */
    @DatabaseField(columnName = "user_name", width = 64)
    private String userName;
    /**
     * 迟到类型
     */
    @DatabaseField(columnName = "delay_type", width = 16, dataType
            = DataType.ENUM_STRING)
    private DelayTypeEnum delayType;

    /**
     * 是否早退
     */
    @DatabaseField(columnName = "leave_early")
    private boolean leaveEarly;
    /**
     * 外出类型
     */
    @DatabaseField(columnName = "out_type", width = 16)
    private AttendanceOutTypeEnum outType;
    /**
     * 旷工天数
     */
    @DatabaseField(columnName = "absent_days")
    private double absentDays;
    /**
     * 是否删除
     */
    @DatabaseField(columnName = "deleted")
    private boolean deleted;
    /**
     * 创建者ID
     */
    @DatabaseField(columnName = "creator_id", width = 64)
    private String creatorId;
    /**
     * 创建者姓名
     */
    @DatabaseField(columnName = "creator_name", width = 64)
    private String creatorName;
    /**
     * 创建时间
     */
    @DatabaseField(columnName = "create_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
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
    @DatabaseField(columnName = "modify_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DelayTypeEnum getDelayType() {
        return delayType;
    }

    public void setDelayType(DelayTypeEnum delayType) {
        this.delayType = delayType;
    }

    public AttendanceOutTypeEnum getOutType() {
        return outType;
    }

    public void setOutType(AttendanceOutTypeEnum outType) {
        this.outType = outType;
    }

    public double getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(double absentDays) {
        this.absentDays = absentDays;
    }

    public boolean isLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(boolean leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        hash
                = 67 * hash +
                (this.attendanceId != null ? this.attendanceId.hashCode() : 0);
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
        final Attendance other = (Attendance) obj;
        if ((this.attendanceId == null) ? (other.attendanceId != null)
                : !this.attendanceId.equals(other.attendanceId)) {
            return false;
        }
        return true;
    }

    

    public void setAuditState(AuditStateEnum auditStateEnum) {
    }

}
