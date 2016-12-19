/*
 * Rest.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:18:42
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yz.ams.consts.AuditStateEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 调休管理
 *
 * @author Your Name <Song Haixiang >
 */
@DatabaseTable(tableName = "rest")
public class Rest implements Serializable {
    private static final long serialVersionUID = -5973060545657301036L;
    /**
     * ID
     */
    @DatabaseField(id = true, columnName = "rest_id", width = 64)
    private String restId;
    /**
     * 申请人ID
     */
    @DatabaseField(columnName = "user_id", width = 64)
    private String userId;
    /**
     * 申请人名称
     */
    @DatabaseField(columnName = "user_name", width = 64)
    private String userName;
    /**
     * 开始时间
     */
    @DatabaseField(columnName = "start_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    
    /**
     * 开始时间
     */
    @DatabaseField(columnName = "end_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    
    /**
     * 是否上午
     */
    @DatabaseField(columnName = "morning")
    private boolean morning;
    /**
     * 天数
     */
    @DatabaseField(columnName = "days", columnDefinition = "DECIMAL(10,1)")
    private double days;
    /**
     * 人员列表，逗号分隔
     */
    @DatabaseField(columnName = "staff_ids", width = 1024)
    private String staffIds;
    /**
     * 人员名称列表，空格分隔
     */
    private String staffNames;
    /**
     * 审核状态
     */
    @DatabaseField(columnName = "audit_state", width = 16)
    private AuditStateEnum auditState;
    /**
     * 事由
     */
    @DatabaseField(columnName = "memo", width = 64)
    private String memo;
    
      /**
     * 备注
     */
    @DatabaseField(columnName = "remark", width = 64)
    private String remark;

    /**
     * 审核人员ID
     */
    @DatabaseField(columnName = "auditor_id", width = 64)
    private String auditorId;
    /**
     * 审核人员姓名
     */
    @DatabaseField(columnName = "auditor_name", width = 64)
    private String auditorName;
    /**
     * 审核时间
     */
    @DatabaseField(columnName = "audit_time", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
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

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStaffNames() {
        return staffNames;
    }

    public void setStaffNames(String staffNames) {
        this.staffNames = staffNames;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public String getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(String staffIds) {
        this.staffIds = staffIds;
    }

    public AuditStateEnum getAuditState() {
        return auditState;
    }

    public void setAuditState(AuditStateEnum auditState) {
        this.auditState = auditState;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.restId);
        return hash;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        final Rest other = (Rest) obj;
        if (!Objects.equals(this.restId, other.restId)) {
            return false;
        }
        return true;
    }

}
