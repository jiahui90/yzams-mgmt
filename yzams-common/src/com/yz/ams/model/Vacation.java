/*
 * Vacation.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-18 10:14:56
 */
package com.yz.ams.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.SickCertificateStateEnum;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 请假表
 *
 * @author Your Name <>
 */
@DatabaseTable(tableName = "vacation")
public class Vacation implements Serializable {

    private static final long serialVersionUID = -2883129306823703120L;
    /**
     * 记录UUID
     */
    @DatabaseField(id = true, columnName = "vacation_id", width = 64)
    private String vacationId;
    /**
     * 请假日期
     */
    @DatabaseField(columnName = "vacation_date", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date vacationDate;
    /**
     * 是否上午
     */
    @DatabaseField(columnName = "morning")
    private boolean morning;
    /**
     * 请假人ID
     */
    @DatabaseField(columnName = "applicant_id", width = 64)
    private String applicantId;
    /**
     * 请假人姓名
     */
    @DatabaseField(columnName = "applicant_name", width = 64)
    private String applicantName;
    /**
     * 请假天数
     */
    @DatabaseField(columnName = "total_days", columnDefinition = "DECIMAL(10,1)")
    private Double totalDays;
    /**
     * 请假事由
     */
    @DatabaseField(columnName = "memo", width = 64)
    private String memo;
    /**
     * 审核状态
     */
    @DatabaseField(columnName = "audit_state", width = 16, dataType = DataType.ENUM_STRING)
    private AuditStateEnum auditState;
    /**
     * 是否有病假
     */
    @DatabaseField(columnName = "has_sick_type")
    private boolean hasSickType;
    /**
     * 病假审核状态
     */
    @DatabaseField(columnName = "certificate_state", width = 16, dataType = DataType.ENUM_STRING)
    private SickCertificateStateEnum certificateState;
    /**
     * 病假图片ID
     */
    @DatabaseField(columnName = "certificate_pic_id", width = 64)
    private String certificatePicId;
    /**
     * 病假图片上传时间
     */
    @DatabaseField(columnName = "certificate_upload_time")
    private Date certificateUploadTime;
    /**
     * 一审人员ID
     */
    @DatabaseField(columnName = "auditor_id1", width = 64)
    private String auditorId1;
    /**
     * 一审人员姓名
     */
    @DatabaseField(columnName = "auditor_name1", width = 64)
    private String auditorName1;
    /**
     * 一审时间
     */
    @DatabaseField(columnName = "audit_time1")
    private Date auditTime1;
    /**
     * 二审人员ID
     */
    @DatabaseField(columnName = "auditor_id2", width = 64)
    private String AuditorId2;
    /**
     * 二审人员姓名
     */
    @DatabaseField(columnName = "auditor_name2", width = 64)
    private String AuditorName2;
    /**
     * 二审时间
     */
    @DatabaseField(columnName = "audit_time2")
    private Date auditTime2;
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
    @DatabaseField(columnName = "create_time")
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
    @DatabaseField(columnName = "modify_time")
    private Date modifyTime;

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    public Date getVacationDate() {
        return vacationDate;
    }

    public void setVacationDate(Date vacationDate) {
        this.vacationDate = vacationDate;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Double totalDays) {
        this.totalDays = totalDays;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public AuditStateEnum getAuditState() {
        return auditState;
    }

    public void setAuditState(AuditStateEnum auditState) {
        this.auditState = auditState;
    }

    public SickCertificateStateEnum getCertificateState() {
        return certificateState;
    }

    public void setCertificateState(SickCertificateStateEnum certificateState) {
        this.certificateState = certificateState;
    }

    public String getCertificatePicId() {
        return certificatePicId;
    }

    public void setCertificatePicId(String certificatePicId) {
        this.certificatePicId = certificatePicId;
    }

    public Date getCertificateUploadTime() {
        return certificateUploadTime;
    }

    public void setCertificateUploadTime(Date certificateUploadTime) {
        this.certificateUploadTime = certificateUploadTime;
    }

    public String getAuditorId1() {
        return auditorId1;
    }

    public void setAuditorId1(String auditorId1) {
        this.auditorId1 = auditorId1;
    }

    public String getAuditorName1() {
        return auditorName1;
    }

    public void setAuditorName1(String auditorName1) {
        this.auditorName1 = auditorName1;
    }

    public Date getAuditTime1() {
        return auditTime1;
    }

    public void setAuditTime1(Date auditTime1) {
        this.auditTime1 = auditTime1;
    }

    public String getAuditorId2() {
        return AuditorId2;
    }

    public void setAuditorId2(String AuditorId2) {
        this.AuditorId2 = AuditorId2;
    }

    public String getAuditorName2() {
        return AuditorName2;
    }

    public void setAuditorName2(String AuditorName2) {
        this.AuditorName2 = AuditorName2;
    }

    public Date getAuditTime2() {
        return auditTime2;
    }

    public void setAuditTime2(Date auditTime2) {
        this.auditTime2 = auditTime2;
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

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isHasSickType() {
        return hasSickType;
    }

    public void setHasSickType(boolean hasSickType) {
        this.hasSickType = hasSickType;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.vacationId);
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
        final Vacation other = (Vacation) obj;
        if (!Objects.equals(this.vacationId, other.vacationId)) {
            return false;
        }
        return true;
    }

}
