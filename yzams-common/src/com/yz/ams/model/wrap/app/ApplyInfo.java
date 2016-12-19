/*
 * ApplyInfo.java
 * 
 * Copyright(c) 2007-2016 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2016-01-25 11:22:25
 */
package com.yz.ams.model.wrap.app;

import com.yz.ams.consts.ApplyInfoTypeEnum;
import com.yz.ams.consts.AuditStateEnum;
import com.yz.ams.consts.SickCertificateStateEnum;
import java.io.Serializable;
import java.util.Date;

/**
 * 申请记录
 * @author litao
 */
public class ApplyInfo implements Serializable{
    private static final long serialVersionUID = -5080869866192788418L;
    /**
     * 申请人姓名
     */
    private String applierName;
    /**
     * 申请对应记录ID,根据InfoType不同查询不同的表
     */
    private String applyRecordId;
    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 申请类型
     */
    private ApplyInfoTypeEnum infoType;
    /**
     * 审核状态
     */
    private AuditStateEnum auditState;
    /**
     * 病假证明状态
     */
    private SickCertificateStateEnum sickCertState;

    public ApplyInfo(String applierName, String applyRecordId, Date applyTime,
            ApplyInfoTypeEnum infoType, AuditStateEnum auditState,
            SickCertificateStateEnum sickCertState) {
        this.applierName = applierName;
        this.applyRecordId = applyRecordId;
        this.applyTime = applyTime;
        this.infoType = infoType;
        this.auditState = auditState;
        this.sickCertState = sickCertState;
    }

    public ApplyInfo() {
    }

    
    public String getApplierName() {
        return applierName;
    }

    public void setApplierName(String applierName) {
        this.applierName = applierName;
    }

    public String getApplyRecordId() {
        return applyRecordId;
    }

    public void setApplyRecordId(String applyRecordId) {
        this.applyRecordId = applyRecordId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public ApplyInfoTypeEnum getInfoType() {
        return infoType;
    }

    public void setInfoType(ApplyInfoTypeEnum infoType) {
        this.infoType = infoType;
    }

    public AuditStateEnum getAuditState() {
        return auditState;
    }

    public void setAuditState(AuditStateEnum auditState) {
        this.auditState = auditState;
    }

    public SickCertificateStateEnum getSickCertState() {
        return sickCertState;
    }

    public void setSickCertState(SickCertificateStateEnum sickCertState) {
        this.sickCertState = sickCertState;
    }
    
}
